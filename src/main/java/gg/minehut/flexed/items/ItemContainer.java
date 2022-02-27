package gg.minehut.flexed.items;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.util.ConcurrentEvictingList;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class ItemContainer {
    private final Flexed flexed = Flexed.getInstance();

    private final List<Item> items = new ArrayList<>();

    private final List<StickItem> sticks = new ArrayList<>();
    private final List<BlockItem> blocks = new ArrayList<>();
    private final List<HatItem> hats = new ArrayList<>();

    private final ConcurrentEvictingList<Item> availableItems  = new ConcurrentEvictingList<>(3);

    public ItemContainer() {
        pickRandom();
    }

    public void pickRandom() {
        if(blocks.size() > 0) {
            BlockItem blockItem = blocks.get(new Random().nextInt(blocks.size()));
            availableItems.add(blockItem);
        }
        if(sticks.size() > 0) {
            StickItem stickItem = sticks.get(new Random().nextInt(sticks.size()));
            availableItems.add(stickItem);
        }
        if(hats.size() > 0) {
            HatItem hatItem = hats.get(new Random().nextInt(hats.size()));
            availableItems.add(hatItem);
        }
    }

    public void terribleItemGrabberThatCrashesServer() {
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
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    private void load() {
        File file = new File(flexed.getPlugin().getDataFolder(), "items.yml");
        if(!file.exists()) file.createNewFile();

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);

        for(String itemName : load.getKeys(false)) {
            flexed.getPlugin().getLogger().info("Deserializing item: " + itemName);

            Item item = Item.deserialize(load.getString(itemName));

            if(item instanceof BlockItem)
                blocks.add((BlockItem) item);
            else if(item instanceof StickItem)
                sticks.add((StickItem) item);
            else if(item instanceof HatItem)
                hats.add((HatItem) item);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public void save() {
        File file = new File(flexed.getPlugin().getDataFolder(), "mines.yml");
        if(!file.exists()) file.createNewFile();

        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        for(Item item : items) {
            load.set(item.getName(), item.serialize());
        }
    }
}
