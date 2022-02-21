package gg.minehut.flexed;

import gg.minehut.flexed.data.DataManager;
import gg.minehut.flexed.lib.LibUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Flexed extends JavaPlugin {
    @Getter(AccessLevel.PUBLIC) private static Flexed instance;
    private final DataManager dataManager = new DataManager();
    @Getter(AccessLevel.NONE) private int joins;

    @Override
    public void onLoad() {
        instance = this;
        new LibUtils();
        joins = loadJoins();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        saveJoins();
        super.onEnable();
    }

    public int incrementJoins() {
        return joins++;
    }

    @SneakyThrows
    private int loadJoins() {
        final File file = new File(this.getDataFolder(), "joins.yml");
        if(!file.exists()) {
            file.createNewFile();
            return 0;
        }
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        return load.getInt("joins");
    }

    @SneakyThrows
    private void saveJoins() {
        final File file = new File(this.getDataFolder(), "joins.yml");
        if(!file.exists()) file.createNewFile();
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        load.set("joins", joins);
    }
}
