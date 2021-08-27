package ru.epserv.epmodule.util.ui;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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
	public UIInstance(@NotNull String title, int size, @NotNull String id) {
		this(PaperComponents.legacySectionSerializer().deserialize(title), size, id);
	}

	public UIInstance(@NotNull Component title, int size, @NotNull String id) {
		this.title = title;
		this.inventory = Bukkit.createInventory(null, size, this.title);
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
		ItemStack[] items = new ItemStack[this.inventory.getSize()];
		for (Integer slot : this.buttons.keySet()) {
			Button button = this.buttons.get(slot);
			items[slot] = button != null ? button.getItem() : (this.background != null ? new ItemStack(this.background) : null);
		}

		this.inventory.setContents(items);
		return this;
	}
	

	public String getID() {
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

	@NotNull
	public Component title() {
		return this.title;
	}

}
