package tw.oresplus.network;

import tw.oresplus.blocks.TileEntityCracker;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;

public class PacketUpdateCracker implements IPacket {
	private int _x;
	private int _y;
	private int _z;
	private NBTTagCompound _data;
	
	public PacketUpdateCracker(NBTTagCompound data, int x, int y, int z) {
		this._x = x;
		this._y = y;
		this._z = z;
		this._data = data;
	}
	
	public PacketUpdateCracker() {}
	
	@Override
	public void readBytes(ByteBuf bytes) {
		this._x = bytes.readInt();
		this._y = bytes.readInt();
		this._z = bytes.readInt();
		if (this._data == null) {
			this._data = new NBTTagCompound();
		}
		int fluidId = bytes.readInt();
		if (fluidId == -1) {
			this._data.setString("FluidName", "");
		}
		else {
			this._data.setString("FluidName", FluidRegistry.getFluidName(fluidId));
		}
		this._data.setInteger("Amount", bytes.readInt());
		NBTTagCompound powerProvider = new NBTTagCompound();
		powerProvider.setDouble("energyStored", bytes.readDouble());
		this._data.setTag("powerProvider", powerProvider);
		this._data.setInteger("BurnTime", bytes.readInt());
		this._data.setInteger("CookTime", bytes.readInt());
	}

	@Override
	public void writeBytes(ByteBuf bytes) {
		bytes.writeInt(this._x);
		bytes.writeInt(this._y);
		bytes.writeInt(this._z);
		String fluidName = this._data.getString("FluidName");
		if (!fluidName.isEmpty()) {
			bytes.writeInt(FluidRegistry.getFluidID(fluidName));
		}
		else {
			bytes.writeInt(-1);
		}
		bytes.writeInt(this._data.getInteger("Amount"));
		bytes.writeDouble(this._data.getCompoundTag("powerProvider").getDouble("energyStored"));
		bytes.writeInt(this._data.getInteger("BurnTime"));
		bytes.writeInt(this._data.getInteger("CookTime"));
	}

	@Override
	public void executeClient(EntityPlayer player) {
		TileEntity te = player.worldObj.getTileEntity(this._x, this._y, this._z);
		if (te instanceof TileEntityCracker) {
			((TileEntityCracker)te).updateFromPacket(this._data);
		}
	}

	@Override
	public void executeServer(EntityPlayer player) { }

}
