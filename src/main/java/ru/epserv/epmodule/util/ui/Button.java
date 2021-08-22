package ru.epserv.epmodule.util.ui;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class Button {

	@NotNull
	private ItemStack item;
	private ButtonClickAction action;

	/**
	 * @deprecated in favour of {@link Button#Button(Material, Component)}
	 */
	@Deprecated(since = "2.3.0")
	public Button(@NotNull Material icon, @NotNull String caption) {
		this(icon, caption, 1);
	}

	/**
	 * @deprecated in favour of {@link Button#Button(Material, Component, int)}
	 */
	@Deprecated(since = "2.3.0")
	public Button(@NotNull Material icon, @NotNull String caption, int number) {
		this(icon, PaperComponents.legacySectionSerializer().deserialize(caption), number);
	}

	public Button(@NotNull Material icon, @Nullable Component caption) {
		this(icon, caption, 1);
	}

	public Button(@NotNull Material icon, @Nullable Component caption, int number) {
		this.item = new ItemStack(icon, number);
		ItemMeta meta = item.getItemMeta();
		if (caption != null && !caption.hasDecoration(TextDecoration.ITALIC))
			caption = caption.decoration(TextDecoration.ITALIC, false);
		meta.displayName(caption);
		this.item.setItemMeta(meta);
	}

	@Nullable
	public ButtonClickAction getAction() {
		return this.action;
	}

	@NotNull
	public Button setAction(@Nullable ButtonClickAction action) {
		this.action = action;
		return this;
	}

	public @NotNull ItemStack getItem() {
		return this.item;
	}

	@NotNull
	public Button setItem(@NotNull ItemStack item) {
		this.item = item;
		return this;
	}

	@NotNull
	public Button click(@NotNull CommandSender sender, @NotNull UIInstance ui, int slot) {
		if (this.action != null)
			this.action.execute(sender, ui, slot, this);
		return this;
	}

	/**
	 * @deprecated in favour of {@link Button#description(Collection)}
	 */
	@Deprecated(since = "2.3.0")
	@NotNull
	public Button setDescription(@Nullable Collection<String> desc) {
		ArrayList<Component> lines = null;
		if (desc != null) {
			lines = new ArrayList<>();
			for (String s : desc)
				lines.add(Component.text(s).decoration(TextDecoration.ITALIC, false));
		}
		this.description(lines);
		return this;
	}

	@NotNull
	public Button description(@Nullable Collection<Component> desc) {
		List<Component> lines;
		if (desc == null)
			lines = null;
		else
			lines = desc.stream().map(c -> {
				if (!c.hasDecoration(TextDecoration.ITALIC))
					return c.decoration(TextDecoration.ITALIC, false);
				else
					return c;
			}).toList();

		ItemMeta meta = this.item.getItemMeta();
		meta.lore(lines);
		this.item.setItemMeta(meta);
		return this;
	}

	/**
	 * @deprecated in favour of {@link Button#description()}
	 */
	@Deprecated(since = "2.3.0")
	@Nullable
	public List<String> getDescription() {
		List<String> lore = this.item.getItemMeta().getLore();
		return lore == null ? null : new ArrayList<>(this.item.getItemMeta().getLore());
	}

	@Nullable
	public List<Component> description() {
		ItemMeta meta = this.item.getItemMeta();
		return meta != null && meta.hasLore() ? meta.lore() : null;
	}

	@NotNull
	public Button setItemFlags(@NotNull ItemFlag @NotNull... flags) {
		this.item.removeItemFlags(ItemFlag.values());
		this.item.addItemFlags(flags);
		return this;
	}

	@NotNull
	public Button setIcon(@NotNull Material icon) {
		this.item.setType(icon);
		return this;
	}

	/**
	 * @deprecated in favour of {@link Button#caption(Component)}
	 */
	@Deprecated(since = "2.3.0")
	@NotNull
	public Button setCaption(@NotNull String caption) {
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + caption);
		this.item.setItemMeta(meta);
		return this;
	}

	@NotNull
	public Button caption(@Nullable Component caption) {
		ItemMeta meta = this.item.getItemMeta();
		if (meta != null) {
			if (caption != null && !caption.hasDecoration(TextDecoration.ITALIC))
				caption = caption.decoration(TextDecoration.ITALIC, false);
			meta.displayName(caption);
			this.item.setItemMeta(meta);
		}
		return this;
	}

}
