package gg.minehut.flexed.items;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Base64;

@Getter
public class Item {
    private final String name;
    private final ItemStack icon;
    private final ItemCategory category;
    private final int price;

    public Item(String name, ItemStack icon, ItemCategory category, int price) {
        this.name = name;
        this.icon = icon;
        this.category = category;
        this.price = price;
    }

    public boolean isStick() {
        return this instanceof StickItem;
    }

    public boolean isHat() {
        return this instanceof HatItem;
    }

    public boolean isBlock() {
        return this instanceof BlockItem;
    }


    public enum ItemCategory {
        STICK, HELMET, BLOCKS;

        public String format() {
            return StringUtils.capitalize(this.name());
        }
    }
}