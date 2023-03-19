package fr.thomarz.command;

import fr.thomarz.gui.TestGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends EasyCommand {

    public TestCommand() {
        super("test");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player) {
            TestGUI testGUI = new TestGUI(null);
            Player player = (Player) commandSender;
            testGUI.openGUI(player);
        }
        return true;
    }
}
