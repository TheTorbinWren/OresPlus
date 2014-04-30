package tw.oresplus.core.config;

import java.util.ArrayList;
import java.util.HashMap;

import tw.oresplus.worldgen.WorldGenOre;

public class ConfigHelper {
	private HashMap<Integer, ArrayList<WorldGenOre>> dimensionControlList;
	
	public ArrayList getDimensionGenerators(int dimId) {
		return this.dimensionControlList.get(dimId);
	}
	
	public void registerGenerator() {
		
	}
}
