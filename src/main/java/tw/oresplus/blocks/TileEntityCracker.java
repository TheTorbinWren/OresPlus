package tw.oresplus.blocks;

import tw.oresplus.api.Ores;
import tw.oresplus.core.FuelHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityCracker 
extends TileEntityMachine 
implements IFluidHandler {

	public static final int sourceItemSlot = 0;
	public static final int furnaceItemSlot = 1;
	public static final int currentItemSlot = 2;
	
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {1};

    public TileEntityCracker() {
		super(1200.0F);
		this.inventory = new ItemStack[3];
		this.inventoryName = "container:cracker";
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.hasWork()) {
			// crack item
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		switch (slot) {
		case furnaceItemSlot:
			return FuelHelper.isItemFuel(item);
		case sourceItemSlot:
			return Ores.crackerRecipes.getResult(item) != null;
		default:
			return false;
		}
	}

	@Override
	public boolean hasWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendUpdatePacket() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recieveUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}

}
