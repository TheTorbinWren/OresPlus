package tw.oresplus.core.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import buildcraft.api.core.BuildCraftAPI;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.Blocks;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class BCHelper extends OresHelper {
	private Class genOilClass;
	private Object genOilClassObj;
	private Method genOilMethod;
	private String[] subMods = { "BuildCraft|Builders", "BuildCraft|Energy", "BuildCraft|Factory", "BuildCraft|Silicon", "BuildCraft|Transport" };
	
	public BCHelper() {
		super("BuildCraft|Core");
	}
	
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("BuildCraft not found, helper disabled");
			return;
		}
		
		if (this.isEnergyLoaded()) {
			try {
				this.genOilClass = Class.forName("buildcraft.energy.worldgen.OilPopulate");
			} catch (ClassNotFoundException e) {
				OresPlus.log.info("Could not find OilPopulate class");
				e.printStackTrace();
			}
			
			Constructor c = null;
			try {
				c = this.genOilClass.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				OresPlus.log.info("Could not find constructor");
				e.printStackTrace();
			} catch (SecurityException e) {
				OresPlus.log.info("Access denied to contructor");
				e.printStackTrace();
			}
			if (c != null) {
				c.setAccessible(true);
				try {
					this.genOilClassObj = c.newInstance();
				} catch (InstantiationException e) {
					OresPlus.log.info("InstatianException");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					OresPlus.log.info("Illegal Access");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					OresPlus.log.info("Illrgal args");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					OresPlus.log.info("Target exception");
					e.printStackTrace();
				}
			}
			
			if (this.genOilClass != null && this.genOilClassObj != null)
				for (Method method : this.genOilClass.getMethods()) {
					if (method.getName().equals("generateOil")) 
						this.genOilMethod = method;
				
				}
		}	
		OresPlus.log.info("BuildCraft found, integration helper initialized");
	}
	
	private boolean isEnergyLoaded() {
		return Loader.isModLoaded("BuildCraft|Energy");
	}
	
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (!this.isEnergyLoaded() || this.genOilMethod == null)
			return;
		
		try {
			this.genOilMethod.invoke(this.genOilClassObj, world, rand, chunkX, chunkZ);
		} catch (IllegalAccessException e) {
			OresPlus.log.info("Call to OilPopulate.generate was denied");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			OresPlus.log.info("Call to OilPopulate.generate was failed, illegal arguments");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			OresPlus.log.info("OilPopulate.generate caused an exception");
			e.printStackTrace();
		}
		
	}

	@Override
	public void registerRecipe(String recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) { }
	
	@Override
	public Block getBlock(String blockName) {
		Block result = super.getBlock(blockName);
		if (result == null) {
			for (String subModName : subMods ) {
				Block subModResult = GameRegistry.findBlock(subModName, blockName);
				if (subModResult != null) {
					result = subModResult;
				}
			}
		}
		return result;
	}
}
