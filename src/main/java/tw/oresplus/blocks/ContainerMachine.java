package tw.oresplus.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerMachine extends Container {
	public TileEntityMachine tileEntity;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    protected int pInvStart;

    protected void addPlayerInventory(InventoryPlayer inventory) {
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
	}

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tileEntity.machineWorkTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.tileEntity.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
    }
    
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tileEntity.machineWorkTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.machineWorkTime);
            }

            if (this.lastBurnTime != this.tileEntity.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.tileEntity.machineWorkTime;
        this.lastBurnTime = this.tileEntity.furnaceBurnTime;
        this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tileEntity.machineWorkTime = par2;
        }

        if (par1 == 1)
        {
            this.tileEntity.furnaceBurnTime = par2;
        }

        if (par1 == 2)
        {
            this.tileEntity.currentItemBurnTime = par2;
        }
    }

    public abstract ItemStack transferStackInSlot(EntityPlayer player, int slotNum);
}
