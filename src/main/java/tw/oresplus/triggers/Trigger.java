package tw.oresplus.triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;

public abstract class Trigger implements ITrigger {

	public abstract String getUniqueTag();

	public abstract IIcon getIcon();
	
	public abstract void registerIcons(IIconRegister iconRegister);

	public abstract boolean hasParameter();

	public abstract boolean requiresParameter();

	public abstract String getDescription();

	public abstract ITriggerParameter createParameter();
}
