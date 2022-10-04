package ro.kmagic.chestsort.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ro.kmagic.chestsort.ChestSort;

import java.util.*;

public class Organizer {
    static final String[] colors = {"white", "orange", "magenta", "light_blue", "light_gray", "yellow", "lime", "pink",
            "gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    private static final int maxInventorySize = 54;
    private static final String emptyPlaceholderString = "~";
    final ChestSort plugin;

    public Organizer(ChestSort plugin) {
        this.plugin = plugin;
    }

    static String getColorOrdered(String c) {
        switch(c) {
            case "white":
                return "01_white";
            case "light_gray":
                return "02_light_gray";
            case "gray":
                return "03_gray";
            case "black":
                return "04_black";
            case "brown":
                return "05_brown";
            case "red":
                return "06_red";
            case "orange":
                return "07_orange";
            case "yellow":
                return "08_yellow";
            case "lime":
                return "09_lime";
            case "green":
                return "10_green";
            case "cyan":
                return "11_cyan";
            case "light_blue":
                return "12_light_blue";
            case "blue":
                return "13_blue";
            case "magenta":
                return "14_magenta";
            case "purple":
                return "15_purple";
            case "pink":
                return "16_pink";
            default:
                return "";
        }
    }

    public String[] getTypeAndColor(String typeName) {
        String myColor = emptyPlaceholderString;

        typeName = typeName.toLowerCase();

        for (String color : colors) {
            if (typeName.startsWith(color)) {
                typeName = typeName.replaceFirst(color + "_", "");
                myColor = getColorOrdered(color);
            }
        }

        String[] typeAndColor = new String[2];
        typeAndColor[0] = typeName;
        typeAndColor[1] = myColor;

        return typeAndColor;
    }
    public Map<String, String> getSortableMap(ItemStack item) {
        if (item == null) {
            return new HashMap<>();
        }

        String[] typeAndColor = getTypeAndColor(item.getType().name());
        String typeName = typeAndColor[0];
        String color = typeAndColor[1];

        Map<String, String> sortableMap = new HashMap<>();
        sortableMap.put("{name}", typeName);
        sortableMap.put("{color}", color);
        return sortableMap;
    }

    public String getSortableString(Map<String, String> sortableMap) {
        String sortableString = "{name},{color}".replaceAll(",", "|");

        for (Map.Entry<String, String> entry : sortableMap.entrySet()) {
            String placeholder = entry.getKey();
            String sortableValue = entry.getValue();
            sortableString = sortableString.replace(placeholder, sortableValue);
        }

        return sortableString;
    }

    public void sortInventory(Inventory inv) {
        sortInventory(inv, 0, inv.getSize() - 1);
    }

    public void sortInventory(Inventory inv, int startSlot, int endSlot) {
        if(inv==null) return;

        Map<ItemStack, Map<String, String>> sortableMaps = new HashMap<>();
        ArrayList<Integer> unsortableSlots = new ArrayList<>();

        for (ItemStack item : inv.getContents()) {
            sortableMaps.put(item, getSortableMap(item));
        }

        ItemStack[] items = inv.getContents();

        for (int i = 0; i < startSlot; i++) {
            items[i] = null;
        }

        for (int i = endSlot + 1; i < inv.getSize(); i++) {
            items[i] = null;
        }

        for (int i = startSlot; i <= endSlot; i++) {
            if (isOversizedStack(items[i])) {
                items[i] = null;
                unsortableSlots.add(i);
            }
        }


        for (int i = startSlot; i <= endSlot; i++) {
            if (!unsortableSlots.contains(i)) {
                inv.clear(i);
            }
        }

        ArrayList<ItemStack> nonNullItemsList = new ArrayList<>();
        for (ItemStack item : items) {
            if (item != null) {
                nonNullItemsList.add(item);
            }
        }

        ItemStack[] nonNullItems = nonNullItemsList.toArray(new ItemStack[0]);

        Arrays.sort(nonNullItems, Comparator.comparing((ItemStack item) -> this.getSortableString(sortableMaps.get(item))));

        Inventory tempInventory = Bukkit.createInventory(null, maxInventorySize);

        for (ItemStack item : nonNullItems) {
            tempInventory.addItem(item);
        }

        int currentSlot = startSlot;
        for (ItemStack item : tempInventory.getContents()) {
            if(item==null) break;
            while (unsortableSlots.contains(currentSlot) && currentSlot < endSlot) {
                currentSlot++;
            }
            inv.setItem(currentSlot, item);
            currentSlot++;
        }

    }

    public boolean isOversizedStack(ItemStack item) {
        return item != null && item.getAmount() > 64;
    }


}
