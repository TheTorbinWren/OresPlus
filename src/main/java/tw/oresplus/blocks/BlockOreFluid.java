package tw.oresplus.blocks;

import tw.oresplus.OresPlus;
import tw.oresplus.core.References;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockOreFluid extends BlockFluidClassic {
	private IIcon _stillIcon;
	private IIcon _flowingIcon;
	
	public BlockOreFluid(Fluid fluid, String name) {
		super(fluid, Material.lava);
		this.setBlockName(name);
		GameRegistry.registerBlock(this, name);
		this.textureName = References.MOD_ID + ":" + name;
	}

	@Override
	public void registerBlockIcons(IIconRegister icons) {
		this._stillIcon = icons.registerIcon(this.textureName);
		this._flowingIcon = icons.registerIcon(this.textureName + ".flowing");
		this.getFluid().setIcons(this._stillIcon, this._flowingIcon);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1 ? this._stillIcon : this._flowingIcon);
	}
}
