package ru.newplugin.newclasses;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import ru.epserv.epmodule.util.ui.UIController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewClasses extends JavaPlugin {

	public final Map<ClassType, YamlConfiguration> classConfigs = new HashMap<>();
	private EventListener listener = null;

	private Connection connection;
	private Database database;

	public static NewClasses getInstance() {
		return JavaPlugin.getPlugin(NewClasses.class);
	}

	public void onEnable() {
		if (!this.getDataFolder().exists() && !this.getDataFolder().mkdirs())
			throw new RuntimeException("Не удалось создать директорию «%s».".formatted(this.getDataFolder()));

		UIController.init();

		for (ClassType type : ClassType.values()) {
			String fileName = type.name().toLowerCase() + ".yml";
			File file = new File(getDataFolder(), fileName);
			if (!file.exists()) {
				InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
				if (is != null) {
					try (is; FileWriter writer = new FileWriter(file)) {
						writer.write(IOUtils.toString(is, StandardCharsets.UTF_8));
					} catch (IOException ex) {
						this.getSLF4JLogger().warn("Не удалось сохранить конфиг «%s», ошибка ввода/вывода.".formatted(fileName), ex);
						return;
					}
				} else {
					this.getSLF4JLogger().warn("Не удалось сохранить конфиг «%s», файл конфига не найден в JAR-файле плагина.".formatted(fileName));
					return;
				}
			}

			this.classConfigs.put(type, YamlConfiguration.loadConfiguration(file));
		}

		this.loadDatabase();
		Objects.requireNonNull(this.getCommand("sc"), "Не найдена команда «/sc» в plugin.yml")
				.setExecutor(new SCommand(this));
		Bukkit.getPluginManager().registerEvents(this.listener = new EventListener(this), this);
	}

	public void onDisable() {
		UIController.close();
		HandlerList.unregisterAll(this.listener);
	}

	public Database getDatabase() {
		if (this.database != null)
			return this.database;
		return this.database = new Database(this.connection);
	}

	private void loadDatabase() {
		this.initDatabase("username TEXT NOT NULL", "class INT NOT NULL");
	}

	private void initDatabase(String... columns) {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + getDataFolder() + "/database.db");
			try (PreparedStatement s = this.connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS users (" + String.join(", ", columns) + ")"
			)) {
				s.executeUpdate();
			}
		} catch (Throwable t) {
			this.getSLF4JLogger().error("Произошла ошибка соединения с базой данных, выключение...", t);
			Bukkit.shutdown();
			return;
		}
		this.getSLF4JLogger().info("База данных подключена!");
	}
}
