package tw.oresplus.core;

import java.util.ArrayList;

import tw.oresplus.OresPlus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.ChunkDataEvent;

public class EventHandlerCore {
	@SubscribeEvent
	public void chunkLoad(ChunkDataEvent.Load event) {
		if (OresPlus.regenKey.equals("DISABLED"))
			return;
		NBTTagCompound oresPlusRegen = event.getData().getCompoundTag("OresPlus");
		if (oresPlusRegen.hasNoTags()) { // regen info not found, checking for old regen key
			oresPlusRegen.setString("ores", event.getData().getString("OresPlus:regenKey"));
		}
		if (!oresPlusRegen.getString("ores").equals(OresPlus.regenKey)) {
			int dim = event.world.provider.dimensionId;
			ArrayList chunks = TickHandler.regenList.get(Integer.valueOf(dim));
			if (chunks == null)
				chunks = new ArrayList();
			chunks.add(event.getChunk().getChunkCoordIntPair());
			TickHandler.regenList.put(Integer.valueOf(dim), chunks);
			
		}
	}

	@SubscribeEvent
	public void chunkSave(ChunkDataEvent.Save event) {
		if (OresPlus.regenKey.equals("DISABLED"))
			return;
		NBTTagCompound oresPlusRegen = new NBTTagCompound();
		oresPlusRegen.setString("ores", OresPlus.regenKey);
		event.getData().setTag("OresPlus", oresPlusRegen);
	}
}












