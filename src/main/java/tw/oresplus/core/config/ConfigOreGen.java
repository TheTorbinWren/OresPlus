package tw.oresplus.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import tw.oresplus.worldgen.OreGenClass;
import tw.oresplus.worldgen.WorldGenOre;

public class ConfigOreGen extends ConfigCore{
	private static HashMap<Integer, ConfigOreGen> oreGenConfigs = new HashMap();
	
	public ConfigOreGen(int dimId) {
		String oreConfigName = "OreGenerators/DIM" + dimId + ".cfg";
		this.init(oreConfigName, new File(baseConfigDir, oreConfigName));
		config.addCustomCategoryComment(CAT_ORE_GEN, "Ore generator configuration = generatorEnabled,denisty%,regenerationEnabled,regenKey");
		this.configured = true;
	}
	
	public static OreGenClass getOreGeneratorConfig(OreGenClass ore, int dimId) {
		if (oreGenConfigs.get(dimId) == null) {
			oreGenConfigs.put(dimId, new ConfigOreGen(dimId));
		}
		ConfigOreGen config = oreGenConfigs.get(dimId);
		OreGenClass generator = config.getOreGen(ore);
		config.saveQuiet();
		return generator;
	}
	
	public static OreGenClass getNetherOreGeneratorConfig(OreGenClass ore) {
		return getOreGeneratorConfig(ore, -1);
	}
	
	public static OreGenClass getEndOreGeneratorConfig(OreGenClass ore) {
		return getOreGeneratorConfig(ore, 1);
	}

}