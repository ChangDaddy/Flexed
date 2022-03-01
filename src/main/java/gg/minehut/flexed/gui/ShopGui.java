package gg.minehut.flexed.gui;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.item.ItemDataColor;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.task.impl.ItemContainer;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicReference;

public class ShopGui {
    public ShopGui(PlayerData data) {
        SGMenu shop = Flexed.getInstance().getSpiGUI()
                .create("Shop", 1);

        for (int i = 0; i < 9; i++) {
            shop.addButton(new SGButton(
                    new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .color(ItemDataColor.BLACK)
                            .name("")
                            .build()
            ));
        }

        for (Item item : ItemContainer.getInstance().getAvailableItems()) {
            if (item instanceof BlockItem) {
                shop.setButton(1, new SGButton(new ItemBuilder(item.getIcon())
                        .lore(
                                "&7Block Pack",
                                "&7Costs &e" + item.getPrice() + " coins"
                        ).build()).withListener(event -> {
                    data.purchaseItem(item);
                }));
            } else if (item instanceof HatItem) {
                shop.setButton(3, new SGButton(new ItemBuilder(item.getIcon())
                        .lore(
                                "&7Helmet",
                                "&7Costs &e" + item.getPrice() + " coins"
                        ).build()).withListener(event -> {
                    data.purchaseItem(item);
                }));
            } else if(item instanceof StickItem) {
                shop.setButton(2, new SGButton(new ItemBuilder(item.getIcon())
                        .lore(
                                "&7Stick",
                                "&7Costs &e" + item.getPrice() + " coins"
                        ).build()).withListener(event -> {
                    data.purchaseItem(item);
                }));
            }
        }

        AtomicReference<BukkitTask> timeRunnable = new AtomicReference<>();

        timeRunnable.set(new BukkitRunnable() {
            @Override
            public void run() {
                shop.setButton(7, new SGButton(new ItemBuilder(Material.WATCH)
                        .name("&e" + ItemContainer.getInstance().getCountDown().convertTime())
                        .lore("&7until shop refreshes")
                        .build()));
                shop.refreshInventory(data.getPlayer());
            }
        }.runTaskTimer(Flexed.getInstance().getPlugin(),0L, 20));

        shop.setOnClose(inventory -> {
            if(timeRunnable.get() != null) timeRunnable.get().cancel();
        });

        shop.setAutomaticPaginationEnabled(false);

        data.getPlayer().openInventory(shop.getInventory());
    }
}
