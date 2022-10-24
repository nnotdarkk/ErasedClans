package fr.erased.clans.discord;

import lombok.SneakyThrows;

import java.awt.*;

public class Test {

    @SneakyThrows
    public void sendTest(){
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1033299482340761642/zek1Q1yRIuw3pZuieMatH2sssw3bR1yBuPbPDmZslZmPCIvInd2giGuH2UkY9PUnBYu0");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(":trophy: - Classement des clans")
                .setDescription("Classement actuel des clans sur le serveur. Actualisé toutes les minutes.")
                .setColor(Color.ORANGE)
                .addField(":first_place: - #1", "Clan: lvl5 [308/800]", false)
                .addField(":second_place: - #2", "Clan: lvl3 [212/600]", false)
                .addField(":third_place: - #3", "Clan: lvl2 [89/400]", false));
        webhook.execute();
    }
}
