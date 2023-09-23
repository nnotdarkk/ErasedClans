package fr.erased.clans;

import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.manager.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanPlaceholders extends PlaceholderExpansion {

    private final ErasedClans main;

    public ClanPlaceholders(ErasedClans main) {
        this.main = main;
    }

    public @NotNull String getIdentifier() {
        return "clans";
    }

    public @NotNull String getAuthor() {
        return "Karam";
    }

    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(Player player, String params) {
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if (params.equalsIgnoreCase("name")) {
            if (playerManager.getClan().equals("null")) {
                return "§cAucun";
            }
            return playerManager.getClan();
        }

        if (params.equalsIgnoreCase("xp")) {
            return String.valueOf(clanManager.getClanXp());
        }

        if (params.equalsIgnoreCase("level")) {
            return String.valueOf(clanManager.getClanLevel());
        }
        return null;
    }
}
