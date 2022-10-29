package fr.erased.clans.fly;

import fr.erased.clans.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyClaims implements CommandExecutor {

    private final Main main;

    public FlyClaims(Main main) {
        this.main = main;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if(!sender.hasPermission("clans.flyclaims")){
            sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
            return true;
        }

        if(!main.getChunkManager().isClaimed(player.getLocation().getChunk())){
            sender.sendMessage("§cVous ne pouvez pas activer le fly dans une zone non claim !");
            return true;
        }

        String claimer = main.getChunkManager().getClaimer(player.getLocation().getChunk());
        String playerclan = main.getPlayerManager().getClan(player);

        if(!claimer.equals(playerclan)){
            sender.sendMessage("§cVous ne pouvez pas activer le fly dans une zone claim par un autre clan !");
            return true;
        }

        if(player.getAllowFlight()) {
            if(!player.getGameMode().equals(GameMode.CREATIVE)){
                player.setAllowFlight(false);
            }
            player.sendMessage("§e§lErased§6§lClans §7» §eVous ne pouvez plus voler dans vos claims");
        } else {
            player.setAllowFlight(true);
            player.sendMessage("§e§lErased§6§lClans §7» §eVous pouvez désormais voler dans vos claims");
        }

        return false;
    }
}
