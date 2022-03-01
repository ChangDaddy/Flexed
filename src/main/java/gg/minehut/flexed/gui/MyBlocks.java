package gg.minehut.flexed.gui;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.util.ColorUtil;

public class MyBlocks {
    public MyBlocks(PlayerData data) {
        SGMenu menu = Flexed.getInstance().getSpiGUI().create("Blocks", 5);

        data.getItems().stream().filter(item -> item instanceof BlockItem).forEach(item -> {
            menu.addButton(new SGButton(new ItemBuilder((item).getIcon())
                    .lore("&7Click to select!")
                    .name(ColorUtil.translate(item.getName()))
                    .build())
                    .withListener(event -> {
                        event.setCancelled(true);
                        data.getPlayer().sendMessage("");
                        data.getPlayer().sendMessage(ColorUtil.translate("&7Selected the &f&l" + item.getName() + " cosmetic!"));
                        data.getPlayer().sendMessage("");
                        data.setBlockItem((BlockItem) item);
                    }));
        });

        data.getPlayer().openInventory(menu.getInventory());
    }
}
