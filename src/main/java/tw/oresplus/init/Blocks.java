package tw.oresplus.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.OreLog;
import tw.oresplus.enums.OreDrops;
import tw.oresplus.enums.Ores;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;

public class Blocks {
	private static boolean isInitialized = false;
	public static HashMap blockList = new HashMap();
	
	public static void init() {
		if (isInitialized) {
			OresPlus.log.info("Block initialization failed, already initialized");
			return;
		}
		OresPlus.log.info("Initializing Blocks");
		
		for (Ores ore : Ores.values()) {
			OreConfig oreConfig = Config.getOre(ore.getDefaultConfig());
			if (oreConfig.enabled)
				new BlockOre(oreConfig);
		}
		
		isInitialized=true;
	}
	
	public static Block getBlock(String blockName) {
		try {
			return (Block) blockList.get(blockName);
		} catch (Throwable e){
			return null;
		}
	}
	
}
