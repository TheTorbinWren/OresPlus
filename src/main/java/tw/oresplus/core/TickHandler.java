package tw.oresplus.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import tw.oresplus.OresPlus;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.worldgen.OreGenerators;
import tw.oresplus.worldgen.WorldGenCore;
import tw.oresplus.worldgen.WorldGenOre;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {
	public static HashMap<Integer, ArrayList<ChunkCoordIntPair>> oreRegenList = new HashMap();
	public static HashMap<Integer, ArrayList<ChunkCoordIntPair>> oilRegenList = new HashMap();
	public static HashMap<Integer, ArrayList<ChunkCoordIntPair>> rubberTreeRegenList = new HashMap();
	public static HashMap<Integer, ArrayList<ChunkCoordIntPair>> beehiveRegenList = new HashMap();
	
	@SubscribeEvent
	public boolean onWorldTick(TickEvent.WorldTickEvent event) {
		
		int dimId = event.world.provider.dimensionId;
		ArrayList<WorldGenOre> oreGenArray = WorldGenCore.oreGenerators.get(dimId);
		if (oreGenArray != null) {
			for (WorldGenOre oreGen : oreGenArray) {
				ArrayList<ChunkCoordIntPair> chunks = oreGen.regenList.get(dimId);
				if (chunks != null && !chunks.isEmpty()) {
					ChunkCoordIntPair coords = chunks.get(0);
					oreGen.generate(event.world, event.world.rand, coords.chunkXPos * 16, coords.chunkZPos * 16);
					event.world.getChunkFromChunkCoords(coords.chunkXPos, coords.chunkZPos).setChunkModified();
		        	if (OresPlus.logRegenerations) {
		        		OreLog.info("Regenerated " + oreGen.getOreName() + " at chunk " + coords.chunkXPos + "," + coords.chunkZPos);
		        	}
		        	chunks.remove(0);
		        	oreGen.regenList.put(Integer.valueOf(dimId), chunks);
				}
			}
		}
		
		if (Helpers.BuildCraft.isLoaded()) {
			ArrayList chunks = oilRegenList.get(Integer.valueOf(dimId));
			if (chunks != null && !chunks.isEmpty()) {
				ChunkCoordIntPair coords = (ChunkCoordIntPair) chunks.get(0);
				Chunk chunk = event.world.getChunkFromChunkCoords(coords.chunkXPos, coords.chunkZPos);
				Helpers.BuildCraft.generate(event.world, event.world.rand, chunk.xPosition, chunk.zPosition);
		        if (OresPlus.logRegenerations) {
		            OreLog.info("Regenerated oil at chunk " + coords.chunkXPos + "," + coords.chunkZPos);
		          }
				chunks.remove(0);
				oilRegenList.put(Integer.valueOf(dimId), chunks);
			}
		}
		
		if (Helpers.IC2.isLoaded()) {
			ArrayList chunks = rubberTreeRegenList.get(Integer.valueOf(dimId));
			if (chunks != null && !chunks.isEmpty()) {
				ChunkCoordIntPair coords = (ChunkCoordIntPair) chunks.get(0);
				Chunk chunk = event.world.getChunkFromChunkCoords(coords.chunkXPos, coords.chunkZPos);
				Helpers.IC2.generate(event.world, event.world.rand, chunk.xPosition, chunk.zPosition);
		        if (OresPlus.logRegenerations) {
		            OreLog.info("Regenerated rubber trees at chunk " + coords.chunkXPos + "," + coords.chunkZPos);
		          }
				chunks.remove(0);
				rubberTreeRegenList.put(Integer.valueOf(dimId), chunks);
			}
		}
		
		if (Helpers.Forestry.isLoaded()) {
			ArrayList chunks = beehiveRegenList.get(Integer.valueOf(dimId));
			if (chunks != null && !chunks.isEmpty()) {
				ChunkCoordIntPair coords = (ChunkCoordIntPair) chunks.get(0);
				Chunk chunk = event.world.getChunkFromChunkCoords(coords.chunkXPos, coords.chunkZPos);
				Helpers.Forestry.generate(event.world, event.world.rand, chunk.xPosition, chunk.zPosition);
		        if (OresPlus.logRegenerations) {
		            OreLog.info("Regenerated beehives at chunk " + coords.chunkXPos + "," + coords.chunkZPos);
		          }
				chunks.remove(0);
				beehiveRegenList.put(Integer.valueOf(dimId), chunks);
			}
		}
		
		return true;
	}

}
