package tw.oresplus.ores;

import java.util.Random;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.recipes.OreItemStack;

public enum GeneralOres implements IOreList {
	Bauxite (1),
	Bitumen (1, OreDrops.BITUMEN),
	Cassiterite (2),
	CertusQuartz (1, OreDrops.CERTUSQUARTZ),
	Cinnabar (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.CINNABAR),
	Galena (2),
	Iridium (3, OreDrops.IRIDIUM),
	Magnesium (1, OreDrops.MAGNESIUM),
	NetherCoal (1, new AspectList().add(Aspect.FIRE, 1)),
	NetherLapis (1, new AspectList().add(Aspect.FIRE, 1)),
	NetherUranium (2, new AspectList().add(Aspect.FIRE, 1)),
	Olivine (2, new AspectList().add(Aspect.ELDRITCH, 1), OreDrops.OLIVINE),
	Pyrite (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.PYRITE),
	Sheldonite (2, new AspectList().add(Aspect.ELDRITCH, 1)),
	Sodalite (2, new AspectList().add(Aspect.ELDRITCH, 1), OreDrops.SODALITE),
	Sphalerite (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.SPHALERITE),
	Tetrahedrite (2),
	Tungstate (2, new AspectList().add(Aspect.ELDRITCH, 1)),
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
	private AspectList _aspects;
	
	private GeneralOres (int harvestLevel, int xpdropLow, int xpdropHigh, AspectList aspects, OreDrops drops) {
		this.oreName = "ore" + this.toString();
		this._enabled = true;
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
		this._xpdropLow = xpdropLow;
		this._xpdropHigh = xpdropHigh;
		this._aspects = aspects;
	}
	
	private GeneralOres(int harvestLevel, AspectList aspects) {
		this(harvestLevel, 0, 0, aspects, OreDrops.ORE);
	}
	
	private GeneralOres(int harvestLevel, AspectList aspects, OreDrops drops) {
		this(harvestLevel, 0, 0, aspects, drops);
	}
	
	private GeneralOres(int harvestLevel, int xpdropLow, int xpdropHigh, OreDrops drops) {
		this(harvestLevel, xpdropLow, xpdropHigh, new AspectList(), drops);
	}
	
	private GeneralOres(int harvestLevel, OreDrops drops) {
		this(harvestLevel, 0, 0, new AspectList(), drops);
	}
	
	private GeneralOres(int harvestLevel, int xpdropLow, int xpdropHigh) {
		this(harvestLevel, xpdropLow, xpdropHigh, new AspectList(), OreDrops.ORE);
	}
	
	private GeneralOres(int harvestLevel) {
		this(harvestLevel, 0, 0, new AspectList(), OreDrops.ORE);
	}
	
	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, this._enabled, this._harvestLevel, 
				this._xpdropLow, this._xpdropHigh, this._drops, this._source);
	}
	
	@Override
	public void registerBlocks() {
		OreClass oreConfig = OresPlus.config.getOre(this.getDefaultConfig());
		if (oreConfig.enabled)
			this.ore = new OreItemStack(new BlockOre(oreConfig));
		
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return null;
	}

	@Override
	public void registerItems() { }

	@Override
	public void registerRecipes() { }

	@Override
	public void registerAspects() {
		if (!Helpers.ThaumCraft.isLoaded())
			return;
		
	    if (this != Uranium) {
	        ThaumcraftApi.registerObjectTag(this.oreName, this._aspects.add(Aspect.EARTH, 1));
	    }
	}

	@Override
	public int getTradeToAmount(Random random) {
		return 0;
	}

	@Override
	public int getTradeFromAmount(Random random) {
		return 0;
	}
}
