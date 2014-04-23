package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.core.OreDictHelper;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.Items;
import tw.oresplus.items.OreItems;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.ores.MetallicOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {
	private static ICraftingHandler neiHandler;
	private static ICraftingHandler tmiHandler;
	
	public static void init() {
		Ores.grinderRecipes = new OreRecipeManager();
		
		neiHandler = new NeiHandler();
		tmiHandler = new TmiHandler();
	}
	
	public static void initRecipes() {
		OreItemStack grinder = new OreItemStack(Blocks.grinder);
		OreItemStack furnace = new OreItemStack(net.minecraft.init.Blocks.furnace);
		OreItemStack flint = new OreItemStack(net.minecraft.init.Items.flint);
		OreItemStack coal = new OreItemStack(net.minecraft.init.Items.coal, 0);
		OreItemStack charcoal = new OreItemStack(net.minecraft.init.Items.coal, 1);
		OreItemStack cracker = new OreItemStack(Blocks.cracker);
		OreItemStack glass = new OreItemStack(net.minecraft.init.Blocks.glass);
		OreItemStack gunpowder = new OreItemStack(net.minecraft.init.Items.gunpowder);
		OreItemStack oreCoal = new OreItemStack(net.minecraft.init.Blocks.coal_ore);
		OreItemStack oreLapis = new OreItemStack(net.minecraft.init.Blocks.lapis_ore);
		OreItemStack oreNikolite = new OreItemStack(DustOres.Nikolite.ore);
		OreItemStack oreRedstone = new OreItemStack(net.minecraft.init.Blocks.redstone_ore);
		OreItemStack tank = new OreItemStack(Helpers.BuildCraft.getBlock("tankBlock"));
		
		// cracker recipe
		if (tank.newStack() != null) {
			addShapedRecipe(cracker.newStack(), "t", "F", 't', tank.newStack(), 'F', furnace.newStack());
		}
		else
			addShapedRecipe(cracker.newStack(), "ggg", "gFg", "ggg", 'g', glass.newStack(), 'F', furnace.newStack());
		
		// grinder recipe
		addShapedRecipe(grinder.newStack(), "fff", "fFf", "fff", 'f', flint.newStack(), 'F', furnace.newStack());
		
		//gunpowder recipes
		addShapedRecipe(gunpowder.newStack(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		
		// misc grinder recipes
		addGrinderRecipe(charcoal.newStack(), OreItems.dustCharcoal.item.newStack());
		addGrinderRecipe(coal.newStack(), OreItems.dustCoal.item.newStack());
		addGrinderRecipe(OreItems.gemUranium.item.newStack(), OreItems.crushedUranium.item.newStack());
		Helpers.IC2.registerRecipe("Macerator", OreItems.gemUranium.item.newStack(), OreItems.crushedUranium.item.newStack());
		addGrinderRecipe(GeneralOres.Uranium.ore.newStack(), OreItems.crushedUranium.item.newStack(2));
		
		// alloy recipes
		addShapelessRecipe(new ItemStack(Ores.getItem("dustBrass"), 4), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(new ItemStack(Ores.getItem("dustBronze"), 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(new ItemStack(Ores.getItem("dustElectrum"), 2), "dustGold", "dustSilver");
		
		// misc nether ore smelting recipes
		addSmelting(GeneralOres.NetherCoal.ore.newStack(), oreCoal.newStack(2), 0.0F);
		addSmelting(GeneralOres.NetherLapis.ore.newStack(), oreLapis.newStack(2), 0.0F);
		addSmelting(GeneralOres.NetherUranium.ore.newStack(), GeneralOres.Uranium.ore.newStack(2), 0.0F);
		
		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerRecipes();
		}
		
		for (GemstoneOres ore : GemstoneOres.values()) {
			ore.registerRecipes();
		}
		
		for (DustOres ore : DustOres.values()) {
			ore.registerRecipes();
		}
	}

	public static void addShapelessRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, params));
	}

	public static void addSmelting(ItemStack input, ItemStack output, Float xp) {
		GameRegistry.addSmelting(input, output, xp);	
	}
	
	public static void addShapedRecipe (ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, params));
	}
	
	@Deprecated
	public static ItemStack getGrinderRecipeResult(Object input) {
		return Ores.grinderRecipes.getResult(input);
	}
	
	@Deprecated
	public static boolean isIC2MaceratorRecipe(String oreName) {
		if (oreName.equals("Bronze") || oreName.equals("Copper") || oreName.equals("Lead") || oreName.equals("Silver") || oreName.equals("Tin"))
			return true;
		else
			return false;
	}

	public static void addGrinderRecipe(Object input, ItemStack output) {
		if (!(input instanceof String) && !(input instanceof ItemStack))
			return;
		Ores.grinderRecipes.add(input, output);
		
	}

	public static void replaceRecipeResults() {
		// replace smelting results
		OresPlus.log.info("Replacing smelting results");
		Collection smeltingResultsList = FurnaceRecipes.smelting().getSmeltingList().values();
		for (Object result : smeltingResultsList.toArray()) {
			if (result instanceof ItemStack) {
				Item item = ((ItemStack)result).getItem();
				UniqueIdentifier itemUid = GameRegistry.findUniqueIdentifierFor(item);
				OresPlus.log.info("Recipe Result " + itemUid.modId + ":" + itemUid.name);
			}
		}
		//ItemStack test = smeltingResultsList.
	}
	
	public static void hideItem(ItemStack item) {
		neiHandler.hideItem(item);
		tmiHandler.hideItem(item);
	}
}
