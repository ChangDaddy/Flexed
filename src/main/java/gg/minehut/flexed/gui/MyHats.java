package gg.minehut.flexed.gui;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.util.ColorUtil;

public class MyHats {

    public MyHats(PlayerData data) {
        SGMenu menu = Flexed.getInstance().getSpiGUI().create("Hats", 5);

        data.getItems().stream().filter(item -> item instanceof HatItem).forEach(item -> {
            menu.addButton(new SGButton(new ItemBuilder(item.getIcon())
                    .lore("&7Click to select!")
                    .build())
                    .withListener(event -> {
                        event.setCancelled(true);
                        data.getPlayer().sendMessage("");
                        data.getPlayer().sendMessage(ColorUtil.translate("&7Selected the &f&l" + item.getName() + " cosmetic!"));
                        data.getPlayer().sendMessage("");

                    }));
        });

        data.getPlayer().openInventory(menu.getInventory());
    }
}
