package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.DataListener;
import gg.minehut.flexed.listener.PlayerListener;
import gg.minehut.flexed.task.ITask;
import me.gleeming.command.CommandHandler;

public class RegisterEvents implements ITask {
    @Override
    public void init() {
        Flexed.getInstance().getPlugin().getServer().getPluginManager().registerEvents(new PlayerListener(), Flexed.getInstance().getPlugin());
        Flexed.getInstance().getPlugin().getServer().getPluginManager().registerEvents(new DataListener(), Flexed.getInstance().getPlugin());
        CommandHandler.registerCommands("gg.minehut.flexed.commands", Flexed.getInstance().getPlugin());

    }
}
