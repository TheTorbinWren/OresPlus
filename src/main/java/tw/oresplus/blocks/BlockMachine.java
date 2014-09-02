package tw.oresplus.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMachine
extends BlockCore 
implements ITileEntityProvider {
	private final Random blockRandom = new Random();
	protected static boolean isLocked;
	protected IIcon[] iconArray = new IIcon[3];
	protected boolean _isWorking;
	protected int guiID;

	public BlockMachine(boolean isActive, String machineName) {
		super(Material.rock, machineName, false);
		this.setHardness(3.5F);
		this.setStepSound(soundTypePiston);
		if (isActive)
			this.setLightLevel(0.875F);
	}
	
	public void onBlockAddded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.func_149930_e(world, x, y, z);
	}
	
    private void func_149930_e(World world, int x, int y, int z) {
    	Block block1 = world.getBlock(x, y, z - 1);
    	Block block2 = world.getBlock(x, y, z + 1);
    	Block block3 = world.getBlock(x - 1, y, z);
    	Block block4 = world.getBlock(x + 1, y, z);
    	byte meta = 3;
    	
    	if (block1.func_149730_j() && !block2.func_149730_j()) {
    		meta = 3;
    	}
    	if (block2.func_149730_j() && !block1.func_149730_j()) {
    		meta = 2;
    	}
    	if (block3.func_149730_j() && !block4.func_149730_j()) {
    		meta = 5;
    	}
    	if (block4.func_149730_j() && !block3.func_149730_j()) {
    		meta = 4;
    	}
    	
    	world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	int metadata = meta == 0 ? 3 : meta;
    	if (side == 1 || side == 0)
    		return this.iconArray[0];
    	else if (side == metadata)
    		return this.iconArray[2];
    	return this.iconArray[1];
    }
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        this.iconArray[0] = icon.registerIcon(OresPlus.MOD_ID + ":machine_top");
        this.iconArray[1] = icon.registerIcon(OresPlus.MOD_ID + ":machine_side");
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        
        if (l == 0) {
        	world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
        	world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
        	world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l ==3 ) {
        	world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
    
    public void breakBlock(World world, int x, int y, int z, Block block, int var6)
    {
        if (!isLocked)
        {
            TileEntityMachine te = (TileEntityMachine)world.getTileEntity(x, y, z);

            if (te != null)
            {
                for (int i1 = 0; i1 < te.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = te.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.blockRandom.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.blockRandom.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.blockRandom.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.blockRandom.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.blockRandom.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.blockRandom.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.blockRandom.nextGaussian() * f3);
                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, var6);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        if (this._isWorking)
        {
            int l = world.getBlockMetadata(x, y, z);
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

            if (l == 4)
            {
            	world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
            	world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 2)
            {
            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 3)
            {
            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (player.isSneaking())
			return false;
		player.openGui(OresPlus.instance, this.guiID, world, x, y, z);
		return true;
    }
	
	public abstract TileEntity createNewTileEntity(World var1, int var2);
	
    public static void updateMachineBlockState(boolean isBurning, World world, int x, int y, int z) {
    	int meta = world.getBlockMetadata(x, y, z);
    	TileEntity te = world.getTileEntity(x, y, z);
    	
    	isLocked = true;
    	if (te instanceof TileEntityGrinder) {
	    	if (isBurning) 
	    		world.setBlock(x, y, z, BlockHelper.getBlock(BlockManager.grinder_lit));
	    	else 
	    		world.setBlock(x, y, z, BlockHelper.getBlock(BlockManager.grinder));
    	}
    	else if (te instanceof OldTileEntityCracker) {
	    	if (isBurning) 
	    		world.setBlock(x, y, z, BlockHelper.getBlock(BlockManager.cracker_lit));
	    	else 
	    		world.setBlock(x, y, z, BlockHelper.getBlock(BlockManager.cracker));
    	}
    	isLocked = false;
    	
    	world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    	
    	if (te != null) {
    		te.validate();
    		world.setTileEntity(x, y, z, te);
    	}
    }

}
