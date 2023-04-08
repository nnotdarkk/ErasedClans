package fr.erased.clans.commands.subcommands;

import fr.erased.clans.Main;
import fr.erased.clans.storage.ClanManager;
import fr.erased.clans.storage.PlayerManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class SetbaseCommand {

    private final Main main;

    public SetbaseCommand(Main main) {
        this.main = main;
    }

    @Command(name = "clan.setbase")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        PlayerManager playerManager = new PlayerManager(main, player);
        ClanManager clanManager = new ClanManager(main, playerManager.getClan());

        if(playerManager.getClan().equals("null")){
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        String ownerId = clanManager.getOwner();
        String playerId = player.getUniqueId().toString();

        if(!ownerId.equals(playerId)) {
            player.sendMessage("§cVous n'avez pas la permission de changer de base de clan");
            return;
        }

        if(clanManager.getClanLevel() < 20){
            player.sendMessage("§cVous devez être niveau 20 pour définir une base de clan");
            return;
        }

        clanManager.setClanBase(player.getLocation());
        player.sendMessage("§a§l» §7Vous avez défini la base de votre clan avec succès");
    }
}
