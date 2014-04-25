package tw.oresplus.core;

import tw.oresplus.OresPlus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
	public static String getName(ItemStack item) {
		int id = OreDictionary.getOreID(item);
		String oreName = OreDictionary.getOreName(id);
		return oreName;
	}
}
