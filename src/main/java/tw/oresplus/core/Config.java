package tw.oresplus.core;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import tw.oresplus.OresPlus;
import tw.oresplus.enums.OreDrops;
import tw.oresplus.enums.OreSources;

public class Config {
	private static final String CAT_ORES = "ores";
	private static final String CAT_ORE_GEN = "ore_generation";
	
	private static OreLog log = OresPlus.log;
	private static boolean configured;
	private static Configuration configFile;
	
	public static void init(FMLPreInitializationEvent event) {
		log.info("Initializing Configuration");
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configured = true;
	}
	
	public static void load() {
		if (!configured) {
			log.info("Error - configuration not initialized!");
			return;
		}
		log.info("Loading Configuration");
		configFile.load();
		
		configFile.addCustomCategoryComment(CAT_ORES, "Ore configuration = oreEnabled,oreSource");
		//configFile.addCustomCategoryComment(CAT_ORES, "oreEnabled = true or false   // enables or disables the ore");
		//configFile.addCustomCategoryComment(CAT_ORES, "oreSource = DEFAULT          // sets the ore source, DEFAULT is mod gen, used to generate ores from other mods");
		
		OresPlus.regenKeyOre = getProp("regenKey", OresPlus.regenKeyOre, "change this to regenerate ores");
		OresPlus.regenKeyOil = getProp("regenKeyOil", OresPlus.regenKeyOil, "change this to regenerate buildcraft oil wells");
		OresPlus.regenKeyRubberTree = getProp("regenKeyRubberTree", OresPlus.regenKeyRubberTree, "change this to regenerate IC2 rubber trees");
	}

	public static void save() {
		if (!configured) {
			log.info("Error - configuration not initialized!");
			return;		
		}
		log.info("Saving Configuration");
		if (configFile.hasChanged())
			configFile.save();
	}
	
	private static boolean getProp(String key, boolean defaultValue, String comment) {
		Property prop = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue);
		if (comment != "")
			prop.comment = comment;
		return prop.getBoolean(defaultValue);
	}
	
	private static boolean getProp(String key, boolean defaultValue) {
		return getProp(key, defaultValue, "");
	}
	
	private static String getProp(String key, String defaultValue, String comment) {
		Property prop = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue);
		if (comment != "")
			prop.comment = comment;
		return prop.getString();
	}
	
	private static String getProp(String key, String defaultValue) {
		return getProp(key, defaultValue, "");
	}
	
	public static String getOreGenCfgLine(OreGenClass oreConfig) {
		if (oreConfig == null)
			return null;
		return Boolean.toString(oreConfig.enabled) + ","
				+ oreConfig.density + ","
				+ Boolean.toString(oreConfig.doRegen);
	}
	
	public static OreGenClass getOreGen(OreGenClass oreConfig) {
		if (oreConfig != null) {
			Property prop = configFile.get(CAT_ORE_GEN, oreConfig.name, getOreGenCfgLine(oreConfig));
			String cfg[] = prop.getString().split(",");
			boolean configChanged = false;
			switch (cfg.length) {
			case 3:
				oreConfig.enabled = Boolean.parseBoolean(cfg[0]);
				oreConfig.density = Integer.parseInt(cfg[1]);
				oreConfig.doRegen = Boolean.parseBoolean(cfg[2]);
				break;
			case 8: // v0.1.1 - v0.1.3
				///oreConfig.oreName = cfg[0];
				oreConfig.enabled = Boolean.parseBoolean(cfg[1]);
				//oreConfig.dimension = Integer.parseInt(cfg[2]);
				//oreConfig.numVeins = Integer.parseInt(cfg[3]);
				//oreConfig.veinSize = Integer.parseInt(cfg[4]);
				//oreConfig.minY = Integer.parseInt(cfg[5]);
				//oreConfig.maxY = Integer.parseInt(cfg[6]);
				oreConfig.doRegen = Boolean.parseBoolean(cfg[7]);
				configChanged = true;
				break;
			default:
				OresPlus.log.info("Could not read config for ore " + oreConfig.name + ". Resetting to default");
				configChanged = true;
			}
			if (configChanged)
				prop.set(getOreGenCfgLine(oreConfig));
		}
		return oreConfig;
	}
	
	private static String getOreCfgLine(OreClass ore) {
		if (ore == null)
			return null;
		return Boolean.toString(ore.enabled) + ","
				+ ore.source.name();
	}
	
	public static OreClass getOre(OreClass ore) {
		if (ore != null) {
			Property prop = configFile.get(CAT_ORES, ore.name, getOreCfgLine(ore));
			String cfg[] = prop.getString().split(",");
			
			boolean configChanged = false;
			switch (cfg.length) {
			case 2:
				ore.enabled = Boolean.parseBoolean(cfg[0]);
				ore.source = OreSources.valueOf(cfg[1]);
				break;
			case 3: // v0.1.1 - v0.1.3
				ore.enabled = Boolean.parseBoolean(cfg[0]);
				// ore.harvestLevel = Integer.parseInt(cfg[1]);
				// ore.drops = OreDrops.valueOf(cfg[2]);
				configChanged = true;
				break;
			default:
				OresPlus.log.info("Could not read config for ore " + ore.name + ". Resetting to default");
				configChanged = true;
			}
			if (configChanged)
				prop.set(getOreCfgLine(ore));
		}
		return ore;
	}
	
}
