package tw.oresplus.init;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.core.OreLog;
import tw.oresplus.enums.OreItems;
import tw.oresplus.items.ItemCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class Items {
	private static boolean isInitialized = false;
	public static HashMap itemList = new HashMap(); 
	
	public static void init() {
		if (isInitialized)
		{
			OresPlus.log.info("Item initialization failed, already initialized");
		}
		else
		{
			OresPlus.log.info("Initializing Items");
			
			for (OreItems item : OreItems.values()) {
				new ItemCore(item.name()).setCreativeTab(CreativeTabs.tabMaterials);
			}
			
			isInitialized = true;
		}
	}
	
	public static Item get(String itemName) {
		return (Item)itemList.get(itemName);
	}
}
