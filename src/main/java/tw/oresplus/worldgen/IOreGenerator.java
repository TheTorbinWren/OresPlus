package tw.oresplus.worldgen;

public interface IOreGenerator {
	public OreGenClass getDefaultConfig();
	
	public void registerGenerator();
	
	public WorldGenOre getOreGenerator();
}
