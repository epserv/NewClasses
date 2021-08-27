package ru.newplugin.newclasses;

import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerClass {

    public static final PlayerClass BUILDER = new PlayerClass(ClassType.BUILDER);
    public static final PlayerClass MINER = new PlayerClass(ClassType.MINER);
    public static final PlayerClass CRAFTER = new PlayerClass(ClassType.CRAFTER);
    public static final PlayerClass FORGER = new PlayerClass(ClassType.FORGER);
    public static final PlayerClass NOBODY = new PlayerClass(ClassType.NOBODY);
    public static final PlayerClass EVERYONE = new PlayerClass(ClassType.EVERYONE);

    private final ClassType type;
    private final List<Material> breakList;
    private final List<Material> interactList;
    private final List<Material> craftList;

    public PlayerClass(@NotNull ClassType type) {
        this.type = type;

        MemorySection config = NewClasses.getInstance().classConfigs.get(type);

        this.breakList = config.getStringList("break").stream().filter(s -> !s.isBlank()).map(Material::getMaterial).toList();
        this.interactList = config.getStringList("interact").stream().filter(s -> !s.isBlank()).map(Material::getMaterial).toList();
        this.craftList = config.getStringList("craft").stream().filter(s -> !s.isBlank()).map(Material::getMaterial).toList();
    }

    @NotNull
    public String getName() {
        return this.type.getName();
    }

    @NotNull
    public ClassType getType() {
        return this.type;
    }

    @NotNull
    public List<Material> getBreak() {
        return this.breakList;
    }

    @NotNull
    public List<Material> getInteract() {
        return this.interactList;
    }

    @NotNull
    public List<Material> getCraft() {
        return this.craftList;
    }

    public boolean canBreak(@NotNull Material material) {
        return this.getBreak().contains(material);
    }

    public boolean canInteract(@NotNull Material material) {
        return this.getInteract().contains(material);
    }

    public boolean canCraft(@NotNull Material material) {
        return this.getCraft().contains(material);
    }

    public static PlayerClass getByID(int id) {
        return switch (id) {
            case 0 -> NOBODY;
            case 1 -> BUILDER;
            case 2 -> CRAFTER;
            case 3 -> FORGER;
            case 4 -> MINER;
            default -> null;
        };
    }
}
