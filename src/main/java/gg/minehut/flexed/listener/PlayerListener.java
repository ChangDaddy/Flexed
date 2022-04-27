package gg.minehut.flexed.listener;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.DataManager;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.task.impl.ItemContainer;
import gg.minehut.flexed.task.impl.LocationTask;
import gg.minehut.flexed.util.ColorUtil;
import gg.minehut.flexed.util.DecayBlock;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        PlayerData data = Flexed.getInstance().getDataManager().get(event.getPlayer());
        if (event.getTo().getY() < 98 && event.getTo().getY() > -2) {
            if (!data.isArena()) {
                data.setArena(true);
                data.loadLayout();
            }
        } else if (event.getTo().getY() < -2) {
            data.killPlayer();
        } else {
            if (data.isArena()) {
                data.setArena(false);
                //Don't wanna spam clear inv
                data.clearInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        event.setDamage(0);
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            PlayerData player = Flexed.getInstance().getDataManager().get((Player) event.getDamager());
            PlayerData entity = Flexed.getInstance().getDataManager().get((Player) event.getEntity());
            if(entity == null || player == null) return;

            entity.setLastAttacked(player);
            event.setDamage(0);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().hasPermission("ffa.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getEntity();
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;
        PlayerData data = Flexed.getInstance().getDataManager().get(event.getPlayer());
        Item item = ItemContainer.getInstance().findItem(ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName()));
        if (item != null && item.isBlock()) {
            event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), data.getBlockItem().getBlock());
            new DecayBlock(event.getBlock().getLocation(), (BlockItem) item);
        } else {
            if (event.getItemInHand().getType() == Material.WEB) {
                Bukkit.getScheduler().runTaskLater(Flexed.getInstance().getPlugin(), () -> {
                    event.getBlock().setType(Material.AIR);
                }, 60);
            }
        }

    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventory(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission("flexed.admin") || Flexed.getInstance().getDataManager().get((Player) event.getWhoClicked()).isEditing()) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType() != Material.ANVIL) return;
        PlayerData data = Flexed.getInstance().getDataManager().get(event.getPlayer());
        if(!data.isEditing()) return;
        event.setCancelled(true);

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = data.getPlayer().getInventory().getItem(i);
            if(itemStack == null) continue;
            Item item = ItemContainer.getInstance().findItem(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()));

            if(item != null) {
                if(item instanceof BlockItem) {
                    data.setBlockSlot(i);
                } else if(item instanceof StickItem) {
                    data.setStickSlot(i);
                }
            } else {
                switch (itemStack.getType()) {
                    case WEB: {
                        data.setWebSlot(i);
                        break;
                    }
                    case ENDER_PEARL: {
                        data.setPearlSlot(i);
                        break;
                    }
                }
            }
        }
        data.getPlayer().sendMessage(ChatColor.GREEN + "Saved!");
        data.clearInventory();
        data.setEditing(false);
        data.getPlayer().teleport(LocationTask.getInstance().get("spawn"));
    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getPlayerAdapter(Player.class).getUser(event.getPlayer());
        String prefix = user.getCachedData().getMetaData().getPrefix();
        String suffix = user.getCachedData().getMetaData().getSuffix();
        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";
        if (p.hasPermission("core.white")) {
            event.setMessage(ColorUtil.translate(event.getMessage()));
            event.setFormat(ColorUtil.translate(prefix + "" + p.getName() + suffix + ": &r") + "%2$s");
        } else {
            event.setFormat(ColorUtil.translate(prefix + p.getName() + suffix + ": &7") + "%2$s");

        }
    }

}
