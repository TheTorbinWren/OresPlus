package tw.oresplus.network;

import net.minecraft.tileentity.TileEntity;
import tw.oresplus.blocks.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class PacketMachine implements IMessage {
	public int x, y, z;
	public byte orientation;
	public float energyBuffer;
	public int burnTime;

	public PacketMachine() { }
	
	public PacketMachine(TileEntityMachine te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.orientation = (byte) te.getOrientation().ordinal();
		this.burnTime = te.furnaceBurnTime;
		this.energyBuffer = te.energyBuffer.getEnergyStored();
	}
	
	@Override
	public void fromBytes(ByteBuf bytes) {
		this.x = bytes.readInt();
		this.y = bytes.readInt();
		this.z = bytes.readInt();
		this.orientation = bytes.readByte();
		this.burnTime = bytes.readInt();
		this.energyBuffer = bytes.readFloat();
}

	@Override
	public void toBytes(ByteBuf bytes) {
		bytes.writeInt(this.x);
		bytes.writeInt(this.y);
		bytes.writeInt(this.z);
		bytes.writeByte(this.orientation);
		bytes.writeInt(this.burnTime);
		bytes.writeFloat(this.energyBuffer);
}
	
	public IMessage onMessage(PacketMachine msg, MessageContext ctx) {
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(msg.x, msg.y, msg.z);
		
		if (te instanceof TileEntityMachine) {
			((TileEntityMachine)te).setOrientation(orientation);
			((TileEntityMachine)te).furnaceBurnTime = this.burnTime;
			((TileEntityMachine)te).energyBuffer.setEnergyBuffer(this.energyBuffer);
		}
		
		return null;
	}
}
