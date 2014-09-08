package tw.oresplus.blocks;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCracker extends BlockMachine {
	public BlockCracker(boolean isActive) {
		super(isActive, isActive ? "CrackerLit" : "cracker");
		this._isWorking = isActive;
		if (!isActive)
			this.setCreativeTab(CreativeTabs.tabDecorations);
		this.guiID = 1;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new OldTileEntityCracker();
	}

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
    	super.registerBlockIcons(icon);
    	this.iconArray[1] = icon.registerIcon(References.MOD_ID + ":cracker_side");
    	this.iconArray[2] = icon.registerIcon(References.MOD_ID + (this._isWorking ? ":cracker_front_on" : ":cracker_front_off"));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return BlockManager.cracker.source.getItem();
    }

}