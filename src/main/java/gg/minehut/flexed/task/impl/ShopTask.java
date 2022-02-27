package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.task.ITask;
import gg.minehut.flexed.util.CountDown;
import org.bukkit.scheduler.BukkitRunnable;

public class ShopTask extends ITask {

    public static final CountDown COUNTDOWN = new CountDown(3600);

    @Override
    public void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                COUNTDOWN.countDown();

                if(COUNTDOWN.isFinished()) {
                    ItemContainer.getInstance().terribleItemGrabberThatCrashesServer();
                    COUNTDOWN.resetTime();
                }
            }
        }.runTaskTimer(Flexed.getInstance().getPlugin(), 20, 0);
    }
}
