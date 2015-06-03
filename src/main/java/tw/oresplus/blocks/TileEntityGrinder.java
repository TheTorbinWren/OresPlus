package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.network.NetHandler;
import tw.oresplus.network.PacketGrinder;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityGrinder extends TileEntityMachine {
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    
	public TileEntityGrinder() {
		super();
		this.inventory = new ItemStack[4];
		this.inventoryName = "container:grinder";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return NetHandler.INSTANCE.getPacketFrom(new PacketGrinder(this));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!worldObj.isRemote) {
			if (this.ticker % 20 == 0) {
			}
		}
		NetHandler.INSTANCE.sendToAll(new PacketGrinder(this));
	}

	@Override
	public boolean hasWork() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}


}
