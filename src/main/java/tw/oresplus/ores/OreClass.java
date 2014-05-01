package tw.oresplus.ores;


public class OreClass {
	public String name;
	public boolean enabled;
	public int harvestLevel;
	public OreDrops drops;
	public OreSources source;
	public int xpDropLow;
	public int xpDropHigh;
	
	public OreClass(String oreName, boolean oreEnabled, 
			int hLevel, int xpLow, int xpHigh, OreDrops oreDrops, OreSources oreSource) {
		this.name = oreName;
		this.enabled = oreEnabled;
		this.harvestLevel = hLevel;
		this.xpDropLow = xpLow;
		this.xpDropHigh = xpHigh;
		this.drops = oreDrops;
		this.source = oreSource;
	}

}
