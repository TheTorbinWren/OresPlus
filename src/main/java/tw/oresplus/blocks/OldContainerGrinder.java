package tw.oresplus.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.recipes.RecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class OldContainerGrinder 
extends Container {
	private OldTileEntityGrinder teGrinder;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

	public OldContainerGrinder(InventoryPlayer inventory, OldTileEntityGrinder te){
		this.teGrinder = te;
        this.addSlotToContainer(new Slot(te, 0, 32, 20));
        this.addSlotToContainer(new Slot(te, 1, 79, 56));
        this.addSlotToContainer(new SlotFurnace(inventory.player, te, 2, 135, 21));
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
		return this.teGrinder.isUseableByPlayer(player);
	}

    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            itemstack = slotStack.copy();
            
            if (slotNum == 2) {
            	if (!this.mergeItemStack(slotStack, 3, 39, false))
            		return null;
            	slot.onSlotChange(slotStack, itemstack);
            } 
            else if (slotNum != 0 && slotNum != 1) {
            	if (FuelHelper.isItemFuel(slotStack)) {
            		if (!this.mergeItemStack(slotStack, 1, 2, false))
            			return null;
            	}
            	else if (Ores.grinderRecipes.getResult(slotStack) != null) {
            		if (!this.mergeItemStack(slotStack, 0, 1, false))
            			return null;
            	} 
            	else if (slotNum >= 3 && slotNum < 30) {
            		if (!this.mergeItemStack(slotStack, 30, 39, false))
            			return null;
            	}
            	else if (slotNum >= 30 && slotNum < 39) {
            		if (!this.mergeItemStack(slotStack, 3, 30, false))
            			return null;
            	}
            } 
            else if (!this.mergeItemStack(slotStack, 3, 39, false)) {
                return null;
            }

            if (slotStack.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (slotStack.stackSize == itemstack.stackSize) 
            	return null;
            slot.onPickupFromSlot(player, slotStack);
        }

        return itemstack;
    }
    
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.teGrinder.machineWorkTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.teGrinder.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.teGrinder.currentItemBurnTime);
    }
    
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.teGrinder.machineWorkTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.teGrinder.machineWorkTime);
            }

            if (this.lastBurnTime != this.teGrinder.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.teGrinder.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.teGrinder.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.teGrinder.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.teGrinder.machineWorkTime;
        this.lastBurnTime = this.teGrinder.furnaceBurnTime;
        this.lastItemBurnTime = this.teGrinder.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.teGrinder.machineWorkTime = par2;
        }

        if (par1 == 1)
        {
            this.teGrinder.furnaceBurnTime = par2;
        }

        if (par1 == 2)
        {
            this.teGrinder.currentItemBurnTime = par2;
        }
    }
}
