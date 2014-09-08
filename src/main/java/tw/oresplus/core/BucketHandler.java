package tw.oresplus.core;

import java.util.HashMap;

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
		ItemStack filledBucket = fillOreBucket(event.world, event.target);
		
		if (filledBucket == null) {
			return;
		}
		
		event.result = filledBucket;
		event.setResult(Result.ALLOW);
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
