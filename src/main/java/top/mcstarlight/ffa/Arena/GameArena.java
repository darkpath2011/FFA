package top.mcstarlight.ffa.Arena;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import lombok.Getter;
import lombok.Setter;
import top.mcstarlight.ffa.Main;

import java.util.*;

/*
 * 本类参考于 https://github.com/Cookie-Studio/AweadyFFA 本项目仓库的FFAArea.java类的实例化形式来做游戏竞技场管理
 */
@Getter
@Setter
public class GameArena {
    private Set<String> players;
    private String name;
    private List<String> items;
    private List<String> armor;
    private String JoinTitle;
    private String JoinTip;
    private String JoinMsg;
    private boolean nol;
    private Position pos;
    private String deathmsg;
    private int kill_money;

    public GameArena(String type) {
        Config config = Main.getArenaConfig();
        this.players = new HashSet<>();
        this.name = config.getString(type+".name");
        double x = config.getDouble(type + ".spawn-pos.x");
        double y = config.getDouble(type + ".spawn-pos.y");
        double z = config.getDouble(type + ".spawn-pos.z");
        String worldName = config.getString(type + ".spawn-pos.world");
        Level level = Server.getInstance().getLevelByName(worldName);
        this.pos = new Position(x, y, z, level);

        this.items = config.getStringList(type+".items");
        this.armor = config.getStringList(type+".armor");
        this.JoinTitle = config.getString(type+".joinTitle");
        this.JoinTip = config.getString(type+".joinTip");
        this.JoinMsg = config.getString(type+".joinMsg");
        this.nol = config.getBoolean(type+".nol");
        this.deathmsg = config.getString(type+".deathmsg");
        this.kill_money = config.getInt(type+".kill_money");
    }

    public void join(Player player){
        player.teleport(getPos());
        players.add(player.getUniqueId().toString());
        player.removeAllEffects();
        player.setGamemode(0);
        if (!getJoinMsg().isEmpty()){
            player.sendMessage(getJoinMsg().replace("{player}",player.getName()));
        }
        if (!getJoinTip().isEmpty()){
            player.sendTip(getJoinTip().replace("{player}",player.getName()));
        }
        if (!getJoinTitle().isEmpty()){
            player.sendTitle(getJoinTitle().replace("{player}",player.getName()));
        }

        player.getInventory().clearAll();
        player.removeAllEffects();
        giveItem(player);
    }

    public void giveItem(Player player) {
        player.getInventory().setArmorContents(new Item[4]);
        for (String itemString : items) {
            String[] parts = itemString.split(":");
            if (parts.length >= 2) {
                int itemId = Integer.parseInt(parts[0]);
                int count = Integer.parseInt(parts[1]);
                int enchantmentId = parts.length >= 3 ? Integer.parseInt(parts[2]) : 0;
                Item item = Item.get(itemId, 0, count);
                String itemName = parts[3];
                String itemDescription = parts[4];
                // 如果有附魔
                if (enchantmentId > 0) {
                    Enchantment enchantment = Enchantment.get(enchantmentId);
                    if (enchantment != null) {
                        item.addEnchantment(enchantment);
                    }
                }
                item.setCustomName(itemName);
                item.setLore(itemDescription);
                player.getInventory().addItem(item);
            }
        }
        // 给玩家装备
        for (int i = 0; i < armor.size(); i++) {
            String armorString = armor.get(i);
            String[] parts = armorString.split(":");
            if (parts.length >= 2) {
                int itemId = Integer.parseInt(parts[0]);
                int count = Integer.parseInt(parts[1]);
                int enchantmentId = parts.length >= 3 ? Integer.parseInt(parts[2]) : 0;
                Item item = Item.get(itemId, 0, count);
                // 如果有附魔
                if (enchantmentId > 0) {
                    Enchantment enchantment = Enchantment.get(enchantmentId);
                    if (enchantment != null) {
                        item.addEnchantment(enchantment);
                    }
                }
                player.getInventory().setArmorItem(i, item);
            }
        }
    }

    public void quit(Player player){
        players.remove(player.getUniqueId().toString());
        if (player.isOnline()){
            player.getInventory().clearAll();
            player.setGamemode(0);
            player.removeAllEffects();
            player.teleport(getPos());
        }
    }

    public int getArenaPlayerCount() {
        return players.size();
    }

    public boolean isInArena(Player player) {
        return players.contains(player.getUniqueId().toString());
    }
}
