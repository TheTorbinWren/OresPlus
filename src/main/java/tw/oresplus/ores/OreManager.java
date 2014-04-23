package tw.oresplus.ores;

import java.util.HashMap;

import tw.oresplus.api.IOreManager;
import net.minecraft.block.Block;

public class OreManager implements IOreManager {
	private static HashMap<String, Block> oreList = new HashMap();
	
	public Block getOre(String oreName) {
		return oreList.get(oreName);
	}
	
	public boolean registerOre(String oreName, Block oreBlock) {
		if (oreList.containsKey(oreName)) {
			return false;
		}
		oreList.put(oreName, oreBlock);
		return true;
	}
	
	public boolean isOreRegistered(String oreName) {
		return oreList.containsKey(oreName);
	}
}
