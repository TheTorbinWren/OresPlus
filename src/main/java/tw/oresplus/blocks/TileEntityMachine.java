package tw.oresplus.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.energy.EnergyBuffer;
import tw.oresplus.network.NetHandler;
import tw.oresplus.network.PacketMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityMachine extends TileEntity 
implements ISidedInventory {
	class Tags {
		public static final String ORIENTATION = "orientation";
		public static final String BURN_TIME = "burnTime";
		public static final String WORK_DONE = "workDone";
		public static final String WORK_REQUIRED = "workRequired";
		public static final String WORK_EFFICIANCY = "workEfficiancy";
		public static final String INVENTORY = "inventory";
		public static final String SLOT = "slot";
	}
	
	// machine orientation
	protected ForgeDirection _orientation;
	
	// machine clock
	protected int ticker = 0;
	
	// machine energy buffer
	public EnergyBuffer energyBuffer;
	
	//machine furnace
	public int furnaceBurnTime;
	protected int _currentItemBurnTime;
	
	public float workDone;
	public float workRequired;
	public float workEfficiancy;
	
	public int startingSourceSlot = 0;
	public int numSourceSlots = 1;
	public int furnaceSlot = 1;
	public int interfaceSlot = 2;
	public int startingOutputSlot = 3;
	public int numOutputSlots = 1;
	
	protected ItemStack[] inventory;
	
	protected String inventoryName;
	protected String customInventoryName = "";
	
	public TileEntityMachine() {
		this._orientation = ForgeDirection.SOUTH;
		
		this.energyBuffer = new EnergyBuffer().setupBuffer(1000.0F, 0.0F, 0.01F);
		
		this.workDone = 0.0F;
		this.workRequired = 100.0F;
		this.workEfficiancy = 1.0F;
	}
	
	public void setOrientation(int orientation) {
		this._orientation = ForgeDirection.getOrientation(orientation);
	}
	
	public ForgeDirection getOrientation() {
		return this._orientation;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.energyBuffer.readFromNBT(tag);
		
		if (tag.hasKey(Tags.ORIENTATION)) {
			this._orientation = ForgeDirection.getOrientation(tag.getByte(Tags.ORIENTATION));
		}
		if (tag.hasKey(Tags.BURN_TIME)) {
			this.furnaceBurnTime = tag.getInteger(Tags.BURN_TIME);
		}
		if (tag.hasKey(Tags.WORK_DONE)) {
			this.workDone = tag.getFloat(Tags.WORK_DONE);
		}
		if (tag.hasKey(Tags.WORK_REQUIRED)) {
			this.workRequired = tag.getFloat(Tags.WORK_REQUIRED);
		}
		if (tag.hasKey(Tags.WORK_EFFICIANCY)) {
			this.workEfficiancy = tag.getFloat(Tags.WORK_EFFICIANCY);
		}
	
		if (tag.hasKey(Tags.INVENTORY)) {
			NBTTagList itemList = tag.getTagList(Tags.INVENTORY, 10);
			for (int i = 0; i < itemList.tagCount(); i++) {
				NBTTagCompound tagSlot = itemList.getCompoundTagAt(i);
				byte slot = tagSlot.getByte(Tags.SLOT);
				if (slot >=0 && slot < this.inventory.length) {
					this.inventory[slot] = ItemStack.loadItemStackFromNBT(tagSlot);
				}
			}
		}
}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		this.energyBuffer.writeToNBT(tag);
		
		tag.setByte(Tags.ORIENTATION, (byte)this._orientation.ordinal());
		tag.setInteger(Tags.BURN_TIME, this.furnaceBurnTime);
		tag.setFloat(Tags.WORK_DONE, this.workDone);
		tag.setFloat(Tags.WORK_REQUIRED, this.workRequired);
		tag.setFloat(Tags.WORK_EFFICIANCY, this.workEfficiancy);
	
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++) {
			ItemStack stack = this.inventory[i];
			if (stack != null) {
				NBTTagCompound tagSlot = new NBTTagCompound();
				tagSlot.setByte(Tags.SLOT, (byte) i);
				stack.writeToNBT(tagSlot);
				itemList.appendTag(tagSlot);
			}
		}
		tag.setTag(Tags.INVENTORY, itemList);
}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		this.ticker++;
		
		this.energyBuffer.update();
		
		this.burnFuel();
		// temporary power handler
		//this.energyBuffer.addEnergy(1.0F);
	}
	
	private void burnFuel() {
		boolean wasBurning = this.furnaceBurnTime > 0;
		boolean needsSave = false;
		
		// tick burning fuel
		if (this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}
		
		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.hasWork()) {
				this._currentItemBurnTime = this.furnaceBurnTime = FuelHelper.getItemBurnTime(this.inventory[this.furnaceSlot]);
				if (this.furnaceBurnTime > 0) {
					this.markDirty();
					if (this.inventory[this.furnaceSlot] != null) {
						--this.inventory[this.furnaceSlot].stackSize;
						if (this.inventory[this.furnaceSlot].stackSize == 0) {
							this.inventory[this.furnaceSlot] = this.inventory[this.furnaceSlot].getItem().getContainerItem(this.inventory[this.furnaceSlot]);
						}
					}
				}
			}
			if (wasBurning != this.furnaceBurnTime > 0) {
				needsSave = true;
				BlockMachine.updateMachineBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if (this.furnaceBurnTime > 0) {
			this.energyBuffer.addEnergy(1.0F);
		}
		
		if (needsSave) {
			this.markDirty();
		}
	}

	public abstract boolean hasWork();
	
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customInventoryName : this.inventoryName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customInventoryName != "";
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return (side != 0 || slot != this.furnaceSlot || (item.getItem() == Items.bucket && slot == this.furnaceSlot));
	}

	@Override
	public int getSizeInventory() {
		if (this.inventory == null)
			return 0;
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot > this.getSizeInventory())
			return null;
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.inventory != null) {
			ItemStack stack;
			if (this.inventory[slot].stackSize <= amount) {
				stack = this.inventory[slot];
				this.inventory[slot] = null;
			}
			else {
				stack = this.inventory[slot].splitStack(amount);
				if (this.inventory[slot].stackSize == 0)
					this.inventory[slot] = null;
			}
			return stack;
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
			ItemStack stack = this.inventory[slot];
			this.inventory[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	protected boolean isSourceSlot(int slot) {
		if (slot >= this.startingSourceSlot && slot < (this.startingSourceSlot + this.numSourceSlots)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (this.isSourceSlot(slot)) {
			return Ores.grinderRecipes.getResult(item) != null;
		}
		else if (slot == this.furnaceSlot) {
			return FuelHelper.isItemFuel(item);
		}
		else {
			return false;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getPowerLevelScaled(int scale) {
		return (int)(this.energyBuffer.getEnergyStored() / this.energyBuffer.getMaxEnergyStored() * scale);
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeScaled(int scale) {
		if (this._currentItemBurnTime == 0) {
			this._currentItemBurnTime = 200;
		}
		return this.furnaceBurnTime / this._currentItemBurnTime * scale;
	}

}
