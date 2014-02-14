package tw.oresplus;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.core.EventHandlerCore;
import tw.oresplus.core.IProxy;
import tw.oresplus.core.OreLog;
import tw.oresplus.core.TickHandler;
import tw.oresplus.enums.OreGenerators;
import tw.oresplus.init.*;
import tw.oresplus.items.ItemCore;
import tw.oresplus.worldgen.WorldGenCore;
import tw.oresplus.worldgen.WorldGenOre;

@Mod(modid = OresPlus.MOD_ID, name = OresPlus.MOD_NAME, version = OresPlus.MOD_VERSION)
public class OresPlus {
	
	@SidedProxy(clientSide="tw.oresplus.client.ClientProxy", serverSide="tw.oresplus.core.ServerProxy") 
	public static IProxy proxy;
	
    public static final String MOD_ID = "OresPlus";
    public static final String MOD_NAME = "OresPlus";
    public static final String MOD_VERSION = "0.1.4";
    
	@Instance(OresPlus.MOD_ID)
	public static OresPlus instance;

    public static OreLog log;
    public static Config config;
    
    public static String regenKey = "DISABLED";
    
    public static WorldGenCore worldGen = new WorldGenCore();
    public static EventHandlerCore eventHandler = new EventHandlerCore();
    public static TickHandler tickHandler = new TickHandler();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	log.init();
    	config.init(event);
		config.load();
		
    	Blocks.init();
    	Items.init();
    	
    	//Register Ore Generators
    	log.info("Registering Ore Generators");
    	for (OreGenerators oreGen : OreGenerators.values()) {
    		OreGenConfig ore = Config.getOreGen(oreGen.getDefaultConfig());
    		if (ore.enabled && !Blocks.blockList.get(ore.oreName).equals(null)) 
   				new WorldGenOre(ore);
    	}
		config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	MinecraftForge.EVENT_BUS.register(eventHandler);
    	FMLCommonHandler.instance().bus().register(tickHandler);
    	
    	/* OreDictionaty dump
    	for (String ore : OreDictionary.getOreNames()) {
    		log.info(ore);
    	}
    	*/
    }
}
