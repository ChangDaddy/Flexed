package gg.minehut.flexed.task.impl;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.StickItem;

public class MyBlocks {
    public MyBlocks(PlayerData data) {
        SGMenu menu = Flexed.getInstance().getSpiGUI().create("Blocks", 5);

        data.getItems().stream().filter(item -> item instanceof BlockItem).forEach(item -> {
            menu.addButton(new SGButton(new ItemBuilder(((BlockItem) item).getBlock())
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
