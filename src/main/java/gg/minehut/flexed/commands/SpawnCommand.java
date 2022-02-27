package gg.minehut.flexed.commands;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.task.impl.LocationTask;
import gg.minehut.flexed.util.ColorUtil;
import me.gleeming.command.Command;
import org.bukkit.entity.Player;

public class SpawnCommand {

    @Command(names = {"spawn"}, playerOnly = true)
    public void spawnCommand(Player player) {
        if(LocationTask.getInstance().get("spawn") == null) player.sendMessage(ColorUtil.translate("&cSpawn is not set, please notify an administrator"));
        PlayerData data = Flexed.getInstance().getDataManager().get(player);
        if(!data.getCombatTag().isFinished() || data.isEditing())
            player.sendMessage(ColorUtil.translate(data.isEditing() ? "&cYou are editing" : "&cYou are in combat!"));
        else {
            player.sendMessage(ColorUtil.translate("&aTeleporting you to spawn!"));
            player.teleport(LocationTask.getInstance().get("spawn"));
        }
    }

    @Command(names = {"setspawn"}, playerOnly = true, permission = "flexed.admin")
    public void setSpawnCommand(Player player) {
        player.sendMessage(ColorUtil.translate("&aSet spawn"));
        LocationTask.getInstance().addLocation("spawn", player.getLocation());
    }
}
