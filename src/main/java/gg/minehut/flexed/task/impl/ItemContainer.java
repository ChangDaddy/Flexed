package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.items.defaults.DefaultBlock;
import gg.minehut.flexed.items.defaults.DefaultHat;
import gg.minehut.flexed.items.defaults.DefaultStick;
import gg.minehut.flexed.task.ITask;
import gg.minehut.flexed.util.ConcurrentEvictingList;
import gg.minehut.flexed.util.CountDown;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class ItemContainer extends ITask {

    @Getter(AccessLevel.PUBLIC)
    private static ItemContainer instance;
    @Getter(AccessLevel.NONE)
    private final Flexed flexed = Flexed.getInstance();
    @Getter
    private final CountDown countDown = new CountDown(60);

    private final List<Item> items = new ArrayList<>();

    private final List<StickItem> sticks = new ArrayList<>();
    private final List<BlockItem> blocks = new ArrayList<>();
    private final List<HatItem> hats = new ArrayList<>();

    private final ConcurrentEvictingList<Item> availableItems = new ConcurrentEvictingList<>(3);

    @Override
    public void init() {
        instance = this;
        items.add(new DefaultStick());
        items.add(new DefaultBlock());
        items.add(new DefaultHat());
        loadItems();
        pickRandom();
        terribleItemGrabberThatCrashesServer();
    }

    public void pickRandom() {
        if (blocks.size() > 0) {
            BlockItem blockItem = blocks.get(new Random().nextInt(blocks.size()));
            availableItems.add(blockItem);
        }
        if (sticks.size() > 0) {
            StickItem stickItem = sticks.get(new Random().nextInt(sticks.size()));
            availableItems.add(stickItem);
        }
        if (hats.size() > 0) {
            HatItem hatItem = hats.get(new Random().nextInt(hats.size()));
            availableItems.add(hatItem);
        }
    }

    public void terribleItemGrabberThatCrashesServer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                countDown.countDown();

                if (countDown.isFinished()) {

                    if (blocks.size() > 1) {
                        while (true) {
                            BlockItem blockItem = blocks.get(new Random().nextInt(blocks.size()));
                            if (!availableItems.contains(blockItem)) {
                                availableItems.add(blockItem);
                                break;
                            }
                        }

                    }
                    if (sticks.size() > 1) {
                        while (true) {
                            StickItem stickItem = sticks.get(new Random().nextInt(sticks.size()));
                            if (!availableItems.contains(stickItem)) {
                                availableItems.add(stickItem);
                                break;
                            }
                        }
                    }
                    if (hats.size() > 1) {
                        while (true) {
                            HatItem hatItem = hats.get(new Random().nextInt(hats.size()));
                            if (!availableItems.contains(hatItem)) {
                                availableItems.add(hatItem);
                                break;
                            }
                        }
                    }

                    countDown.resetTime();
                }
            }
        }.runTaskTimerAsynchronously(Flexed.getInstance().getPlugin(), 0, 20);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadItems() {
        File file = new File(Flexed.getInstance().getPlugin().getDataFolder(), "items.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);

        for (String key : load.getKeys(false)) {
            String name = load.getString(key + ".name");
            ItemStack icon = load.getItemStack(key + ".icon");
            int price = load.getInt(key + ".price");
            Item.ItemCategory category = Item.ItemCategory.valueOf(load.getString(key + ".category"));

            switch (category) {
                case STICK: {
                    StickItem stickItem = new StickItem(name, icon, price);
                    items.add(stickItem);
                    sticks.add(stickItem);
                    break;
                }
                case BLOCKS: {
                    ItemStack phase2 = load.getItemStack(key + ".phase2");
                    ItemStack phase3 = load.getItemStack(key + ".phase3");
                    BlockItem blockItem = new BlockItem(name, icon, price, phase2, phase3);
                    items.add(blockItem);
                    blocks.add(blockItem);
                    break;
                }
                case HELMET: {
                    HatItem hatItem = new HatItem(name, icon, icon, price);
                    items.add(new HatItem(name, icon, icon, price));
                    hats.add(hatItem);
                    break;
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveItems() {
        //Remove hard coded items
        items.remove(findItem("NO_HELMET"));
        items.remove(findItem("DEFAULT_PACK"));
        items.remove(findItem("DEFAULT_STICK"));

        File file = new File(Flexed.getInstance().getPlugin().getDataFolder(), "items.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        for (Item item : items) {
            load.set(item.getName() + ".name", item.getName());
            load.set(item.getName() + ".icon", item.getIcon());
            load.set(item.getName() + ".category", item.getCategory().name());
            load.set(item.getName() + ".price", item.getPrice());
            if (item.isBlock()) {
                BlockItem blockItem = (BlockItem) item;
                load.set(item.getName() + ".phase2", blockItem.getPhase1());
                load.set(item.getName() + ".phase3", blockItem.getPhase2());
            }
        }
        try {
            load.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Item findItem(String item) {
        if (item == null) return null;
        for (Item items : items) {
            if (item.equalsIgnoreCase(items.getName())) return items;
        }
        return null;
    }
}
