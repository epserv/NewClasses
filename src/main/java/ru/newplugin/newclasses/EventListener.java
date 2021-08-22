package ru.newplugin.newclasses;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.epserv.epmodule.util.ui.*;

import java.util.Collection;
import java.util.Collections;

import static ru.epserv.epmodule.util.StyleUtils.*;

public class EventListener implements Listener {

	private final NewClasses plugin;

	public EventListener(NewClasses plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!this.plugin.getDatabase().isClassSet(player.getName()))
			this.openMenu(player);
	}

	private void openMenu(Player player) {
		UIInstance ui = new UIInstance(text("Выбор профессии"), 45, "class-selection");
		Button[] buttons = {
				new Button(Material.REDSTONE, green("Строитель")),
				new Button(Material.IRON_PICKAXE, green("Шахтёр")),
				new Button(Material.IRON_HOE, green("Ремесленник")),
				new Button(Material.ANVIL, green("Кузнец")),
				new Button(Material.BARRIER, green("Без профессии"))
		};
		Collection<Component> description = Collections.singleton(spaced(
				darkGray("(", darkRed("CLICK"), ")"),
				gray("Чтобы ", red("выбрать"), " данную профессию")
		));
		ButtonClickAction action = new ButtonClickAction(ButtonClickAction.ActionType.EXECUTE_FUNCTION, (ActionRunnable) (ui1, slot, button1) -> {
			try {
				this.plugin.getDatabase().setClass(player.getName(), switch (slot) {
					case 19 -> 1;
					case 21 -> 4;
					case 23 -> 2;
					case 25 -> 3;
					default -> 0;
				});
				player.sendMessage(green("Профессия ", red("успешно"), " выбрана!"));
				player.closeInventory();
				UIController.unregisterPrivateUI(ui1);
				player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_PLAYER_LEVELUP.key(), Sound.Source.BLOCK, 1f, 2f));
			} catch (Exception ex) {
				player.sendMessage(red("Не удалось выбрать професию, попробуйте ещё раз."));
				player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT.key(), Sound.Source.BLOCK, 1f, 0f));
				ex.printStackTrace();
			}
			return null;
		});
		for (Button button : buttons) {
			button.description(description).setAction(action);
		}

		UIController.registerPrivateUI(ui.setOwner(player.getName())
				.setButton(19, buttons[0]).setButton(21, buttons[1]).setButton(23, buttons[2])
				.setButton(25, buttons[3]).setButton(40, buttons[4])
				.update());
		ui.show(player);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player player) {
			if (!this.plugin.getDatabase().isClassSet(player.getName())) {
				// it causes StackOverflowError if menu is reopened at the same tick
				Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.openMenu(player), 1);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (player.isOp())
			return;
		if (!this.plugin.getDatabase().isClassSet(player.getName())) {
			e.setCancelled(true);
		} else {
			PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
			if (playerClass == null || playerClass.getType() == ClassType.NOBODY
					|| (!PlayerClass.EVERYONE.canBreak(e.getBlock().getType()) && !playerClass.canBreak(e.getBlock().getType()))) {
				e.setDropItems(false);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (player.isOp())
			return;
		if (!this.plugin.getDatabase().isClassSet(player.getName())) {
			e.setCancelled(true);
		} else {
			PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
			if (playerClass == null || playerClass.getType() == ClassType.NOBODY
					|| (!PlayerClass.EVERYONE.canBreak(e.getBlock().getType()) && !playerClass.canBreak(e.getBlock().getType()))) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.isOp() || e.getAction() == Action.LEFT_CLICK_BLOCK)
			return;
		if (!this.plugin.getDatabase().isClassSet(player.getName())) {
			e.setCancelled(true);
		} else {
			Block block = e.getClickedBlock();
			if (block == null)
				return;

			PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
			if (playerClass == null || playerClass.getType() == ClassType.NOBODY || (
					!PlayerClass.EVERYONE.canInteract(block.getType()) && !playerClass.canInteract(block.getType())
			)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCraft(CraftItemEvent e) {
		if (e.getWhoClicked().isOp())
			return;
		if (e.getWhoClicked() instanceof Player player) {
			if (!player.isOp())
				if (!this.plugin.getDatabase().isClassSet(player.getName())) {
					e.setCancelled(true);
				} else {
					Material type = e.getRecipe().getResult().getType();
					PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
					if (playerClass == null || playerClass.getType() == ClassType.NOBODY || (!PlayerClass.EVERYONE.canCraft(type) && !playerClass.canCraft(type))) {
						e.setCancelled(true);
					}
				}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFoodChange(FoodLevelChangeEvent e) {
		if (!(e.getEntity() instanceof Player player) || player.isOp())
			return;
		if (!this.plugin.getDatabase().isClassSet(player.getName())) {
			e.setCancelled(true);
			return;
		}

		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
		if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
			e.setCancelled(true);
			e.setFoodLevel(20);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntity().getLastDamageCause() != null) {
			EntityDamageEvent lastDamageCause = e.getEntity().getLastDamageCause();
			if (lastDamageCause instanceof EntityDamageByEntityEvent cause1) {
				Entity damager = cause1.getDamager();
				if (e.getEntity() instanceof Player)
					return;
				PlayerClass playerClass = null;
				if (damager instanceof Player player)
					playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
				if (!(damager instanceof Player player) || !this.plugin.getDatabase().isClassSet(player.getName())
						|| playerClass == null || playerClass.getType() != ClassType.FORGER)
					e.getDrops().clear();
				return;
			}
			if (!(e.getEntity() instanceof Player))
				e.getDrops().clear();
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onExplode(EntityExplodeEvent e) {
		e.setYield(0);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bedExplosion(PlayerBedEnterEvent e) {
		if (e.getBed().getLocation().getWorld().getEnvironment() != World.Environment.NORMAL)
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void anchorExplosion(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null
				&& e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR
				&& e.getClickedBlock().getWorld().getEnvironment() != World.Environment.NETHER)
			e.setCancelled(true);
	}

}
