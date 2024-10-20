package top.mcstarlight.ffa.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.*;
import cn.nukkit.math.Vector3;
import top.mcstarlight.ffa.Main;

import java.util.Random;

public class KBCommand extends Command{
    private Random random = new Random();

    public KBCommand() {
        super("kb", "调整kb和ac指令", "/kb [kb值] [ac值] [运用世界]");
        this.setPermission("ffaplugin.kbac");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
//        if (!testPermission(commandSender)) {
//            commandSender.sendMessage("你没有权限哦");
//            return false;
//        }
//        if (strings.length < 3) {
//            commandSender.sendMessage("参数格式错误");
//        }
//        if (!(commandSender instanceof Player)){
//            Main.getInstance().getLogger().info("不能再后台执行");
//            return false;
//        }
//        if (!commandSender.hasPermission(getPermission()))
//            return false;

        return false;
    }
}
