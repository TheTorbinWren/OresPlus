package tw.oresplus.ores;

import tw.oresplus.core.OreClass;

public enum OresClassic {
	Amethyst (2, OreDrops.AMETHYST),
	Apatite (1, OreDrops.APATITE),
	Bauxite (1),
	Bitumen (1, OreDrops.BITUMEN),
	Cassiterite (2),
	CertusQuartz (1, OreDrops.CERTUSQUARTZ),
	Cinnabar (2, OreDrops.CINNABAR),
	Galena (2),
	GreenSapphire (2, 3, 7, OreDrops.GREENSAPPHIRE),
	Iridium (3, OreDrops.IRIDIUM),
	Magnesium (1, OreDrops.MAGNESIUM),
	Manganese (2),
	NetherCoal (1),
	NetherDiamond (2),
	NetherEmerald (2),
	NetherLapis (1),
	NetherNikolite (2),
	NetherRedstone (2),
	NetherUranium (2),
	Nikolite (2, OreDrops.NIKOLITE),
	Olivine (2, OreDrops.OLIVINE),
	Phosphorite (1, OreDrops.PHOSPHORITE),
	Potash (1, OreDrops.POTASH),
	Pyrite (2, OreDrops.PYRITE),
	Ruby (2, 3, 7, OreDrops.RUBY),
	Saltpeter (1, OreDrops.SALTPETER),
	Sapphire (2, 3, 7, OreDrops.SAPPHIRE),
	Sheldonite (2),
	Sodalite (2, OreDrops.SODALITE),
	Sphalerite (2, OreDrops.SPHALERITE),
	Sulfur (1, OreDrops.SULFUR),
	Tetrahedrite (2),
	Topaz (2, 3, 7, OreDrops.TOPAZ),
	Tungstate (2),
	Tungsten (2),
	Uranium (2);
	
	private String _name;
	private boolean _enabled;
	private int _harvestLevel;
	private OreDrops _drops;
	private OreSources _source;
	private int _xpdropLow;
	private int _xpdropHigh;
	
	private OresClassic (int harvestLevel, int xpdropLow, int xpdropHigh, OreDrops drops) {
		this._name = "ore" + this.name();
		this._enabled = true;
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
		this._xpdropLow = xpdropLow;
		this._xpdropHigh = xpdropHigh;
	}
	
	private OresClassic(int harvestLevel, OreDrops drops) {
		this(harvestLevel, 0, 0, drops);
	}
	
	private OresClassic(int harvestLevel, int xpdropLow, int xpdropHigh) {
		this(harvestLevel, xpdropLow, xpdropHigh, OreDrops.ORE);
	}
	
	private OresClassic(int harvestLevel) {
		this(harvestLevel, 0, 0, OreDrops.ORE);
	}
	
	public OreClass getDefaultConfig() {
		return new OreClass(this._name, this._enabled, this._harvestLevel, 
				this._xpdropLow, this._xpdropHigh, this._drops, this._source);
	}
	
	public boolean enabled() {
		return this._enabled;
	}
	
	public String oreName() {
		return this._name;
	}
}
