package ru.epserv.epmodule.util.ui;

import org.jetbrains.annotations.NotNull;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
@FunctionalInterface
public interface ActionRunnable {

	Object run(@NotNull UIInstance ui, int slot, @NotNull Button button);

}
