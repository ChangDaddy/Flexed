package gg.minehut.flexed.commands;

import com.samjakob.spigui.SGMenu;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.task.impl.ItemContainer;
import gg.minehut.flexed.task.impl.LocationTask;
import gg.minehut.flexed.util.ColorUtil;
import me.gleeming.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KitCommand {

    @Command(names = {"kit"}, playerOnly = true)
    public void kitCommand(Player player) {
        if (LocationTask.getInstance().get("kitspawn") == null) {
            player.sendMessage(ColorUtil.translate("&cSpawn not set please notify administrator"));
            return;
        }

        PlayerData data = Flexed.getInstance().getDataManager().get(player);
        if(data.isEditing()) return;
        data.setEditing(true);

        data.loadLayout();
        player.teleport(LocationTask.getInstance().get("kitspawn"));
        player.sendMessage(ColorUtil.translate("&7How to edit your kit:"));
        player.sendMessage(ColorUtil.translate("&7- &eArrange the items in your inventory to your liking"));
        player.sendMessage(ColorUtil.translate("&7- &eRight click the anvil"));
        player.sendMessage(ColorUtil.translate("&7- &eYour items will be saved in that order, and you'll be teleported to spawn"));
    }

    @Command(names = {"setKitSpawn"}, playerOnly = true, permission = "flexed.admin")
    public void kitSpawnCommand(Player player) {
        LocationTask.getInstance().addLocation("kitspawn", player.getLocation());
        player.sendMessage(ColorUtil.translate("&cSet spawn at location " + player.getLocation().toString()));
    }
}
