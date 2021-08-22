package ru.newplugin.newclasses;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.newplugin.newclasses.NewClasses;

import static ru.epserv.epmodule.util.StyleUtils.green;

public class SCommand implements CommandExecutor {

    private final NewClasses plugin;

    public SCommand(NewClasses plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (!sender.isOp())
            return true;
        if (args.length != 2)
            return false;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
            return false;
        this.plugin.getDatabase().setClass(player.getName(), Integer.parseInt(args[1]));
        sender.sendMessage(green("Успешно!"));
        return true;
    }
}
