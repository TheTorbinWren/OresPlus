package tw.oresplus.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerMachine extends Container {
	protected TileEntityMachine tileEntity;
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
	}
	
	protected void addPlayerInventory(InventoryPlayer inventory) {
        int row; int column;

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                this.addSlotToContainer(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (column = 0; column < 9; ++column) {
            this.addSlotToContainer(new Slot(inventory, column, 8 + column * 18, 142));
        }
	}
	
}
