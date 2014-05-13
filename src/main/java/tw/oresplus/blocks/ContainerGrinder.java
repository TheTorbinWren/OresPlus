package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerGrinder extends ContainerMachine {

	public ContainerGrinder(InventoryPlayer inventory, TileEntityGrinder te) {
		this.tileEntity = te;
		this.addSlotToContainer(new Slot(te, te.sourceItemSlot, 32, 20));
		this.addSlotToContainer(new Slot(te, te.furnaceItemSlot, 79, 56));
		this.addSlotToContainer(new Slot(te, te.currentItemSlot, 79, 20));
		this.addSlotToContainer(new SlotFurnace(inventory.player, te, te.outputItemSlot, 135, 21));
		this.addPlayerInventory(inventory);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
		TileEntityGrinder te = (TileEntityGrinder)this.tileEntity;
		ItemStack itemStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemStack = slotStack.copy();
			
			if (slotNum == te.outputItemSlot) {
            	if (!this.mergeItemStack(slotStack, 4, 40, false))
            		return null;
            	slot.onSlotChange(slotStack, itemStack);
			}
			else if (slotNum >= 4) {
				if (FuelHelper.isItemFuel(slotStack)) {
					if (!this.mergeItemStack(slotStack, te.furnaceItemSlot, te.furnaceItemSlot + 1, false))
						return null;
				}
				else if (Ores.grinderRecipes.getResult(slotStack) != null) {
					if (!this.mergeItemStack(slotStack, te.sourceItemSlot, te.sourceItemSlot + 1, false))
						return null;
				}
				else if (slotNum >= 4 && slotNum < 31) {
					if (!this.mergeItemStack(slotStack, 31, 40, false))
						return null;
				}
				else if (slotNum >= 31 && slotNum < 40) {
					if (!this.mergeItemStack(slotStack, 4, 31, false))
						return null;
				}
			}
			else if (!this.mergeItemStack(slotStack, 4, 40, false)) {
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
	
	@Override
	public void detectAndSendChanges() {
		((TileEntityGrinder)this.tileEntity).sendUpdatePacket();
	}
}
