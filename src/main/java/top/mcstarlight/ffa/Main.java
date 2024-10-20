package top.mcstarlight.ffa;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import top.mcstarlight.ffa.Arena.GameArena;
import top.mcstarlight.ffa.Listener.GameListener;
import top.mcstarlight.ffa.Listener.PlayerListener;
import top.mcstarlight.ffa.Listener.WorldListener;
import top.mcstarlight.ffa.Variable.RsNPC;
import top.mcstarlight.ffa.Variable.Tips;
import top.mcstarlight.ffa.command.FFACommand;
import top.mcstarlight.ffa.command.KBCommand;

import java.io.File;
import java.util.HashMap;

/*
 * 本项目部分代码参考于多个Github项目仓库
 * https://github.com/Cookie-Studio/AweadyFFA
 * https://github.com/PetteriM1/KDR
 * 感谢 GMCLUB 和 404 服务器对我代码提升的支持。虽然我在许多方面还有不足，但与这两个服务器的帮助相比，我深感荣幸。同时，我也要感谢众多 GitHub 项目的开源贡献者们，感谢你们的支持与参考！
 */
public class Main extends PluginBase {
    @Getter
    private static Main instance;
    @Getter
    private static boolean useMySQL;
    @Getter
    private static HashMap<String, GameArena> arenas = new HashMap<>();
    @Getter
    private static Config playerDataConfig;
    @Getter
    private static Config eloDataConfig;
    @Getter
    private static boolean isELOEnabled;
    @Getter
    private static Config arenaConfig;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        playerDataConfig = new Config(new File(getDataFolder(),"player_data.yml"), Config.YAML);
        arenaConfig = new Config(new File(getDataFolder(), "arena.yml"), Config.YAML);
        useMySQL = getConfig().getBoolean("mysql.enable");
        isELOEnabled = getConfig().getBoolean("elo.enable");

        if (isELOEnabled) {
            eloDataConfig = new Config(new File(getDataFolder(),"player_elo_data.yml"), Config.YAML);
        }
        getLogger().info("FFA插件加载中");
        getServer().getCommandMap().register("ffa", new FFACommand());
        getServer().getCommandMap().register("kb", new KBCommand());
    }

    @Override
    public void onEnable() {
        loadArenas();
        getLogger().info("此项目的部分代码参考并学习于 Github 开源项目。在此，向这些开源项目的作者致以最崇高的敬意！");
        getLogger().info("注意: 本项目完全免费。需注意，其不得被用于任何商业用途，其中包括但不限于倒卖等行为。");
        getLogger().info("存储模式: " + (useMySQL ? "MYSQL(还未开发)" : "YAML"));
        Tips.init();
        RsNPC.init();
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("插件卸载");
    }

    private void loadArenas() {
        getLogger().info("加载房间中...");
        for (String name : arenaConfig.getKeys(false)) {
            GameArena Arena = new GameArena(name);
            arenas.put(name, Arena);
            getLogger().info("成功加载: " + name);
        }
    }

    public boolean isPlayerInArena(Player player) {
        return arenas.values().stream().anyMatch(gameArenaArea -> gameArenaArea.isInArena(player));
    }

    public GameArena getPlayerArena(Player player) {
        return arenas.values().stream()
                .filter(gameArenaArea -> gameArenaArea.isInArena(player))
                .findFirst()
                .orElse(null);
    }

    public GameArena getArena(String name) {
        return arenas.values().stream()
                .filter(gameArenaArea -> gameArenaArea.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public int getTotalFFAPlayers() {
        return arenas.values().stream()
                .mapToInt(GameArena::getArenaPlayerCount)
                .sum();
    }

}
