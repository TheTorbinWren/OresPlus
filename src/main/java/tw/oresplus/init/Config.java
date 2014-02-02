package tw.oresplus.init;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import tw.oresplus.OresPlus;
import tw.oresplus.core.OreLog;
import tw.oresplus.enums.OreDrops;

public class Config {
	private static final String CAT_ORES = "ores";
	private static final String CAT_ORE_GEN = "ore_generation";
	
	private static OreLog log = OresPlus.log;
	private static boolean configured;
	private static Configuration configFile;
	
	/*
	
	// Nether Ore Generation
	public static OreGenConfig genCinnabar = new OreGenConfig("oreCinnabar", true, -1, 4, 12, 64, 128, false);
	public static OreGenConfig genCinnabarSmall = new OreGenConfig("oreCinnabar", true, -1, 8, 8, 64, 128, false);
	public static OreGenConfig genCinnabarSmallest = new OreGenConfig("oreCinnabar", true, -1, 16, 4, 64, 128, false);
	public static OreGenConfig genCinnabarBig = new OreGenConfig("oreCinnabar", true, -1, 2, 24, 64, 128, false);
	public static OreGenConfig genCinnabarBiggest = new OreGenConfig("oreCinnabar", true, -1, 1, 32, 64, 128, false);
	public static OreGenConfig genNetherCoal = new OreGenConfig("oreNetherCoal", true, -1, 8, 16, 1, 126, false);
	public static OreGenConfig genNetherCopper = new OreGenConfig("oreNetherCopper", true, -1, 8, 8, 1, 126, false);
	public static OreGenConfig genNetherDiamond = new OreGenConfig("oreNetherDiamond", true, -1, 4, 3, 1, 126, false);
	public static boolean genNetherEmerald = true;
	public static boolean genNetherGold = true;
	public static boolean genNetherIron = true;
	public static boolean genNetherLapis = true;
	public static boolean genNetherLead = true;
	public static boolean genNetherNikolite = true;
	public static boolean genNetherRedstone = true;
	public static boolean genNetherSilver = true;
	public static boolean genNetherTin = true;
	public static boolean genNetherUranium = true;
	public static OreGenConfig genPyrite = new OreGenConfig("orePyrite", true, -1, 4, 12, 0, 64, false);
	public static OreGenConfig genPyriteSmall = new OreGenConfig("orePyrite", true, -1, 8, 8, 0, 64, false);
	public static OreGenConfig genPyriteSmallest = new OreGenConfig("orePyrite", true, -1, 16, 4, 0, 64, false);
	public static OreGenConfig genPyriteBig = new OreGenConfig("orePyrite", true, -1, 2, 24, 0, 64, false);
	public static OreGenConfig genPyriteBiggest = new OreGenConfig("orePyrite", true, -1, 1, 32, 0, 64, false);
	public static OreGenConfig genSphalerite = new OreGenConfig("oreSphalerite", true, -1, 4, 12, 32, 96, false);
	public static OreGenConfig genSphaleriteSmall = new OreGenConfig("oreSphalerite", true, -1, 8, 8, 32, 96, false);
	public static OreGenConfig genSphaleriteSmallest = new OreGenConfig("oreSphalerite", true, -1, 16, 4, 32, 96, false);
	public static OreGenConfig genSphaleriteBig = new OreGenConfig("oreSphalerite", true, -1, 2, 24, 32, 96, false);
	public static OreGenConfig genSphaleriteBiggest = new OreGenConfig("oreSphalerite", true, -1, 1, 32, 32, 96, false);
	
	// End Ore Generation
	public static boolean genOlivine = true;
	public static boolean genSheldonite = true;
	public static boolean genSodalite = true;
	public static boolean genTungstate = true;
	*/
	
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
		
