package tw.oresplus.triggers;

import java.util.LinkedList;

import tw.oresplus.blocks.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipeTile;

public class TriggerProvider implements ITriggerProvider {

	@Override
	public LinkedList<ITrigger> getPipeTriggers(IPipeTile pipe) {
		return null;
	}

	@Override
	public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
		LinkedList triggers = new LinkedList();
		
		if (tile instanceof TileEntityMachine) {
			triggers.add(OresTrigger.hasWork);
			triggers.add(OresTrigger.workDone);
		}
		
		return triggers;
	}

}
