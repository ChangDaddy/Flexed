package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.task.ITask;
import gg.minehut.flexed.util.CountDown;

public class ShopTask extends ITask {

    public static final CountDown COUNTDOWN = new CountDown(3600);

    @Override
    public void init() {
        COUNTDOWN.countDown();

        if(COUNTDOWN.isFinished()) {
            Flexed.getInstance().getItemContainer().terribleItemGrabberThatCrashesServer();
            COUNTDOWN.resetTime();
        }
    }
}
