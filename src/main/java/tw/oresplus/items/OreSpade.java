package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import tw.oresplus.OresPlus;
import net.minecraft.item.ItemSpade;

public class OreSpade extends ItemSpade {

	public OreSpade(String itemName, ToolMaterial material) {
		super(material);
		setUnlocalizedName(itemName);
		setTextureName("OresPlus:" + itemName);
		GameRegistry.registerItem(this, itemName);
	}

}
