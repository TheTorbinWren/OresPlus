package tw.oresplus.core.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import cpw.mods.fml.common.Loader;

public class ForestryHelper extends OresHelper {
	private Class genBeesClass;
	private Object genBeesClassObj;
	private Method genBeesMethod;
	
	public ForestryHelper() {
		super("Forestry");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Forestry not found, helper disabled");
			return;
		}
		
		try {
			this.genBeesClass = Class.forName("forestry.apiculture.worldgen.HiveDecorator");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Constructor c = null;
		try {
			c = this.genBeesClass.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (c != null) {
			c.setAccessible(true);
			try {
				this.genBeesClassObj = c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		if (this.genBeesClass != null && this.genBeesClassObj != null) {
			
			for (Method method : this.genBeesClass.getMethods()) {
				if (method.getName().equals("generate"))
					this.genBeesMethod = method;
			}
			
			/*
			try {
				this.genBeesMethod = this.genBeesClass.getDeclaredMethod("decorateHives");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			this.genBeesMethod.setAccessible(true);
			*/
			
		}
		OresPlus.log.info("Forestry found, helper Initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (!this.isLoaded())
			return;
		
		try {
			//this.genBeesMethod.invoke(this.genBeesClassObj, world, rand, chunkX * 16, chunkZ * 16);
			this.genBeesMethod.invoke(this.genBeesClassObj, new PopulateChunkEvent.Post(world.getChunkProvider(), world, rand, chunkX, chunkZ, false));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//OresPlus.log.info("Regenerated Beehives at chunk " + chunkX + ", " + chunkZ);
	}

	@Override
	public void registerRecipe(String recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) { }
}
