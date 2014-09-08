package tw.oresplus.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import tw.oresplus.OresPlus;
import tw.oresplus.core.References;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigMain extends ConfigCore {
	public boolean enableMachines = true;

	public void init(FMLPreInitializationEvent event) {
		
		File configFile = new File(baseConfigDir, References.MOD_ID + ".cfg");
		if (!configFile.exists()) {
			File oldConfigFile = event.getSuggestedConfigurationFile();
			if (oldConfigFile.exists())
				oldConfigFile.renameTo(configFile);
		}
		
		super.init("Main", configFile);
		
	    config.addCustomCategoryComment(CAT_REGEN, "Configure general regeneration options here");
		
		configured = true;
	}
	
	public void load() {
		this.enableMachines = this.getBoolean("enableMachines", this.enableMachines, "Set to false to disable crafting of this mods machinary");
	}

}
