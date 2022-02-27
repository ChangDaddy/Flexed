package gg.minehut.flexed;

import com.samjakob.spigui.SpiGUI;
import gg.minehut.flexed.data.DataListener;
import gg.minehut.flexed.data.DataManager;
import gg.minehut.flexed.items.ItemContainer;
import gg.minehut.flexed.lib.LibUtils;
import gg.minehut.flexed.task.TaskBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import me.gleeming.command.CommandHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Flexed {
    @Getter(AccessLevel.PUBLIC) private static Flexed instance;
    private final DataManager dataManager = new DataManager();
    @Getter(AccessLevel.NONE) private int joins;
    private SpiGUI spiGUI;
    private JavaPlugin plugin;
    private ItemContainer itemContainer;

    public void onLoad(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
        joins = loadJoins();
    }

    public void onEnable() {
        spiGUI = new SpiGUI(plugin);
        new TaskBuilder();
        saveJoins();
        CommandHandler.registerCommands("gg.minehut.flexed.commands", plugin);
        itemContainer = new ItemContainer();
        this.plugin.getServer().getPluginManager().registerEvents(new DataListener(), plugin);
    }

    public int incrementJoins() {
        return joins++;
    }

    @SneakyThrows
    private int loadJoins() {
        final File file = new File(plugin.getDataFolder(), "joins.yml");
        if(!file.exists()) {
            file.createNewFile();
            return 0;
        }
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        return load.getInt("joins");
    }

    @SneakyThrows
    private void saveJoins() {
        final File file = new File(plugin.getDataFolder(), "joins.yml");
        if(!file.exists()) file.createNewFile();
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);
        load.set("joins", joins);
    }
}
