package me.rishabhvenu.teamglow.nms.v1_16_R2;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.rishabhvenu.aseplugin.utils.Reflection;
import me.rishabhvenu.teamglow.IGlowHandler;
import net.minecraft.server.v1_16_R2.DataWatcher;
import net.minecraft.server.v1_16_R2.DataWatcherObject;
import net.minecraft.server.v1_16_R2.DataWatcherRegistry;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GlowHandler implements IGlowHandler {
    @Override
    public void register(Player player) {
        ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext context, Object obj, ChannelPromise promise) {
                try {
                    if (obj instanceof PacketPlayOutEntityMetadata entityDataPacket) {
                        Team team = player.getScoreboard().getPlayerTeam(player);
                        if (team != null) {
                            boolean isPlayer = false;
                            Player teammate = null;
                            for (Player entity : Bukkit.getOnlinePlayers()) {
                                if (entity.getEntityId() == (int) Reflection.getPrivateField(entityDataPacket, "a")) {
                                    isPlayer = true;
                                    teammate = entity;
                                    break;
                                }
                            }
                            if (isPlayer && team.equals(player.getScoreboard().getPlayerTeam(teammate))) {
                                DataWatcher data = ((CraftPlayer)teammate).getHandle().getDataWatcher();
                                DataWatcherObject<Byte> accessor = new DataWatcherObject<>(0, DataWatcherRegistry.a);
                                data.set(accessor, (byte) (data.get(accessor) | 0x40));
                            }
                        }
                    }
                    super.write(context, obj, promise);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        (((CraftPlayer)player).getHandle()).playerConnection.networkManager.channel.pipeline()
                .addBefore("packet_handler", player.getName(), handler);
    }
}
