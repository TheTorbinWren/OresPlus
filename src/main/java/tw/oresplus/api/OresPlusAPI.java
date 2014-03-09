package tw.oresplus.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class OresPlusAPI {
	private static OresPlusAPI instance = new OresPlusAPI();
	
	private OresPlusAPI () {}
	
	private class BlockInfo {
		public String ownerMod;
		public Block block;
		
		public BlockInfo(String owner, Block newBlock) {
			this.ownerMod = owner;
			this.block = newBlock;
		}

	}
	
	private static Map<String, BlockInfo> blockList = new HashMap();
	private static HashMap<String, Item> itemList = new HashMap(); 

	public static Block getBlock(String blockName) {
		BlockInfo block = blockList.get(blockName);
		if (block != null)
			return block.block;
		else
			return null;
	}
	
	public static boolean isBlockRegistered(String blockName) {
		return blockList.containsKey(blockName);
	}
	
	private static void registerBlock(String blockName, BlockInfo blockInfo) {
		blockList.put(blockName, blockInfo);
	}
	
	public static void registerBlock(String blockName, String modID, Block block) {
		registerBlock(blockName, instance.new BlockInfo(modID, block));
	}
	
	public static Item getItem(String itemName) {
		return itemList.get(itemName);
	}
	
	public static void registerItem(String itemName, Item item) {
		itemList.put(itemName, item);
	}
	
	public static boolean isItemRegistered(String itemName) {
		return itemList.containsKey(itemName);
	}
}
