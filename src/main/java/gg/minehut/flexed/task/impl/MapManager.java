package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.task.ITask;
import lombok.AccessLevel;
import lombok.Getter;

public class MapManager extends ITask {

    @Getter(AccessLevel.PUBLIC)
    private static MapManager instance;

    @Override
    public void init() {
        instance = this;
    }
}
