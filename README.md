# EasyGUI
<h1>1) Developer - Plugin Implementation</h1>

<h2>1.1) Create a GUI</h2>
The following code show you how to create a default GUI. You can use this to show avalables method for build the gui.

```java
import fr.thomarz.gui.MenuGUI;
import fr.thomarz.item.TItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ExampleGUI extends MenuGUI {

    public ExampleGUI(MenuGUI previousMenu) {
        super(
                "MyTitle", // The title of the GUI
                6 * 9, // The size of the GUI
                null, // The previous GUI
                0, // The current page of the gui
                5 // The amount of pages
        );

        TItem myTestItem = new TItem(Material.DIAMOND_SWORD)
                .setName("My Item !")
                .addLoreLine("First Line..")
                .addLoreLine("Second Line...")
                .addEnchant(Enchantment.DAMAGE_ALL, 1);
        
        // Add the item at position 4 in all pages
        addItem(myTestItem, 4);

        // Add the item at position 34 in the 1rst page
        addItem(item, 34, 1);
        
        // Fill the gui horizontal border with GLASS_BOTTLE
        addHorizontalBorder(new TItem(Material.GLASS_BOTTLE));
        
        // Fill the gui vertical border with GOLD_INGOT
        addVerticalBorder(new TItem(Material.GOLD_INGOT));
    
        // Fill the gui border (vertical + horizontal)
        addBorder(new TItem(Material.STONE));
        
        // Add an item at column 2 and at row 4
        addItemTable(new TItem(Material.REDSTONE_BLOCK), 2, 4);
    }

    @Override
    public void onClick(Player player, ItemStack current, Inventory inventory, InventoryAction action, int slot) {
        // When a player click on an item in the gui
    }
}
```

<h2>1.2) Open a GUI</h2>
After creating a GUI Class, you need to use this method to open a gui for a player.

````java
import fr.thomarz.item.TItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MyListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // Create a new instance of the GUI
        ExampleGUI exampleGUI = new ExampleGUI();

        // Open the gui at the first page (by default)
        exampleGUI.openGUI(player);

        // Open the gui at page 3
        exampleGUI.openGUI(player, 3);

        // You can also update the gui here
        exampleGUI.addItem(new TItem(Material.BARRIER), 3);
    }
}
````