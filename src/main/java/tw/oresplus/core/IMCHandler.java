package tw.oresplus.core;

import net.minecraft.nbt.NBTTagCompound;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.worldgen.OreGenClass;
import tw.oresplus.worldgen.OreGenType;
import tw.oresplus.worldgen.WorldGenOre;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.registry.GameRegistry;

public class IMCHandler {
	
	public void recieveIMC(FMLInterModComms.IMCEvent event) {
		OresPlus.log.info("Recieved IMC evemt");
		for (IMCMessage message : event.getMessages()) {
			try {
				if (!message.isNBTMessage())
					break;
				NBTTagCompound messageData = message.getNBTValue();
				if (message.key.equals("registerOre")) {
					if (!messageData.getString("oreName").equals("") && !Ores.manager.isOreRegistered(messageData.getString("oreName"))) {
						Ores.manager.registerOre(messageData.getString("oreName"), GameRegistry.findBlock(message.getSender(), messageData.getString("oreName")));
					}
				}
				else if (message.key.equals("registerGenerator")) {
					if (!messageData.getString("oreName").equals("") && !Ores.manager.isOreRegistered(messageData.getString("oreName"))) {
						OreGenClass oreGen = new OreGenClass(
								messageData.getString("genName"),
								messageData.getString("oreName"), 
								messageData.getBoolean("genEnabled"), 
								messageData.getInteger("genDim"), 
								messageData.getInteger("numVeins"), 
								messageData.getInteger("veinSize"), 
								messageData.getInteger("minY"), 
								messageData.getInteger("maxY"), 
								messageData.getBoolean("doRegen"), 
								OreGenType.NORMAL, 
								messageData.getInteger("oreDensity"),
								messageData.getString("regenKey"));
						new WorldGenOre(oreGen);
					}
				}
			}
			catch (Exception e) {
				OresPlus.log.info("Error, recieved invalid IMC message from " + message.getSender());
				e.printStackTrace();
			}
		}
	}

}
