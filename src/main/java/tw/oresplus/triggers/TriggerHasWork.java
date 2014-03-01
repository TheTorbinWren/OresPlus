package tw.oresplus.triggers;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.TileEntityMachine;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerHasWork extends Trigger {
	private IIcon triggerIcon;
	
	@Override
	public String getUniqueTag() {
		return OresPlus.MOD_ID + ":HasWork";
	}

	@Override
	public IIcon getIcon() {
		return triggerIcon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.triggerIcon = iconRegister.registerIcon(OresPlus.MOD_ID + ":triggers/hasWork.png");
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITriggerParameter createParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTriggerActive(ForgeDirection direction, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof TileEntityMachine)
			return ((TileEntityMachine) tile).hasWork();
		return false;
	}
}
