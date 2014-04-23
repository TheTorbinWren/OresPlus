package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class OreSword extends ItemSword
{
  public OreSword(String itemName, Item.ToolMaterial material)
  {
    super(material);
    setUnlocalizedName(itemName);
    setTextureName("OresPlus:" + itemName);
    GameRegistry.registerItem(this, itemName);
  }
}