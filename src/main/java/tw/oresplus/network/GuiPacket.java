package tw.oresplus.network;

import tw.oresplus.OresPlus;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GuiPacket implements IPacket {
	private int _oilLevel;

	public GuiPacket() {
	}
	
	public GuiPacket(int oilLevel) {
		this ._oilLevel = oilLevel;
	}
	
	@Override
	public void readBytes(ByteBuf bytes) {
		this._oilLevel = bytes.readInt();
	}

	@Override
	public void writeBytes(ByteBuf bytes) {
		bytes.writeInt(this._oilLevel);
	}

	@Override
	public void executeClient(EntityPlayer player) {
		// TODO Auto-generated method stub
		OresPlus.log.info("Oil Level " + this._oilLevel);
	}

	@Override
	public void executeServer(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
