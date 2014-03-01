package tw.oresplus.core.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import buildcraft.api.core.BuildCraftAPI;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.blocks.Blocks;
import cpw.mods.fml.common.Loader;

public class BCHelper {
	public boolean isEnergyLoaded;
	
	private Class genOilClass;
	private Object genOilClassObj;
	private Method genOilMethod;
	
	public BCHelper() {
		this.isEnergyLoaded = false;
	}
	
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("BuildCraft not found, helper disabled");
			return;
		}
		this.isEnergyLoaded = Loader.isModLoaded("BuildCraft|Energy");
		
		if (this.isEnergyLoaded) {
			Block blockOil = this.getBlock("blockOil");
			if (blockOil != null)
				OresPlusAPI.registerBlock("blockOil", "BuildCraft", blockOil);
			
			try {
				this.genOilClass = Class.forName("buildcraft.energy.worldgen.OilPopulate");
			} catch (ClassNotFoundException e) {
				OresPlus.log.info("Could not find OilPopulate class");
				e.printStackTrace();
			}
			
			Constructor c = null;
			try {
				c = genOilClass.getDeclaredConstructor();
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
						genOilMethod = method;
				
				}
		}	
		OresPlus.log.info("BuildCraft found, helper Initialized");
	}
	
	public static boolean isLoaded() {
		return Loader.isModLoaded("BuildCraft|Core");
	}
	
	public void generateOil(World world, Random rand, int chunkX, int chunkZ) {
		if (!this.isEnergyLoaded || this.genOilMethod == null)
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
	
	public Block getBlock(String blockName) {
		for (Block bcBlock : BuildCraftAPI.softBlocks) {
			if (bcBlock.getUnlocalizedName().equals(blockName)) 
				return bcBlock;
		}
		return null;
	}
}
