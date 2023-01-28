package fr.erased.clans.discord;

import fr.erased.clans.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskDiscord extends BukkitRunnable {

    private final Main main;

    public TaskDiscord(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        new WebhookManager(main).sendWebhook();
    }
}
