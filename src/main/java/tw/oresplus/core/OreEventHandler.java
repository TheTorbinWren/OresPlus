package tw.oresplus.core;

import java.util.ArrayList;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.worldgen.OreGenType;
import tw.oresplus.worldgen.WorldGenOre;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.world.ChunkDataEvent;

public class OreEventHandler {
	@SubscribeEvent
	public void chunkLoad(ChunkDataEvent.Load event) {
		NBTTagCompound oresPlusRegen = event.getData().getCompoundTag("OresPlus");
		
		if (oresPlusRegen.hasNoTags()) { // regen info not found, checking for old regen key
			oresPlusRegen.setString("ores", event.getData().getString("OresPlus:regenKey"));
		}
		
		if (!OresPlus.regenKeyOre.equals("DISABLED") && !oresPlusRegen.getString("ores").equals(OresPlus.regenKeyOre)) {
			int dim = event.world.provider.dimensionId;
			ArrayList chunks = TickHandler.oreRegenList.get(Integer.valueOf(dim));
			if (chunks == null)
				chunks = new ArrayList();
			chunks.add(event.getChunk().getChunkCoordIntPair());
			TickHandler.oreRegenList.put(Integer.valueOf(dim), chunks);
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
		oresPlusRegen.setString("ores", OresPlus.regenKeyOre);
		oresPlusRegen.setString("oil", OresPlus.regenKeyOil);
		oresPlusRegen.setString("rubberTree", OresPlus.regenKeyRubberTree);
		oresPlusRegen.setString("beehives", OresPlus.regenKeyBeehives);
		event.getData().setTag("OresPlus", oresPlusRegen);
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












