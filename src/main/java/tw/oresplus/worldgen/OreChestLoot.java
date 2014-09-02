package tw.oresplus.worldgen;

import java.util.Random;

import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import tw.oresplus.items.ItemManager;
import tw.oresplus.items.Items;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.OreItemStack;

public class OreChestLoot {
	private static String[] randomChests = { ChestGenHooks.DUNGEON_CHEST, ChestGenHooks.MINESHAFT_CORRIDOR, ChestGenHooks.PYRAMID_DESERT_CHEST, ChestGenHooks.PYRAMID_JUNGLE_CHEST, ChestGenHooks.STRONGHOLD_CORRIDOR, ChestGenHooks.STRONGHOLD_CROSSING, ChestGenHooks.STRONGHOLD_LIBRARY };

	private static OreItemStack[] commonItems = { MetallicOres.Copper.ingot, MetallicOres.Lead.ingot, MetallicOres.Nickel.ingot, MetallicOres.Silver.ingot, MetallicOres.Tin.ingot, MetallicOres.Zinc.ingot };
	private static OreItemStack[] uncommonItems = { MetallicOres.Aluminium.ingot, MetallicOres.Brass.ingot, MetallicOres.Bronze.ingot, MetallicOres.Electrum.ingot, MetallicOres.Manganese.ingot, MetallicOres.Osmium.ingot, GemstoneOres.Amethyst.gem };
	private static OreItemStack[] rareItems = { MetallicOres.Ardite.ingot, MetallicOres.Cobalt.ingot, MetallicOres.Coldiron.ingot, MetallicOres.Platinum.ingot, GemstoneOres.GreenSapphire.gem, GemstoneOres.Ruby.gem, GemstoneOres.Sapphire.gem, GemstoneOres.Topaz.gem };
	private static OreItemStack[] ultraRareItems = { MetallicOres.Adamantine.ingot, MetallicOres.Mithral.ingot, Items.gemRedGarnet.item, Items.gemYellowGarnet.item };
	  
	private static OreItemStack[] commonSmithItems = {MetallicOres.Copper.ingot, MetallicOres.Lead.ingot, MetallicOres.Nickel.ingot, MetallicOres.Silver.ingot, MetallicOres.Tin.ingot, MetallicOres.Zinc.ingot };
	private static OreItemStack[] uncommonSmithItems = {
		MetallicOres.Aluminium.ingot, MetallicOres.Brass.ingot, MetallicOres.Electrum.ingot, MetallicOres.Manganese.ingot, MetallicOres.Osmium.ingot,
		ItemManager.toolAdamantineAxe, ItemManager.toolAdamantineHoe, ItemManager.toolAdamantinePickaxe, ItemManager.toolAdamantineSpade,
		ItemManager.toolColdironAxe, ItemManager.toolColdironHoe, ItemManager.toolColdironPickaxe, ItemManager.toolColdironSpade,
		ItemManager.toolMithralAxe, ItemManager.toolMithralHoe, ItemManager.toolMithralPickaxe, ItemManager.toolMithralSpade};
	private static OreItemStack[] rareSmithItems = {
		MetallicOres.Adamantine.ingot, MetallicOres.Ardite.ingot, MetallicOres.Cobalt.ingot, MetallicOres.Coldiron.ingot, MetallicOres.Mithral.ingot, MetallicOres.Platinum.ingot,
		ItemManager.toolAdamantineSword, ItemManager.toolColdironSword, ItemManager.toolMithralSword};
	private static OreItemStack[] ultraRareSmithItems = {
		ItemManager.armorAdamantineBoots, ItemManager.armorAdamantineChestplate, ItemManager.armorAdamantineHelmet, ItemManager.armorAdamantineLeggings,
		ItemManager.armorColdironBoots, ItemManager.armorColdironChestplate, ItemManager.armorColdironHelmet, ItemManager.armorColdironLeggings,
		ItemManager.armorMithralBoots, ItemManager.armorMithralChestplate, ItemManager.armorMithralHelmet, ItemManager.armorMithralLeggings};

	public static void registerChestLoot() {
	    Random rand = new Random();

	    for (String chest : randomChests) {
	    	for (OreItemStack item : commonItems)
	    		ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(2 + rand.nextInt(4)), 1, 3, 5));

	    	for (OreItemStack item : uncommonItems)
	    		ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(1 + rand.nextInt(4)), 1, 2, 4));

	    	for (OreItemStack item : rareItems)
	    		ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(), 1, 1, 2));

	    	for (OreItemStack item : ultraRareItems)
	    		ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(), 1, 1, 1));
	    }
	    for (OreItemStack item : commonSmithItems)
	    	ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(item.newStack(2 + rand.nextInt(4)), 1, 3, 5));
	      
	    for (OreItemStack item : uncommonSmithItems)
	    	ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(item.newStack(1 + rand.nextInt(4)), 1, 2, 4));
	      
	    for (OreItemStack item : rareSmithItems)
	    	ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(item.newStack(), 1, 1, 2));
	      
	    for (OreItemStack item : ultraRareSmithItems)
	    	ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(item.newStack(), 1, 1, 1));
	}
}
