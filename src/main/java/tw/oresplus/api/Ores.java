package tw.oresplus.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Ores {
	private static Ores instance = new Ores();
	
	private Ores () {}
	
	private class BlockInfo {
		public String ownerMod;
		public Block block;
		
		public BlockInfo(String owner, Block newBlock) {
			this.ownerMod = owner;
			this.block = newBlock;
		}

	}
	
	public static IOreManager manager;
	
	public static IOreRecipeList grinderRecipes;
	
	//private static Map<String, BlockInfo> blockList = new HashMap();
	private static HashMap<String, Item> itemList = new HashMap(); 

	// use manager.getOre
	@Deprecated
	public static Block getBlock(String blockName) {
		/*
		BlockInfo block = blockList.get(blockName);
		if (block != null)
			return block.block;
		else
			return null;
		*/
		return manager.getOre(blockName);
	}
	
	// use manager.isOreRegistered
	@Deprecated
	public static boolean isBlockRegistered(String blockName) {
		return manager.isOreRegistered(blockName);
		//return blockList.containsKey(blockName);
	}
	
	// use manager.registerOre
	@Deprecated
	private static void registerBlock(String blockName, BlockInfo blockInfo) {
		//blockList.put(blockName, blockInfo);
		manager.registerOre(blockName, blockInfo.block);
	}
	
	// use manager.registerOre
	@Deprecated
	public static void registerBlock(String blockName, String modID, Block block) {
		//registerBlock(blockName, instance.new BlockInfo(modID, block));
		manager.registerOre(blockName, block);
	}
	
	@Deprecated
	public static Item getItem(String itemName) {
		return itemList.get(itemName);
	}
	
	@Deprecated
	public static void registerItem(String itemName, Item item) {
		itemList.put(itemName, item);
	}
	
	@Deprecated
	public static boolean isItemRegistered(String itemName) {
		return itemList.containsKey(itemName);
	}
}
