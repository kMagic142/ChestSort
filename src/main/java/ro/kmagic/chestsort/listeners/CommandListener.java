package ro.kmagic.chestsort.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.kmagic.chestsort.ChestSort;
import ro.kmagic.chestsort.utils.Utils;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(ChestSort.getInstance().getChestSortingToggle().get(player.getUniqueId()) == null) {
            ChestSort.getInstance().getChestSortingToggle().put(player.getUniqueId(), true);
            player.sendMessage(Utils.color("&aChest sorting has been enabled."));
            return true;
        }

        if(ChestSort.getInstance().getChestSortingToggle().get(player.getUniqueId())) {
            ChestSort.getInstance().getChestSortingToggle().put(player.getUniqueId(), false);
            player.sendMessage(Utils.color("&cChest sorting has been disabled."));
            return true;
        }

        if(!ChestSort.getInstance().getChestSortingToggle().get(player.getUniqueId())) {
            ChestSort.getInstance().getChestSortingToggle().put(player.getUniqueId(), true);
            player.sendMessage(Utils.color("&aChest sorting has been enabled."));
            return true;
        }

        return true;
    }
}
