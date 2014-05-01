package tw.oresplus.worldgen;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.core.config.ConfigOreGen;


public enum OreGeneratorsNether implements IOreGenerator {
	CinnabarSmallest("oreCinnabar", -1, 16, 4, 64, 128),
	CinnabarSmall("oreCinnabar", -1, 8, 8, 64, 128),
	Cinnabar("oreCinnabar", -1, 4, 12, 64, 128),
	CinnabarLarge("oreCinnabar", -1, 2, 24, 64, 128),
	CinnabarLargest("oreCinnabar", -1, 1, 32, 64, 128),
	NetherArdite("oreNetherArdite", -1, 8, 3, 32, 96),
	NetherCoal("oreNetherCoal", -1, 8, 16, 1, 126),
	NetherCobalt("oreNetherCobalt", -1, 8, 3, 32, 96),
	NetherCopper("oreNetherCopper", -1, 8, 8, 1, 126),
	NetherDiamond("oreNetherDiamond", -1, 4, 3, 1, 126),
	NetherEmerald("oreNetherEmerald", -1, 3, 2, 1, 126),
	NetherGold("oreNetherGold", -1, 8, 6, 1, 126),
	NetherIron("oreNetherIron", -1, 8, 8, 1, 126),
	NwtherLapis("oreNetherLapis", -1, 8, 6, 1, 126),
	NetherLead("oreNetherLead", -1, 6, 6, 1, 126),
	NetherNikolite("oreNetherNikolite", -1, 8, 4, 1, 126),
	NetherRedstone("oreNetherRedstone", -1, 6, 8, 1, 126),
	NetherSilver("oreNetherSilver", -1, 6, 4, 1, 126),
	NetherTin("oreNetherTin", -1, 8, 8, 1, 126),
	NetherUranium("oreNetherUranium", -1, 3, 2, 1, 126),
	PyriteSmallest("orePyrite", -1, 16, 4, 0, 64),
	PyriteSmall("orePyrite", -1, 8, 8, 0, 64),
	Pyrite("orePyrite", -1, 4, 12, 0, 64),
	PyriteLarge("orePyrite", -1, 2, 24, 0, 64),
	PyriteLargest("orePyrite", -1, 1, 32, 0, 64),
	Quartz("oreQuartz", -1, 16, 13, 10, 118),
	SphaleriteSmallest("oreSphalerite", -1, 16, 4, 32, 96),
	SphaleriteSmall("oreSphalerite", -1, 8, 8, 32, 96),
	Sphalerite("oreSphalerite", -1, 4, 12, 32, 96),
	SphaleriteLarge("oreSphalerite", -1, 2, 24, 32, 96),
	SphaleriteLargest("oreSphalerite", -1, 1, 32, 32, 96);
	
	private String _oreName;
	private boolean _enabled;
	private int _dimension;
	private int _numVeins;
	private int _veinSize;
	private int _minY;
	private int _maxY;
	private boolean _enableRegen;
	private OreGenType _genType;
	private int _density;
	
	public WorldGenOre generator;
	
	private OreGeneratorsNether(String oreName, int dimension, int numVeins, 
			int veinSize, int minY, int maxY) {
		this(oreName, dimension, numVeins, veinSize, minY, maxY, OreGenType.NORMAL);
	}
	
	private OreGeneratorsNether(String oreName, int dimension, int numVeins,
			int veinSize, int minY, int maxY, OreGenType genType) {
		this._oreName = oreName;
		this._enabled = true;
		this._dimension = dimension;
		this._numVeins = numVeins;
		this._veinSize = veinSize;
		this._minY = minY;
		this._maxY = maxY;
		this._enableRegen = false;
		this._genType = genType;
		this._density = 100;
	}
	
	
	public OreGenClass getDefaultConfig() {
		return new OreGenClass(this.name(), this._oreName, this._enabled, 
				this._dimension, this._numVeins, this._veinSize, this._minY, 
				this._maxY, this._enableRegen, this._genType, this._density, "DISABLED");
	}
	
	public void registerGenerator() {
		OreGenClass oreGen = ConfigOreGen.getNetherOreGeneratorConfig(getDefaultConfig());
		if (this._enabled && Ores.manager.isOreRegistered(this._oreName)) 
			this.generator = new WorldGenOre(oreGen);
	}

	@Override
	public WorldGenOre getOreGenerator() {
		// TODO Auto-generated method stub
		return null;
	}
}