		/*
		
		genCinnabar = getOreGen(genCinnabar);
		genCinnabarSmall = getOreGen(genCinnabarSmall);
		genCinnabarSmallest = getOreGen(genCinnabarSmallest);
		genCinnabarBig = getOreGen(genCinnabarBig);
		genCinnabarBiggest = getOreGen(genCinnabarBiggest);
		genNetherCoal = getOreGen(genNetherCoal);
		genNetherCopper = getOreGen(genNetherCopper);
		genNetherDiamond = getOreGen(genNetherDiamond);
		genNetherEmerald = getProp("genNetherEmerald", genNetherEmerald, "set to false to disable netheremerald generation");
		genNetherGold = getProp("genNetherGold", genNetherGold, "set to false to disable nethergold generation");
		genNetherIron = getProp("genNetherIron", genNetherIron, "set to false to disable netheriron generation");
		genNetherLapis = getProp("genNetherLapis", genNetherLapis, "det to false to disable netherlapis generation");
		genNetherLead = getProp("genNetherLead", genNetherLead, "set to false to disable netherlead generation");
		genNetherNikolite = getProp("genNetherNikolite", genNetherNikolite, "set to false to disable nethernikolite generation");
		genNetherRedstone = getProp("genNetherRedstone", genNetherRedstone, "set to false to disable netherredstone generation");
		genNetherSilver = getProp("genNetherSilver", genNetherSilver, "set to false to disable nethersilver generation");
		genNetherTin = getProp("genNetherTin", genNetherTin, "set to false to disable nethertin generation");
		genNetherUranium = getProp("genNetherUranium", genNetherUranium, "set to false to disable netheruranium generation");
		genPyrite = getOreGen(genPyrite);
		genPyriteSmall = getOreGen(genPyriteSmall);
		genPyriteSmallest = getOreGen(genPyriteSmallest);
		genPyriteBig = getOreGen(genPyriteBig);
		genPyriteBiggest = getOreGen(genPyriteBiggest);
		genSphalerite = getOreGen(genSphalerite);
		genSphaleriteSmall = getOreGen(genSphaleriteSmall);
		genSphaleriteSmallest = getOreGen(genSphaleriteSmallest);
		genSphaleriteBig = getOreGen(genSphaleriteBig);
		genSphaleriteBiggest = getOreGen(genSphaleriteBiggest);
		
		genOlivine = getProp("genOlivine", genOlivine, "set to false to disable olivine generation");
		genSheldonite = getProp("genSheldonite", genSheldonite, "set to false to disable sheldonite generation");
		genSodalite = getProp("genSodalite", genSodalite, "set to false to disable sodalite generation");
		genTungstate = getProp("genTungstate", genTungstate, "set to false to disable tungstate generation");
		*/
		
		OresPlus.regenKey = getProp("regenKey", OresPlus.regenKey, "change this to regenerate ores");
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
	
	public static OreGenConfig getOreGen(OreGenConfig oreConfig) {
		if (!oreConfig.equals(null)) {
			OresPlus.log.info("Reading ore gen info for " + oreConfig.name);
			String cfgLine = oreConfig.oreName + ","
				+ Boolean.toString(oreConfig.enabled) + ","
				+ oreConfig.dimension + ","
				+ oreConfig.numVeins + ","
				+ oreConfig.veinSize + ","
				+ oreConfig.minY + ","
				+ oreConfig.maxY + ","
				+ Boolean.toString(oreConfig.doRegen);
			Property prop = configFile.get(CAT_ORE_GEN, oreConfig.name, cfgLine);
			String cfg[] = prop.getString().split(",");
			if (cfg.length == 8) {
				oreConfig.oreName = cfg[0];
				oreConfig.enabled = Boolean.parseBoolean(cfg[1]);
				oreConfig.dimension = Integer.parseInt(cfg[2]);
				oreConfig.numVeins = Integer.parseInt(cfg[3]);
				oreConfig.veinSize = Integer.parseInt(cfg[4]);
				oreConfig.minY = Integer.parseInt(cfg[5]);
				oreConfig.maxY = Integer.parseInt(cfg[6]);
				oreConfig.doRegen = Boolean.parseBoolean(cfg[7]);
			}
			else {
				OresPlus.log.info("Could not read config for ore " + oreConfig.name + ". Resetting to default");
				prop.set(cfgLine);
			}
		}
		return oreConfig;
	}
	
	public static OreConfig getOre(OreConfig ore) {
		if (!ore.equals(null)) {
			String cfgLine = Boolean.toString(ore.enabled) + ","
				+ ore.harvestLevel + ","
				+ ore.drops.name();
			Property prop = configFile.get(CAT_ORES, ore.name, cfgLine);
			String cfg[] = prop.getString().split(",");
			if (cfg.length == 3) {
				ore.enabled = Boolean.parseBoolean(cfg[0]);
				ore.harvestLevel = Integer.parseInt(cfg[1]);
				ore.drops = OreDrops.valueOf(cfg[2]);
			}
			else {
				OresPlus.log.info("Could not read config for ore " + ore.name + ". Resetting to default");
				prop.set(cfgLine);
			}
		}
		return ore;
	}
	
}
