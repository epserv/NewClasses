package ru.epserv.epmodule.util.ui;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class UIInstance {
	
	private final String id;
	protected Inventory inventory;
	private final Map<Integer, Button> buttons;
	private Material background = null;
	private String owner = null;
	
	private final Component title;

	/**
	 * @deprecated в пользу {@link UIInstance#UIInstance(Component, int, String)}
	 */
	@Deprecated
	public UIInstance(String title, int size, String id) {
		this(PaperComponents.legacySectionSerializer().deserialize(title), size, id);
	}

	public UIInstance(Component title, int size, String id) {
		this.title = title;
		this.inventory = Bukkit.createInventory(null, size, title);
		this.buttons = new HashMap<>();
		this.id = id;
	}
	
	public UIInstance setButton(int slot, Button button) {
		this.buttons.put(slot, button);
		return this;
	}
	
	public Material getBackground() {
		return this.background;
	}
	
	public UIInstance setBackground(Material material) {
		this.background = material;
		return this;
	}
	
	public UIInstance setOwner(String name) {
		this.owner = name;
		return this;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public UIInstance update() {
		Inventory i = Bukkit.createInventory(null, this.inventory.getSize(), this.title);
		for (Integer slot : this.buttons.keySet()) {
			Button button = this.buttons.get(slot);
			
			if (button == null) {
				if (this.background == null)
					continue;
				
				i.setItem(slot, new ItemStack(this.background));
				continue;
			}
			
			i.setItem(slot, button.getItem());
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			InventoryView iv = p.getOpenInventory();
			if (iv.getType() != InventoryType.CRAFTING && iv.getTopInventory().equals(this.inventory))
				p.openInventory(i);
		}
		this.inventory = i;
		return this;
	}
	

	public String getId() {
		return this.id;
	}

	public void show(Player player) {
		player.openInventory(this.inventory);
	}
	
	protected void destroy() {
		if (UIController.isInstanceRegistered(this.id))
			UIController.unregisterUI(this.id);
	}
	
	public void click(int slot, CommandSender sender) {
		if (!this.buttons.containsKey(slot))
			return;
		
		this.buttons.get(slot).click(sender, this, slot);
	}

}
