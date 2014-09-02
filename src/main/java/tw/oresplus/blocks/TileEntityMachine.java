package tw.oresplus.blocks;

import java.util.LinkedList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.OresPlus;
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

public abstract class TileEntityMachine extends TileEntity 
implements IPowerReceptor, ITriggerProvider, ISidedInventory {
	private boolean _hasFurnace = false;
	protected int _furnaceBurnTime;
	protected int _currentItemBurnTime;
	protected float _energyRequired;
	protected float _energySpent;
	protected float _efficiancy = 1.0F;
	protected float _minimumBCEnergyRequired = 25.0F;
	protected float _maximumBCEnergyStored = 1500.0F;
	
	protected float _energyBuffer;
	
    protected String inventoryName;
    protected String customInventoryName = "";
    
	protected PowerHandler powerHandler;
	
	protected ItemStack[] inventory;
	private int furnaceSlot = -1;
	
	protected NBTTagCompound updateData = new NBTTagCompound();
	
	protected int logTicker;
	
	public TileEntityMachine(float energyRequired) {
		super();
		
		this._energyRequired = energyRequired;
		this._energySpent = 0.0F;
		this._energyBuffer= 0.0F;
		
		this.powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
		this.initPowerHandler();
		
		this.logTicker = 0;
	}
	
	public TileEntityMachine setMinimumBCEnergyRequired(float minimumBCEnergyRequired) {
		this._minimumBCEnergyRequired = minimumBCEnergyRequired;
		this.initPowerHandler();
		return this;
	}
	
	public TileEntityMachine setMaximumBCEnergyStored(float maximumBCEnergyStored) {
		this._maximumBCEnergyStored = maximumBCEnergyStored;
		this.initPowerHandler();
		return this;
	}
	
	public TileEntityMachine setEfficiancy(float efficiancy) {
		this._efficiancy = efficiancy;
		return this;
	}
	
	public TileEntityMachine initFurnace(int slotNum) {
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
		this._energySpent = tagCompound.getFloat("energySpent");
		this._energyBuffer = tagCompound.getFloat("energyBuffer");
		
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
		tagCompound.setFloat("energySpent", this._energySpent);
		tagCompound.setFloat("energyBuffer", this._energyBuffer);
		
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
	public void doWork(PowerHandler workProvider) {
		//this.debug("BC: Using energy");
		this._energyBuffer += this.powerHandler.useEnergy(100.0F, 100.0F, true);
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.logTicker++ > 20) {
			this.logTicker = 0;
		}
		//this.debug("Ticking");
		this.burnFuel();
		this.powerHandler.update();
		//this.debug("Stored Energy: " + this._energyBuffer);
	}
	
	private void debug(String message) {
		if (true) {
		//if (this.logTicker == 0) {
			OresPlus.log.debug("Machine: " + message);
		}
	}

	private boolean canBurn() {
		return this._hasFurnace && this.furnaceSlot >= 0;
	}
	
	private void burnFuel() {
		//this.debug("Burning fuel");
		
		if (!this.canBurn()) {
			return;
		}
		
		boolean wasBurning = this._furnaceBurnTime > 0;
		boolean needsSave = false;
		
		// tick burning fuel
		if (this._furnaceBurnTime > 0) {
			this._furnaceBurnTime--;
		}
		
		//this.debug("furnaceBurnTime: " + this._furnaceBurnTime);
		//this.debug("wasBurning: " + wasBurning);
		
		if (!this.worldObj.isRemote) {
			// start burn if needed
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
			// update on/off state
			if (wasBurning != this._furnaceBurnTime > 0) {
				needsSave = true;
				BlockMachine.updateMachineBlockState(this._furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		// add energy to power handler 
		if (this._furnaceBurnTime > 0) {
			this.addEnergy(1.0F);
		}
		
		// save if nessesary
		if (needsSave) { 
			this.markDirty();
		}
	}
	
	public boolean isBurning() {
		return true;
	}
	
	public void addEnergy(float energy) {
		this._energyBuffer += energy;
	}
	
	public boolean useEnergy(float energyNeeded) {
		if (this._energyBuffer < energyNeeded) {
			return false;
		}
		else {
			this._energyBuffer -= energyNeeded;
			return true;
		}
	}
	
	public double getEnergyStored() {
		return this._energyBuffer;
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
	
	public abstract void sendUpdatePacket();
	
	public abstract void recieveUpdatePacket(NBTTagCompound data);

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale) {
		if (this._currentItemBurnTime == 0)
			this._currentItemBurnTime = 200;
		return this._furnaceBurnTime * scale / this._currentItemBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getWorkProgressScaled(int scale) {
		return (int)(this._energySpent * scale / this._energyRequired);
	}
	
	@SideOnly(Side.CLIENT)
	public int getPowerLevelScaled(int scale) {
		return (int)(this._energyBuffer * 32 / this._maximumBCEnergyStored);
	}
}
