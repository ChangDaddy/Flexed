package gg.minehut.flexed.data;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.util.ColorUtil;
import gg.minehut.flexed.util.ConcurrentEvictingList;
import lombok.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    private final boolean firstJoin;
    @Setter private int kills, deaths, coins, maxKs, ks;
    private final List<Item> items = new ArrayList<>();
    private BlockItem blockItem;
    private StickItem stickItem;
    private HatItem hatItem;

    public PlayerData(final Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.firstJoin = load();
    }

    @SneakyThrows
    public boolean load() {
        boolean newPlayer = false;

        File dataPath = new File(Flexed.getInstance().getPlugin().getDataFolder(), "data");
        if(!dataPath.exists()) dataPath.mkdir();
        File playerFile = new File(dataPath,  player.getUniqueId().toString() + ".yml");

        if(!playerFile.exists()) {
            newPlayer = true;
            playerFile.createNewFile();
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(playerFile);
            setKills(load.getInt("kills"));
            setKills(load.getInt("deaths"));
            setCoins(load.getInt("coins"));
            setDeaths(load.getInt("ks"));
            setDeaths(load.getInt("maxKs"));
        }

        return newPlayer;
    }

    public void purchaseItem(Item item) {
        if(coins - item.getPrice() < 0) {
            player.sendMessage(ColorUtil.translate("&cYou cannot afford that item!"));
        } else {
            player.sendMessage(ColorUtil.translate("&7You just purchased " + item.getName() + " for &e" + item.getPrice() + " coins"));

        }
    }

    @SneakyThrows
    public void save() {
        File dataPath = new File(Flexed.getInstance().getPlugin().getDataFolder(), "data");
        if(!dataPath.exists()) dataPath.mkdir();
        File playerFile = new File(dataPath,  player.getUniqueId().toString() + ".yml");
        if(!playerFile.exists()) return;
        final YamlConfiguration load = YamlConfiguration.loadConfiguration(playerFile);

        load.set("kills", getKills());
        load.set("deaths", getDeaths());
        load.set("coins", getCoins());
        load.set("ks", getKs());
        load.set("maxKs", getMaxKs());
    }
}
