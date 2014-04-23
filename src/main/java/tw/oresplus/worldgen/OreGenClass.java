package tw.oresplus.worldgen;


public class OreGenClass {
	public String name;
	public String oreName;
	public boolean enabled;
	public int dimension;
	public int numVeins;
	public int veinSize;
	public int minY;
	public int maxY;
	public boolean doRegen;
	public OreGenType genType;
	public int density;
	
	public OreGenClass(String name, String oreName, boolean en, int dim, int num, int size, 
			int min, int max, boolean regen, OreGenType oreGenType, int oreDensity) {
		this.name = name;
		this.oreName = oreName;
		this.enabled = en;
		this.dimension = dim;
		this.numVeins = num;
		this.veinSize = size;
		this.minY = min;
		this.maxY = max;
		this.doRegen = regen;
		this.genType = oreGenType;
		this.density = oreDensity;
	}

}
