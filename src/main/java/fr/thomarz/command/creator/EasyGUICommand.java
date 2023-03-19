package fr.thomarz.command.creator;

import fr.thomarz.EasyGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class EasyGUICommand extends Command {

    private List<EasyCommandPart> commandParts;

    public EasyGUICommand(String name) {
        super(name);
        this.commandParts = new ArrayList<>();

        // Register the help menu for each command
        addCommandPart("help", "Display the help menu", new IEasyCommand() {
            @Override
            public void execute(CommandSender sender, String[] args) {
                showHelpMenu(sender);
            }
        });
    }

    /**
     * Register the command into the Bukkit CommandMap
     */
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

    /**
     * Display the help menu based on the registered EasyCommandPart
     * @param sender The sender that we want to display the help menu
     */
    public void showHelpMenu(CommandSender sender) {
        for (EasyCommandPart commandPart : commandParts) {
            sender.sendMessage(commandPart.getInfoLine());
        }
    }

    /**
     * Add a command part for the command
     * @param args The args of the command
     * @param lore The lore of the command
     * @param command The executor of the command
     */
    public void addCommandPart(String args, String lore, IEasyCommand command) {
        commandParts.add(new EasyCommandPart(getName(), args, lore, command));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        onExecute(sender, label, args);

        for (EasyCommandPart part : commandParts) {
            if (part.getCommand() == null) continue;
            int max = Math.min(part.getArgs().length, args.length);

            for (int i = 0; i < max; i++) {
                if (!args[i].equalsIgnoreCase(part.getArgs()[i])) break;
                if (i + 1 == max) part.getCommand().execute(sender, args);
            }
        }
        return true;
    }

    public abstract void onExecute(CommandSender sender, String label, String[] args);
}
