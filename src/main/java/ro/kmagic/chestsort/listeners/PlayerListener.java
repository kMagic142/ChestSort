package ro.kmagic.chestsort.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import ro.kmagic.chestsort.ChestSort;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLeftClickChest(PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) return;
        if(event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if(ChestSort.getInstance().getChestSortingToggle().get(event.getPlayer().getUniqueId()) == null) return;
        if(!ChestSort.getInstance().getChestSortingToggle().get(event.getPlayer().getUniqueId())) return;

        Block clickedBlock = event.getClickedBlock();
        if(!(clickedBlock.getState() instanceof Container)) return;
        if(!belongsToChestLikeBlock(((Container) clickedBlock.getState()).getInventory())) return;

        Container containerState = (Container) clickedBlock.getState();
        Inventory inventory = containerState.getInventory();


        ChestSort.getInstance().getOrganizer().sortInventory(inventory);

    }

    private boolean belongsToChestLikeBlock(Inventory inventory) {
        if (inventory.getHolder() != null && inventory.getHolder().getClass().getName().toLowerCase().contains("boat")) {
            return true;
        }

        if (inventory.getHolder() == null) {
            return false;
        }

        return inventory.getHolder() instanceof Chest || inventory.getHolder() instanceof DoubleChest;
    }

}
