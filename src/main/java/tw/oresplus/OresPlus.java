package tw.oresplus;

import java.io.File;
import java.util.Map;

import buildcraft.api.gates.ActionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.blocks.TileEntityCracker;
import tw.oresplus.blocks.TileEntityGrinder;
import tw.oresplus.core.Config;
import tw.oresplus.core.GuiHandler;
import tw.oresplus.core.IMCHandler;
import tw.oresplus.core.IProxy;
import tw.oresplus.core.OreEventHandler;
import tw.oresplus.core.OreLog;
import tw.oresplus.core.TickHandler;
import tw.oresplus.core.helpers.AppEngHelper;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.fluids.Fluids;
import tw.oresplus.items.ItemCore;
import tw.oresplus.items.Items;
import tw.oresplus.network.NetHandler;
import tw.oresplus.ores.OreManager;
import tw.oresplus.recipes.RecipeManager;
import tw.oresplus.triggers.OresTrigger;
import tw.oresplus.triggers.TriggerProvider;
import tw.oresplus.worldgen.OreChestLoot;
import tw.oresplus.worldgen.OreGenClass;
import tw.oresplus.worldgen.OreGenerators;
import tw.oresplus.worldgen.WorldGenCore;
import tw.oresplus.worldgen.WorldGenOre;

@Mod(modid = OresPlus.MOD_ID, name = OresPlus.MOD_NAME, version = OresPlus.MOD_VERSION, dependencies="required-after:Forge@10.12.0.1040")
public class OresPlus {
	
	@SidedProxy(clientSide="tw.oresplus.client.ClientProxy", serverSide="tw.oresplus.core.ServerProxy") 
	public static IProxy proxy;
	
    public static final String MOD_ID = "OresPlus";
    public static final String MOD_NAME = "OresPlus";
    public static final String MOD_VERSION = "0.5.27 Beta";
    
	@Instance(OresPlus.MOD_ID)
	public static OresPlus instance;

    public static OreLog log;
    public static Config config;
    
    public static String regenKeyOre = "DISABLED";
    public static String regenKeyOil = "DISABLED";
    public static String regenKeyRubberTree = "DISABLED";
    public static String regenKeyBeehives = "DISABLED";
    public static boolean iridiumPlateRecipe = true;
    public static boolean difficultAlloys = false;
    public static boolean logRegenerations = false;
    
    public static WorldGenCore worldGen = new WorldGenCore();
    public static OreEventHandler eventHandler = new OreEventHandler();
    public static TickHandler tickHandler = new TickHandler();
    public static IMCHandler imcHandler = new IMCHandler();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	log.init();
    	
		RecipeManager.init();

		config.init(event);
		
	    regenKeyOre = Config.getString(Config.CAT_REGEN, "regenKey", regenKeyOre, "change this to regenerate ores");
	    regenKeyOil = Config.getString(Config.CAT_REGEN, "regenKeyOil", regenKeyOil, "change this to regenerate buildcraft oil wells");
	    regenKeyRubberTree = Config.getString(Config.CAT_REGEN, "regenKeyRubberTree", regenKeyRubberTree, "change this to regenerate IC2 rubber trees");
	    regenKeyBeehives = Config.getString(Config.CAT_REGEN, "regenKeyBeehives", regenKeyBeehives, "change this to regenerate Forestry beehives");

	    iridiumPlateRecipe = Config.getBoolean("iridiumPlateRecipe", iridiumPlateRecipe, "enables an ore dictionary-enabled recipe for iridium plate");
	    difficultAlloys = Config.getBoolean("difficultAlloys", difficultAlloys, "enable true to set brass & bronze alloy recipes to output only 2 dusts");
	    logRegenerations = Config.getBoolean("logRegenerations", logRegenerations, "enavble to log all regenerations that occur");

		NetHandler.instance.preInit();
		
		Ores.manager = new OreManager();
		
    	Blocks.init();
    	Items.init();
    	Fluids.init();
    	
    	//Register Ore Generators
    	log.info("Registering Ore Generators");
    	for (OreGenerators oreGen : OreGenerators.values()) {
    		oreGen.registerGenerator();
    	}
    	
		config.save();
		
		// Initialize Integration Helpers
    	for (Helpers helper : Helpers.values()) {
    		helper.init();
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	RecipeManager.initRecipes();
    	
    	GameRegistry.registerTileEntity(TileEntityGrinder.class, "TileEntityGrinder");
    	GameRegistry.registerTileEntity(TileEntityCracker.class, "TileEntityCracker");
    	
    	MinecraftForge.EVENT_BUS.register(eventHandler);
    	MinecraftForge.ORE_GEN_BUS.register(eventHandler);
    	FMLCommonHandler.instance().bus().register(tickHandler);
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	
    	OresTrigger.registerTriggers();
    	ActionManager.registerTriggerProvider(new TriggerProvider());
    	
    	NetHandler.instance.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	RecipeManager.replaceRecipeResults();
    	
    	OreChestLoot.registerChestLoot();
    }
    
    @EventHandler
	public void recieveIMC(FMLInterModComms.IMCEvent event) {
    	imcHandler.recieveIMC(event);
    }
    
    @EventHandler
    public void handleMissingMaps(FMLMissingMappingsEvent event) {
    	Blocks.handleMissingMaps(event);
    }
    
    @EventHandler
    public void handRemaps(FMLModIdMappingEvent event) {
    	Blocks.handleRemaps(event);
    }
    
}
