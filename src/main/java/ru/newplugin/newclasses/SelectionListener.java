package ru.newplugin.newclasses;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.epserv.epmodule.util.ui.*;

import java.util.*;

import static ru.epserv.epmodule.util.StyleUtils.*;

public class SelectionListener implements Listener {

	private static final Component SUGGEST_SELECT = gold(
			"Вы можете выбрать профессию позже с помощью ",
			clickCommand("/class", hoverText("(Клик)", red("/class"))),
			"."
	);

	private final NewClasses plugin;

	private final Set<String> firstJoinClosed = new HashSet<>();

	public SelectionListener(NewClasses plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
		if (!player.hasPlayedBefore() || playerClass == null) {
			this.plugin.getDatabase().setClass(player.getName(), ClassType.NOBODY.getID());
			this.openMenu(player);
		}
	}

	void openMenu(Player player) {
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
				PlayerClass playerClass = Optional.ofNullable(PlayerClass.getByID(switch (slot) {
					case 19 -> 1;
					case 21 -> 4;
					case 23 -> 2;
					case 25 -> 3;
					default -> 0;
				})).orElse(PlayerClass.NOBODY);
				this.plugin.getDatabase().setClass(player.getName(), playerClass.getType().getID());
				if (playerClass.getType() == ClassType.NOBODY) {
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(SUGGEST_SELECT);
				} else {
					player.setGameMode(Bukkit.getDefaultGameMode());
					player.sendMessage(green("Профессия ", red("успешно"), " выбрана!"));
				}
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

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player player))
			return;

		if (!player.hasPlayedBefore() && !this.firstJoinClosed.contains(player.getName().toLowerCase())) {
			this.firstJoinClosed.add(player.getName().toLowerCase());
			player.sendMessage(SUGGEST_SELECT);
		}
	}

}
