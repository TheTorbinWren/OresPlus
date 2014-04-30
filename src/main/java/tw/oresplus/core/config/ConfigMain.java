package tw.oresplus.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import tw.oresplus.OresPlus;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigMain extends ConfigCore {

	public void init(FMLPreInitializationEvent event) {
		
		File configFile = new File(baseConfigDir, OresPlus.MOD_ID + ".cfg");
		if (!configFile.exists()) {
			File oldConfigFile = event.getSuggestedConfigurationFile();
			if (oldConfigFile.exists())
				oldConfigFile.renameTo(configFile);
		}
		
		super.init("Main", configFile);
		
		config.addCustomCategoryComment(CAT_ORES, "Ore configuration = oreEnabled,oreSource");
		config.addCustomCategoryComment(CAT_ORE_GEN, "Ore generator configuration = generatorEnabled,denisty%,regenerateOre");
	    config.addCustomCategoryComment(CAT_REGEN, "Configure general regeneration options here");
		
		configured = true;
	}

}
