package tw.oresplus.core;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tw.oresplus.OresPlus;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;

public class ItemMapHelper {
	private HashMap<String, Item> remapList = new HashMap();

	public ItemMapHelper() {
		// Add remaps for storage blocks (oreBlockXXX -> blockXXX)
		for (MetallicOres ore : MetallicOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
		}
		for (GemstoneOres ore : GemstoneOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
		}
		for (DustOres ore : DustOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
		}
	}
	
	public void handleMissingMaps(FMLMissingMappingsEvent event) {
		OresPlus.log.info("recieved missing maps event");
		for (MissingMapping map : event.get()) {
			if (this.remapList.containsKey(map.name)) {
				switch (map.type) {
				case BLOCK:
					OresPlus.log.info("Remapping Missing Mapping for Block " + map.name);
					map.remap(Block.getBlockFromItem(remapList.get(map.name)));
					break;
				default:
					OresPlus.log.info("Remapping Missing Mapping for Item " + map.name);
					map.remap(remapList.get(map.name));
				}
			}
		}
	}

}
