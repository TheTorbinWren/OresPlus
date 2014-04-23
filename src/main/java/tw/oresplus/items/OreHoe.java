package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class OreHoe extends ItemHoe
{
  public OreHoe(String itemName, Item.ToolMaterial material)
  {
    super(material);
    setUnlocalizedName(itemName);
    setTextureName("OresPlus:" + itemName);
    GameRegistry.registerItem(this, itemName);
  }
}