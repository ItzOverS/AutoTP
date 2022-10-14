package autotp.autotp;

import autotp.autotp.commands.tpa;
import autotp.autotp.commands.tpaccept;
import autotp.autotp.commands.tpdeny;
import autotp.autotp.events.OnOpenGUIsClicked;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public final class AutoTP extends JavaPlugin {

    private static AutoTP plugin;
    public static HashMap<Player, Player> requestData = new HashMap<>();
    public static HashMap<Player, Player> ReversedRequestData = new HashMap<>();
    public static HashMap<Player, Player> BackUpRequestData = new HashMap<>();
    public static int rangeOfHashMaps = 0;
    public static AutoTP getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        requestData.clear();
        Objects.requireNonNull(getServer().getPluginCommand("tpa")).setExecutor(new tpa());
        Objects.requireNonNull(getServer().getPluginCommand("tpaccept")).setExecutor(new tpaccept());
        Objects.requireNonNull(getServer().getPluginCommand("tpdeny")).setExecutor(new tpdeny());
        getServer().getPluginManager().registerEvents(new OnOpenGUIsClicked(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[AutoTP] " + ChatColor.GREEN + "plugin simplify turned on!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
