package tw.oresplus.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGrinder extends BlockMachine {

	public BlockGrinder(boolean isGrinding) {
		super(isGrinding ? "grinderLit" : "grinder");
		this._isWorking = isGrinding;
		if (!isGrinding)
			this.setCreativeTab(CreativeTabs.tabDecorations);
		this.guiID = 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityGrinder();
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
    	super.registerBlockIcons(icon);
        this.iconArray[2] = icon.registerIcon(this._isWorking ? OresPlus.MOD_ID + ":grinder_front_on" : OresPlus.MOD_ID + ":grinder_front_off");
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(OresPlusAPI.getBlock("grinder"));
    }

}
