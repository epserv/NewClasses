package ru.epserv.epmodule.util.ui;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class ButtonClickAction {
	
	public enum ActionType {
		RUN_COMMAND,
		EXECUTE_FUNCTION,
		RUN_CONSOLE_COMMAND
	}
	
	private final ActionType type;
	private final Object action;
	
	public ButtonClickAction(@NotNull ActionType actionType, @NotNull Object action) {
		this.type = actionType;
		
		switch (actionType) {
		case EXECUTE_FUNCTION:
			if (!(action instanceof ActionRunnable))
				throw new IllegalArgumentException("type of `action` must be ru.epserv.epmodule.modules.skills.ui.ActionRunnable if `actionType` == EXECUTE_FUNCTION");
			break;
		case RUN_COMMAND:
			if (!(action instanceof String))
				throw new IllegalArgumentException("type of `action` must be java.lang.String if `actionType` == RUN_COMMAND");
			break;
		case RUN_CONSOLE_COMMAND:
			if (!(action instanceof String))
				throw new IllegalArgumentException("type of `action` must be java.lang.String if `actionType` == RUN_CONSOLE_COMMAND");
			break;
		
		}
		this.action = action;
	}
	
	public ButtonClickAction setAction(@NotNull Object action) {
		switch (this.type) {
		case EXECUTE_FUNCTION:
			if (!(action instanceof ActionRunnable))
				throw new IllegalArgumentException("type of `action` must be ru.epserv.epmodule.modules.skills.ui.ActionRunnable if `actionType` == EXECUTE_FUNCTION");
			break;
		case RUN_COMMAND:
			if (!(action instanceof String))
				throw new IllegalArgumentException("type of `action` must be java.lang.String if `actionType` == RUN_COMMAND");
			break;
		case RUN_CONSOLE_COMMAND:
			if (!(action instanceof String))
				throw new IllegalArgumentException("type of `action` must be java.lang.String if `actionType` == RUN_CONSOLE_COMMAND");
			break;
		}
		return this;
	}
	
	public ButtonClickAction execute(CommandSender sender, UIInstance ui, int slot, Button button) {
		switch (this.type) {
			case EXECUTE_FUNCTION -> ((ActionRunnable) this.action).run(ui, slot, button);
			case RUN_COMMAND -> Bukkit.dispatchCommand(sender, (String) this.action);
			case RUN_CONSOLE_COMMAND -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) this.action);
		}
		return this;
	}

}
