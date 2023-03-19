package fr.thomarz.gui;

import fr.thomarz.EasyData;
import fr.thomarz.EasyGUI;
import fr.thomarz.item.TAction;
import fr.thomarz.item.TItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class MenuGUI implements Listener {

    private String title;
    private int size;
    private MenuGUI previousMenu;
    private int currentPage;
    private int maxPages;
    private boolean canClick;
    private List<Inventory> pages;
    private Map<String, TAction> actions;

    public MenuGUI(String title, int size, MenuGUI previousMenu, int currentPage, int maxPages) {
        this.title = title;
        this.size = size;
        this.previousMenu = previousMenu;
        this.currentPage = currentPage;
        this.maxPages = maxPages;
        this.canClick = false;
        this.pages = new ArrayList<>();
        this.actions = new HashMap<>();

        createPages();
        Bukkit.getServer().getPluginManager().registerEvents(this, EasyGUI.getInstance());
    }

    /**
     * Add an item in the gui at a page and position
     * @param itemStack The item to add
     * @param position The position of the item
     * @param page The page of the item
     */
    public void addItem(ItemStack itemStack, int position, int page) {
        if (size < position) return;
        try {
            pages.get(page).setItem(position, itemStack);
        } catch (Exception e) {
            EasyGUI.warning(e.getMessage());
        }
    }

    /**
     * Add an item in the gui at a page and position
     * @param item The item to add
     * @param position The position of the item
     * @param page The page of the item
     */
    public void addItem(TItem item, int position, int page) {
        if (item.getAction() != null) actions.put(item.getName(), item.getAction());
        addItem(item.toItemStack(), position, page);
    }

    /**
     * Add an item in the gui in each pages at a position
     * @param item The item to add
     * @param position The position of the item
     */
    public void addItem(TItem item, int position) {
        for (int page = 0; page <= this.maxPages; page++) {
            addItem(item, position, page);
        }
    }

    /**
     * Add an item in the gui in each pages at a position
     * @param item The item to add
     * @param position The position of the item
     */
    public void addItem(ItemStack item, int position) {
        for (int page = 0; page <= maxPages; page++) {
            addItem(item, position, page);
        }
    }

    /**
     * Add an item at a position retrieve by [column, row]
     * @param item The item to add
     * @param column The column of the item
     * @param row The row of the item
     * @param page The page of the item
     */
    public void addItemTable(TItem item, int column, int row, int page) {
        int position = getPosition(column, row);
        addItem(item, position, page);
    }

    /**
     * Add an item at a position retrieve by [column, row] in each pages
     * @param item The item to add
     * @param column The column of the item
     * @param row The row of the item
     */
    public void addItemTable(TItem item, int column, int row) {
        for (int page = 0; page <= this.maxPages; page++) {
            addItemTable(item, column, row, page);
        }
    }

    /**
     * Retrieve a position with the [column, row]
     * @param column The column index
     * @param row The row index
     * @return The position retrieve by the [column, row]
     */
    public int getPosition(int column, int row) {
        return (9 * row) + column;
    }

    /**
     * Generate each pages
     */
    private void createPages() {
        for (int page = 0; page <= maxPages; page++) {
            this.pages.add(Bukkit.createInventory(null, size, title + (maxPages > 0 ? " (" + (page + 1) + "/" + maxPages + ")" : "")));
        }
    }

    /**
     * Open the current gui for a player
     * @param player The player we want to open the gui
     * @param page The page to open
     */
    public void openGUI(Player player, int page) {
        try {
            player.openInventory(pages.get(page));
        } catch (Exception e) {
            EasyGUI.error("Cannot open page: " + page + ": " + e.getMessage());
        }
    }

    /**
     * Open the first page of the current gui for a player
     * @param player The player we want to open the gui
     */
    public void openGUI(Player player) {
        openGUI(player, currentPage);
    }

    /**
     * @return The amount of rows in the current gui
     */
    public int getRows() {
        return size / 9;
    }

    /**
     * Place item on vertical right and left border, it
     * will place item1 and item2 alternatively
     * @param item1 The first item
     * @param item2 The second item
     * @param page The page to add the border
     */
    public void addVerticalBorder(TItem item1, TItem item2, int page) {
        for (int row = 0; row < getRows(); row++) {
            addItemTable(row % 2 == 0 ? item1 : item2, 0, row, page);
            addItemTable(row % 2 == 0 ? item1 : item2, 8, row, page);
        }
    }

    /**
     * Place item on vertical right and left border, it
     * will place item1 and item2 alternatively
     * @param item1 The first item
     * @param item2 The second item
     */
    public void addVerticalBorder(TItem item1, TItem item2) {
        for (int page = 0; page < maxPages; page++) {
            addVerticalBorder(item1, item2, page);
        }
    }

    /**
     * Place item on vertical right and left border
     * @param item The border item
     */
    public void addVerticalBorder(TItem item) {
        addVerticalBorder(item, item);
    }

    /**
     * Place item on vertical right and left border
     * @param item The border item
     * @param page The page to add the border
     */
    public void addVerticalBorder(TItem item, int page) {
        addVerticalBorder(item, item, page);
    }

    public void addHorizontalBorder(TItem item1, TItem item2, int page) {
        for (int column = 0; column < 9; column++) {
            addItemTable(column % 2 == 0 ? item1 : item2, column, 0, page);
            addItemTable(column % 2 == 0 ? item1 : item2, column, getRows() - 1, page);
        }
    }

    public void addHorizontalBorder(TItem item1, TItem item2) {
        for (int page = 0; page < maxPages; page++) {
            addHorizontalBorder(item1, item2, page);
        }
    }

    public void addHorizontalBorder(TItem item) {
        addHorizontalBorder(item, item);
    }

    public void addHorizontalBorder(TItem item, int page) {
        addHorizontalBorder(item, item, page);
    }

    public void addBorder(TItem item1, TItem item2, int page) {
        addVerticalBorder(item1, item2, page);
        addHorizontalBorder(item1, item2, page);
    }

    public void addBorder(TItem item1, TItem item2) {
        addVerticalBorder(item1, item2);
        addHorizontalBorder(item1, item2);
    }

    public void addBorder(TItem item, int page) {
        addBorder(item, item, page);
    }

    public void addBorder(TItem item) {
        addBorder(item, item);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().startsWith(getTitle())) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (!canClick) {
            event.setCancelled(true);
            event.setCursor(new ItemStack(Material.AIR));
        }

        int slot = event.getSlot();
        onClick(player, itemStack, pages.get(currentPage), event.getAction(), slot);

        if (!itemStack.hasItemMeta()) {
            return;
        }
        String name = itemStack.getItemMeta().getDisplayName();

        // Custom actions
        if (actions.containsKey(name)) {
            TAction action = actions.get(name);
            if (action != null) {
                action.action();
                return;
            }
        }

        // Close, Return, Next & Previous
        if (EasyData.close.equals(name)) {
            player.getOpenInventory().close();
        } else if (EasyData.back.equals(name)) {
            if (previousMenu != null) {
                previousMenu.openGUI(player);
            }
        } else if (EasyData.next.equals(name)) {
            if (currentPage >= maxPages) {
                return;
            }
            currentPage++;
            openGUI(player, currentPage);
        } else if (EasyData.previous.equals(name)) {
            if (currentPage <= 0) {
                return;
            }
            currentPage--;
            openGUI(player, currentPage);
        }
    }

    public abstract void onClick(Player player, ItemStack current, Inventory inventory, InventoryAction action, int slot);
}
