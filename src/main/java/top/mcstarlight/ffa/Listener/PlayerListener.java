package top.mcstarlight.ffa.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import top.mcstarlight.ffa.Arena.GameArena;
import top.mcstarlight.ffa.Main;
import top.mcstarlight.ffa.mechanism.ELO;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (Main.isELOEnabled()){
            ELO.setELO(player.getName(),Main.getInstance().getConfig().getInt("elo.default-score"));
        }

        player.getInventory().clearAll();
        player.removeAllEffects();
        player.teleport(Main.getInstance().getServer().getDefaultLevel().getSpawnLocation());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main mainInstance = Main.getInstance();
        if (mainInstance.isPlayerInArena(player)) {
            GameArena arena = mainInstance.getPlayerArena(player);
            if (arena != null) {
                arena.quit(player);
            }
        }
    }
}
