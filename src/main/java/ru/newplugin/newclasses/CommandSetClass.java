package ru.newplugin.newclasses;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.newplugin.newclasses.NewClasses;

import static ru.epserv.epmodule.util.StyleUtils.*;

public class CommandSetClass implements CommandExecutor {

    private final NewClasses plugin;

    public CommandSetClass(NewClasses plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (!sender.isOp())
            return true;
        if (args.length != 2)
            return false;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(red("Игрок не найден."));
            return true;
        }

        ClassType type = ClassType.match(args[1]);
        if (type == null) {
            sender.sendMessage(red("Не удалось найти класс ", gray(args[1]), "."));
            return true;
        }
        player.setGameMode(type == ClassType.NOBODY ? GameMode.ADVENTURE : Bukkit.getDefaultGameMode());
        this.plugin.getDatabase().setClass(player.getName(), type.getID());
        sender.sendMessage(green("Успешно!"));
        return true;
    }
}
