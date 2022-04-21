package gg.minehut.flexed.commands;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StatCommand {

    @Command(names = {"stats", "stat"}, playerOnly = true)
    public void statsCommand(Player player, @Param(name = "player", required = false) Player otherPlayer) {
        
        if(otherPlayer != null) player = otherPlayer;

        SGMenu menu = Flexed.getInstance().getSpiGUI().create("Stats", 1);
        final PlayerData data = Flexed.getInstance().getDataManager().get(player);

        menu.setButton(3, new SGButton(new ItemBuilder(Material.STICK)
                .name("&eKnockFFA")
                .lore(
                        "&8» &7Kills: &e" + data.getKills(),
                        "&8» &7Deaths: &e" + data.getDeaths(),
                        "&8» &7Best KS: &e" + data.getMaxKs()
                ).build()));

        menu.setButton(4, new SGButton(new ItemBuilder(Material.SKULL_ITEM)
                .name("&7"+player.getName())
                .skullOwner(player.getName())
                .build()));

        menu.setButton(7, new SGButton(new ItemBuilder(Material.GOLD_INGOT)
                .name("&eCoins")
                .lore("&8» &7Coins: &e" + data.getCoins())
                .build()));

        menu.setAutomaticPaginationEnabled(false);
        player.openInventory(menu.getInventory());
    }

}
