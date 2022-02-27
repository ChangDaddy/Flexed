package gg.minehut.flexed.items.defaults;

import gg.minehut.flexed.items.HatItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultHat extends HatItem {
    public DefaultHat() {
        super("NO_HELMET", new ItemStack(Material.BARRIER), new ItemStack(Material.AIR), 0);
    }
}