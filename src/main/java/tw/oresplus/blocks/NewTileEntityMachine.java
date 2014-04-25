package tw.oresplus.blocks;

import java.util.LinkedList;

import tw.oresplus.core.FuelHelper;
import tw.oresplus.triggers.OresTrigger;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class NewTileEntityMachine extends TileEntity 
implements IPowerReceptor, ITriggerProvider, ISidedInventory {
    private boolean _hasFurnace = false;
	private boolean _furnaceBurning;
	private int _furnaceBurnTime;
	private int _currentItemBurnTime;
	protected float _energyRequired;
	protected float _energySpent;
	protected float _efficiancy = 1.0F;
	protected float _minimumBCEnergyRequired = 25.0F;
	protected float _maximumBCEnergyStored = 1500.0F;
	
    protected String inventoryName;
    protected String customInventoryName = "";
    
	protected PowerHandler powerHandler;
	
	protected ItemStack[] inventory;
	private int furnaceSlot = -1;
	
	public NewTileEntityMachine(float energyRequired) {
		super();
		
		this._energyRequired = energyRequired;
		this._energySpent = 0.0F;
		
		this.powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
		this.initPowerHandler();
	}
	
	public NewTileEntityMachine setMinimumBCEnergyRequired(float minimumBCEnergyRequired) {
		this._minimumBCEnergyRequired = minimumBCEnergyRequired;
		this.initPowerHandler();
		return this;
	}
	
	public NewTileEntityMachine setMaximumBCEnergyStored(float maximumBCEnergyStored) {
		this._maximumBCEnergyStored = maximumBCEnergyStored;
		this.initPowerHandler();
		return this;
	}
	
	public NewTileEntityMachine setEfficiancy(float efficiancy) {
		this._efficiancy = efficiancy;
		return this;
	}
	
	public NewTileEntityMachine initFurnace(int slotNum) {
		if (slotNum >= 0) {
			this._hasFurnace = true;
			this.furnaceSlot = slotNum;
		}
		return this;
	}
	
	private void initPowerHandler() {
		this.powerHandler.configure(50.0F, 100.0F, this._minimumBCEnergyRequired, this._maximumBCEnergyStored);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		this.powerHandler.readFromNBT(tagCompound);
		this._minimumBCEnergyRequired = tagCompound.getFloat("minBCEnergy");
		this._maximumBCEnergyStored = tagCompound.getFloat("maxBCEnergy");
		this.initPowerHandler();
		
		this._hasFurnace = tagCompound.getBoolean("hasFurnace");
		this.furnaceSlot = tagCompound.getInteger("furnaceSlot");
		this._efficiancy = tagCompound.getFloat("efficiency");
		this._energyRequired = tagCompound.getFloat("energyRequired");
		
		NBTTagList itemList = tagCompound.getTagList("inventory", 10);
		for (int i = 0; i < itemList.tagCount(); i++) {
			NBTTagCompound tag = itemList.getCompoundTagAt(i);
			byte slot = tag.getByte("slot");
			if (slot >=0 && slot < this.inventory.length) {
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		
		if (this.canBurn()) {
			this._furnaceBurnTime = tagCompound.getInteger("furnaceBurnTime");
			this._currentItemBurnTime = FuelHelper.getItemBurnTime(this.inventory[this.furnaceSlot]);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		this.powerHandler.writeToNBT(tagCompound);
		tagCompound.setFloat("minBCEnergy", this._minimumBCEnergyRequired);
		tagCompound.setFloat("maxBCEnergy", this._maximumBCEnergyStored);
		
		tagCompound.setBoolean("hasFurnace", this._hasFurnace);
		tagCompound.setInteger("furnaceSlot", this.furnaceSlot);
		tagCompound.setFloat("efficiency", this._efficiancy);
		tagCompound.setFloat("energyRequired", this._energyRequired);
		
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++) {
			ItemStack stack = this.inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("inventory", itemList);
		
		if (this.canBurn()) {
			tagCompound.setInteger("furnaceBurnTime", this._furnaceBurnTime);
		}
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.burnFuel();
		this.powerHandler.update();
	}
	
	private boolean canBurn() {
		return this._hasFurnace && this.furnaceSlot >= 0;
	}
	
	private void burnFuel() {
		if (!this.canBurn()) {
			return;
		}
		
		boolean wasBurning = this._furnaceBurnTime > 0;
		boolean needsSave = false;
		
		if (this._furnaceBurnTime > 0) {
			this._furnaceBurnTime--;
		}
		
		if (!this.worldObj.isRemote) {
			if (this._furnaceBurnTime == 0 && this.hasWork()) {
				this._currentItemBurnTime = this._furnaceBurnTime = FuelHelper.getItemBurnTime(this.inventory[this.furnaceSlot]);
				if (this._furnaceBurnTime > 0) {
					this.markDirty();
					if (this.inventory[this.furnaceSlot] != null) {
						--this.inventory[this.furnaceSlot].stackSize;
						if (this.inventory[this.furnaceSlot].stackSize == 0) {
							this.inventory[this.furnaceSlot] = this.inventory[this.furnaceSlot].getItem().getContainerItem(this.inventory[this.furnaceSlot]);
						}
					}
				}
			}
			if (wasBurning != this._furnaceBurnTime > 0) {
				needsSave = true;
				BlockMachine.updateMachineBlockState(this._furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		if (this._furnaceBurnTime > 0) {
			this.powerHandler.addEnergy(1);
		}
		
		if (needsSave) { 
			this.markDirty();
		}
	}
	
	public abstract boolean hasWork();

	@Override
	public LinkedList<ITrigger> getPipeTriggers(IPipeTile pipe) {
		return null;
	}

	@Override
	public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
		LinkedList triggers = new LinkedList();
		triggers.add(OresTrigger.hasWork);
		triggers.add(OresTrigger.workDone);
		return triggers;
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

}
