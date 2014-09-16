package tw.oresplus.core;

import java.util.HashMap;

import tw.oresplus.OresPlus;
import tw.oresplus.items.OreBucket;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {
	//public static HashMap<Block, Item> bucketMap = new HashMap<Block, Item>();
	
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		if (event.current.getItem() instanceof OreBucket) {
			/*
			if (event.target == null) {
				return;
			}
			else {
				int clickX = event.target.blockX;
				int clickY = event.target.blockY;
				int clickZ = event.target.blockZ;
				
				if (!event.world.canMineBlock(event.entityPlayer, clickX, clickY, clickZ)) {
					return;
				}
				
				switch (event.target.sideHit) {
				case 0:
					--clickY;
					break;
				case 1:
					++clickY;
					break;
				case 2:
					--clickZ;
					break;
				case 3:
					++clickZ;
					break;
				case 4:
					--clickX;
					break;
				case 5:
					++clickX;
					break;
				}
				
				if (!event.entityPlayer.canPlayerEdit(clickX, clickY, clickZ, event.target.sideHit, event.current)) {
					return;
				}
				
				if (this.placeLiquid(event.world, clickX, clickY, clickZ, event.current) && !event.entityPlayer.capabilities.isCreativeMode) {
					event.result = new ItemStack(net.minecraft.init.Items.bucket);
					event.setResult(Result.ALLOW);
				}
			}
			*/
		}
		else {
			ItemStack filledBucket = fillOreBucket(event.world, event.target);
			
			if (filledBucket == null) {
				return;
			}
			
			event.result = filledBucket;
			event.setResult(Result.ALLOW);
		}
	}

	private boolean placeLiquid(World world, int blockX, int blockY, int blockZ, ItemStack item) {
		if (!world.getBlock(blockX, blockY, blockZ).isAir(world, blockY, blockY, blockZ)
				&& world.getBlock(blockX, blockY, blockZ).getMaterial().isSolid()) {
			return false;
		}
		try {
			Block block = FluidContainerRegistry.getFluidForFilledItem(item).getFluid().getBlock();
			
			if (block == null) {
				return false;
			}
			
			world.setBlock(blockX, blockY, blockZ, block, 0, 3);
		}
		catch (Exception e) {
			OresPlus.log.info("ERROR: caught exception placing liquid block" + e);
			return false;
		}
		return true;
	}

	private ItemStack fillOreBucket(World world, MovingObjectPosition target) {
		Block targetBlock = world.getBlock(target.blockX, target.blockY, target.blockZ);
		if (!(targetBlock instanceof BlockFluidClassic)) {
			return null;
		}
		ItemStack filledBucket = FluidContainerRegistry.fillFluidContainer(new FluidStack(((BlockFluidClassic)targetBlock).getFluid(), 1000), new ItemStack(net.minecraft.init.Items.bucket));
		if (filledBucket != null && world.getBlockMetadata(target.blockX, target.blockY, target.blockZ) == 0) {
			world.setBlockToAir(target.blockX, target.blockY, target.blockZ);
			return filledBucket;
		}
		else {
			return null;
		}
	}
}
