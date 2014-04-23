package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class OrePickaxe extends ItemPickaxe
{
  public OrePickaxe(String itemName, Item.ToolMaterial material)
  {
    super(material);
    setUnlocalizedName(itemName);
    setTextureName("OresPlus:" + itemName);
    GameRegistry.registerItem(this, itemName);
  }
}