package gg.minehut.flexed;

import gg.minehut.flexed.lib.LibUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class FlexedLoader extends JavaPlugin {
    private Flexed flexed;
    @Override
    public void onEnable() {
        flexed.onEnable();
        super.onEnable();
    }

    @Override
    public void onLoad() {
        new LibUtils(this); // Load libs before calling SpiGui
        flexed = new Flexed();
        flexed.onLoad(this);
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
