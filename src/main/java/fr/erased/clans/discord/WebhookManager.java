package fr.erased.clans.discord;

import fr.erased.clans.Main;
import lombok.SneakyThrows;

import java.awt.*;

public class WebhookManager {

    private final Main main;

    public WebhookManager(Main main) {
        this.main = main;
    }

    @SneakyThrows
    public void sendWebhook(){

        int i = main.getClanManager().getClansCount();
        String clan1 = main.getClanManager().getAllXpClansToList().get(i-1);
        String clan2 = main.getClanManager().getAllXpClansToList().get(i-2);
        String clan3 = main.getClanManager().getAllXpClansToList().get(i-3);

        if(clan1 == null || clan2 == null || clan3 == null){
            return;
        }

        int lvl1 = main.getClanManager().getClanLevel(clan1);
        int lvl2 = main.getClanManager().getClanLevel(clan2);
        int lvl3 = main.getClanManager().getClanLevel(clan3);

        int exp1 = main.getClanManager().getClanXp(clan1);
        int exp2 = main.getClanManager().getClanXp(clan2);
        int exp3 = main.getClanManager().getClanXp(clan3);

        int expToNext1 = main.getClanManager().getClanExpToNextLevel(clan1);
        int expToNext2 = main.getClanManager().getClanExpToNextLevel(clan2);
        int expToNext3 = main.getClanManager().getClanExpToNextLevel(clan3);

        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1044658845382889602/WjaB5YU4_qcPMYfwxDcGKTNKepplSnVwiae-xaVHv_wvDvRPiMCAYvB0F9-oS6DW4yoM");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(":trophy: - Classement des clans")
                .setDescription("Classement actuel des clans sur le serveur. Actualisé toutes les 15 minutes.")
                .setColor(Color.ORANGE)
                .addField(":first_place: - #1", clan1 + ": lvl" + lvl1 + " [" + exp1 + "/" + expToNext1 + "]", false)
                .addField(":second_place: - #2", clan2 + ": lvl" + lvl2 + " [" + exp2 + "/" + expToNext2 + "]", false)
                .addField(":third_place: - #3", clan3 + ": lvl" + lvl3 + " [" + exp3 + "/" + expToNext3 + "]", false));
        webhook.execute();
    }
}
