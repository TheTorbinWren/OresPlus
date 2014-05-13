package tw.oresplus.network;

import tw.oresplus.blocks.TileEntityGrinder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class PacketUpdateGrinder extends PacketUpdateMachine {
	
	public PacketUpdateGrinder(NBTTagCompound data, int x, int y, int z) {
		super(data, x, y, z);
	}
	
	public PacketUpdateGrinder() {}

	@Override
	public void readBytes(ByteBuf bytes) {
		super.readBytes(bytes);
	}

	@Override
	public void writeBytes(ByteBuf bytes) {
		super.writeBytes(bytes);
	}

	@Override
	public void executeClient(EntityPlayer player) {
		TileEntity te = player.worldObj.getTileEntity(this._x, this._y, this._z);
		if (te instanceof TileEntityGrinder) {
			((TileEntityGrinder)te).recieveUpdatePacket(this._data);
		}
	}

	@Override
	public void executeServer(EntityPlayer player) {}
}
