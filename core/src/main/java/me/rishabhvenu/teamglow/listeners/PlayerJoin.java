package me.rishabhvenu.teamglow.listeners;

import me.rishabhvenu.aseplugin.managers.ListenerManager;
import me.rishabhvenu.teamglow.TeamGlow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin extends ListenerManager {
    public PlayerJoin() {
        super(TeamGlow.getInstance());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (TeamGlow.getInstance().getConfig().getBoolean("enabled")) {
            TeamGlow.getHandler().register(event.getPlayer());
        }
    }
}
