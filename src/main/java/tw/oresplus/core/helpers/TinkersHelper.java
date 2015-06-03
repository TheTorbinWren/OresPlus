package tw.oresplus.core.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tconstruct.library.crafting.FluidType;
import tw.oresplus.OresPlus;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TinkersHelper extends OresHelper {
	private Class smelteryClass;
	private Object smelteryClassObj;
	private Method dictionaryMeltingMethod;

	TinkersHelper() {
		super("TConstruct");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Tinkers Construct not found, integration helper disabled");
			return;
		}
		
		// setup smeltry ore dictionary method
		try {
			this.smelteryClass = Class.forName("tconstruct.library.crafting.Smeltery");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Constructor c = null;
		try {
			c = this.smelteryClass.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		if (c != null) {
			c.setAccessible(true);
			try {
				this.smelteryClassObj = c.newInstance();
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
		
		if (c != null && this.smelteryClassObj != null) {
			/*
			try {
				this.dictionaryMeltingMethod = this.smelteryClass.getDeclaredMethod("addDictionaryMelting", FluidType.class, ItemStack.class, int.class, int.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			*/
			for (Method method : this.smelteryClass.getMethods()) {
				if (method.getName().equals("addDictionaryMelting")) {
					this.dictionaryMeltingMethod = method;
				}
			}
		}
		
		OresPlus.log.info("Tinkers Construct found, integration helper initialized");
		
		// register fluid types for non tinkers native ores
		// Adamantine
		FluidType.registerFluidType("Adamantine", MetallicOres.Adamantine.moltenFluidBlock, 0, 1000, MetallicOres.Adamantine.moltenFluid, false);
		// Brass
		FluidType.registerFluidType("Brass", MetallicOres.Brass.moltenFluidBlock, 0, 450, MetallicOres.Brass.moltenFluid, false);
		// Cold Iron
		FluidType.registerFluidType("Coldiron", MetallicOres.Coldiron.moltenFluidBlock, 0, 750, MetallicOres.Coldiron.moltenFluid, false);
		// Manganese
		FluidType.registerFluidType("Manganese", MetallicOres.Manganese.moltenFluidBlock, 0, 600, MetallicOres.Manganese.moltenFluid, false);
		// Mithral
		FluidType.registerFluidType("Mithril", MetallicOres.Mithril.moltenFluidBlock, 0, 800, MetallicOres.Mithril.moltenFluid, false);
		// Osmium
		FluidType.registerFluidType("Osmium", MetallicOres.Osmium.moltenFluidBlock, 0, 1200, MetallicOres.Osmium.moltenFluid, false);
		// Yellorium
		FluidType.registerFluidType("Yellorium", MetallicOres.Yellorium.moltenFluidBlock, 0, 600, MetallicOres.Yellorium.moltenFluid, false);
		// Zinc		
		FluidType.registerFluidType("Zinc", MetallicOres.Zinc.moltenFluidBlock, 0, 400, MetallicOres.Zinc.moltenFluid, false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (!this.isLoaded()) {
			return;
		}
	}

	@Override
	public void postInit() {
		if (!this.isLoaded()) {
			return;
		}
		// register smeltery recipes of non tinker native items
		
		List<FluidType> exceptions = Arrays.asList(new FluidType[] { FluidType.getFluidType("Water"), FluidType.getFluidType("Stone"), FluidType.getFluidType("Ender"), FluidType.getFluidType("Glass"), FluidType.getFluidType("Slime"), FluidType.getFluidType("Obsidian") });
	    Iterator iter = FluidType.fluidTypes.entrySet().iterator();
	    while (iter.hasNext()) {
	    	Map.Entry pairs = (Map.Entry)iter.next();
		    FluidType ft = (FluidType)pairs.getValue();
		    if (!exceptions.contains(ft)) {
		        String fluidName = (String)pairs.getKey();	
		        // crushed ore
		        this.AddSmelteryDictionaryMelting("crushed" + fluidName, ft, -55, 144);
				// purified crushed ore
		        this.AddSmelteryDictionaryMelting("crushedPurified" + fluidName, ft, -65, 144);
				// tiny dusts
		        this.AddSmelteryDictionaryMelting("dustTiny" + fluidName, ft, -125, 16);
				// native clusters
		        this.AddSmelteryDictionaryMelting("cluster" + fluidName, ft, -25, 288);
		    }	
	    }
	}

	private void AddSmelteryDictionaryMelting(String oreName, FluidType type, int tempDiff, int amount) {
		OresPlus.log.info("Attempting to register smeltery melting for " + oreName);
		if (this.dictionaryMeltingMethod != null) {
			try {
				this.dictionaryMeltingMethod.invoke(this.smelteryClassObj, oreName, type, tempDiff, amount);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		else {
			OresPlus.log.info("ERROR: Could not access Tinkers Contruct to add melting");
		}
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) { }


	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input, NBTTagCompound metadata, Object output, Object secondaryOutput) { }

}
