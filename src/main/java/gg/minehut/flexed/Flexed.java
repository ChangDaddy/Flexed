package gg.minehut.flexed;

import com.samjakob.spigui.SpiGUI;
import gg.minehut.flexed.data.DataListener;
import gg.minehut.flexed.data.DataManager;
import gg.minehut.flexed.task.TaskBuilder;
import gg.minehut.flexed.task.impl.ItemContainer;
import gg.minehut.flexed.task.impl.JoinCounter;
import gg.minehut.flexed.task.impl.LocationTask;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Flexed {
    @Getter(AccessLevel.PUBLIC)
    private static Flexed instance;
    private final DataManager dataManager = new DataManager();
    private SpiGUI spiGUI;
    private JavaPlugin plugin;


    public void onLoad(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public void onEnable() {
        spiGUI = new SpiGUI(plugin);
        new TaskBuilder();
    }

    public void onDisable() {
        JoinCounter.getInstance().saveJoins();
        LocationTask.getInstance().save();
        ItemContainer.getInstance().saveItems();
    }
}
