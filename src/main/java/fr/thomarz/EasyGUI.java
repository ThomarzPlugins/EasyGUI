package fr.thomarz;

import fr.thomarz.command.TestCommand;
import fr.thomarz.util.EasyConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyGUI extends JavaPlugin {

    @Getter
    private static EasyGUI instance;

    @Getter
    private EasyConfig easyConfig;

    @Getter
    @Setter
    private EasyData data;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Config
        easyConfig = new EasyConfig();
        easyConfig.initConfig();
        boolean validConfig = easyConfig.loadConfig();

        if (!validConfig) {
            error("Invalid config, fields are missing...");
            getPluginLoader().disablePlugin(this);
            return;
        }

        // Listeners

        // Commands
        new TestCommand().register();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static void info(String message) {
        Bukkit.getLogger().info("[EasyGUI] " + message);
    }
    public static void warning(String message) {
        Bukkit.getLogger().warning("[EasyGUI] " + message);
    }
    public static void error(String message) {
        Bukkit.getLogger().severe("[EasyGUI] " + message);
    }

}
