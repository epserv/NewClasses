package ru.newplugin.newclasses;

import org.jetbrains.annotations.NotNull;

public enum ClassType {
	NOBODY(0, "Нет профессии"),
	CRAFTER(2, "Ремесленник"),
	FORGER(3, "Кузнец"),
	BUILDER(1, "Строитель"),
	MINER(4, "Шахтёр"),

	EVERYONE(-1, "Все профессии") // всё, что может EVERYONE, могут все, независимо от профессии
	;

	private final int id;
	private final String name;

	ClassType(int id, @NotNull String name) {
		this.id = id;
		this.name = name;
	}

	public int getID() {
		return this.id;
	}

	@NotNull
	public String getName() {
		return this.name;
	}
}
