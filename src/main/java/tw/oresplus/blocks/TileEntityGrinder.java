package tw.oresplus.blocks;

import java.util.LinkedList;

import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.This;
import tw.oresplus.OresPlus;
import tw.oresplus.core.FuelHelper;
import tw.oresplus.recipes.RecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGrinder 
extends TileEntityMachine {
	
	public TileEntityGrinder() {
		super(1200);
		this.inventory = new ItemStack[3];
		this.inventoryName = "container:grinder";
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		if (this.hasWork()) {
			if (this.powerHandler.useEnergy(this.energyRequired, this.energyRequired, true) != this.energyRequired)
				return;
			doGrind((int)(this.energyRequired * 1.5));
		}
	}

	private void grindItem() {
		if (!this.hasWork())
			return;
		ItemStack item = RecipeManager.getGrinderRecipeResult(this.currentItem);
		if (this.inventory[2] == null)
			this.inventory[2] = item.copy();
		else if (this.inventory[2].getItem() == item.getItem())
			this.inventory[2].stackSize += item.stackSize;
		
		if (this.inventory[0] == null) {
			this.currentItem = null;
			this.machineWorkTime = 0;
		}
	}
	
	public void updateEntity() {
		super.updateEntity();
		
		this.burnFuel();
		
		if (!this.worldObj.isRemote) {
			
			if (this.isBurning() && this.hasWork())
				doGrind(1);
			
		}
		
	}

	private void doGrind(int grindAmount) {
		if (this.machineWorkTime == 0)
			this.startGrind();
		if (this.currentItem != null) {
			this.machineWorkTime += grindAmount;
			if (this.machineWorkTime >= this.workTimeNeeded) {
				this.machineWorkTime -= this.workTimeNeeded;
				this.grindItem();
				this.markDirty();
			}
		}
	}
	
	private void startGrind() {
		if (this.currentItem == null && this.inventory[0] != null) {
			this.currentItem = this.inventory[0].copy();
			--this.inventory[0].stackSize;
			if (this.inventory[0].stackSize == 0)
				this.inventory[0] = null;
		}
		else if (this.inventory[0].isItemEqual(this.currentItem)) {
			--this.inventory[0].stackSize;
			if (this.inventory[0].stackSize == 0)
				this.inventory[0] = null;
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == 2 ? false : (slot == 1 ? FuelHelper.isItemFuel(item) : true);
	}
	
	@Override
	public boolean hasWork() {
		if (this.inventory[0] == null && this.currentItem == null)
			return false;
		
		ItemStack item = RecipeManager.getGrinderRecipeResult(this.currentItem != null ? this.currentItem : this.inventory[0]);
		if (item == null) {
			return false;
		}
		if (this.inventory[2] == null) {
			return true;
		}
		if (!this.inventory[2].isItemEqual(item)) {
			return false;
		}
		int result = this.inventory[2].stackSize + item.stackSize;
		
		return result <= this.getInventoryStackLimit() && result <= inventory[2].getMaxStackSize();
	}

	@Override
	public LinkedList<ITrigger> getPipeTriggers(IPipeTile pipe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
		// TODO Auto-generated method stub
		return null;
	}
}
