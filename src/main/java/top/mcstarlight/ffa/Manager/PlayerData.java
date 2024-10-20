package top.mcstarlight.ffa.Manager;

import cn.nukkit.Player;

public class PlayerManager {
    public int getKills(Player p) {
        return data.getInt("kills." + p.getName(), 0);
    }

    public int getDeaths(Player p) {
        return data.getInt("deaths." + p.getName(), 0);
    }

    public void addKill(Player p) {
        data.set("kills." + p.getName(), getKills(p) + 1);
    }

    public void addDeath(Player p) {
        data.set("deaths." + p.getName(), getDeaths(p) + 1);
    }

    public double getKDR(Player p) {
        return (double) getKills(p) / getDeaths(p);
    }
}
