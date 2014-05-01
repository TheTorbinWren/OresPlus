package tw.oresplus.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.Action;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.OreLog;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.ores.OreClass;
import tw.oresplus.ores.OreDrops;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;

public class Blocks {
	private static boolean isInitialized = false;
	
	public static OreItemStack grinder;
	public static OreItemStack grinder_lit;
	public static OreItemStack cracker;
	public static OreItemStack cracker_lit;
	
	public static void init() {
		if (isInitialized) {
			OresPlus.log.info("Block initialization failed, already initialized");
			return;
		}
		OresPlus.log.info("Initializing Blocks");

		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerBlocks();
		}
		
		for (GemstoneOres ore : GemstoneOres.values()) {
			ore.registerBlocks();
		}
		
		for (DustOres ore : DustOres.values()) {
			ore.registerBlocks();
		}
		
		for (GeneralOres ore : GeneralOres.values()) {
			ore.registerBlocks();
		}
		
		// register machine blocks
		grinder = new OreItemStack(new BlockGrinder(false));
		grinder_lit = new OreItemStack(new BlockGrinder(true));
		RecipeManager.hideItem(grinder_lit);
		
		cracker = new OreItemStack(new BlockCracker(false));
		cracker_lit = new OreItemStack(new BlockCracker(true));
		RecipeManager.hideItem(cracker_lit);
		
		// register vanilla ores for custom ore generators
		Ores.manager.registerOre("oreIron", net.minecraft.init.Blocks.iron_ore);
		Ores.manager.registerOre("oreGold", net.minecraft.init.Blocks.gold_ore);
		Ores.manager.registerOre("oreDiamond", net.minecraft.init.Blocks.diamond_ore);
		Ores.manager.registerOre("oreEmerald", net.minecraft.init.Blocks.emerald_ore);
		Ores.manager.registerOre("oreLapis",  net.minecraft.init.Blocks.lapis_ore);
		Ores.manager.registerOre("oreQuartz", net.minecraft.init.Blocks.quartz_ore);
		Ores.manager.registerOre("oreCoal", net.minecraft.init.Blocks.coal_ore);
		
		isInitialized=true;
	}
	
	public static void handleRemaps(FMLModIdMappingEvent event) {
		OresPlus.log.info("recieved remap event");
		
	}

	public static void registerAspects() {
	    ThaumcraftApi.registerObjectTag(cracker.source, new AspectList());
	    ThaumcraftApi.registerObjectTag(grinder.source, new AspectList());
	}
	
}