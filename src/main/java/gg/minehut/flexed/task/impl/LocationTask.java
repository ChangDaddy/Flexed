package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.task.ITask;
import gg.minehut.flexed.util.LocationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocationTask extends ITask {
    @Getter(AccessLevel.PUBLIC) private static LocationTask instance;

    private final Map<String, Location> locationMap = new HashMap<>();

    @Override
    public void init() {
        instance = this;
        load();
    }

    @SneakyThrows
    public void load() {
        File file = new File(Flexed.getInstance().getPlugin().getDataFolder().getAbsolutePath(), "locations.yml");
        if(!file.exists()) return;
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);

        for(String locationName : load.getKeys(false)) {
            locationMap.put(locationName, LocationUtil.parseToLocation(load.getString(locationName)));
        }
    }

    @SneakyThrows
    public void save() {
        File file = new File(Flexed.getInstance().getPlugin().getDataFolder().getAbsolutePath(), "locations.yml");
        if(!file.exists()) file.createNewFile();
        YamlConfiguration load = YamlConfiguration.loadConfiguration(file);

        for(Map.Entry<String, Location> locationEntry : locationMap.entrySet()) {
            load.set(locationEntry.getKey(), LocationUtil.parseToString(locationEntry.getValue()));
        }

        load.save(file);
    }

    public void addLocation(String name, Location location) {
        locationMap.put(name, location);
    }

    public Location get(String name) {
        try {
            return locationMap.get(name);
        } catch (Exception  e){
            return  null;
        }
    }
}
