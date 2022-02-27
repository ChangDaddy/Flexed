package gg.minehut.flexed.items;

import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.util.ColorUtil;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;

@Getter
public class StickItem extends Item {
    private final ItemStack stick;
    public StickItem(String name, ItemStack icon, int price) {
        super(name, icon, ItemCategory.STICK, price);
        ItemStack stick = new ItemStack(icon);
        ItemMeta itemMeta = stick.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translate("&f&l&o" + name));
        itemMeta.spigot().setUnbreakable(true);
        stick.setItemMeta(itemMeta);
        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        this.stick = stick;
    }
}