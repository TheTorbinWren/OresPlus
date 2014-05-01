package tw.oresplus.ores;

import java.util.HashMap;

import tw.oresplus.api.IOreManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class OreManager implements IOreManager {
	private static HashMap<String, Block> oreList = new HashMap();
	private static HashMap<String, Item> oreItemList = new HashMap();
	
	public Block getOre(String oreName) {
		return this.oreList.get(oreName);
	}
	
	public boolean registerOre(String oreName, Block oreBlock) {
		if (this.oreList.containsKey(oreName)) {
			return false;
		}
		this.oreList.put(oreName, oreBlock);
		return true;
	}
	
	public boolean isOreRegistered(String oreName) {
		return this.oreList.containsKey(oreName);
	}

	@Override
	public boolean registerOreItem(String itemName, Item oreItem) {
		if (this.oreItemList.containsKey(itemName))
			return false;
		this.oreItemList.put(itemName, oreItem);
		return true;
	}

	@Override
	public boolean isItemRegistered(String itemName) {
		return this.oreItemList.containsKey(itemName);
	}

	@Override
	public Item getOreItem(String itemName) {
		return this.oreItemList.get(itemName);
	}
}
