package top.mcstarlight.ffa.Variable;

import cn.nukkit.Player;
import com.smallaswater.npc.data.RsNpcConfig;
import com.smallaswater.npc.variable.BaseVariableV2;
import com.smallaswater.npc.variable.VariableManage;
import top.mcstarlight.ffa.Main;

public class RsNPC extends BaseVariableV2 {
    public static void init() {
        VariableManage.addVariableV2("ffa", RsNPC.class);
    }

    public void onUpdate(Player player, RsNpcConfig rsNpcConfig) {
        initVariable();
    }

    private void initVariable() {
        addVariable("%ffa_online_players%", String.valueOf(Main.getInstance().getTotalFFAPlayers()));
    }

}
