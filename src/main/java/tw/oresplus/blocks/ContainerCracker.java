package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.items.OreItems;
import tw.oresplus.recipes.RecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCracker extends ContainerMachine {
	public ContainerCracker(InventoryPlayer inventory, TileEntityMachine te) {
		this.tileEntity = te;
        this.addSlotToContainer(new Slot(te, 0, 32, 20));
        this.addSlotToContainer(new Slot(te, 1, 79, 56));
        this.addPlayerInventory(inventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            itemstack = slotStack.copy();

            if (slotNum == 0 || slotNum == 1) {
            	if (!this.mergeItemStack(slotStack, 2, 38, false))
            		return null;
            }
            else {
            	if (FuelHelper.isItemFuel(slotStack)) {
            		if (!this.mergeItemStack(slotStack, 1, 2, false))
            			return null;
            	}
            	else if (slotStack.getItem() == OreItems.itemBitumen.item.source.getItem()) {
            		if (!this.mergeItemStack(slotStack, 0, 1, false))
            			return null;
            	} 
            	else if (slotNum >= 3 && slotNum < 30) {
            		if (!this.mergeItemStack(slotStack, 29, 38, false))
            			return null;
            	}
            	else if (slotNum >= 30 && slotNum < 39) {
            		if (!this.mergeItemStack(slotStack, 2, 29, false))
            			return null;
            	}
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

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		((TileEntityCracker)this.tileEntity).sendUpdate();
	}
}
