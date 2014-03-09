package tw.oresplus.core.helpers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public enum Helpers {
	AppliedEnergistics(new AppEngHelper()),
	BuildCraft(new BCHelper()),
	Forestry(new ForestryHelper()),
	GregTech(new GTHelper()),
	IC2(new IC2Helper()),
	NetherOres(new NOHelper()),
	RailCraft(new RCHelper()),
	ThaumCraft(new TCHelper()),
	ThermalExpansion(new TEHelper());
	
	private OresHelper _oresHelper;
	
	private Helpers(OresHelper oresHelper) {
		_oresHelper = oresHelper;
	}
	
	public void init() {
		this._oresHelper.init();
	}
	
	public boolean isLoaded() {
		return this._oresHelper.isLoaded();
	}
	
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		this._oresHelper.generate(world, rand, chunkX, chunkZ);
	}
	
	public Block getBlock(String blockName) {
		return this._oresHelper.getBlock(blockName);
	}
}

