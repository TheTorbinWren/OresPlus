package tw.oresplus.blocks;

import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import tw.oresplus.OresPlus;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.recipes.RecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityMachine 
extends TileEntity 
implements ISidedInventory, IPowerReceptor, ITriggerProvider {
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
	
	protected ItemStack[] inventory;
    //private abstract ItemStack[] inventory;
	
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int machineWorkTime;
	protected final int workTimeNeeded; 
    
    protected String inventoryName;
    protected String customInventoryName = "";
    
    protected PowerHandler powerHandler;
    
    protected float energyRequired = 25F;

	protected ItemStack currentItem;

    protected TileEntityMachine(int worktime) {
    	powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
    	this.initPower();
    	this.workTimeNeeded = worktime;
    }
    
    private void initPower() {
    	powerHandler.configure(50F, 100F, this.energyRequired, 1500F);
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
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customInventoryName : this.inventoryName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customInventoryName != "";
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
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	public abstract boolean isItemValidForSlot(int slot, ItemStack item);
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		
		powerHandler.readFromNBT(tagCompound);
		this.initPower();

		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);

		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound tag = tagList.getCompoundTagAt(i);

			byte slot = tag.getByte("Slot");

			if(slot >= 0 && slot < inventory.length){
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.machineWorkTime = tagCompound.getShort("CookTime");
		this.currentItemBurnTime = FuelHelper.getItemBurnTime(this.inventory[1]);
		
		NBTTagCompound itemTag = tagCompound.getCompoundTag("CurrentItem");
		if (itemTag != null) {
			this.currentItem = ItemStack.loadItemStackFromNBT(itemTag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		
		powerHandler.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short)this.machineWorkTime);
		
		NBTTagList itemList = new NBTTagList();

		for(int i = 0; i < inventory.length; i++){
			ItemStack stack = inventory[i];

			if(stack != null){
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
		
		if (this.currentItem != null) {
			NBTTagCompound itemTag = new NBTTagCompound(); 
			this.currentItem.writeToNBT(itemTag);
			tagCompound.setTag("CurrentItem", itemTag);
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
		return (side != 0 || slot != 1 || item.getItem() == Items.bucket);
	}
	
	public void updateEntity() {
		super.updateEntity();
		powerHandler.update();
	}

	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.powerHandler.getPowerReceiver();
	}

	@Override
	public abstract void doWork(PowerHandler workProvider);

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	public boolean canBurn() {
		return false;
	}
	
	public abstract boolean hasWork();
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale) {
		if (this.currentItemBurnTime == 0)
			this.currentItemBurnTime = 200;
		return this.furnaceBurnTime * scale / this.currentItemBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getWorkProgressScaled(int scale) {
		return this.machineWorkTime * scale / this.workTimeNeeded;
	}
	

	protected void burnFuel() {
		boolean wasBurning = this.furnaceBurnTime > 0;
		boolean needsSave = false;
		
		if (this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}
		
		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.hasWork()) {
				this.currentItemBurnTime = this.furnaceBurnTime = FuelHelper.getItemBurnTime(this.inventory[1]);
				if (this.furnaceBurnTime > 0) {
					this.markDirty();
					if (this.inventory[1] != null) {
						--this.inventory[1].stackSize;
						if (this.inventory[1].stackSize == 0)
							this.inventory[1] = this.inventory[1].getItem().getContainerItem(this.inventory[1]);
					}
				}
			}
			if (wasBurning != this.furnaceBurnTime > 0) {
                needsSave = true;
                BlockMachine.updateMachineBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		if (needsSave)
			this.markDirty();

	}
}
