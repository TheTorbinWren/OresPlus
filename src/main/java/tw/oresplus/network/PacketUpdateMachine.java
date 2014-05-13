package tw.oresplus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PacketUpdateMachine implements IPacket {
	protected NBTTagCompound _data;
	protected int _x;
	protected int _y;
	protected int _z;
	
	public PacketUpdateMachine(NBTTagCompound data, int x, int y, int z) {
		this._data = data;
		this._x = x;
		this._y = y;
		this._z = z;
	}
	
	public PacketUpdateMachine() {}
	
	@Override
	public void readBytes(ByteBuf bytes) {
		this._x = bytes.readInt();
		this._y = bytes.readInt();
		this._z = bytes.readInt();
		if (this._data == null) {
			this._data = new NBTTagCompound();
		}
		NBTTagCompound tag = new NBTTagCompound();
		tag.setDouble("energyStored", bytes.readDouble());
		this._data.setTag("powerProvider", tag);
		this._data.setFloat("energySpent", bytes.readFloat());
		this._data.setInteger("furnaceBurnTime", bytes.readInt());
	}
	
	@Override
	public void writeBytes(ByteBuf bytes) {
		bytes.writeInt(this._x);
		bytes.writeInt(this._y);
		bytes.writeInt(this._z);
		bytes.writeDouble(this._data.getCompoundTag("powerProvider").getDouble("energyStored"));
		bytes.writeFloat(this._data.getFloat("energySpent"));
		bytes.writeInt(this._data.getInteger("furnaceBurnTime"));
	}

}
