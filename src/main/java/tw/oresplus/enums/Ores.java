package tw.oresplus.enums;

import tw.oresplus.core.OreClass;

public enum Ores {
	Adamantine (3),
	Amethyst (2, OreDrops.AMETHYST),
	Apatite (1, OreDrops.APATITE),
	Bauxite (1),
	Bitumen (1, OreDrops.BITUMEN),
	Cassiterite (2),
	CertusQuartz (1, OreDrops.CERTUSQUARTZ),
	Cinnabar (2, OreDrops.CINNABAR),
	Coldiron (2),
	Copper (1),
	Galena (2),
	GreenSapphire (2, 3, 7, OreDrops.GREENSAPPHIRE),
	Iridium (3, OreDrops.IRIDIUM),
	Lead (1),
	Magnesium (1, OreDrops.MAGNESIUM),
	Manganese (2),
	Mithral (3),
	NetherCoal (1),
	NetherCopper (1),
	NetherDiamond (2),
	NetherEmerald (2),
	NetherGold (2),
	NetherIron (1),
	NetherLapis (1),
	NetherLead (1),
	NetherNikolite (2),
	NetherRedstone (2),
	NetherSilver (2),
	NetherTin (1),
	NetherUranium (2),
	Nickel (2),
	Nikolite (2, OreDrops.NIKOLITE),
	Olivine (2, OreDrops.OLIVINE),
	Phosphorite (1, OreDrops.PHOSPHORITE),
	Platinum (2),
	Potash (1, OreDrops.POTASH),
	Pyrite (2, OreDrops.PYRITE),
	Ruby (2, 3, 7, OreDrops.RUBY),
	Saltpeter (1, OreDrops.SALTPETER),
	Sapphire (2, 3, 7, OreDrops.SAPPHIRE),
	Sheldonite (2),
	Silver (2),
	Sodalite (2, OreDrops.SODALITE),
	Sphalerite (2, OreDrops.SPHALERITE),
	Sulfur (1, OreDrops.SULFUR),
	Tetrahedrite (2),
	Tin (1),
	Topaz (2, 3, 7, OreDrops.TOPAZ),
	Tungstate (2),
	Tungsten (2),
	Uranium (2, OreDrops.URANIUM),
	Zinc (2);
	
	private String _name;
	private boolean _enabled;
	private int _harvestLevel;
	private OreDrops _drops;
	private OreSources _source;
	private int _xpdropLow;
	private int _xpdropHigh;
	
	private Ores (int harvestLevel, int xpdropLow, int xpdropHigh, OreDrops drops) {
		this._name = "ore" + this.name();
		this._enabled = true;
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
		this._xpdropLow = xpdropLow;
		this._xpdropHigh = xpdropHigh;
	}
	
	private Ores(int harvestLevel, OreDrops drops) {
		this(harvestLevel, 0, 0, drops);
	}
	
	private Ores(int harvestLevel, int xpdropLow, int xpdropHigh) {
		this(harvestLevel, xpdropLow, xpdropHigh, OreDrops.ORE);
	}
	
	private Ores(int harvestLevel) {
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
