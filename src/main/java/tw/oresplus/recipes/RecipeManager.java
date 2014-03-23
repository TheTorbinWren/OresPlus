package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.core.OreDictHelper;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.Items;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.ores.OreItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {
	public static ArrayList<GrinderRecipe> grinderRecipes = new ArrayList();
	
	public static void initRecipes() {
		ItemStack grinder = new ItemStack(OresPlusAPI.getBlock("grinder"), 1);
		ItemStack furnace = new ItemStack(net.minecraft.init.Blocks.furnace, 1);
		ItemStack flint = new ItemStack(net.minecraft.init.Items.flint, 1);
		ItemStack coal = new ItemStack(net.minecraft.init.Items.coal, 1, 0);
		ItemStack charcoal = new ItemStack(net.minecraft.init.Items.coal, 1, 1);
		ItemStack cracker = new ItemStack(OresPlusAPI.getBlock("cracker"), 1);
		ItemStack glass = new ItemStack(net.minecraft.init.Blocks.glass, 1);
		ItemStack gunpowder = new ItemStack(net.minecraft.init.Items.gunpowder, 4);
		ItemStack oreCoal = new ItemStack(net.minecraft.init.Blocks.coal_ore, 2);
		ItemStack oreCopper = new ItemStack(OresPlusAPI.getBlock("oreCopper"), 2);
		ItemStack oreDiamond = new ItemStack(net.minecraft.init.Blocks.diamond_ore, 2);
		ItemStack oreEmerald = new ItemStack(net.minecraft.init.Blocks.emerald_ore, 2);
		ItemStack oreGold = new ItemStack(net.minecraft.init.Blocks.gold_ore, 2);
		ItemStack oreIron = new ItemStack(net.minecraft.init.Blocks.iron_ore, 2);
		ItemStack oreLapis = new ItemStack(net.minecraft.init.Blocks.lapis_ore, 2);
		ItemStack oreLead = new ItemStack(OresPlusAPI.getBlock("oreLead"), 2);
		ItemStack oreNikolite = new ItemStack(OresPlusAPI.getBlock("oreNikolite"), 2);
		ItemStack oreRedstone = new ItemStack(net.minecraft.init.Blocks.redstone_ore, 2);
		ItemStack oreSilver = new ItemStack(OresPlusAPI.getBlock("oreSilver"), 2);
		ItemStack oreTin = new ItemStack(OresPlusAPI.getBlock("oreTin"), 2);
		ItemStack oreUranium = new ItemStack(OresPlusAPI.getBlock("oreUranium"), 2);
		
		Block tankBlock = Helpers.BuildCraft.getBlock("tankBlock");
		if (tankBlock != null) {
			ItemStack tank = new ItemStack(tankBlock, 1);
			addShapedRecipe(cracker, "t", "F", 't', tank, 'F', furnace);
		}
		else
			addShapedRecipe(cracker, "ggg", "gFg", "ggg", 'g', glass, 'F', furnace);
		
		addShapedRecipe(grinder, "fff", "fFf", "fff", 'f', flint, 'F', furnace);
		addShapedRecipe(gunpowder, "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder, "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder, "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder, "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		
		// new GrinderRecipe("oreBauxite", new ItemStack(Items.get("dustBauxite"), 2));
		// new GrinderRecipe("oreCassiterite", new ItemStack(Items.get("dustCassiterite"), 2));
		new GrinderRecipe(charcoal, new ItemStack(OresPlusAPI.getItem("dustCharcoal"), 1));
		new GrinderRecipe(coal, new ItemStack(OresPlusAPI.getItem("dustCoal"), 1));
		new GrinderRecipe(new ItemStack(OresPlusAPI.getItem("gemUranium"), 1), new ItemStack(OresPlusAPI.getItem("crushedUranium"), 1));
		Helpers.IC2.registerRecipe("Macerator", new ItemStack(OresPlusAPI.getItem("gemUranium"), 1), new ItemStack(OresPlusAPI.getItem("crushedUranium"), 1));
		// new GrinderRecipe("oreGalena", new ItemStack(Items.get("dustGalena"), 2));
		// new GrinderRecipe("oreManganese", new ItemStack(Items.get("dustManganese"), 2));
		// new GrinderRecipe("oreSheldonite", new ItemStack(Items.get("dustSheldonite"), 2));
		// new GrinderRecipe("oreTetrahedrite", new ItemStack(Items.get("dustTetrahedrite"), 2));
		
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBrass"), 4), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBronze"), 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustElectrum"), 2), "dustGold", "dustSilver");
		
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherCoal"), 1), oreCoal, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherDiamond"), 1), oreDiamond, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherEmerald"), 1), oreEmerald, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherLapis"), 1), oreLapis, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherNikolite"), 1), oreNikolite, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherRedstone"), 1), oreRedstone, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherUranium"), 1), oreUranium, 0.0F);
		
		for (MetallicOres ore : MetallicOres.values()) {
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
	
	public static ItemStack getGrinderRecipeResult(Object source) {
		for (GrinderRecipe recipe : grinderRecipes) {
			if (recipe.hasSource(OreDictHelper.parseOre(source)))
				return recipe.getResult();
		}
		return null;
	}
	
	public static boolean isIC2MaceratorRecipe(String oreName) {
		if (oreName.equals("Bronze") || oreName.equals("Copper") || oreName.equals("Lead") || oreName.equals("Silver") || oreName.equals("Tin"))
			return true;
		else
			return false;
	}

	public static void addGrinderRecipe(Object input, ItemStack output) {
		if (!(input instanceof String) && !(input instanceof ItemStack))
			return;
		new GrinderRecipe(input, output);
		
	}

}
