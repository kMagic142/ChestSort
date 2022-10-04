package ro.kmagic.chestsort;

import org.bukkit.plugin.java.JavaPlugin;
import ro.kmagic.chestsort.listeners.CommandListener;
import ro.kmagic.chestsort.listeners.PlayerListener;
import ro.kmagic.chestsort.utils.Organizer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ChestSort extends JavaPlugin {

    private static ChestSort instance;

    private Organizer organizer;

    private final Map<UUID, Boolean> chestSortingToggle = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        this.organizer = new Organizer(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("sortchest").setExecutor(new CommandListener());

        getLogger().info("ChestSort is now enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChestSort is now disabled.");
    }

    public static ChestSort getInstance() {
        return instance;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public Map<UUID, Boolean> getChestSortingToggle() {
        return chestSortingToggle;
    }
}
