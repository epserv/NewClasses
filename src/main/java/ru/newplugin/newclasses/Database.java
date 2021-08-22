package ru.newplugin.newclasses;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private final Connection connection;

    public Database(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Nullable
    public final PlayerClass getPlayerClass(@NotNull String username) {
        try (PreparedStatement s = this.connection.prepareStatement("SELECT * FROM users WHERE username=?")) {
            s.setString(1, username.toLowerCase());
            try (ResultSet rs = s.executeQuery()) {
                return PlayerClass.getByID(rs.getInt("class"));
            }
        } catch (SQLException ex) {
            NewClasses.getInstance().getSLF4JLogger().warn("Не удалось получить профессию игрока «%s»".formatted(username), ex);
            return null;
        }
    }

    public final boolean isClassSet(@NotNull String username) {
        try (PreparedStatement s = this.connection.prepareStatement("SELECT username FROM users WHERE username=?")) {
            s.setString(1, username.toLowerCase());
            try (ResultSet rs = s.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            NewClasses.getInstance().getSLF4JLogger().warn("Не удалось проверить, имеет ли игрок «%s» профессию".formatted(username), ex);
            return false;
        }
    }

    public final void setClass(@NotNull String username, int id) {
        try (PreparedStatement s = this.connection.prepareStatement(this.isClassSet(username)
                ? "UPDATE users SET class=? WHERE username=?" : "INSERT INTO users (class, username) VALUES (?, ?)"
        )) {
            s.setInt(1, id);
            s.setString(2, username.toLowerCase());
            s.executeUpdate();
        } catch (SQLException ex) {
            NewClasses.getInstance().getSLF4JLogger().warn("Не удалось установить профессию ID %d игроку «%s»".formatted(id, username), ex);
        }
    }

}
