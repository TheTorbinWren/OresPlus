package tw.oresplus.core;

import java.util.ArrayList;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.BlockManager;
import tw.oresplus.worldgen.IOreGenerator;
import tw.oresplus.worldgen.OreGenType;
import tw.oresplus.worldgen.OreGenerators;
import tw.oresplus.worldgen.OreGeneratorsEnd;
import tw.oresplus.worldgen.OreGeneratorsNether;
import tw.oresplus.worldgen.WorldGenCore;
import tw.oresplus.worldgen.WorldGenOre;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;

public class OreEventHandler {
	@SubscribeEvent
	public void chunkLoad(ChunkDataEvent.Load event) {
		NBTTagCompound oresPlusRegen = event.getData().getCompoundTag("OresPlus");
		
		if (oresPlusRegen.hasNoTags()) { // regen info not found, checking for old regen key
			oresPlusRegen.setString("ores", event.getData().getString("OresPlus:regenKey"));
		}
		
	    NBTTagCompound oreRegenArray = oresPlusRegen.getCompoundTag("oreRegenArray");
	    int dimId = event.world.provider.dimensionId;
	    ArrayList<WorldGenOre> oreGenArray = WorldGenCore.oreGenerators.get(dimId);
	    if (oreGenArray != null) {
	    	for (WorldGenOre oreGen : oreGenArray) {
	    		String oreRegenKey = oreRegenArray.getString(oreGen.getOreName());
	    		if (oreRegenKey.equals("")) {
	    			oreRegenKey = oresPlusRegen.getString("ores");
	    		}
	    		if (oreGen.doRegen && !oreGen.regenKey.equals(oreRegenKey)) {
	    			ArrayList<ChunkCoordIntPair> chunks = oreGen.regenList.get(Integer.valueOf(dimId));
	    			if (chunks == null)
	    				chunks = new ArrayList();
	    			chunks.add(event.getChunk().getChunkCoordIntPair());
	    			oreGen.regenList.put(dimId, chunks);
	    		}
	    	}
	    }
		
		if (!OresPlus.regenKeyOil.equals("DISABLED") && !oresPlusRegen.getString("oil").equals(OresPlus.regenKeyOil)) {
			int dim = event.world.provider.dimensionId;
			ArrayList chunks = TickHandler.oilRegenList.get(Integer.valueOf(dim));
			if (chunks == null)
				chunks = new ArrayList();
			chunks.add(event.getChunk().getChunkCoordIntPair());
			TickHandler.oilRegenList.put(Integer.valueOf(dim), chunks);
		}
		
		if (!OresPlus.regenKeyRubberTree.equals("DISABLED") && !oresPlusRegen.getString("rubberTree").equals(OresPlus.regenKeyRubberTree)) {
			int dim = event.world.provider.dimensionId;
			ArrayList chunks = TickHandler.rubberTreeRegenList.get(Integer.valueOf(dim));
			if (chunks == null)
				chunks = new ArrayList();
			chunks.add(event.getChunk().getChunkCoordIntPair());
			TickHandler.rubberTreeRegenList.put(Integer.valueOf(dim), chunks);
		}
		
		if (!OresPlus.regenKeyBeehives.equals("DISABLED") && !oresPlusRegen.getString("beehives").equals(OresPlus.regenKeyBeehives)) {
			int dim = event.world.provider.dimensionId;
			ArrayList chunks = TickHandler.beehiveRegenList.get(Integer.valueOf(dim));
			if (chunks == null)
				chunks = new ArrayList();
			chunks.add(event.getChunk().getChunkCoordIntPair());
			TickHandler.beehiveRegenList.put(Integer.valueOf(dim), chunks);
		}
	}

	@SubscribeEvent
	public void chunkSave(ChunkDataEvent.Save event) {
		NBTTagCompound oresPlusRegen = new NBTTagCompound();
		
		int dimId = event.world.provider.dimensionId;
	    NBTTagCompound oreRegenArray = new NBTTagCompound();
	    ArrayList<WorldGenOre> oreGenArray = WorldGenCore.oreGenerators.get(dimId);
	    if (oreGenArray != null) {
	    	for (WorldGenOre oreGen : oreGenArray) {
	    		oreRegenArray.setString(oreGen.getOreName(), oreGen.regenKey);
	    	}
	    }
	    oresPlusRegen.setTag("oreRegenArray", oreRegenArray);
		oresPlusRegen.setString("ores", OresPlus.regenKeyOre);
		oresPlusRegen.setString("oil", OresPlus.regenKeyOil);
		oresPlusRegen.setString("rubberTree", OresPlus.regenKeyRubberTree);
		oresPlusRegen.setString("beehives", OresPlus.regenKeyBeehives);
		event.getData().setTag("OresPlus", oresPlusRegen);
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event) {
		OresPlus.log.info("Loading ore generators for dimension id " + event.world.provider.dimensionId);
		int dim = event.world.provider.dimensionId;
		if (WorldGenCore.oreGenerators.get(dim) != null) {
			OresPlus.log.debug("Skipping load, dimension already loaded");
			return;
		}
		ArrayList<WorldGenOre> oreGenList = new ArrayList();
		IOreGenerator[] sources;
		switch (dim) {
		case -1:
			sources = OreGeneratorsNether.values();
			break;
		case 1:
			sources = OreGeneratorsEnd.values();
			break;
		default:
			sources = OreGenerators.values();
		}
		for (IOreGenerator oreGen : sources) {
			WorldGenOre generator = oreGen.getOreGenerator(dim);
			if (generator != null) {
				OresPlus.log.debug("Adding generator for " + generator.getOreName() + " to dimension id " + dim);
				oreGenList.add(generator);
			}
		}
		if (!oreGenList.isEmpty()) {
			WorldGenCore.oreGenerators.put(dim, oreGenList);
		}
		OresPlus.log.info("Loaded world gen for dimension id " + dim);
	}
	
	@SubscribeEvent
	public void genOre(GenerateMinable event) {
		switch (event.type) {
		//case EMERALD:
		case QUARTZ:
		case IRON:
		case GOLD:
		case LAPIS:
		case REDSTONE:
		case DIAMOND:
		case COAL:
			event.setResult(Result.DENY);
			break;
		default:
			event.setResult(Result.ALLOW);
		}
	}
}












