package fr.thomarz.item;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TItem implements Cloneable {

    public static final String SEPARATOR = ":";
    public static final String TYPE_SEPARATOR = ";";

    private ItemStack item;
    @Getter
    private TAction action;

    public TItem(Material material) {
        this(material, 1);
    }

    public TItem(Material material, String name) {
        this(material, 1);
        setName(name);
    }

    public TItem(ItemStack itemStack, String name) {
        this(itemStack);
        setName(name);
    }

    public TItem(String serialize) {
        this(TItem.deserialize(serialize).toItemStack());
    }

    public TItem(ItemStack item) {
        this.item = item;
    }

    public TItem(Material material, int amount) {
        item = new ItemStack(material, amount);
    }

    public TItem(Material material, int amount, byte data) {
        item = new ItemStack(material, amount, data);
    }

    public TItem clone() {
        return new TItem(item);
    }

    public TItem newInstance() {
        return new TItem(new ItemStack(item));
    }

    public TItem setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public TItem setItemStack(ItemStack itemStack) {
        item = itemStack;
        return this;
    }

    public TItem addPotionEffect(PotionEffect effect){
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();
        itemMeta.addCustomEffect(effect, false);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem setData(byte data){
        item.getData().setData(data);
        return this;
    }

    public TItem setName(String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return this;
    }

    public String getName() {
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getDisplayName();
    }

    public TItem setPotion(boolean splash, PotionType potionType) {
        Potion potion = new Potion(1);
        potion.setType(potionType);
        potion.setSplash(splash);
        potion.apply(item);
        return this;
    }

    public TItem addUnsafeEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public TItem removeEnchantment(Enchantment enchantment) {
        item.removeEnchantment(enchantment);
        return this;
    }

    public TItem setItemMeta(ItemMeta meta) {
        this.item.setItemMeta(meta);
        return this;
    }

    public TItem setMaterial(Material material) {
        this.item.setType(material);
        return this;
    }

    public TItem setSkullOwner(String owner) {
        item.setDurability((byte) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwner(owner);
        item.setItemMeta(skullMeta);
        return this;
    }

    public TItem addEnchant(Enchantment enchantment, int level) {
        if (level <= 0) return this;
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public TItem addEnchantments(Map<Enchantment, Integer> enchantments) {
        item.addEnchantments(enchantments);
        return this;
    }

    public TItem setBannerColor(DyeColor baseColor, Pattern ... patterns){
        BannerMeta bmeta = (BannerMeta) item.getItemMeta();
        bmeta.setBaseColor(baseColor);
        bmeta.setPatterns(Arrays.asList(patterns));
        item.setItemMeta(bmeta);
        return this;
    }

    public TItem setInfinityDurability() {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem setLore(String... lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem setLore(List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem removeLoreLine(String line) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (!lore.contains(line))
            return this;
        lore.remove(line);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem removeLoreLine(int index) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if ((index < 0) || (index > lore.size()))
            return this;
        lore.remove(index);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem addLoreLine(String line) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (itemMeta.hasLore())
            lore = new ArrayList<>(itemMeta.getLore());
        lore.add(line);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem addLoreLine(String... lines) {
        for (String line : lines) {
            addLoreLine(line);
        }
        return this;
    }

    public TItem addLoreLine(List<String> lines) {
        for (String line : lines) {
            addLoreLine(line);
        }
        return this;
    }

    public TItem setAction(TAction action) {
        this.action = action;
        return this;
    }

    public TItem setLoreAction(String action) {
        addLoreLine(" §e§l➟ §6Clique pour " + action);
        return this;
    }

    public TItem setLoreError(String action) {
        addLoreLine("§cImpossible, " + action);
        return this;
    }

    public TItem setEndLore(String end) {
        addLoreLine("§6§l" + end);
        return this;
    }

    public TItem addLoreLine(String line, int pos) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.set(pos, line);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem addItemFlag(ItemFlag... flag) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(flag);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem glow() {
        addEnchant(Enchantment.KNOCKBACK, 0);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return this;
    }

    public TItem setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta itemMeta = (LeatherArmorMeta) item.getItemMeta();
            itemMeta.setColor(color);
            item.setItemMeta(itemMeta);
        } catch (ClassCastException ignored) {
        }
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }

    public String serialize() {
        String materialName = item.getType().name();
        byte materialData = item.getData().getData();
        int amount = item.getAmount();

        // Material
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(materialName).append(SEPARATOR).append(materialData).append(SEPARATOR).append(amount).append(TYPE_SEPARATOR);

        // Other
        byte durability = (byte) item.getDurability();
        stringBuilder.append(durability).append(TYPE_SEPARATOR);

        // Enchant & Potion Effect
        int i = 0;

        Map<Enchantment, Integer> enchantmentIntegerMap = item.getEnchantments();
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            enchantmentIntegerMap = meta.getStoredEnchants();
        }

        for (Map.Entry<Enchantment, Integer> enchantments : enchantmentIntegerMap.entrySet()) {
            Enchantment enchantment = enchantments.getKey();
            String enchantName = enchantment.getName();
            int enchantLevel = enchantments.getValue();

            stringBuilder.append(enchantName).append(SEPARATOR).append(enchantLevel).append(i + 1 < item.getEnchantments().size() ? "-" : "");
            i++;
        }
        return stringBuilder.toString();
    }

    public static TItem deserialize(String serialize) {
        String[] args = serialize.split(TYPE_SEPARATOR);

        String[] argsMaterial = args[0].split(SEPARATOR);
        String[] argsOther = args[1].split(SEPARATOR);

        // Material
        Material material = Material.getMaterial(argsMaterial[0]);
        byte materialData = Byte.parseByte(argsMaterial[1]);
        int amount = Integer.parseInt(argsMaterial[2]);

        TItem itemBuilder = new TItem(material, amount, materialData);

        // Other
        byte durability = Byte.parseByte(argsOther[0]);
        itemBuilder.setDurability(durability);

        // Enchant
        try {
            String[] argsEnchants = args[2].split("-");

            for (String enchant : argsEnchants) {
                String[] argsEnchant = enchant.split(SEPARATOR);
                Enchantment enchantment = Enchantment.getByName(argsEnchant[0]);
                int enchantmentLevel = Integer.parseInt(argsEnchant[1]);

                if (material == Material.ENCHANTED_BOOK) {
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemBuilder.toItemStack().getItemMeta();
                    meta.addStoredEnchant(enchantment, enchantmentLevel, true);
                    itemBuilder.toItemStack().setItemMeta(meta);
                }else {
                    itemBuilder.addEnchant(enchantment, enchantmentLevel);
                }
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            return itemBuilder;
        }
        return itemBuilder;
    }
}
