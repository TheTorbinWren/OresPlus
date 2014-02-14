package tw.oresplus.init;

import tw.oresplus.enums.OreDrops;
import tw.oresplus.enums.OreSources;

public class OreConfig {
	public String name;
	public boolean enabled;
	public int harvestLevel;
	public OreDrops drops;
	public OreSources source;
	
	public OreConfig(String oreName, boolean oreEnabled, 
			int hLevel, OreDrops oreDrops, OreSources oreSource) {
		this.name = oreName;
		this.enabled = oreEnabled;
		this.harvestLevel = hLevel;
		this.drops = oreDrops;
		this.source = oreSource;
	}

}
