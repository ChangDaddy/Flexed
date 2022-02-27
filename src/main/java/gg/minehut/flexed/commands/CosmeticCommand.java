package gg.minehut.flexed.commands;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.gui.MyBlocks;
import gg.minehut.flexed.gui.MyHats;
import gg.minehut.flexed.gui.MySticks;
import gg.minehut.flexed.gui.ShopGui;
import gg.minehut.flexed.util.ColorUtil;
import me.gleeming.command.Command;
import me.gleeming.command.help.Help;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticCommand {

    @Command(names = {"cosmetics sticks", "cosmetic sticks"}, playerOnly = true)
    public void cosmeticSticksCommand(Player player) {
        new MySticks(Flexed.getInstance().getDataManager().get(player));
    }

    @Command(names = {"cosmetics packs", "cosmetic packs"}, playerOnly = true)
    public void cosmeticBlocksCommand(Player player) {
        new MyBlocks(Flexed.getInstance().getDataManager().get(player));
    }

    @Command(names = {"cosmetics helmets", "cosmetic helmets"}, playerOnly = true)
    public void cosmeticHatCommand(Player player) {
        new MyHats(Flexed.getInstance().getDataManager().get(player));
    }

    @Help(names = {"cosmetics", "cosmetic"})
    public void cosmeticHelp(CommandSender sender) {
        sender.sendMessage(ColorUtil.translate("&b→ &7Incorrect usage. Please use /cosmetics <sticks/helmets/packs>"));
    }

    @Command(names = {"shop"}, playerOnly = true)
    public void shopCommand(Player player) {
        new ShopGui(Flexed.getInstance().getDataManager().get(player));
    }
}
