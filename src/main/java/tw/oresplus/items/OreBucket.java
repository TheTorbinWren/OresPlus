package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.References;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class OreBucket extends ItemBucket {

	public OreBucket(String itemName, Block block) {
		super(block);
		this.setUnlocalizedName(itemName);
		this.setTextureName(References.MOD_ID + ":" + itemName);
		Ores.manager.registerOreItem(itemName, this);
		GameRegistry.registerItem(this, itemName);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(world, player, false);
		
		if (position == null) {
			return stack;
		}
		else {
			int clickX = position.blockX;
			int clickY = position.blockY;
			int clickZ = position.blockZ;
			
			if (!world.canMineBlock(player, clickX, clickY, clickZ)) {
				return stack;
			}
			
			switch (position.sideHit) {
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
			
			if (!player.canPlayerEdit(clickX, clickY, clickZ, position.sideHit, stack)) {
				return stack;
			}
			
			if (this.placeLiquid(world, clickX, clickY, clickZ) && !player.capabilities.isCreativeMode) {
				return new ItemStack(net.minecraft.init.Items.bucket);
			}
			return stack;			
		}
	}

	private boolean placeLiquid(World world, int blockX, int blockY, int blockZ) {
		if (!world.getBlock(blockX, blockY, blockZ).isAir(world, blockY, blockY, blockZ)
				&& world.getBlock(blockX, blockY, blockZ).getMaterial().isSolid()) {
			return false;
		}
		try {
			Block block = FluidContainerRegistry.getFluidForFilledItem(new ItemStack(this)).getFluid().getBlock();
			
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
	
}
