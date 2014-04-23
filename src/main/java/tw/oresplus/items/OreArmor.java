package tw.oresplus.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemArmor;

public class OreArmor extends ItemArmor
{
  public OreArmor(String itemName, ItemArmor.ArmorMaterial material, int renderType, int armorType)
  {
    super(material, renderType, armorType);
    setUnlocalizedName(itemName);
    setTextureName("OresPlus:" + itemName);
    GameRegistry.registerItem(this, itemName);
  }
}