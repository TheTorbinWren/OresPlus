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
		RecipeItemStack grinder = new RecipeItemStack(Blocks.grinder);
		RecipeItemStack furnace = new RecipeItemStack(net.minecraft.init.Blocks.furnace);
		RecipeItemStack flint = new RecipeItemStack(net.minecraft.init.Items.flint);
		RecipeItemStack coal = new RecipeItemStack(net.minecraft.init.Items.coal, 0);
		RecipeItemStack charcoal = new RecipeItemStack(net.minecraft.init.Items.coal, 1);
		RecipeItemStack cracker = new RecipeItemStack(Blocks.cracker);
		RecipeItemStack glass = new RecipeItemStack(net.minecraft.init.Blocks.glass);
		RecipeItemStack gunpowder = new RecipeItemStack(net.minecraft.init.Items.gunpowder);
		RecipeItemStack oreCoal = new RecipeItemStack(net.minecraft.init.Blocks.coal_ore);
		RecipeItemStack oreLapis = new RecipeItemStack(net.minecraft.init.Blocks.lapis_ore);
		RecipeItemStack oreNikolite = new RecipeItemStack(DustOres.Nikolite.ore);
		RecipeItemStack oreRedstone = new RecipeItemStack(net.minecraft.init.Blocks.redstone_ore);
		RecipeItemStack oreUranium = new RecipeItemStack(GeneralOres.Uranium.ore);
		RecipeItemStack dustCharcoal = new RecipeItemStack(OreItems.dustCharcoal.item);
		RecipeItemStack dustCoal = new RecipeItemStack(OreItems.dustCoal.item);
		RecipeItemStack gemUranium = new RecipeItemStack(OreItems.gemUranium.item);
		RecipeItemStack crushedUranium = new RecipeItemStack(OreItems.crushedUranium.item);
		RecipeItemStack tank = new RecipeItemStack(Helpers.BuildCraft.getBlock("tankBlock"));
		
		// cracker recipe
		if (tank.getSource() != null) {
			addShapedRecipe(cracker.getSource(), "t", "F", 't', tank.getSource(), 'F', furnace.getSource());
		}
		else
			addShapedRecipe(cracker.getSource(), "ggg", "gFg", "ggg", 'g', glass.getSource(), 'F', furnace.getSource());
		
		// grinder recipe
		addShapedRecipe(grinder.getSource(), "fff", "fFf", "fff", 'f', flint.getSource(), 'F', furnace.getSource());
		
		//gunpowder recipes
		addShapedRecipe(gunpowder.getSource(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.getSource(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.getSource(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.getSource(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		
		// misc grinder recipes
		new GrinderRecipe(charcoal.getSource(), dustCharcoal.getSource());
		new GrinderRecipe(coal.getSource(), dustCoal.getSource());
		new GrinderRecipe(gemUranium.getSource(), crushedUranium.getSource());
		Helpers.IC2.registerRecipe("Macerator", gemUranium.getSource(), crushedUranium.getSource());
		
		// alloy recipes
		addShapelessRecipe(new ItemStack(Ores.getItem("dustBrass"), 4), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(new ItemStack(Ores.getItem("dustBronze"), 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(new ItemStack(Ores.getItem("dustElectrum"), 2), "dustGold", "dustSilver");
		
		// misc nether ore smelting recipes
		addSmelting(new ItemStack(Ores.getBlock("oreNetherCoal"), 1), oreCoal.getSource(2), 0.0F);
		addSmelting(new ItemStack(Ores.getBlock("oreNetherLapis"), 1), oreLapis.getSource(2), 0.0F);
		addSmelting(new ItemStack(Ores.getBlock("oreNetherUranium"), 1), oreUranium.getSource(2), 0.0F);
		
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

	@Deprecated
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
