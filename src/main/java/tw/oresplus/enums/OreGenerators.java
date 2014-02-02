package tw.oresplus.enums;

import tw.oresplus.init.OreGenConfig;

public enum OreGenerators {
	Adamantine("oreAdamantine", 0, 1, 4, 0, 25),
	Amethyst("oreAmethyst", 0, 6, 2, 0, 40),
	Apatite("oreApatite", 0, 6, 6, 25, 50),
	Bauxite("oreBauxite", 0, 2, 16, 32, 80),
	BauxiteHigh("oreBauxite", 0, 3, 16, 70, 220),
	Bitumen("oreBitumen", 0, 4, 4, 0, 60),
	Cassiterite("oreCassiterite", 0, 1, 32, 32, 70),
	CassiteriteHigh("oreCassiterite", 0, 1, 32, 70, 220),
	CinnabarSmallest("oreCinnabar", -1, 16, 4, 64, 128),
	CinnabarSmall("oreCinnabar", -1, 8, 8, 64, 128),
	Cinnabar("oreCinnabar", -1, 4, 12, 64, 128),
	CinnabarLarge("oreCinnabar", -1, 2, 24, 64, 128),
	CinnabarLargest("oreCinnabar", -1, 1, 32, 64, 128),
	Coldiron("oreColdiron", 0, 4, 6, 0, 45),
	Cooperite("oreSheldonite", 1, 1, 4, 0, 127),
	Copper("oreCopper", 0, 18, 8, 10, 60),
	CopperHigh("oreCopper", 0, 12, 10, 60, 220),
	Galena("oreGalena", 0, 1, 16, 0, 32),
	GreenSapphire("oreGreenSapphire", 0, 6, 2, 0, 40),
	Iridium("oreIridium", 0, 1, 1, 0, 50),
	Lead("oreLead", 0, 4, 8, 10, 45),
	Magnesium("oreMagnesium", 0, 4, 4, 0, 140),
	Manganese("oreManganese", 0, 5, 5, 0, 140),
	Mithral("oreMithral", 0, 3, 3, 0, 140),
	NetherCoal("oreNetherCoal", -1, 8, 16, 1, 126),
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
	Nickel("oreNickel", 0, 4, 8, 5, 25),
	Nikolite("oreNikolite", 0, 4, 10, 0, 15),
	Olivine("oreOlivine", 1, 5, 8, 0, 127),
	Phosporite("orePhosphorite", 0, 4, 4, 0, 140),
	Platinum("orePlatinum", 0, 3, 4, 0, 30),
	Potash("orePotash", 0, 4, 4, 0, 140),
	PyriteSmallest("orePyrite", -1, 16, 4, 0, 64),
	PyriteSmall("orePyrite", -1, 8, 8, 0, 64),
	Pyrite("orePyrite", -1, 4, 12, 0, 64),
	PyriteLarge("orePyrite", -1, 2, 24, 0, 64),
	PyriteLargest("orePyrite", -1, 1, 32, 0, 64),
	Ruby("oreRuby", 0, 6, 2, 0, 40),
	Saltpeter("oreSaltpeter", 0, 8, 8, 50, 60),
	Sapphire("oreSapphire", 0, 6, 2, 0, 40),
	Silver("oreSilver", 0, 6, 8, 0, 40),
	Sodalite("oreSodalite", 1, 8, 16, 0, 127),
	SphaleriteSmallest("oreSphalerite", -1, 16, 4, 32, 96),
	SphaleriteSmall("oreSphalerite", -1, 8, 8, 32, 96),
	Sphalerite("oreSphalerite", -1, 4, 12, 32, 96),
	SphaleriteLarge("oreSphalerite", -1, 2, 24, 32, 96),
	SphaleriteLargest("oreSphalerite", -1, 1, 32, 32, 96),
	SphaleriteOverworld("oreSphalerite", 0, 8, 1, 2, 10, OreGenType.UNDER_LAVA),
	Sulfur("oreSulfur", 0, 8, 10, 6, 15, OreGenType.NEAR_LAVA),
	Tetrahedrite("oreTetrahedrite", 0, 1, 32, 32, 70),
	TetrahedriteHigh("oreTetrahedrite", 0, 1, 32, 70, 220),
	Tin("oreTin", 0, 12, 10, 12, 56),
	Topaz("oreTopaz", 0, 6, 2, 0, 40),
	Tungstate("oreTungstate", 1, 4, 16, 0, 127),
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
	}
	
	
	public OreGenConfig getDefaultConfig() {
		return new OreGenConfig(this.name(), this._oreName, this._enabled, 
				this._dimension, this._numVeins, this._veinSize, this._minY, 
				this._maxY, this._enableRegen, this._genType);
	}
}
