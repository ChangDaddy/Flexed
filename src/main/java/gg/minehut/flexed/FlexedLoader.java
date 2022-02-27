package gg.minehut.flexed;

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
        flexed = new Flexed();
        flexed.onLoad(this);
        super.onLoad();
    }

    @Override
    public void onDisable() {
        flexed.onDisable();
        super.onDisable();
    }
}
