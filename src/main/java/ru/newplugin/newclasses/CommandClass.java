package ru.newplugin.newclasses;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static ru.epserv.epmodule.util.StyleUtils.red;

public class CommandClass implements CommandExecutor {

    private final NewClasses plugin;

    public CommandClass(NewClasses plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(red("Эту команду можно использовать только в игре!"));
            return true;
        }

        PlayerClass playerClass = this.plugin.getDatabase().getPlayerClass(player.getName());
        if (playerClass != null && playerClass.getType() != ClassType.NOBODY) {
            sender.sendMessage(red("Профессию можно выбрать только один раз."));
            return true;
        }

        this.plugin.selectionListener.openMenu(player);
        return true;
    }
}
