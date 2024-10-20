package top.mcstarlight.ffa.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import me.onebone.economyapi.EconomyAPI;
import top.mcstarlight.ffa.Arena.GameArena;
import top.mcstarlight.ffa.Main;
import top.mcstarlight.ffa.Manager.PlayerData;
import top.mcstarlight.ffa.mechanism.ELO;

public class GameListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity(); // 被攻击者
        Player attacker = (Player) event.getDamager(); // 攻击者
        if (player.getHealth() - event.getFinalDamage() < 1) {
            event.setCancelled(true);

            GameArena arena = Main.getInstance().getPlayerArena(player);
            Main.getInstance().getServer().broadcastMessage(Main.getInstance().getConfig().getString("lang.death_message").replace("{attack}",attacker.getName()).replace("{player}",player.getName()));
            PlayerData.addKill(attacker.getName());
            PlayerData.addDeath(player.getName());
            if(Main.isELOEnabled()){
                ELO.updateELO(attacker.getName(),player.getName());
            }

            attacker.setHealth(attacker.getMaxHealth());
            player.setHealth(player.getMaxHealth());
            player.getInventory().clearAll();

            EconomyAPI.getInstance().addMoney(attacker.getUniqueId(), arena.getKill_money());
            if (Main.getInstance().getConfig().getBoolean("death_spawn")){
                player.teleport(arena.getPos());
                arena.giveItem(player);
            } else {
                player.teleport(Main.getInstance().getServer().getDefaultLevel().getSpawnLocation());
                arena.quit(player);
            }
        }
    }
}
