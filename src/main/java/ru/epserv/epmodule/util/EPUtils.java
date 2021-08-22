package ru.epserv.epmodule.util;

import org.jetbrains.annotations.NotNull;

/**
 * @apiNote a part of private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class EPUtils {

	public static boolean validateName(@NotNull String name) {
		return validateName(name, true);
	}

	public static boolean validateName(@NotNull String name, boolean validateLength) {
		return name.equals("Cool.J") || name.matches("[A-Za-z0-9_]" + (validateLength ? "{3,32}" : "{1,256}"));
	}

}
