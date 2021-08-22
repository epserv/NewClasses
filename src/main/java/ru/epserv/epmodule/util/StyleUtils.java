package ru.epserv.epmodule.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.SelectorComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @apiNote private code by l_MrBoom_l. <b>Do not distribute!</b>
 * @author l_MrBoom_l
 */
public class StyleUtils {

    @NotNull
    public static Component text(@NotNull Object @NotNull... objects) {
        return text(components(objects));
    }

    @NotNull
    public static Component text(@NotNull Component @NotNull... components) {
        return single(components);
    }

    @NotNull
    public static Component darkAqua(@NotNull Object @NotNull... objects) {
        return darkAqua(components(objects));
    }

    @NotNull
    public static Component darkAqua(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.DARK_AQUA);
    }

    @NotNull
    public static Component green(@NotNull Object @NotNull... objects) {
        return green(components(objects));
    }

    @NotNull
    public static Component green(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.GREEN);
    }

    @NotNull
    public static Component red(@NotNull Object @NotNull... objects) {
        return red(components(objects));
    }

    @NotNull
    public static Component red(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.RED);
    }

    @NotNull
    public static Component blue(@NotNull Object @NotNull... objects) {
        return blue(components(objects));
    }

    @NotNull
    public static Component blue(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.BLUE);
    }

    @NotNull
    public static Component aqua(@NotNull Object @NotNull... objects) {
        return aqua(components(objects));
    }

    @NotNull
    public static Component aqua(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.AQUA);
    }

    @NotNull
    public static Component gray(@NotNull Object @NotNull... objects) {
        return gray(components(objects));
    }

    @NotNull
    public static Component gray(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.GRAY);
    }

    @NotNull
    public static Component gold(@NotNull Object @NotNull... objects) {
        return gold(components(objects));
    }

    @NotNull
    public static Component gold(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.GOLD);
    }

    @NotNull
    public static Component darkGreen(@NotNull Object @NotNull... objects) {
        return darkGreen(components(objects));
    }

    @NotNull
    public static Component darkGreen(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.DARK_GREEN);
    }

    @NotNull
    public static Component yellow(@NotNull Object @NotNull... objects) {
        return yellow(components(objects));
    }

    @NotNull
    public static Component yellow(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.YELLOW);
    }

    @NotNull
    public static Component lightPurple(@NotNull Object @NotNull... objects) {
        return lightPurple(components(objects));
    }

    @NotNull
    public static Component lightPurple(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.LIGHT_PURPLE);
    }

    @NotNull
    public static Component darkPurple(@NotNull Object @NotNull... objects) {
        return darkPurple(components(objects));
    }

    @NotNull
    public static Component darkPurple(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.DARK_PURPLE);
    }

    @NotNull
    public static Component white(@NotNull Object @NotNull... objects) {
        return white(components(objects));
    }

    @NotNull
    public static Component white(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.WHITE);
    }

    @NotNull
    public static Component darkRed(@NotNull Object @NotNull... objects) {
        return darkRed(components(objects));
    }

    @NotNull
    public static Component darkRed(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.DARK_RED);
    }

    @NotNull
    public static Component darkGray(@NotNull Object @NotNull... objects) {
        return darkGray(components(objects));
    }

    @NotNull
    public static Component darkGray(@NotNull Component @NotNull... components) {
        return single(components).colorIfAbsent(NamedTextColor.DARK_GRAY);
    }

    @NotNull
    public static Component newline() {
        return Component.newline();
    }

    @NotNull
    public static Component space() {
        return Component.space();
    }

    @NotNull
    public static Component empty() {
        return Component.empty();
    }

    @NotNull
    public static Component translatable(@NotNull String key, @NotNull Object @NotNull... objects) {
        return translatable(key, components(objects));
    }
    @NotNull
    public static Component translatable(@NotNull String key, @NotNull Component @NotNull... components) {
        return Component.translatable(key, components);
    }

    @NotNull
    public static Component reset(@NotNull Object @NotNull... objects) {
        return reset(components(objects));
    }

    @NotNull
    public static Component reset(@NotNull Component @NotNull... components) {
        return single(components).color(null);
    }

    @NotNull
    public static Component underline(@NotNull Object @NotNull... objects) {
        return underline(components(objects));
    }

    @NotNull
    public static Component underline(@NotNull Component @NotNull... components) {
        return single(components).decorate(TextDecoration.UNDERLINED);
    }

    @NotNull
    public static Component italic(@NotNull Object @NotNull... objects) {
        return italic(components(objects));
    }

    @NotNull
    public static Component italic(@NotNull Component @NotNull... components) {
        return single(components).decorate(TextDecoration.ITALIC);
    }

    @NotNull
    public static Component strikethrough(@NotNull Object @NotNull... objects) {
        return strikethrough(components(objects));
    }

    @NotNull
    public static Component strikethrough(@NotNull Component @NotNull... components) {
        return single(components).decorate(TextDecoration.STRIKETHROUGH);
    }

    @NotNull
    public static Component obfuscate(@NotNull Object @NotNull... objects) {
        return obfuscate(components(objects));
    }

    @NotNull
    public static Component obfuscate(@NotNull Component @NotNull... components) {
        return single(components).decorate(TextDecoration.OBFUSCATED);
    }

    @NotNull
    public static Component bold(@NotNull Object @NotNull... objects) {
        return bold(components(objects));
    }

    @NotNull
    public static Component bold(@NotNull Component @NotNull... components) {
        return single(components).decorate(TextDecoration.BOLD);
    }

    @NotNull
    public static Component clickUrl(@NotNull String url, @NotNull Object @NotNull... objects) {
        return clickUrl(url, components(objects));
    }

    @NotNull
    public static Component clickUrl(@NotNull String url, @NotNull Component @NotNull... components) {
        return single(components).clickEvent(ClickEvent.openUrl(url));
    }

    @NotNull
    public static Component clickCommand(@NotNull String command, @NotNull Object @NotNull... objects) {
        return clickCommand(command, components(objects));
    }

    @NotNull
    public static Component clickCommand(@NotNull String command, @NotNull Component @NotNull... components) {
        return single(components).clickEvent(ClickEvent.runCommand(command));
    }

    @NotNull
    public static Component hoverText(@NotNull String text, @NotNull Object @NotNull... objects) {
        return hoverText(text, components(objects));
    }

    @NotNull
    public static Component hoverText(@NotNull String text, @NotNull Component... components) {
        return single(components).hoverEvent(HoverEvent.showText(text(text)));
    }

    @NotNull
    public static Component hoverText(@NotNull Component text, @NotNull Object @NotNull... objects) {
        return hoverText(text, components(objects));
    }

    @NotNull
    public static Component hoverText(@NotNull Component text, @NotNull Component @NotNull... components) {
        return single(components).hoverEvent(HoverEvent.showText(text));
    }

    @NotNull
    public static Component join(@NotNull Component separator, @NotNull Collection<?> objects) {
        return Component.join(separator, objects.stream().map(obj -> obj instanceof Component c ? c : text(String.valueOf(obj))).toArray(Component[]::new));
    }

    @NotNull
    public static Component join(@NotNull Collection<?> objects) {
        return join(Component.empty(), objects);
    }

    @NotNull
    public static TextComponent join(@NotNull Component separator, @NotNull Object @NotNull... objects) {
        return Component.join(separator, components(objects));
    }

    @NotNull
    public static Component join(@NotNull Object... objects) {
        return join(Component.empty(), objects);
    }

    @NotNull
    public static Component@NotNull[] components(@Nullable Object @NotNull... objects) {
        return Arrays.stream(objects).filter(Objects::nonNull).map(StyleUtils::component).toArray(Component[]::new);
    }

    @NotNull
    public static Component component(@NotNull Object obj) {
        return obj instanceof Component c ? c : Component.text(String.valueOf(obj));
    }

    public static Component single(@NotNull Component @NotNull... components) {
        return components.length == 0 ? Component.empty() : (components.length == 1 ? components[0] : join((Object[]) components));
    }

    public static Component single(@NotNull Collection<Component> components) {
        return single(components.toArray(Component[]::new));
    }

    public static Component regular(@NotNull Component component) {
        Component c = component;
        for (TextDecoration d : TextDecoration.values())
            c = c.decoration(d, false);
        return c;
    }

    @NotNull
    public static TextComponent spaced(@Nullable Object @NotNull... objects) {
        return join(space(), (Object[]) components(objects));
    }

    @NotNull
    public static TextComponent newlined(@Nullable Object @NotNull... objects) {
        return join(newline(), (Object[]) components(objects));
    }

    @NotNull
    public static SelectorComponent selector(@NotNull String pattern) {
        return Component.selector(pattern);
    }

}
