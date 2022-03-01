package gg.minehut.flexed.gui;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.util.ColorUtil;

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
                        data.getPlayer().sendMessage(ColorUtil.translate("&7Selected the &f&l" + item.getName() + " cosmetic!"));
                        data.getPlayer().sendMessage("");
                        data.setStickItem((StickItem) item);
                    }));
        });

        data.getPlayer().openInventory(menu.getInventory());
    }
}
