package tw.oresplus.core;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tw.oresplus.OresPlus;
import tw.oresplus.items.Items;
import tw.oresplus.ores.DustyOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.ores.MineralOres;
import tw.oresplus.recipes.OreItemStack;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;

public class ItemMapHelper {
	private HashMap<String, Item> remapList = new HashMap();

	public ItemMapHelper() {
		// Add remaps for storage blocks (oreBlockXXX -> blockXXX)
		for (MetallicOres ore : MetallicOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
			if (!ore.isAlloy) {
				remapList.put("OresPlus:nativeCluster" + ore.toString(), ore.cluster.source.getItem());
			}
		}
		for (GemstoneOres ore : GemstoneOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
		}
		for (DustyOres ore : DustyOres.values()) {
			remapList.put("OresPlus:oreBlock" + ore.toString(), ore.block.source.getItem());
		}
		// add Peridot remaps
		remapList.put("OresPlus:oreGreenSapphire", GemstoneOres.Peridot.ore.source.getItem());
		remapList.put("OresPlus:oreNetherGreenSapphire", GemstoneOres.Peridot.netherOre.source.getItem());
		remapList.put("OresPlus:blockGreenSapphire", GemstoneOres.Peridot.block.source.getItem());
		remapList.put("OresPlus:gemGreenSapphire", GemstoneOres.Peridot.gem.source.getItem());
		// add Mithril remaps
		remapList.put("OresPlus:oreMithral", MetallicOres.Mithril.ore.source.getItem());
		remapList.put("OresPlus:oreNetherMithral", MetallicOres.Mithril.netherOre.source.getItem());
		remapList.put("OresPlus:blockMithral", MetallicOres.Mithril.block.source.getItem());
		remapList.put("OresPlus:ingotMithral", MetallicOres.Mithril.ingot.source.getItem());
		remapList.put("OresPlus:nuggetMithral", MetallicOres.Mithril.nugget.source.getItem());
		remapList.put("OresPlus:dustMithral", MetallicOres.Mithril.dust.source.getItem());
		remapList.put("OresPlus:dustTinyMithral", MetallicOres.Mithril.tinyDust.source.getItem());
		remapList.put("OresPlus:crushedMithral", MetallicOres.Mithril.crushedOre.source.getItem());
		remapList.put("OresPlus:crushedPurifiedMithral", MetallicOres.Mithril.purifiedCrushedOre.source.getItem());
		remapList.put("OresPlus:clusterMithral", MetallicOres.Mithril.cluster.source.getItem());
		remapList.put("OresPlus:clumpMithral", MetallicOres.Mithril.clump.source.getItem());
		remapList.put("OresPlus:dustDirtyMithral", MetallicOres.Mithril.dirtyDust.source.getItem());
		remapList.put("OresPlus:shardMithral", MetallicOres.Mithril.shard.source.getItem());
		remapList.put("OresPlus:crystalMithral", MetallicOres.Mithril.crystal.source.getItem());
		remapList.put("OresPlus:bucketMoltenMithral", MetallicOres.Mithril.bucket.source.getItem());
		//remapList.put("OresPlus:block.molten.mithral", MetallicOres.Mithril.moltenFluidBlock.getItem());
		remapList.put("OresPlus:oreSheldonite", MineralOres.Cooperite.ore.source.getItem());
		remapList.put("OresPlus:dustSheldonite", MineralOres.Cooperite.dust.source.getItem());
		remapList.put("OresPlus:dustTungstate", Items.dustTungsten.item.source.getItem());
	}
	
	public void handleMissingMaps(FMLMissingMappingsEvent event) {
		OresPlus.log.info("recieved missing maps event");
		for (MissingMapping map : event.get()) {
			if (this.remapList.containsKey(map.name)) {
				switch (map.type) {
				case BLOCK:
					OresPlus.log.info("Remapping missing mapping for block " + map.name);
					map.remap(Block.getBlockFromItem(remapList.get(map.name)));
					break;
				default:
					OresPlus.log.info("Remapping missing mapping for item " + map.name);
					map.remap(remapList.get(map.name));
				}
			}
		}
	}

}
