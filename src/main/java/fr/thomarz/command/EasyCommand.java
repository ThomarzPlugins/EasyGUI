package fr.thomarz.command;

import fr.thomarz.command.creator.EasyGUICommand;
import fr.thomarz.command.creator.IEasyCommand;
import org.bukkit.command.CommandSender;

public class EasyCommand extends EasyGUICommand {

    public EasyCommand() {
        super("easy");

        addCommandPart("gui", "Open the main gui menu", new IEasyCommand() {
            @Override
            public void execute(CommandSender sender, String[] args) {

            }
        });
    }

    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {

    }
}
