package tw.oresplus.items;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.core.OreLog;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.OreItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class Items {
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
			
			for (DustOres ore : DustOres.values()) {
				ore.registerItems();
			}
			
			for (OreItems item : OreItems.values()) {
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
}
