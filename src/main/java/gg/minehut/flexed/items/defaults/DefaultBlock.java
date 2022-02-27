package gg.minehut.flexed.items.defaults;

import gg.minehut.flexed.items.BlockItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultBlock extends BlockItem{
    public DefaultBlock() {
        super("DEFAULT_PACK",new ItemStack(Material.WOOL, 1, DyeColor.LIME.getData()), 0, new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData()), new ItemStack(Material.WOOL, 1, DyeColor.RED.getData()));
    }
}
