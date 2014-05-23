package tw.oresplus.triggers;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.OldTileEntityMachine;
import tw.oresplus.blocks.TileEntityMachine;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.gates.ITileTrigger;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerMachine 
extends Trigger 
implements ITileTrigger {
	private IIcon icon;
	private boolean active;
	
	public TriggerMachine(boolean checkActive) {
		this.active = checkActive;
	}
	
	@Override
	public String getUniqueTag() {
		return OresPlus.MOD_ID + ":" + (this.active ? "HasWork" : "WorkDone");
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = iconRegister.registerIcon(OresPlus.MOD_ID + ":triggers/" + (this.active ? "hasWork" : "workDone"));
		
	}

	@Override
	public boolean hasParameter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requiresParameter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		return this.active ? "Has work" : "Work Done";
	}

	@Override
	public ITriggerParameter createParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTriggerActive(ForgeDirection direction, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof TileEntityMachine) {
			if (this.active)
				return ((TileEntityMachine)tile).hasWork();
			return !((TileEntityMachine)tile).hasWork();
		}
		else if (tile instanceof OldTileEntityMachine) {
			if (this.active)
				return ((OldTileEntityMachine)tile).hasWork();
			return !((OldTileEntityMachine)tile).hasWork();
		}
		return false;
	}

	@Override
	public ITrigger rotateLeft() {
		// TODO Auto-generated method stub
		return null;
	}
}
