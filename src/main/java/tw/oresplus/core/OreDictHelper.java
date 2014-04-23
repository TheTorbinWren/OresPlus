package tw.oresplus.core;

import tw.oresplus.OresPlus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
	public static Object parseOre(Object source) {
		Object ret = source;
		if (source instanceof ItemStack) {
			int oreID = OreDictionary.getOreID((ItemStack)source);
			if (oreID != -1) {
				ret = OreDictionary.getOreName(oreID);
			}
		}
		return ret;
	}
	
	public static String getName(ItemStack item) {
		int id = OreDictionary.getOreID(item);
		String oreName = OreDictionary.getOreName(id);
		OresPlus.log.info("Parsing " + oreName);
		return oreName;
	}
}
