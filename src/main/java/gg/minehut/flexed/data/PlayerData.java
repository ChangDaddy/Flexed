package gg.minehut.flexed.data;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.items.BlockItem;
import gg.minehut.flexed.items.HatItem;
import gg.minehut.flexed.items.Item;
import gg.minehut.flexed.items.StickItem;
import gg.minehut.flexed.task.impl.ItemContainer;
import gg.minehut.flexed.task.impl.JoinCounter;
import gg.minehut.flexed.task.impl.LocationTask;
import gg.minehut.flexed.util.ColorUtil;
import gg.minehut.flexed.util.CountDown;
import gg.minehut.flexed.util.FastBoard;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private final Player player;
    private final UUID uuid;
    private final boolean firstJoin;
    private boolean editing, arena;
    private int kills, deaths, coins, maxKs, ks, stickSlot, blockSlot, webSlot, pearlSlot;
    private final List<Item> items = new ArrayList<>();
    private BlockItem blockItem;
    private StickItem stickItem;
    private HatItem hatItem;
    private PlayerData lastAttacked;
    private final CountDown combatTag = new CountDown(15, true);
    private final FastBoard board;

    public PlayerData(final Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.board = new FastBoard(player);
        this.firstJoin = load();
        stickSlot = 0;
        blockSlot = 1;
        webSlot = 2;
        pearlSlot = 3;
    }

    @SneakyThrows
    public boolean load() {
        boolean newPlayer = false;

        File dataPath = new File(Flexed.getInstance().getPlugin().getDataFolder(), "data");
        if(!dataPath.exists()) dataPath.mkdir();
        File playerFile = new File(dataPath,  player.getUniqueId().toString() + ".yml");

        if(!playerFile.exists()) {
            newPlayer = true;

            playerFile.createNewFile();

            setBlockItem((BlockItem) ItemContainer.getInstance().findItem("DEFAULT_PACK"));
            setHatItem((HatItem) ItemContainer.getInstance().findItem("NO_HELMET"));
            setStickItem((StickItem) ItemContainer.getInstance().findItem("DEFAULT_STICK"));
            items.add(getBlockItem());
            items.add(getHatItem());
            items.add(getStickItem());
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(playerFile);
            setKills(load.getInt("kills"));
            setKills(load.getInt("deaths"));
            setCoins(load.getInt("coins"));
            setDeaths(load.getInt("ks"));
            setDeaths(load.getInt("maxKs"));
            setStickSlot(load.getInt("stickSlot"));
            setBlockSlot(load.getInt("blockSlot"));
            setWebSlot(load.getInt("webSlot"));
            setPearlSlot(load.getInt("pearlSlot"));
            for(String key : load.getConfigurationSection("items").getKeys(false)) {
                items.add(ItemContainer.getInstance().findItem(load.getString("items." + key)));
            }

            setBlockItem((BlockItem) ItemContainer.getInstance().findItem(load.getString("blockItem")));
            setStickItem((StickItem)  ItemContainer.getInstance().findItem(load.getString("stickItem")));
            setHatItem((HatItem) ItemContainer.getInstance().findItem(load.getString("hatItem")));
        }
        return newPlayer;
    }

    public void loadLayout() {
        getPlayer().getInventory().setItem(getStickSlot(), getStickItem().getStick());
        getPlayer().getInventory().setItem(getBlockSlot(), getBlockItem().getBlock());
        getPlayer().getInventory().setItem(getWebSlot(), new ItemStack(Material.WEB));
        getPlayer().getInventory().setItem(getPearlSlot(), new ItemStack(Material.ENDER_PEARL));
        getPlayer().getInventory().setHelmet(getHatItem().getIcon());
        getPlayer().updateInventory();
    }

    public void purchaseItem(Item item) {
        if(coins - item.getPrice() < 0) {
            player.sendMessage(ColorUtil.translate("&cYou cannot afford that item!"));
        } else {
            player.sendMessage(ColorUtil.translate("&7You just purchased " + item.getName() + " for &e" + item.getPrice() + " coins"));

        }
    }

    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    @SneakyThrows
    public void save() {
        File dataPath = new File(Flexed.getInstance().getPlugin().getDataFolder(), "data");
        if(!dataPath.exists()) dataPath.mkdir();
        File playerFile = new File(dataPath,  player.getUniqueId().toString() + ".yml");
        if(!playerFile.exists()) return;
        final YamlConfiguration load = YamlConfiguration.loadConfiguration(playerFile);

        load.set("kills", getKills());
        load.set("deaths", getDeaths());
        load.set("coins", getCoins());
        load.set("ks", getKs());
        load.set("maxKs", getMaxKs());
        load.set("blockItem", blockItem.getName());
        load.set("stickItem", stickItem.getName());
        load.set("hatItem", hatItem.getName());
        load.set("stickSlot", stickSlot);
        load.set("blockSlot", blockSlot);
        load.set("webSlot", webSlot);
        load.set("pearlSlot", pearlSlot);
        for(Item item : items) {
            load.set("items." + item.getName(), item.getName());
        }
        load.save(playerFile);
    }

    public void killPlayer() {
        if(lastAttacked != null) {
            lastAttacked.loadLayout();
            player.sendMessage(ColorUtil.translate(String.format("&c→ &f%s knocked you into the void!", lastAttacked.getPlayer().getName())));
            lastAttacked.getPlayer().sendMessage(ColorUtil.translate(String.format("&a→ &7You knocked &f" + player.getName() + " &7into the void &8[&f%s&8]", player, ks)));
            lastAttacked.setKills(lastAttacked.getKills() + 1);
            lastAttacked.setKs(lastAttacked.getKs() + 1);
            if(lastAttacked.getKs() % 5 == 0) {
                Bukkit.broadcastMessage(ColorUtil.translate(String.format("&b→ &f%s is on a &3&l" + ks + " KILLSTREAK", player.getName()) + "!"));
            }

            if (ks >= 5) {
                lastAttacked.setCoins(lastAttacked.getCoins() + getKs() / 2 + 5);
            } else {
                lastAttacked.setCoins(lastAttacked.getCoins() + 5);
            }
            lastAttacked = null;


        }
        if(ks >= 5) {
            Bukkit.broadcastMessage(ColorUtil.translate(String.format("&b→ &f%s lost their &3&l" + ks + " KILLSTREAK", player.getName()) + "!"));
        }
        if(getMaxKs() > ks) {
            setMaxKs(ks);
        }
        setKs(0);
        clearInventory();
        setDeaths(getDeaths() + 1);
        player.teleport(LocationTask.getInstance().get("spawn"));
    }

    public void updateBoard() {
        board.updateTitle("&3&lFLEXED &8| &3&lS4");
        board.updateLine(0, "");
        board.updateLine(1, "&3&lKFFA");
        board.updateLine(2, "&8• &7Kills: &f" + getKills());
        board.updateLine(3, "&8• &7Deaths: &f" + getDeaths());
        board.updateLine(4, "&8• &7Coins: &f" + getCoins());
        board.updateLine(5, "&8• &7Streak: &f" + getKs() + " &8(&b" + getMaxKs() + "&8)");
        board.updateLine(6, "");
        board.updateLine(7, "&b&lSERVER");
        board.updateLine(8, "&8- &7Online: &f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        board.updateLine(9, "&8- &7Joins: &f" + JoinCounter.getInstance().getJoins());
        board.updateLine(10, "&8- &7TPS: &f20");
        board.updateLine(11, "");
        board.updateLine(12, "&7flexed.minehut.gg");
    }
}
