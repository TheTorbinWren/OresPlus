package tw.oresplus.api;

import net.minecraft.block.Block;

public interface IOreManager {
	public boolean registerOre(String oreName, Block oreBlock);
	
	public boolean isOreRegistered(String oreName);
	
	public Block getOre(String oreName);
}
