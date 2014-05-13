package tw.oresplus.items;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class OreAxe extends ItemAxe
{
	private static ArrayList<String> axeList = new ArrayList();
	
	public OreAxe(String itemName, Item.ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(itemName);
		setTextureName("OresPlus:" + itemName);
		GameRegistry.registerItem(this, itemName);
		axeList.add(itemName);
	}
	
	public static ArrayList<String> getAxeList() {
		return axeList;
	}
}