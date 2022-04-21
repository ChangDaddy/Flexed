package gg.minehut.flexed.data;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class DataManager {
    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public PlayerData addPlayer(Player player) {
        playerDataMap.put(player.getUniqueId(), new PlayerData(player));
        return get(player);
    }

    public void removePlayer(Player player) {
        get(player).save();
        playerDataMap.remove(player.getUniqueId());
    }

    public PlayerData get(Player player) {
        try {
            return playerDataMap.get(player.getUniqueId());
        } catch (Exception e) {
            return null;
        }
    }
}
