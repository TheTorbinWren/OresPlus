package tw.oresplus.core.helpers;

import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.core.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;

public class IC2Helper extends OresHelper {
	private Class genRubTreeClass;
	private Object genRubTreeClassObj;
	private Method genRubTreeMethod;
	private boolean genRubberTree = false;
	
	public IC2Helper() {
		super("IC2");
	}
	
	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("IC2 not found, helper disabled");
			return;
		}
		if (this.getBlock("blockRubWood") != null) {
			this.genRubberTree  = true;
		}
		else
			OresPlus.log.info("Rubber Tree gen disabled");
		if (this.genRubberTree) {
			try {
				this.genRubTreeClass = Class.forName("ic2.core.block.WorldGenRubTree");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Constructor c = null;
			try {
				c = this.genRubTreeClass.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (c != null) {
				c.setAccessible(true);
				try {
					this.genRubTreeClassObj = c.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (this.genRubTreeClass != null && this.genRubTreeClassObj != null) {
				for (Method method : this.genRubTreeClass.getMethods()) {
					if (method.getName().equals("func_76484_a")) {
						this.genRubTreeMethod = method;
					}
				}
			}
		}
		OresPlus.log.info("IC2 found, helper Initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (!this.genRubberTree || this.genRubTreeMethod == null) {
			OresPlus.log.info("Rubber tree gen appears to be disabled.");
			return;
		}
		
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(chunkX * 16 + 16, chunkZ * 16 + 16);
		if (biome != null && biome.biomeName != null) {
			int numTrees = 0;
			if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)) {
				numTrees += rand.nextInt(10) + 5;
			}
			if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE)) {
				numTrees += rand.nextInt(5) + 1;
			}
			if (rand.nextInt(100) + 1 <= numTrees * 2) {
				try {
					this.genRubTreeMethod.invoke(this.genRubTreeClassObj, world, rand, chunkX * 16 + rand.nextInt(16), numTrees, chunkZ * 16 + rand.nextInt(16));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	private void registerGrind(ItemStack input, ItemStack... outputs) {
		if (!this.isLoaded())
			return;
		if (Recipes.macerator.getOutputFor(input, true) == null)
			Recipes.macerator.addRecipe(new RecipeInputItemStack(input), null, outputs);
	}

	private void registerWash(ItemStack input, NBTTagCompound metadata, ItemStack... outputs) {
		if (!this.isLoaded())
			return;
		if (Recipes.oreWashing.getOutputFor(input, true) == null) {
			Recipes.oreWashing.addRecipe(new RecipeInputItemStack(input), metadata, outputs);
		}
	}

	@Override
	public void registerRecipe(String recipeType, ItemStack input, 
			NBTTagCompound metadata, ItemStack... outputs) {
		if (recipeType.equals("Macerator")) {
			this.registerGrind(input, outputs);
		}
		else if (recipeType.equals("OreWasher")) {
			this.registerWash(input, metadata, outputs);
		}
	}
}
