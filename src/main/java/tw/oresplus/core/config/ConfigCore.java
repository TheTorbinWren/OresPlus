package tw.oresplus.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Property.Type;
import tw.oresplus.OresPlus;
import tw.oresplus.ores.OreClass;
import tw.oresplus.ores.OreDrops;
import tw.oresplus.ores.OreSources;
import tw.oresplus.worldgen.OreGenClass;

public abstract class ConfigCore {
	public static final String CAT_ORES = "ores";
	public static final String CAT_ORE_GEN = "ore_generation";
	public static final String CAT_REGEN = "regeneration";
	private static final String CAT_CONFIG = "configFile";
	
	protected boolean configured = false;
	protected Configuration config;
	
	protected final int configVersion = 1;
	protected int configFileVersion;
	
	protected static File baseConfigDir;
	
	public static void setBaseDir(File modConfigDir) {
		baseConfigDir = new File(modConfigDir, OresPlus.MOD_ID + "/");
	}
	
	public void init(String configName, File configFile) {
		OresPlus.log.info("Initializing " + configName + " Configuration");
		
		this.config = new Configuration(configFile);
		
		this.config.load();
		
		this.config.addCustomCategoryComment(CAT_CONFIG, "Config file versioning");

		if (this.config.hasKey(CAT_CONFIG, "configVersion")) {
		    this.configFileVersion = getInt(CAT_CONFIG, "configVersion", configFileVersion, "Configuration File Version - Do not change, modifying this may break your game");
	    }
	    else {
	    	this.configFileVersion = 0;
	    }
	    OresPlus.log.info("Reading from v" + configFileVersion + " Configuration File");
	}
	
	public void save() {
		if (!configured) {
			OresPlus.log.info("Error - configuration not initialized!");
			return;		
		}
		OresPlus.log.info("Saving Configuration");
		
		if (configFileVersion != configVersion) {
			Property prop = config.get(CAT_CONFIG, "configVersion", configVersion);
			prop.comment = "Configuration File Version - Do not change, modifying this may break your game";
			prop.set(configVersion);
		}
		
		if (config.hasChanged())
			config.save();
	}
	
	private int getInt(String key, int defaultValue, String comment) {
		return getInt(Configuration.CATEGORY_GENERAL, key, defaultValue, comment);
	}

	private int getInt(String category, String key, int defaultValue, String comment) {
		Property prop = config.get(category, key, defaultValue);
		if (comment != "")
			prop.comment = comment;
		return prop.getInt(defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultValue, String comment) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, key, defaultValue);
		if (comment != "")
			prop.comment = comment;
		return prop.getBoolean(defaultValue);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return getBoolean(key, defaultValue, "");
	}
	
	public String getString(String catagory, String key, String defaultValue, String comment) {
		Property prop = config.get(catagory, key, defaultValue);
		if (comment != "")
			prop.comment = comment;
		return prop.getString();
	}
	
	public String getString(String key, String defaultValue, String comment) {
		return getString(Configuration.CATEGORY_GENERAL, key, defaultValue, comment);
	}

	public String getString(String key, String defaultValue) {
		return getString(Configuration.CATEGORY_GENERAL, key, defaultValue, "");
	}
	
	private String getOreGenCfgLine(OreGenClass oreConfig) {
		if (oreConfig == null)
			return null;
		return Boolean.toString(oreConfig.enabled) + ","
				+ oreConfig.density + ","
				+ Boolean.toString(oreConfig.doRegen) + ","
				+ oreConfig.regenKey;
	}
	
	public OreGenClass getOreGen(OreGenClass oreConfig) {
		if (oreConfig != null) {
			Property prop = config.get(CAT_ORE_GEN, oreConfig.name, getOreGenCfgLine(oreConfig));
			String cfg[] = prop.getString().split(",");
			boolean configChanged = false;
			switch (configFileVersion) {
			case 1:
				oreConfig.enabled = Boolean.parseBoolean(cfg[0]);
				oreConfig.density = Integer.parseInt(cfg[1]);
				oreConfig.doRegen = Boolean.parseBoolean(cfg[2]);
				oreConfig.regenKey = cfg[3];
				break;
			default:
				switch (cfg.length) {
				case 3: // v0.1.4 - 0.4.23
					oreConfig.enabled = Boolean.parseBoolean(cfg[0]);
					oreConfig.density = Integer.parseInt(cfg[1]);
					oreConfig.doRegen = Boolean.parseBoolean(cfg[2]);
					break;
				case 4: // current
					oreConfig.enabled = Boolean.parseBoolean(cfg[0]);
					oreConfig.density = Integer.parseInt(cfg[1]);
					oreConfig.doRegen = Boolean.parseBoolean(cfg[2]);
					oreConfig.regenKey = cfg[3];
					break;
				case 8: // v0.1.1 - v0.1.3
					oreConfig.enabled = Boolean.parseBoolean(cfg[1]);
					oreConfig.doRegen = Boolean.parseBoolean(cfg[7]);
					configChanged = true;
					break;
				default:
					OresPlus.log.info("Could not read config for ore " + oreConfig.name + ". Resetting to default");
					configChanged = true;
				}
			}
			if (configChanged)
				prop.set(getOreGenCfgLine(oreConfig));
		}
		return oreConfig;
	}
	
	private String getOreCfgLine(OreClass ore) {
		if (ore == null)
			return null;
		return Boolean.toString(ore.enabled) + ","
				+ ore.source.name();
	}
	
	public OreClass getOre(OreClass ore) {
		if (ore != null) {
			Property prop = config.get(CAT_ORES, ore.name, getOreCfgLine(ore));
			String cfg[] = prop.getString().split(",");
			
			boolean configChanged = false;
			switch (cfg.length) {
			case 2:
				ore.enabled = Boolean.parseBoolean(cfg[0]);
				ore.source = OreSources.valueOf(cfg[1]);
				break;
			case 3: // v0.1.1 - v0.1.3
				ore.enabled = Boolean.parseBoolean(cfg[0]);
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
