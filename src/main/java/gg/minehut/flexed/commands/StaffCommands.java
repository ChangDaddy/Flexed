package gg.minehut.flexed.commands;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.util.ColorUtil;
import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommands {

    @Command(names = {"vanish"}, permission = "core.staff")
    public void vanish(final Player player, @Param(name = "player", required = false) Player target) {
        if(target != null && !player.hasPermission("core.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have the permission to vanish other people!");
            return;
        }

        PlayerData data = Flexed.getInstance().getDataManager().get(target != null ? target : player);
        data.vanish();

        player.sendMessage(target == null
                ? data.isVanished()
                ? "\247aVanished"
                : "\247cUnvanished"
                : ((data.isVanished()
                ? "\247aVanished "
                : "\247aUnvanished ") + target.getName())
        );
    }

    @Command(names = {"sc", "staffchat"}, permission = "core.staff", description = "Allows users to talk in staffchat")
    public void staffChatCommand(final CommandSender sender, @Param(name = "message", concated = true) String message) {
        Bukkit.broadcast(ColorUtil.translate("&7[&cSC&7] &c" + sender.getName() + " » &f" + message), "core.staff");
    }
}
