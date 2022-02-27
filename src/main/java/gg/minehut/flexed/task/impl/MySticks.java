package gg.minehut.flexed.task.impl;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.StickItem;
import org.bukkit.ChatColor;

public class MySticks {
    public MySticks(PlayerData data) {
        SGMenu menu = Flexed.getInstance().getSpiGUI().create("Sticks", 5);

        data.getItems().stream().filter(item -> item instanceof StickItem).forEach(item -> {
            menu.addButton(new SGButton(new ItemBuilder(((StickItem) item).getStick())
                    .lore("&7Click to select!")
                    .build())
                    .withListener(event -> {
                        event.setCancelled(true);
                        data.getPlayer().sendMessage("");
                        data.getPlayer().sendMessage("&7Selected the &f&l" + item.getName() + " cosmetic!");
                        data.getPlayer().sendMessage("");
                    }));
        });
    }
}
