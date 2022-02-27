package gg.minehut.flexed.items;

import gg.minehut.flexed.util.ColorUtil;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;

@Getter
public class BlockItem extends Item implements Serializable {

    private final ItemStack phase1, phase2;
    private final ItemStack block;

    public BlockItem(String name, ItemStack icon, int price, ItemStack phase1, ItemStack phase2) {
        super(name, icon, price);
        this.phase1 = phase1;
        this.phase2 = phase2;
        ItemStack block = new ItemStack(icon);
        block.setAmount(64);
        ItemMeta itemMeta = block.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ColorUtil.translate("&f&l&o" +name));
        block.setItemMeta(itemMeta);
        this.block = block;
    }
}