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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class Items {
	private static boolean isInitialized = false;
	
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
			
			isInitialized = true;
		}
	}
}
