package tw.oresplus.core;

import java.util.ArrayList;

import tw.oresplus.OresPlus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.ChunkDataEvent;

public class EventHandlerCore {
	@SubscribeEvent
	public void chunkLoad(ChunkDataEvent.Load event) {
		if (OresPlus.regenKey.equals("DISABLED"))
			return;
		if (!event.getData().getString("OresPlus:regenKey").equals(OresPlus.regenKey)) {
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
		//OresPlus.log.info("Saving chunk x" + event.getChunk().xPosition + ", z" + event.getChunk().zPosition);
		event.getData().setString("OresPlus:regenKey", OresPlus.regenKey);
	}
}












