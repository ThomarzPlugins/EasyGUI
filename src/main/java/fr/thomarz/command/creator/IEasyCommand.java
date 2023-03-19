package fr.thomarz.command.creator;

import org.bukkit.command.CommandSender;

public interface IEasyCommand {

    void execute(CommandSender sender, String[] args);

}
