package top.mcstarlight.ffa.mechanism;

import top.mcstarlight.ffa.Manager.PlayerData;

/**
 * 此部分代码来自于PM1所制作的KDR插件
 * 项目地址：https://github.com/PetteriM1/KDR
 */
public class KDR {
    public static double getKDR(String p) {
        return (double) PlayerData.getKills(p) / PlayerData.getDeaths(p);
    }
}
