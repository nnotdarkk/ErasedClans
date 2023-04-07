package fr.erased.clans;

import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ClanPlaceholders extends PlaceholderExpansion {

    private final Main main;

    public ClanPlaceholders(Main main) {
        this.main = main;
    }

    @Override
    public String getIdentifier() {
        return "clans";
    }

    @Override
    public String getAuthor() {
        return "Karam";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(params.equalsIgnoreCase("name")){
            if(playerManager.getClan().equals("null")){
                return "§cAucun";
            }
            return playerManager.getClan();
        }

        if(params.equalsIgnoreCase("xp")){
            return String.valueOf(clanManager.getClanXp());
        }

        if(params.equalsIgnoreCase("level")){
            return String.valueOf(clanManager.getClanLevel());
        }
        return null;
    }
}
