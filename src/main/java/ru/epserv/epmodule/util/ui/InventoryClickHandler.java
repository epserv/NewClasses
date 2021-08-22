package ru.epserv.epmodule.util.ui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class InventoryClickHandler implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!e.getInventory().equals(e.getClickedInventory()))
			return;
		UIInstance ui = UIController.getInstance(e.getInventory());

		if (ui == null) {
			UIInstance[] instances = UIController.getPrivateInstances(e.getInventory());
			for (UIInstance instance : instances) {
				if (instance.getOwner().equals(e.getWhoClicked().getName())) {
					ui = instance;
					break;
				}
			}
			
			if (ui == null)
				return;
		}

		e.setCancelled(true);
		
		ui.click(e.getSlot(), e.getWhoClicked());
	}

}
