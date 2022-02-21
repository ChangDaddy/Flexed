package gg.minehut.flexed.data;


import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(Flexed.getInstance().getDataManager().addPlayer(player)
                .isFirstJoin() ? ColorUtil.translate("&8(&a+&8) &7" + player.getName() + " joined. &8[" + Flexed.getInstance().incrementJoins() + "]")
                : ColorUtil.translate("&8(&a+&8) &7" + player.getName() + " joined."));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("");
        Flexed.getInstance().getDataManager().removePlayer(player);
    }
}
