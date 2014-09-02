package tw.oresplus.blocks;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.network.PacketUpdateGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityGrinder extends TileEntityMachine {
	public static final int sourceItemSlot = 0;
	public static final int furnaceItemSlot = 1;
	public static final int currentItemSlot = 2;
	public static final int outputItemSlot = 3;
	
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {3, 1};
    private static final int[] slotsSides = new int[] {1};
    
    public TileEntityGrinder() {
		super(1200.0F);
		this.inventory = new ItemStack[4];
		this.inventoryName = "container:grinder";
		this.initFurnace(furnaceItemSlot);
		this.logTicker = 0;
	}
	
	@Override
	public void updateEntity() {
		
		super.updateEntity();
		if (this.hasWork()) {
			float energyRequired = this._minimumBCEnergyRequired / this._efficiancy;
			/*
			if (this.powerHandler.useEnergy(energyRequired, energyRequired, true) != energyRequired)
				return;
			*/
			if (this.useEnergy(energyRequired)) {
				this.grindItem((int)energyRequired);
			}
		}
	}
	
	private void grindItem(int grindAmount) {
		if (this._energySpent == 0.0F) {
			this.startGrind();
		}
		if (this.inventory[currentItemSlot] != null) {
			this._energySpent += grindAmount;
			if (this._energySpent >= this._energyRequired) {
				this.finishGrind();
			}
		}
	}
	
	private void startGrind() {
		if (this.inventory[currentItemSlot] == null && this.inventory[sourceItemSlot] != null) {
			this.inventory[currentItemSlot] = this.inventory[sourceItemSlot].splitStack(1);
			if (this.inventory[sourceItemSlot].stackSize == 0)
				this.inventory[sourceItemSlot] = null;
		}
	}
	
	private void finishGrind() {
		// Add result to output slot
		ItemStack grindResult = Ores.grinderRecipes.getResult(this.inventory[currentItemSlot]);
		if (this.inventory[outputItemSlot] == null) {
			this.inventory[outputItemSlot] = grindResult.copy();
		}
		else if (this.inventory[outputItemSlot].isItemEqual(grindResult)) {
			this.inventory[outputItemSlot].stackSize += grindResult.stackSize;
		}
		
		if (this.inventory[sourceItemSlot] == null) {
			// source depleted, done grinding
			this.inventory[currentItemSlot] = null;
			this._energySpent = 0.0f;
		}
		else if (this.inventory[sourceItemSlot].isItemEqual(this.inventory[currentItemSlot])) {
			// Decrease source stack size
			--this.inventory[sourceItemSlot].stackSize;
			if (this.inventory[sourceItemSlot].stackSize == 0) {
				this.inventory[sourceItemSlot] = null;
			}
			this._energySpent -= this._energyRequired;
		}
		else {
			// source item does not match current item
			this.inventory[currentItemSlot] = null;
			this._energySpent = 0.0F;
		}
		
		// mark for saving
		this.markDirty();
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

	@Override
	public void sendUpdatePacket() {
		if (!this.worldObj.isRemote) {
			NBTTagCompound update = new NBTTagCompound();
			update.setFloat("energySpent", this._energySpent);
			update.setInteger("furnaceBurnTime", this._furnaceBurnTime);
			update.setInteger("currentItemBurnTime", this._currentItemBurnTime);
			update.setFloat("energyBuffer", this._energyBuffer);
			
			NBTTagCompound furnaceItem = new NBTTagCompound();
			if (inventory[furnaceItemSlot] != null) {
				inventory[furnaceItemSlot].writeToNBT(furnaceItem);
				update.setTag("furnaceItem", furnaceItem);
			}
			
			NBTTagCompound sourceItem = new NBTTagCompound();
			if (inventory[sourceItemSlot] != null) {
				inventory[sourceItemSlot].writeToNBT(sourceItem);
				update.setTag("sourceItem", sourceItem);
			}
			
			NBTTagCompound currentItem = new NBTTagCompound();
			if (inventory[currentItemSlot] != null) {
				inventory[currentItemSlot].writeToNBT(currentItem);
				update.setTag("currentItem", currentItem);
			}
			
			NBTTagCompound outputItem = new NBTTagCompound();
			if (inventory[outputItemSlot] != null) {
				inventory[outputItemSlot].writeToNBT(outputItem);				
				update.setTag("outputItem", outputItem);
			}
			
			if (!this.updateData.equals(update)) {
				OresPlus.netHandler.sendToPlayers(new PacketUpdateGrinder(update, this.xCoord, this.yCoord, this.zCoord));
				this.updateData = update;
			}
		}
	}
	
	@Override
	public void recieveUpdatePacket(NBTTagCompound data) {
		this._energySpent = data.getFloat("energySpent");
		this._furnaceBurnTime = data.getInteger("furnaceBurnTime");
		this._currentItemBurnTime = data.getInteger("currentItemBurnTime");
		this._energyBuffer = data.getFloat("energyBufer");
		if (!data.getCompoundTag("furnaceItem").hasNoTags()) {
			this.inventory[furnaceItemSlot].readFromNBT(data.getCompoundTag("furnaceItem"));
		}
		if (!data.getCompoundTag("sourceItem").hasNoTags()) {
			this.inventory[sourceItemSlot].readFromNBT(data.getCompoundTag("sourceItem"));
		}
		if (!data.getCompoundTag("currentItem").hasNoTags()) {
			this.inventory[currentItemSlot].readFromNBT(data.getCompoundTag("currentItem"));
		}
		if (!data.getCompoundTag("outputItem").hasNoTags()) {
			this.inventory[outputItemSlot].readFromNBT(data.getCompoundTag("outputItem"));
		}
	}

}
