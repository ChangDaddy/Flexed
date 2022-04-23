package gg.minehut.flexed.commands;

import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.task.impl.ItemContainer;
import gg.minehut.flexed.util.ColorUtil;
import me.gleeming.command.Command;
import me.gleeming.command.help.Help;
import me.gleeming.command.paramter.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class CreateItem {

    @Command(names = {"create block"}, playerOnly = true, permission = "flexed.admin")
    public void createBlock(Player player,@Param(name = "name") String name, @Param(name = "price") Integer price, @Param(name = "id") String args1, @Param(name = "id2") String args2, @Param(name = "id3") String args3) {
        try {

            String id = args1.split(":")[0];
            String data = args1.split(":")[1];

            String id2 = args2.split(":")[0];
            String data2 = args2.split(":")[1];

            String id3 = args3.split(":")[0];
            String data3 = args3.split(":")[1];

            ItemStack block1 = new ItemStack(Integer.parseInt(id), 1, (short) Integer.parseInt(data));
            ItemStack block2 = new ItemStack(Integer.parseInt(id2), 1, (short) Integer.parseInt(data2));
            ItemStack block3 = new ItemStack(Integer.parseInt(id3), 1, (short) Integer.parseInt(data3));

            BlockItem blockItem = new BlockItem(name, block1, price, block2, block3);
            ItemContainer.getInstance().getItems().add(blockItem);
            player.sendMessage(ChatColor.GREEN + "Created item!");

        } catch (Exception e) {
            player.sendMessage("Invalid ID");
        }
    }

    @Command(names = {"create stick"}, playerOnly = true, permission = "flexed.admin")
    public void createStick(Player player, @Param(name = "name") String name, @Param(name = "id") String args, @Param(name = "price") Integer price) {
        try {
            String id = args.split(":")[0];
            String data = args.split(":")[1];

            ItemStack itemStack = new ItemStack(Integer.parseInt(id), 1, (short) Integer.parseInt(data));
            StickItem stickItem = new StickItem(name, itemStack, price);

            ItemContainer.getInstance().getItems().add(stickItem);
            player.sendMessage(ChatColor.GREEN + "Created item!");
        } catch (Exception e) {
            player.sendMessage("Invalid ID");
        }
    }

    @Command(names = {"create hat"}, playerOnly = true, permission = "flexed.admin")
    public void createHat(Player player, @Param(name = "name") String name, @Param(name = "id") String args, @Param(name = "price") Integer price) {
        try {
            String id = args.split(":")[0];
            String data = args.split(":")[1];

            ItemStack itemStack = new ItemStack(Integer.parseInt(id), 1, (short) Integer.parseInt(data));
            HatItem hatItem = new HatItem(name, itemStack,itemStack, price);

            ItemContainer.getInstance().getItems().add(hatItem);
            player.sendMessage(ChatColor.GREEN + "Created item!");
        } catch (Exception e) {
            player.sendMessage("Invalid ID");
        }
    }

    @Command(names = {"refreshshop"}, playerOnly = true, permission = "flexed.admin")
    public void refreshCommand(Player player) {
        final long n = System.currentTimeMillis();

        ItemContainer.getInstance().saveItems();
        ItemContainer.getInstance().getItems().clear();
        ItemContainer.getInstance().getBlocks().clear();
        ItemContainer.getInstance().getHats().clear();
        ItemContainer.getInstance().getSticks().clear();
        ItemContainer.getInstance().getAvailableItems().clear();
        ItemContainer.getInstance().loadItems();

        final long time = System.currentTimeMillis() - n;
        player.sendMessage(ColorUtil.translate("&aReloaded items in " + time + "ms"));
    }

    @Help(names = {"create"})
    public void helpCreate(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Usage: /create <hat, stick, block>");
    }

}
