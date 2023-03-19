package fr.thomarz.command;

import fr.thomarz.EasyGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public abstract class EasyCommand extends Command {
    public EasyCommand(String name) {
        super(name);
    }

    public void register() {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("lacraftia", this);
            EasyGUI.info("Loaded command: " + getName());
        } catch (Exception e) {
            EasyGUI.error(e.getMessage());
        }
    }
}
