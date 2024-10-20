package top.mcstarlight.ffa.Variable;

import cn.nukkit.Player;
import tip.utils.Api;
import tip.utils.variables.BaseVariable;
import top.mcstarlight.ffa.Manager.PlayerData;
import top.mcstarlight.ffa.mechanism.ELO;
import top.mcstarlight.ffa.mechanism.KDR;

public class Tips extends BaseVariable {
    public Tips(Player player) {
        super(player);
    }

    public static void init() {
        Api.registerVariables("ffa", Tips.class);
    }

    @Override
    public void strReplace() {
        addStrReplaceString("{elo}",String.valueOf(ELO.getELO(this.player.getName())));
        addStrReplaceString("{kdr}",String.valueOf(KDR.getKDR(this.player.getName())));
        addStrReplaceString("{kills}",String.valueOf(PlayerData.getKills(this.player.getName())));
        addStrReplaceString("{deaths}",String.valueOf(PlayerData.getDeaths(this.player.getName())));
    }
}
