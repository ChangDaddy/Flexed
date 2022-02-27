package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.task.ITask;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatTagTask extends ITask implements Listener {
    @Getter(AccessLevel.PUBLIC) private static CombatTagTask instance;

    @Override
    public void init() {
        instance = this;
        Flexed.getInstance().getPlugin().getServer().getPluginManager().registerEvents(this, Flexed.getInstance().getPlugin());

        new BukkitRunnable() {
            @Override
            public void run() {
                for (PlayerData data : Flexed.getInstance().getDataManager().getPlayerDataMap().values()) {
                    if (!data.getCombatTag().isFinished()) data.getCombatTag().countDown();
                    data.updateBoard();
                }
            }
        }.runTaskTimerAsynchronously(Flexed.getInstance().getPlugin(), 20, 0);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            PlayerData playerData = Flexed.getInstance().getDataManager().get(player);
            PlayerData damagerData = Flexed.getInstance().getDataManager().get(damager);
            playerData.getCombatTag().resetTime();
            damagerData.getCombatTag().resetTime();
        }
    }
}
