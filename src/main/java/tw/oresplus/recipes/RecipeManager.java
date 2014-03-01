package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.core.OreDictHelper;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.enums.OreItems;
import tw.oresplus.items.Items;
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
		ItemStack ingotAdamantine = new ItemStack(OresPlusAPI.getItem("ingotAdamantine"), 1);
		ItemStack ingotBrass = new ItemStack(OresPlusAPI.getItem("ingotBrass"), 1);
		ItemStack ingotBronze = new ItemStack(OresPlusAPI.getItem("ingotBronze"), 1);
		ItemStack ingotColdiron = new ItemStack(OresPlusAPI.getItem("ingotColdiron"), 1);
		ItemStack ingotCopper = new ItemStack(OresPlusAPI.getItem("ingotCopper"), 1);
		ItemStack ingotElectrum = new ItemStack(OresPlusAPI.getItem("ingotElectrum"), 1);
		ItemStack ingotMithral = new ItemStack(OresPlusAPI.getItem("ingotMithral"), 1);
		ItemStack ingotSilver = new ItemStack(OresPlusAPI.getItem("ingotSilver"), 1);
		ItemStack ingotTin = new ItemStack(OresPlusAPI.getItem("ingotTin"), 1);
		ItemStack ingotZinc = new ItemStack(OresPlusAPI.getItem("ingotZinc"), 1);
		Block tankBlock = OresPlus.bcHelper.getBlock("tankBlock");
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
		
		new GrinderRecipe("oraAdamantine", new ItemStack(OresPlusAPI.getItem("dustAdamantine"), 2));
		// new GrinderRecipe("oreBauxite", new ItemStack(Items.get("dustBauxite"), 2));
		// new GrinderRecipe("oreCassiterite", new ItemStack(Items.get("dustCassiterite"), 2));
		new GrinderRecipe(charcoal, new ItemStack(OresPlusAPI.getItem("dustCharcoal"), 1));
		new GrinderRecipe(coal, new ItemStack(OresPlusAPI.getItem("dustCoal"), 1));
		new GrinderRecipe("oreColdiron", new ItemStack(OresPlusAPI.getItem("dustColdiron"), 2)); 
		new GrinderRecipe("oreCopper", new ItemStack(OresPlusAPI.getItem("dustCopper"), 2));
		// new GrinderRecipe("oreGalena", new ItemStack(Items.get("dustGalena"), 2));
		new GrinderRecipe("oreGold", new ItemStack(OresPlusAPI.getItem("dustGold"), 2));
		new GrinderRecipe("oreIron", new ItemStack(OresPlusAPI.getItem("dustIron"), 2));
		// new GrinderRecipe("oreLead", new ItemStack(Items.get("dustLead"), 2));
		// new GrinderRecipe("oreManganese", new ItemStack(Items.get("dustManganese"), 2));
		new GrinderRecipe("oreMithral", new ItemStack(OresPlusAPI.getItem("dustMithral"), 2));
		// new GrinderRecipe("oreNickel", new ItemStack(Items.get("dustNickel"), 2));
		// new GrinderRecipe("orePlatinum", new ItemStack(Items.get("dustPlatinum"), 2));
		// new GrinderRecipe("oreSheldonite", new ItemStack(Items.get("dustSheldonite"), 2));
		new GrinderRecipe("oreSilver", new ItemStack(OresPlusAPI.getItem("dustSilver"), 2));
		// new GrinderRecipe("oreTetrahedrite", new ItemStack(Items.get("dustTetrahedrite"), 2));
		new GrinderRecipe("oreTin", new ItemStack(OresPlusAPI.getItem("dustTin"), 2));
		new GrinderRecipe("oreZinc", new ItemStack(OresPlusAPI.getItem("dustZinc"), 2));
		
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBrass"), 4), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBronze"), 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustElectrum"), 2), "dustGold", "dustSilver");
		
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustAdamantine"), 1), ingotAdamantine, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustBrass"), 1), ingotBrass, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustBronze"), 1), ingotBronze, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustColdiron"), 1), ingotColdiron, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustCopper"), 1), ingotCopper, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustElectrum"), 1), ingotElectrum, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustGold"), 1), new ItemStack(net.minecraft.init.Items.gold_ingot, 1), 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustIron"), 1), new ItemStack(net.minecraft.init.Items.iron_ingot, 1), 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustMithral"), 1), ingotMithral, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustSilver"), 1), ingotSilver, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustTin"), 1), ingotTin, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustZinc"), 1), ingotZinc, 0.0F);
		
		addSmelting(new ItemStack(Blocks.getBlock("oreAdamantine"), 1), ingotAdamantine, 0.0F);
		addSmelting(new ItemStack(Blocks.getBlock("oreColdiron"), 1), ingotColdiron, 0.0F);
		addSmelting(new ItemStack(Blocks.getBlock("oreCopper"), 1), ingotCopper, 0.0F);
		addSmelting(new ItemStack(Blocks.getBlock("oreMithral"), 1), ingotMithral, 0.0F);
		addSmelting(new ItemStack(Blocks.getBlock("oreSilver"), 1), ingotSilver, 0.0F);
		addSmelting(new ItemStack(Blocks.getBlock("oreTin"), 1), ingotTin, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreZinc"), 1), ingotZinc, 0.0F);
		
	}

	private static void addShapelessRecipe(ItemStack output, Object... params) {
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

}
