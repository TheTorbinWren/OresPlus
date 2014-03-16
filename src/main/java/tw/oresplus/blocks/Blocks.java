package tw.oresplus.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.core.Config;
import tw.oresplus.core.OreClass;
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
	
	public static void init() {
		if (isInitialized) {
			OresPlus.log.info("Block initialization failed, already initialized");
			return;
		}
		OresPlus.log.info("Initializing Blocks");
		
		OresPlusAPI.registerBlock("oreIron", "minecraft", net.minecraft.init.Blocks.iron_ore);
		OresPlusAPI.registerBlock("oreGold", "minecraft", net.minecraft.init.Blocks.gold_ore);
		OresPlusAPI.registerBlock("oreLapis", "minecraft", net.minecraft.init.Blocks.lapis_ore);
		OresPlusAPI.registerBlock("oreRedstone", "minecraft", net.minecraft.init.Blocks.redstone_ore);
		OresPlusAPI.registerBlock("oreDiamond", "minecraft", net.minecraft.init.Blocks.diamond_ore);
		//blockList.put("oreEmerald", new BlockInfo("minecraft", net.minecraft.init.Blocks.emerald_ore));
		OresPlusAPI.registerBlock("oreQuartz", "minecraft", net.minecraft.init.Blocks.quartz_ore);
		OresPlusAPI.registerBlock("oreCoal", "minecraft", net.minecraft.init.Blocks.coal_ore);
		
		for (Ores ore : Ores.values()) {
			OreClass oreConfig = Config.getOre(ore.getDefaultConfig());
			if (oreConfig.enabled)
				new BlockOre(oreConfig);
		}
		
		new BlockGrinder(false);
		new BlockGrinder(true);
		new BlockCracker();
		
		new BlockCore(Material.iron, "oreBlockAdamantine").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockBrass").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockBronze").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockColdiron").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockCopper").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockElectrum").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockLead").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockMithral").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockNickel").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockPlatinum").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockSilver").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockTin").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		new BlockCore(Material.iron, "oreBlockZinc").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
		
		isInitialized=true;
	}
	
	public static Block getBlock(String blockName) {
		try {
			return OresPlusAPI.getBlock(blockName);
		} catch (Throwable e){
			return null;
		}
	}

	public static void handleRemaps(FMLModIdMappingEvent event) {
		OresPlus.log.info("recieved remap event");
		
	}

	public static void handleMissingMaps(FMLMissingMappingsEvent event) {
		OresPlus.log.info("recieved missing maps event");
	}
	
}
