package tw.oresplus.ores;

import net.minecraft.item.ItemStack;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.Config;
import tw.oresplus.core.OreClass;
import tw.oresplus.recipes.OreItemStack;

public enum GeneralOres implements IOres {
	Bauxite (1),
	Bitumen (1, OreDrops.BITUMEN),
	Cassiterite (2),
	CertusQuartz (1, OreDrops.CERTUSQUARTZ),
	Cinnabar (2, OreDrops.CINNABAR),
	Galena (2),
	Iridium (3, OreDrops.IRIDIUM),
	Magnesium (1, OreDrops.MAGNESIUM),
	NetherCoal (1),
	NetherLapis (1),
	NetherUranium (2),
	Olivine (2, OreDrops.OLIVINE),
	Pyrite (2, OreDrops.PYRITE),
	Sheldonite (2),
	Sodalite (2, OreDrops.SODALITE),
	Sphalerite (2, OreDrops.SPHALERITE),
	Tetrahedrite (2),
	Tungstate (2),
	Tungsten (2),
	Uranium (2);
	
	
	
	public String oreName;
	
	public OreItemStack ore;
	
	private boolean _enabled;
	private int _harvestLevel;
	private OreDrops _drops;
	private OreSources _source;
	private int _xpdropLow;
	private int _xpdropHigh;
	
	private GeneralOres (int harvestLevel, int xpdropLow, int xpdropHigh, OreDrops drops) {
		this.oreName = "ore" + this.toString();
		this._enabled = true;
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
		this._xpdropLow = xpdropLow;
		this._xpdropHigh = xpdropHigh;
	}
	
	private GeneralOres(int harvestLevel, OreDrops drops) {
		this(harvestLevel, 0, 0, drops);
	}
	
	private GeneralOres(int harvestLevel, int xpdropLow, int xpdropHigh) {
		this(harvestLevel, xpdropLow, xpdropHigh, OreDrops.ORE);
	}
	
	private GeneralOres(int harvestLevel) {
		this(harvestLevel, 0, 0, OreDrops.ORE);
	}
	
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, this._enabled, this._harvestLevel, 
				this._xpdropLow, this._xpdropHigh, this._drops, this._source);
	}
	
	public boolean enabled() {
		return this._enabled;
	}
	
	public String oreName() {
		return this.oreName;
	}
	
	public void RegisterBlocks() {
		OreClass oreConfig = Config.getOre(this.getDefaultConfig());
		if (oreConfig.enabled)
			this.ore = new OreItemStack(new BlockOre(oreConfig));
		
	}

	@Override
	public OreClass getDefaultConfigNether() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerBlocks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerAspects() {
		// TODO Auto-generated method stub
		
	}
}
