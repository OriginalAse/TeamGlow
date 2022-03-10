package me.rishabhvenu.teamglow;

import me.rishabhvenu.aseplugin.AsePlugin;
import me.rishabhvenu.aseplugin.utils.Reflection;
import me.rishabhvenu.teamglow.listeners.PlayerJoin;
import org.bukkit.Bukkit;

public class TeamGlow extends AsePlugin {
    private static TeamGlow instance;
    private static IGlowHandler handler;

    public TeamGlow() {
        super(true);
    }

    @Override
    public void onStart() {
        instance = this;
        handler = (IGlowHandler) Reflection.instantiateClass("me.rishabhvenu.teamglow.nms." + getNMSVersion() + ".GlowHandler");
        registerListeners();
    }

    private void registerListeners() {
        new PlayerJoin();
    }

    private String getNMSVersion(){
        String version = Bukkit.getServer().getClass().getPackage().getName();
        return version.substring(version.lastIndexOf('.') + 1);
    }

    public static TeamGlow getInstance() {
        return instance;
    }

    public static IGlowHandler getHandler() {
        return handler;
    }
}
