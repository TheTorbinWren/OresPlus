package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class NewTileEntityGrinder extends NewTileEntityMachine {
	private static final int sourceItemSlot = 0;
	private static final int furnaceItemSlot = 1;
	private static final int currentItemSlot = 2;
	private static final int outputItemSlot = 3;
	
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {3, 1};
    private static final int[] slotsSides = new int[] {1};

    public NewTileEntityGrinder() {
		super(1200.0F);
		this.inventory = new ItemStack[4];
		this.inventoryName = "container:grinder";
		this.initFurnace(furnaceItemSlot);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.hasWork()) {
			float energyRequired = this._minimumBCEnergyRequired / this._efficiancy;
			if (this.powerHandler.useEnergy(energyRequired, energyRequired, true) != energyRequired)
				return;
			this.grindItem((int)energyRequired);
		}
	}
	
	private void grindItem(int grindAmount) {
		if (this._energySpent == 0.0F) {
			this.startGrind();
		}
		if (this.inventory[currentItemSlot] != null) {
			this._energySpent += grindAmount;
			if (this._energySpent >= this._energyRequired) {
				this._energySpent -= this._energyRequired;
				this.finishGrind();
				this.markDirty();
			}
		}
	}
	
	private void startGrind() {
		if (this.inventory[currentItemSlot] == null && this.inventory[sourceItemSlot] != null) {
			this.inventory[currentItemSlot] = this.inventory[sourceItemSlot].splitStack(1);
			if (this.inventory[sourceItemSlot].stackSize == 0)
				this.inventory[sourceItemSlot] = null;
		}
		else if (this.inventory[currentItemSlot].isItemEqual(this.inventory[sourceItemSlot])) {
			--this.inventory[sourceItemSlot].stackSize;
			if (this.inventory[sourceItemSlot].stackSize == 0)
				this.inventory[sourceItemSlot] = null;
		}
	}
	
	private void finishGrind() {
		ItemStack grindResult = Ores.grinderRecipes.getResult(this.inventory[currentItemSlot]);
		if (this.inventory[outputItemSlot] == null) {
			this.inventory[outputItemSlot] = grindResult.copy();
		}
		else if (this.inventory[outputItemSlot].isItemEqual(grindResult)) {
			this.inventory[outputItemSlot].stackSize += grindResult.stackSize;
		}
		
		if (this.inventory[sourceItemSlot] == null) {
			this.inventory[currentItemSlot] = null;
			this._energySpent = 0.0f;
		}
	}

	@Override
	public boolean hasWork() {
		if (this.inventory[sourceItemSlot] == null && this.inventory[currentItemSlot] == null)
			return false;
		
		ItemStack grindResult = Ores.grinderRecipes.getResult(this.inventory[currentItemSlot] != null ? this.inventory[currentItemSlot] : this.inventory[sourceItemSlot]);
		if (grindResult == null)
			return false;
		
		if (this.inventory[outputItemSlot] == null)
			return true;
		
		if (!this.inventory[outputItemSlot].isItemEqual(grindResult))
			return false;
		
		int resultSize = grindResult.stackSize + this.inventory[outputItemSlot].stackSize;
		return resultSize <= this.getInventoryStackLimit() && resultSize <= this.inventory[outputItemSlot].getMaxStackSize();
	}

	/*
	 * ISidedInventory methods
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		switch (slot) {
		case sourceItemSlot:
			return Ores.grinderRecipes.getResult(item) != null;
		case furnaceItemSlot:
			return FuelHelper.isItemFuel(item);
		default:
			return false;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return (side != 0 || (slot != furnaceItemSlot && slot != currentItemSlot) || (item.getItem() == Items.bucket && slot == furnaceItemSlot));
	}

}
