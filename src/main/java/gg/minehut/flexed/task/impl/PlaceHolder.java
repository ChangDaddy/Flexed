package gg.minehut.flexed.task.impl;

import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.data.PlayerData;
import gg.minehut.flexed.task.ITask;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceHolder extends PlaceholderExpansion implements ITask {

    @Override
    public @NotNull String getIdentifier() {
        return "flexed";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FinalBoolean";
    }

    @Override
    public @NotNull String getVersion() {
        return "69";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public void init() {
        new PlaceHolder().register();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("blexedkills")) {
            PlayerData data = Flexed.getInstance().getDataManager().get(player.getPlayer());
            return data.getKills() + "";
        }

        if(params.equalsIgnoreCase("blexeddeaths")) {
            PlayerData data = Flexed.getInstance().getDataManager().get(player.getPlayer());
            return data.getDeaths() + "";
        }

        if(params.equalsIgnoreCase("blexedcoins")) {
            PlayerData data = Flexed.getInstance().getDataManager().get(player.getPlayer());
            return data.getCoins() + "";
        }

        if(params.equalsIgnoreCase("blexedmaxKs")) {
            PlayerData data = Flexed.getInstance().getDataManager().get(player.getPlayer());
            return data.getMaxKs() + "";
        }

        if(params.equalsIgnoreCase("blexedks")) {
            PlayerData data = Flexed.getInstance().getDataManager().get(player.getPlayer());
            return data.getKs() + "";
        }

        return null;
    }
}
