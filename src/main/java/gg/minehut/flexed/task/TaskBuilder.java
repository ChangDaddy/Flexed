package gg.minehut.flexed.task;

import gg.minehut.flexed.task.impl.*;

import java.lang.reflect.InvocationTargetException;

public class TaskBuilder {

    private final Class<?>[] tasks = {
            CombatTagTask.class,
            EventManager.class,
            ItemContainer.class,
            ItemContainer.class,
            JoinCounter.class,
            LocationTask.class,
            MapManager.class,
            RegisterEvents.class,
            PlaceHolder.class
    };

    public TaskBuilder() {
        for (Class<?> task : tasks) {
            try {
                task.getMethod("init").invoke(task.newInstance());
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}