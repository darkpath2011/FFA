package top.mcstarlight.ffa.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import top.mcstarlight.ffa.Arena.GameArena;
import top.mcstarlight.ffa.Main;
import top.mcstarlight.ffa.form.MainForm;

public class FFACommand extends Command {
    public FFACommand() {
        super("ffa", "ffa指令", "/ffa");
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;
        if (strings.length < 1){
            MainForm.FFAMenu((Player)commandSender);
        } else {
            GameArena arena = Main.getInstance().getArena(strings[0]);
            if (arena != null){
                arena.join(player);
            } else {
                player.sendMessage("§c§l模式不存在!");
            }
        }

        return false;
    }
}
