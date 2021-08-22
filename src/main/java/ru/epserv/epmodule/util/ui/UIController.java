package ru.epserv.epmodule.util.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.newplugin.newclasses.NewClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class UIController {

	private static Map<String, UIInstance> list;
	private static List<UIInstance> privateList;
	
	private static boolean ready = false;
	private static InventoryClickHandler handler;
	
	public static void init() {
		if (UIController.ready)
			return;
		UIController.list = new HashMap<>();
		UIController.privateList = new ArrayList<>();
		
		UIController.handler = new InventoryClickHandler();
		Bukkit.getPluginManager().registerEvents(UIController.handler, NewClasses.getInstance());

		UIController.ready = true;
	}
	
	public static void close() {
		if (!UIController.ready)
			return;
		
		InventoryClickEvent.getHandlerList().unregister(UIController.handler);
		
		UIController.list = null;
		UIController.handler = null;
		
		UIController.ready = false;
	}
	
	public static void registerUI(@NotNull UIInstance instance) {
		checkReady();

		if (UIController.list.containsKey(instance.getId()))
			throw new IllegalArgumentException("Specified instance (ID '" + instance.getId() + "') is already registered. Unregister it first.");
		UIController.list.put(instance.getId(), instance);
	}
	
	public static void unregisterUI(@NotNull String id) {
		checkReady();

		if (!UIController.list.containsKey(id))
			throw new IllegalArgumentException("UIInstance with ID '" + id + "' is not registered.");
		UIController.list.remove(id);
	}
	
	public static void registerPrivateUI(@NotNull UIInstance instance) {
		checkReady();
		UIController.privateList.add(instance);
	}
	
	public static boolean unregisterPrivateUI(@NotNull UIInstance instance) {
		checkReady();
		return UIController.privateList.remove(instance);
	}

	public static boolean isInstanceRegistered(String id) {
		checkReady();
		return UIController.list.containsKey(id);
	}
	
	public static void show(Player player, String instanceId) {
		checkReady();
		
		
		if (!UIController.list.containsKey(instanceId))
			throw new IllegalArgumentException("UIInstance with ID '" + instanceId + "' is not registered.");
		
		UIController.list.get(instanceId).show(player);
	}
	
	@Nullable
	protected static UIInstance getInstance(Inventory inventory) {
		checkReady();
		
		for (UIInstance ui : UIController.list.values()) {
			if (ui.inventory.equals(inventory))
				return ui;
		}
		return null;
	}
	

	public static UIInstance[] getPrivateInstances(Inventory inv) {
		checkReady();
		
		List<UIInstance> instances = new ArrayList<>();
		for (UIInstance ui : UIController.privateList) {
			if (ui.inventory.equals(inv))
				instances.add(ui);
		}
		return instances.toArray(new UIInstance[0]);
	}

	public static void checkReady() {
		if (!UIController.ready)
			throw new IllegalStateException("UIController hasn't been initialized with UIController#init()");
	}
}
