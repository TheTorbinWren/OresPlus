package tw.oresplus.blocks;

import tw.oresplus.network.NetHandler;
import tw.oresplus.network.PacketCracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityCracker extends TileEntityMachine {
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {1};

	public TileEntityCracker() {
		super();
		this.inventory = new ItemStack[3];
		this.inventoryName = "container:cracker";
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
		return NetHandler.INSTANCE.getPacketFrom(new PacketCracker(this));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!worldObj.isRemote) {
			if (this.ticker % 20 == 0) {
			}
		}
		NetHandler.INSTANCE.sendToAll(new PacketCracker(this));
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
