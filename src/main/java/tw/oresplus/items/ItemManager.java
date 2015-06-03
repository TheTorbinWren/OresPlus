package tw.oresplus.items;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.core.OreLog;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.ores.MineralOres;
import tw.oresplus.ores.DustyOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.OreItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ItemManager {
	private static boolean isInitialized = false;
	
	public static ItemArmor.ArmorMaterial armorAdamantine = EnumHelper.addArmorMaterial("ADAMANTINE", 35, new int[] { 3, 8, 6, 3 }, 8);
	public static ItemArmor.ArmorMaterial armorColdiron = EnumHelper.addArmorMaterial("COLDIRON", 15, new int[] { 2, 6, 5, 2 }, 18);
	public static ItemArmor.ArmorMaterial armorMithral = EnumHelper.addArmorMaterial("MITHRAL", 20, new int[] { 3, 8, 6, 3 }, 25);

	public static Item.ToolMaterial toolAdamantine = EnumHelper.addToolMaterial("ADAMANTINE", 3, 1250, 12.0F, 3.0F, 16);
	public static Item.ToolMaterial toolColdiron = EnumHelper.addToolMaterial("COLDIRON", 2, 250, 6.0F, 2.0F, 17);
	public static Item.ToolMaterial toolMithral = EnumHelper.addToolMaterial("MITHRAL", 2, 560, 8.0F, 3.0F, 20);
	
	public static OreItemStack armorAdamantineHelmet;
	public static OreItemStack armorAdamantineChestplate;
	public static OreItemStack armorAdamantineLeggings;
	public static OreItemStack armorAdamantineBoots;
	public static OreItemStack armorColdironHelmet;
	public static OreItemStack armorColdironChestplate;
	public static OreItemStack armorColdironLeggings;
	public static OreItemStack armorColdironBoots;
	public static OreItemStack armorMithralHelmet;
	public static OreItemStack armorMithralChestplate;
	public static OreItemStack armorMithralLeggings;
	public static OreItemStack armorMithralBoots;
	public static OreItemStack toolAdamantineAxe;
	public static OreItemStack toolAdamantineHoe;
	public static OreItemStack toolAdamantinePickaxe;
	public static OreItemStack toolAdamantineSpade;
	public static OreItemStack toolAdamantineSword;
	public static OreItemStack toolColdironAxe;
	public static OreItemStack toolColdironHoe;
	public static OreItemStack toolColdironPickaxe;
	public static OreItemStack toolColdironSpade;
	public static OreItemStack toolColdironSword;
	public static OreItemStack toolMithralAxe;
	public static OreItemStack toolMithralHoe;
	public static OreItemStack toolMithralPickaxe;
	public static OreItemStack toolMithralSpade;
	public static OreItemStack toolMithralSword;

	public static void init() {
		if (isInitialized)
		{
			OresPlus.log.info("Item initialization failed, already initialized");
		}
		else
		{
			OresPlus.log.info("Initializing Items");
			
			for (MetallicOres ore : MetallicOres.values()) {
				ore.registerItems();
			}
			
			for (GemstoneOres ore : GemstoneOres.values()) {
				ore.registerItems();
			}
			
			for (DustyOres ore : DustyOres.values()) {
				ore.registerItems();
			}
			
			for (MineralOres ore : MineralOres.values()) {
				ore.registerItems();
			}
			
			for (Items item : Items.values()) {
				item.registerItems();;
			}
			
		    armorAdamantineHelmet = new OreItemStack(new OreArmor("armorAdamantineHelmet", armorAdamantine, 2, 0));
		    armorAdamantineChestplate = new OreItemStack(new OreArmor("armorAdamantineChestplate", armorAdamantine, 2, 1));
		    armorAdamantineLeggings = new OreItemStack(new OreArmor("armorAdamantineLeggings", armorAdamantine, 2, 2));
		    armorAdamantineBoots = new OreItemStack(new OreArmor("armorAdamantineBoots", armorAdamantine, 2, 3));
		    armorColdironHelmet = new OreItemStack(new OreArmor("armorColdironHelmet", armorColdiron, 2, 0));
		    armorColdironChestplate = new OreItemStack(new OreArmor("armorColdironChestplate", armorColdiron, 2, 1));
		    armorColdironLeggings = new OreItemStack(new OreArmor("armorColdironLeggings", armorColdiron, 2, 2));
		    armorColdironBoots = new OreItemStack(new OreArmor("armorColdironBoots", armorColdiron, 2, 3));
		    armorMithralHelmet = new OreItemStack(new OreArmor("armorMithralHelmet", armorMithral, 2, 0));
		    armorMithralChestplate = new OreItemStack(new OreArmor("armorMithralChestplate", armorMithral, 2, 1));
		    armorMithralLeggings = new OreItemStack(new OreArmor("armorMithralLeggings", armorMithral, 2, 2));
		    armorMithralBoots = new OreItemStack(new OreArmor("armorMithralBoots", armorMithral, 2, 3));
		    toolAdamantineAxe = new OreItemStack(new OreAxe("toolAdamantineAxe", toolAdamantine));
		    toolAdamantineHoe = new OreItemStack(new OreHoe("toolAdamantineHoe", toolAdamantine));
		    toolAdamantinePickaxe = new OreItemStack(new OrePickaxe("toolAdamantinePickaxe", toolAdamantine));
		    toolAdamantineSpade = new OreItemStack(new OreSpade("toolAdamantineSpade", toolAdamantine));
		    toolAdamantineSword = new OreItemStack(new OreSword("toolAdamantineSword", toolAdamantine));
		    toolColdironAxe = new OreItemStack(new OreAxe("toolColdironAxe", toolColdiron));
		    toolColdironHoe = new OreItemStack(new OreHoe("toolColdironHoe", toolColdiron));
		    toolColdironPickaxe = new OreItemStack(new OrePickaxe("toolColdironPickaxe", toolColdiron));
		    toolColdironSpade = new OreItemStack(new OreSpade("toolColdironSpade", toolColdiron));
		    toolColdironSword = new OreItemStack(new OreSword("toolColdironSword", toolColdiron));
		    toolMithralAxe = new OreItemStack(new OreAxe("toolMithralAxe", toolMithral));
		    toolMithralHoe = new OreItemStack(new OreHoe("toolMithralHoe", toolMithral));
		    toolMithralPickaxe = new OreItemStack(new OrePickaxe("toolMithralPickaxe", toolMithral));
		    toolMithralSpade = new OreItemStack(new OreSpade("toolMithralSpade", toolMithral));
		    toolMithralSword = new OreItemStack(new OreSword("toolMithralSword", toolMithral));

			isInitialized = true;
		}
	}

	public static void registerAspects() {
		if (Helpers.ThaumCraft.isLoaded()) {
			ThaumcraftApi.registerObjectTag(armorAdamantineHelmet.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorAdamantineChestplate.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorAdamantineLeggings.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorAdamantineBoots.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorColdironHelmet.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorColdironChestplate.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorColdironLeggings.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorColdironBoots.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorMithralHelmet.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorMithralChestplate.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorMithralLeggings.source, new AspectList());
			ThaumcraftApi.registerObjectTag(armorMithralBoots.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolAdamantineAxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolAdamantineHoe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolAdamantinePickaxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolAdamantineSpade.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolAdamantineSword.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolColdironAxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolColdironHoe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolColdironPickaxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolColdironSpade.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolColdironSword.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolMithralAxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolMithralHoe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolMithralPickaxe.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolMithralSpade.source, new AspectList());
			ThaumcraftApi.registerObjectTag(toolMithralSword.source, new AspectList());
		}
	}
}
