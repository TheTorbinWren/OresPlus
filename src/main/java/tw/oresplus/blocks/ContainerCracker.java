package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerCracker extends ContainerMachine {
	public ContainerCracker(InventoryPlayer inventory, TileEntityCracker te) {
		this.tileEntity = te;
		this.addSlotToContainer(new Slot(te, te.startingSourceSlot, 50, 20));
		this.addSlotToContainer(new Slot(te, te.furnaceSlot, 79, 64));
		this.addSlotToContainer(new Slot(te, te.interfaceSlot, 150, 10));
		this.addPlayerInventory(inventory);
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
		TileEntityCracker te = (TileEntityCracker)this.tileEntity;
		ItemStack itemStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemStack = slotStack.copy();
			
			if (slotNum >= 2) {
				if (FuelHelper.isItemFuel(slotStack)) {
					if (!this.mergeItemStack(slotStack, te.furnaceSlot, te.furnaceSlot + 1, false))
						return null;
				}
				else if (Ores.crackerRecipes.getResult(slotStack) != null) {
					if (!this.mergeItemStack(slotStack, te.startingSourceSlot, te.startingSourceSlot + 1, false))
						return null;
				}
				else if (slotNum >= 2 && slotNum < 29) {
					if (!this.mergeItemStack(slotStack, 29, 38, false))
						return null;
				}
				else if (slotNum >= 30 && slotNum < 38) {
					if (!this.mergeItemStack(slotStack, 2, 29, false))
						return null;
				}
			}
			else if (!this.mergeItemStack(slotStack, 2, 38, false)) {
				return null;
			}
			
            if (slotStack.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (slotStack.stackSize == itemStack.stackSize) 
            	return null;
            slot.onPickupFromSlot(player, slotStack);
		}
		
		return itemStack;
	}

}
