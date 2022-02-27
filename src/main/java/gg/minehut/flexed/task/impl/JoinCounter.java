package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.task.ITask;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class JoinCounter extends ITask {
    @Getter(AccessLevel.PUBLIC) private static JoinCounter instance;
    @Getter private int joins;


    @Override
    public void init() {
        instance = this;
        joins = loadJoins();
    }

    public int incrementJoins() {
        return joins++;
    }

    @SneakyThrows
    private int loadJoins() {
        final File file = new File(Flexed.getInstance().getPlugin().getDataFolder(), "joins.yml");
        if(!file.exists()) {
            file.createNewFile();
            return 0;
        }
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        return load.getInt("joins");
    }

    @SneakyThrows
    public void saveJoins() {
        final File file = new File(Flexed.getInstance().getPlugin().getDataFolder(), "joins.yml");
        if(!file.exists()) file.createNewFile();
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        load.set("joins", joins);

        load.save(file);
    }
}
