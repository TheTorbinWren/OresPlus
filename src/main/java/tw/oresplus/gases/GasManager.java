package tw.oresplus.gases;

import tw.oresplus.ores.MetallicOres;
import tw.oresplus.ores.MineralOres;

public class GasManager {
	public static void init() {
		for (Gases gas : Gases.values()) {
			gas.registerGas();
		}
		
		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerGases();
		}
		
		for (MineralOres ore : MineralOres.values()) {
			ore.registerGases();
		}
	}
}
