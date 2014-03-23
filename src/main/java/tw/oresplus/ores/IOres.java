package tw.oresplus.ores;

import tw.oresplus.core.OreClass;

public interface IOres {
	public String getOreName();
	
	public OreClass getDefaultConfig();
	
	public OreClass getDefaultConfigNether();
	
	public void registerBlocks();
	
	public void registerItems();
	
	public void registerRecipes();
}
