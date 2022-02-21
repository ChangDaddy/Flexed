package gg.minehut.flexed.data;

import gg.minehut.flexed.Flexed;
import lombok.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

@Getter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    private final boolean firstJoin;
    @Setter private int kills, deaths, coins, maxKs, ks;

    public PlayerData(final Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.firstJoin = load();
    }

    @SneakyThrows
    public boolean load() {
        boolean newPlayer = false;

        File dataPath = new File(Flexed.getInstance().getDataFolder(), "data");
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
}
