package fr.erased.clans.discord;

import org.bukkit.scheduler.BukkitRunnable;

public class TaskDiscord extends BukkitRunnable {


    @Override
    public void run() {
        new WebhookManager().sendWebhook();
    }
}
