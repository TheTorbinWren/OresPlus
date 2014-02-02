package tw.oresplus.init;

import tw.oresplus.enums.OreDrops;

public class OreConfig {
	public String name;
	public boolean enabled;
	public int harvestLevel;
	public OreDrops drops;
	
	public OreConfig(String oreName, boolean oreEnabled, 
			int hLevel, OreDrops oreDrops) {
		this.name = oreName;
		this.enabled = oreEnabled;
		this.harvestLevel = hLevel;
		this.drops = oreDrops;
	}

}
