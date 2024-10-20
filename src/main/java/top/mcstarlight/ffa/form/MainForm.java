package top.mcstarlight.ffa.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcstarlight.ffa.Arena.GameArena;
import top.mcstarlight.ffa.Main;

import java.util.List;

public class MainForm {
    public static void FFAMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(Main.getInstance().getConfig().getString("lang.select_mode_form.title"), Main.getInstance().getConfig().getString("lang.select_mode_form.content"));

        List<GameArena> gameArenaList = Main.getArenas().values().stream().toList();
        for (GameArena gameArena : gameArenaList) {
            String buttonText = String.format(Main.getInstance().getConfig().getString("lang.select_mode_form.bottonText")
                    .replace("{mode}", gameArena.getName())
                    .replace("{players}",String.valueOf(gameArena.getArenaPlayerCount())));
            window.addButton(new ElementButton(buttonText));
        }

        window.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (window.wasClosed()) return;
            int clickedButtonId = window.getResponse().getClickedButtonId();
            if (clickedButtonId >= 0 && clickedButtonId < gameArenaList.size()) {
                GameArena selectedGameArena = gameArenaList.get(clickedButtonId);
                selectedGameArena.join(player);
            }
        }));

        player.showFormWindow(window);
    }
}
