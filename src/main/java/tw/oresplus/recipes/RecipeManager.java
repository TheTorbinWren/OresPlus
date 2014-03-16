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
		
		addOreRecipes("Adamantine", 0.0F);
		addOreRecipes("Brass", 0.0F, true);
		addOreRecipes("Bronze", 0.0F, true);
		addOreRecipes("Coldiron", 0.0F);
		addOreRecipes("Copper", 0.0F);
		addOreRecipes("Electrum", 0.0F, true);
		addOreRecipes("Lead", 0.0F);
		addOreRecipes("Mithral", 0.0F);
		addOreRecipes("Nickel", 0.0F);
		addOreRecipes("Platinum", 0.0F);
		addOreRecipes("Silver", 0.0F);
		addOreRecipes("Tin", 0.0F);
		addOreRecipes("Zinc", 0.0F);
		
		// new GrinderRecipe("oreBauxite", new ItemStack(Items.get("dustBauxite"), 2));
		// new GrinderRecipe("oreCassiterite", new ItemStack(Items.get("dustCassiterite"), 2));
		new GrinderRecipe(charcoal, new ItemStack(OresPlusAPI.getItem("dustCharcoal"), 1));
		new GrinderRecipe(coal, new ItemStack(OresPlusAPI.getItem("dustCoal"), 1));
		// new GrinderRecipe("oreGalena", new ItemStack(Items.get("dustGalena"), 2));
		new GrinderRecipe("oreGold", new ItemStack(OresPlusAPI.getItem("dustGold"), 2));
		new GrinderRecipe("oreIron", new ItemStack(OresPlusAPI.getItem("dustIron"), 2));
		// new GrinderRecipe("oreManganese", new ItemStack(Items.get("dustManganese"), 2));
		// new GrinderRecipe("oreSheldonite", new ItemStack(Items.get("dustSheldonite"), 2));
		// new GrinderRecipe("oreTetrahedrite", new ItemStack(Items.get("dustTetrahedrite"), 2));
		
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBrass"), 4), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustBronze"), 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem("dustElectrum"), 2), "dustGold", "dustSilver");
		
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustGold"), 1), new ItemStack(net.minecraft.init.Items.gold_ingot, 1), 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getItem("dustIron"), 1), new ItemStack(net.minecraft.init.Items.iron_ingot, 1), 0.0F);
		
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherCoal"), 1), oreCoal, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherCopper"), 1), oreCopper, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherDiamond"), 1), oreDiamond, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherEmerald"), 1), oreEmerald, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherGold"), 1), oreGold, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherIron"), 1), oreIron, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherLapis"), 1), oreLapis, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherLead"), 1), oreLead, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherNikolite"), 1), oreNikolite, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherRedstone"), 1), oreRedstone, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherSilver"), 1), oreSilver, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherTin"), 1), oreTin, 0.0F);
		addSmelting(new ItemStack(OresPlusAPI.getBlock("oreNetherUranium"), 1), oreUranium, 0.0F);
		
	}

	private static void addOreRecipes(String name, Float smeltXP, boolean isAlloy) {
		String oreName = "ore" + name;
		String dustName = "dust" + name;
		String ingotName = "ingot" + name;
		String nuggetName = "nugget" + name;
		String oreBlockName = "oreBlock" + name;
		String crushedOreName = "crushed" + name;

		ItemStack ingot = new ItemStack(OresPlusAPI.getItem(ingotName), 1);
		ItemStack block = new ItemStack(OresPlusAPI.getBlock(oreBlockName), 1);
		
		// add Grinder Recipe
		if (!isAlloy) {
			new GrinderRecipe(oreName, new ItemStack(OresPlusAPI.getItem(crushedOreName), 2));
			// add IC2 macerator recipe
			if (Helpers.IC2.isLoaded() && !isIC2MaceratorRecipe(name)) {
				Helpers.IC2.registerGrind(new ItemStack(OresPlusAPI.getBlock(oreName), 1), new ItemStack(OresPlusAPI.getItem(crushedOreName), 2));
			}
		}
		// add ingot Grinder recipe
		new GrinderRecipe(ingotName, new ItemStack(OresPlusAPI.getItem(dustName), 1));
		//add ingot macerator recipe
		if (Helpers.IC2.isLoaded() && !isIC2MaceratorRecipe(name))
			Helpers.IC2.registerGrind(new ItemStack(OresPlusAPI.getItem(ingotName), 1), new ItemStack(OresPlusAPI.getItem(dustName), 1));
		// add ingot -> nugget recipe
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem(nuggetName), 9), ingotName);
		// add nugget -> ingot recipe
		addShapedRecipe(new ItemStack(OresPlusAPI.getItem(ingotName), 1), "nnn", "nnn", "nnn", 'n', nuggetName);
		// add ingot -> oreBlock recipe
		addShapedRecipe(new ItemStack(OresPlusAPI.getBlock(oreBlockName), 1), "iii", "iii", "iii", 'i', ingotName);
		// add oreBlock -> ingot recipe
		addShapelessRecipe(new ItemStack(OresPlusAPI.getItem(ingotName), 9), block);
		// add crushed ore smelting recipe
		if (!isAlloy)
			addSmelting(new ItemStack(OresPlusAPI.getItem(crushedOreName), 1), ingot, smeltXP);
		// add dust smelting recipe
		addSmelting(new ItemStack(OresPlusAPI.getItem(dustName), 1), ingot, smeltXP);
		// add ore smelting recipe
		if (!isAlloy)
			addSmelting(new ItemStack(OresPlusAPI.getItem(oreName), 1), ingot, smeltXP);
	}
	
	private static void addOreRecipes(String oreName, Float smeltXP) {
		addOreRecipes(oreName, smeltXP, false);
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
	
	private static boolean isIC2MaceratorRecipe(String oreName) {
		if (oreName == "Bronze" || oreName == "Copper" || oreName == "Lead" || oreName == "Silver" || oreName == "Tin")
			return true;
		else
			return false;
	}

}
