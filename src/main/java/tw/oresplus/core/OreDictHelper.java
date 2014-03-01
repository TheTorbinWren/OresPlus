package tw.oresplus.core;

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
}
