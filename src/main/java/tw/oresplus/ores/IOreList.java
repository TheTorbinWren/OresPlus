package tw.oresplus.ores;

import java.util.Random;

public interface IOreList {
	public OreClass getDefaultConfig();
	
	public OreClass getDefaultConfigNether();
	
	public void registerBlocks();
	
	public void registerItems();
	
	public void registerRecipes();
	
	public void registerAspects();
	
	public int getTradeToAmount(Random random);
	
	public int getTradeFromAmount(Random random);
}
