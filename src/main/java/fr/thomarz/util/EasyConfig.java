package fr.thomarz.util;

import fr.thomarz.EasyData;
import fr.thomarz.EasyGUI;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EasyConfig {

    private FileConfiguration config;

    public EasyConfig() {
        this.config = EasyGUI.getInstance().getConfig();
    }

    /**
     * Init the config and copy default values
     */
    public void initConfig() {
        config.options().copyDefaults(true);
        EasyGUI.getInstance().saveDefaultConfig();
    }

    /**
     * Read fields of the config and create a cache. You need to reload the plugin to
     * reload the cache and load changes
     * @return True if the loaded config is valid, otherwise, returns false
     */
    public boolean loadConfig() {
        FileConfiguration config = EasyGUI.getInstance().getConfig();

        // Load
        String close = config.getString("names.close");
        String back = config.getString("names.back");
        String previous = config.getString("names.previous");
        String next = config.getString("names.next");
        int closePos = config.getInt("positions.close");
        int backPos = config.getInt("positions.back");
        int previousPos = config.getInt("positions.previous");
        int nextPos = config.getInt("positions.next");

        // Set
        EasyData.close = close;
        EasyData.back = back;
        EasyData.previous = previous;
        EasyData.next = next;
        EasyData.closePos = closePos;
        EasyData.backPos = backPos;
        EasyData.previousPos = previousPos;
        EasyData.nextPos = nextPos;

        return isValid();
    }

    /**
     * Save the config
     */
    public void saveConfig() {
        EasyGUI.getInstance().saveConfig();
    }

    /**
     * Check if the config.yml file is valid
     * @return True if the config is valid, otherwise, returns false
     */
    private boolean isValid() {
        List<Field> fields = Arrays.stream(EasyData.class.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());

        for (Field field : fields) {
            try {
                System.out.println(field.get(EasyData.class));
                if (field.get(EasyData.class) == null) {
                    return false;
                }
            } catch (IllegalAccessException ignored) {

            }
        }
        return true;
    }
}
