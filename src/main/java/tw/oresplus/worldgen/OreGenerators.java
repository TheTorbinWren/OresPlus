package tw.oresplus.worldgen;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.config.ConfigCore;


public enum OreGenerators implements IOreGenerator {
	Adamantine("oreAdamantine", 0, 1, 4, 0, 25),
	Aluminium("oreAluminium", 0, 3, 6, 0, 64),
	Amethyst("oreAmethyst", 0, 6, 6, 0, 40),
	Apatite("oreApatite", 0, 6, 6, 25, 50),
	Bauxite("oreBauxite", 0, 2, 16, 32, 80),
	BauxiteHigh("oreBauxite", 0, 3, 16, 70, 220),
	Bitumen("oreBitumen", 0, 4, 4, 0, 60),
	Cassiterite("oreCassiterite", 0, 1, 32, 32, 70),
	CassiteriteHigh("oreCassiterite", 0, 1, 32, 70, 220),
	CertusQuartz("oreCertusQuartz", 0, 8, 4, 12, 72),
	Coal("oreCoal", 0, 20, 16, 0, 128),
	Coldiron("oreColdiron", 0, 4, 6, 0, 45),
	Copper("oreCopper", 0, 18, 8, 10, 60),
	CopperHigh("oreCopper", 0, 12, 10, 60, 220),
	Diamond("oreDiamond", 0, 1, 7, 0, 16),
	Galena("oreGalena", 0, 1, 16, 0, 32),
	Gold("oreGold", 0, 2, 8, 0, 32),
	GreenSapphire("oreGreenSapphire", 0, 4, 6, 0, 40),
	Iridium("oreIridium", 0, 1, 1, 0, 50),
	Iron("oreIron", 0, 20, 8, 0, 128),
	Lapis("oreLapis", 0, 1, 6, 16, 32),
	Lead("oreLead", 0, 4, 8, 10, 45),
	Magnesium("oreMagnesium", 0, 4, 4, 0, 140),
	Manganese("oreManganese", 0, 5, 5, 0, 140),
	Mithral("oreMithral", 0, 3, 3, 0, 140),
	Nickel("oreNickel", 0, 4, 8, 5, 25),
	Nikolite("oreNikolite", 0, 4, 10, 0, 15),
	Osmium("oreOsmium", 0, 12, 8, 0, 60),
	Phosporite("orePhosphorite", 0, 4, 4, 0, 140),
	Platinum("orePlatinum", 0, 3, 4, 0, 30),
	Potash("orePotash", 0, 4, 4, 0, 140),
	Redstone("oreRedstone", 0, 8, 7, 0, 16),
	Ruby("oreRuby", 0, 4, 6, 0, 40),
	Saltpeter("oreSaltpeter", 0, 8, 8, 50, 60),
	Sapphire("oreSapphire", 0, 4, 6, 0, 40),
	Silver("oreSilver", 0, 6, 8, 0, 40),
	SphaleriteOverworld("oreSphalerite", 0, 8, 1, 2, 10, OreGenType.UNDER_LAVA),
	Sulfur("oreSulfur", 0, 8, 10, 6, 15, OreGenType.NEAR_LAVA),
	Tetrahedrite("oreTetrahedrite", 0, 1, 32, 32, 70),
	TetrahedriteHigh("oreTetrahedrite", 0, 1, 32, 70, 220),
	Tin("oreTin", 0, 12, 10, 12, 56),
	Topaz("oreTopaz", 0, 4, 6, 0, 40),
	Tungsten("oreTungsten", 0, 1, 4, 0, 15),
	Uranium("oreUranium", 0, 20, 3, 0, 62),
	Zinc("oreZinc", 0, 6, 5, 0, 140);
	
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
	
	private OreGenerators(String oreName, int dimension, int numVeins, 
			int veinSize, int minY, int maxY) {
		this(oreName, dimension, numVeins, veinSize, minY, maxY, OreGenType.NORMAL);
	}
	
	private OreGenerators(String oreName, int dimension, int numVeins,
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
		OreGenClass oreGen = OresPlus.config.getOreGen(getDefaultConfig());
		if (this._enabled && Ores.manager.isOreRegistered(this._oreName)) 
			this.generator = new WorldGenOre(oreGen);
	}

	@Override
	public WorldGenOre getOreGenerator() {
		// TODO Auto-generated method stub
		return null;
	}
}
