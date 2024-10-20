package top.mcstarlight.ffa.Manager;

import top.mcstarlight.ffa.Main;

/**
 * 此类部分代码来自于PM1所制作的KDR插件
 * 项目地址：https://github.com/PetteriM1/KDR
 */
public class PlayerData {
    public static int getKills(String p) {
        return Main.getPlayerDataConfig().getInt("kills." + p, 0);
    }

    public static int getDeaths(String p) {
        return Main.getPlayerDataConfig().getInt("deaths." + p, 0);
    }

    public static void addKill(String p) {
        Main.getPlayerDataConfig().set("kills." + p, getKills(p) + 1);
        Main.getPlayerDataConfig().save();
    }

    public static void addDeath(String p) {
        Main.getPlayerDataConfig().set("deaths." + p, getDeaths(p) + 1);
        Main.getPlayerDataConfig().save();
    }

}
