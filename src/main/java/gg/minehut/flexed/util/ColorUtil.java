package gg.minehut.flexed.util;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ColorUtil {
    public String translate(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
