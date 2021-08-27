package ru.newplugin.newclasses;

import org.bukkit.GameMode;
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
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfessionsListener implements Listener {

	private final NewClasses plugin;

	public ProfessionsListener(NewClasses plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (player.isOp())
			return;
		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
		if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
			e.setCancelled(true);
		} else {
			if (!PlayerClass.EVERYONE.canBreak(e.getBlock().getType()) && !playerClass.canBreak(e.getBlock().getType())) {
				e.setDropItems(false);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (player.isOp())
			return;
		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
		if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
			e.setCancelled(true);
		} else {
			if (!PlayerClass.EVERYONE.canBreak(e.getBlock().getType()) && !playerClass.canBreak(e.getBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.isOp() || e.getAction() == Action.LEFT_CLICK_BLOCK)
			return;
		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
		if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
			e.setCancelled(true);
		} else {
			Block block = e.getClickedBlock();
			if (block == null)
				return;

			if (!PlayerClass.EVERYONE.canInteract(block.getType()) && !playerClass.canInteract(block.getType())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCraft(CraftItemEvent e) {
		if (e.getWhoClicked().isOp())
			return;
		if (e.getWhoClicked() instanceof Player player) {
			PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
			if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
				e.setCancelled(true);
			} else {
				Material type = e.getRecipe().getResult().getType();
				if (!PlayerClass.EVERYONE.canCraft(type) && !playerClass.canCraft(type)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFoodChange(FoodLevelChangeEvent e) {
		if (!(e.getEntity() instanceof Player player) || player.isOp())
			return;
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
				if (!(damager instanceof Player) || playerClass == null || playerClass.getType() != ClassType.FORGER)
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

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(e.getPlayer().getName());
		if (playerClass == null || playerClass.getType() == ClassType.NOBODY) {
			e.getPlayer().setGameMode(GameMode.ADVENTURE);
		}
	}

}
