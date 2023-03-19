package fr.thomarz.gui;

import fr.thomarz.item.TItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestGUI extends MenuGUI {

    public TestGUI(MenuGUI previousMenu) {
        super("TestGUI", 6 * 9, previousMenu, 0, 3);
        addBorder(new TItem(Material.STONE));
    }

    @Override
    public void onClick(Player player, ItemStack current, Inventory inventory, InventoryAction action, int slot) {

    }
}
