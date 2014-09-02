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
import tw.oresplus.recipes.RecipeManager;

public enum GeneralOres implements IOreList {
	Bitumen (1, OreDrops.BITUMEN, 0.0D),
	CertusQuartz (1, OreDrops.CERTUSQUARTZ, 1250.0D),
	Cinnabar (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.CINNABAR, 0.0D),
	Iridium (3, OreDrops.IRIDIUM, 0.0D),
	Magnesium (1, OreDrops.MAGNESIUM, 0.0D),
	NetherCoal (1, new AspectList().add(Aspect.FIRE, 1), 0.0D),
	NetherLapis (1, new AspectList().add(Aspect.FIRE, 1), 0.0D),
	NetherUranium (2, new AspectList().add(Aspect.FIRE, 1), 0.0D),
	Olivine (2, new AspectList().add(Aspect.ELDRITCH, 1), OreDrops.OLIVINE, 0.0D),
	Pyrite (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.PYRITE, 0.0D),
	Sheldonite (2, new AspectList().add(Aspect.ELDRITCH, 1), 0.0D),
	Sodalite (2, new AspectList().add(Aspect.ELDRITCH, 1), OreDrops.SODALITE, 0.0D),
	Sphalerite (2, new AspectList().add(Aspect.FIRE, 1), OreDrops.SPHALERITE, 0.0D),
	Tungstate (2, new AspectList().add(Aspect.ELDRITCH, 1), 0.0D),
	Tungsten (2, 0.0D),
	Uranium (2, 2608.8982568638758D);
	
	public String oreName;
	
	public OreItemStack ore;
	
	private boolean _enabled;
	private int _harvestLevel;
	private OreDrops _drops;
	private OreSources _source;
	private int _xpdropLow;
	private int _xpdropHigh;
	private AspectList _aspects;
	private double _uuCost;
	
	private GeneralOres (int harvestLevel, int xpdropLow, int xpdropHigh, AspectList aspects, OreDrops drops, double uuCost) {
		this.oreName = "ore" + this.toString();
		this._enabled = true;
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
		this._xpdropLow = xpdropLow;
		this._xpdropHigh = xpdropHigh;
		this._aspects = aspects;
		this._uuCost = uuCost;
	}
	
	private GeneralOres(int harvestLevel, AspectList aspects, double uuCost) {
		this(harvestLevel, 0, 0, aspects, OreDrops.ORE, uuCost);
	}
	
	private GeneralOres(int harvestLevel, AspectList aspects, OreDrops drops, double uuCost) {
		this(harvestLevel, 0, 0, aspects, drops, uuCost);
	}
	
	private GeneralOres(int harvestLevel, int xpdropLow, int xpdropHigh, OreDrops drops, double uuCost) {
		this(harvestLevel, xpdropLow, xpdropHigh, new AspectList(), drops, uuCost);
	}
	
	private GeneralOres(int harvestLevel, OreDrops drops, double uuCost) {
		this(harvestLevel, 0, 0, new AspectList(), drops, uuCost);
	}
	
	private GeneralOres(int harvestLevel, int xpdropLow, int xpdropHigh, double uuCost) {
		this(harvestLevel, xpdropLow, xpdropHigh, new AspectList(), OreDrops.ORE, uuCost);
	}
	
	private GeneralOres(int harvestLevel, double uuCost) {
		this(harvestLevel, 0, 0, new AspectList(), OreDrops.ORE, uuCost);
	}
	
	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, this._enabled, this._harvestLevel, 
				this._xpdropLow, this._xpdropHigh, this._drops, this._source);
	}
	
	@Override
	public void registerBlocks() {
		this.ore = new OreItemStack(new BlockOre(this.getDefaultConfig()));
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return null;
	}

	@Override
	public void registerItems() { }

	@Override
	public void registerRecipes() {
		// register uuMatter costs
		if (this._uuCost != 0.0D) {
			RecipeManager.addUUMatterRecipe(this.ore.source, this._uuCost);
		}
	}

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

	@Override
	public void registerFluids() {
		// TODO Auto-generated method stub
		
	}
}
