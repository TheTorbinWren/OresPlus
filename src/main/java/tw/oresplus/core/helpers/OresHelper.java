package tw.oresplus.core.helpers;

import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class OresHelper {
	protected String _modID;
	
	OresHelper(String modID) {
		this._modID = modID;
	}
	
	public abstract void init();
	
	public boolean isLoaded() {
		if (this._modID == null)
			return false;
		return Loader.isModLoaded(this._modID);
	}

	public abstract void generate(World world, Random rand, int chunkX, int chunkZ);
	
	public Block getBlock(String blockName) {
		if (this._modID == null)
			return null;
		return GameRegistry.findBlock(this._modID, blockName);
	};
	
	public abstract void registerGrind(ItemStack input, ItemStack output);
}
