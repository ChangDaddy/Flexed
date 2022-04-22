package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.event.Event;
import gg.minehut.flexed.event.maps.SumoMap;
import gg.minehut.flexed.task.ITask;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class EventManager extends ITask {

    @Getter(AccessLevel.PUBLIC)
    private static EventManager instance;

    private Event currentEvent;

    @Override
    public void init() {
        instance = this;
        Bukkit.getScheduler().runTaskAsynchronously(Flexed.getInstance().getPlugin(), this::tickEvents);
    }

    private void tickEvents() {
        if(currentEvent != null) {
            currentEvent.update();
        }
    }

    public void createSumoEvent(Event.Type type, PlayerData host, SumoMap sumoMap) {

    }

    public void deleteEvent(String reason) {
        if(currentEvent != null) {
            currentEvent.state = Event.State.END;
            Bukkit.broadcastMessage(ChatColor.RED + "The event was cancelled. Reason: " + reason);
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "Error please fix..");
        }
    }

    public void deleteEvent() {
        if(currentEvent != null) {
            currentEvent.state = Event.State.END;
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "Error please fix..");
        }
    }
}
