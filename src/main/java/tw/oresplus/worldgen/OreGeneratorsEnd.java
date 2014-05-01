package tw.oresplus.worldgen;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.core.config.ConfigOreGen;


public enum OreGeneratorsEnd implements IOreGenerator {
	Cooperite("oreSheldonite", 1, 1, 4, 0, 127),
	Olivine("oreOlivine", 1, 5, 8, 0, 127),
	Sodalite("oreSodalite", 1, 8, 16, 0, 127),
	Tungstate("oreTungstate", 1, 4, 16, 0, 127);
	
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
	
	private OreGeneratorsEnd(String oreName, int dimension, int numVeins, 
			int veinSize, int minY, int maxY) {
		this(oreName, dimension, numVeins, veinSize, minY, maxY, OreGenType.NORMAL);
	}
	
	private OreGeneratorsEnd(String oreName, int dimension, int numVeins,
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
		OreGenClass oreGen = ConfigOreGen.getEndOreGeneratorConfig(getDefaultConfig());
		if (this._enabled && Ores.manager.isOreRegistered(this._oreName)) 
			this.generator = new WorldGenOre(oreGen);
	}

	@Override
	public WorldGenOre getOreGenerator(int dimId) {
		if (this.generator == null) {
			this.registerGenerator();
		}
		return this.generator;
	}
}
