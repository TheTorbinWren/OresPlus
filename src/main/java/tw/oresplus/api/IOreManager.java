package tw.oresplus.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IOreManager {
	public boolean registerOre(String oreName, Block oreBlock);
	
	public boolean isOreRegistered(String oreName);
	
	public Block getOre(String oreName);
	
	public boolean registerOreItem(String itemName, Item oreItem);
	
	public boolean isItemRegistered(String itemName);
	
	public Item getOreItem(String itemName);
}
