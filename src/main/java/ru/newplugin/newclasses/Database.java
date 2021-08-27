package ru.newplugin.newclasses;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, ClassType> classesCache = new HashMap<>();
    private final Connection connection;

    public Database(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Nullable
    public final PlayerClass getPlayerClass(@NotNull String username) {
        String lowerUsername = username.toLowerCase();
        ClassType cached = this.classesCache.get(username.toLowerCase());
        if (cached != null) // don't query database if cached
            return PlayerClass.getByID(cached.getID());

        try (PreparedStatement s = this.connection.prepareStatement("SELECT * FROM users WHERE username=?")) {
            s.setString(1, lowerUsername);
            try (ResultSet rs = s.executeQuery()) {
                return PlayerClass.getByID(rs.getInt("class"));
            }
        } catch (SQLException ex) {
            NewClasses.getInstance().getSLF4JLogger().warn("Не удалось получить профессию игрока «%s»".formatted(username), ex);
            return null;
        }
    }

    public final void setClass(@NotNull String username, int id) {
        String lowerUsername = username.toLowerCase();
        ClassType cached = this.classesCache.get(username.toLowerCase());
        if (cached != null && cached.getID() == id) // don't update database if not needed
            return;
        try (PreparedStatement s = this.connection.prepareStatement(this.getPlayerClass(username) != null
                ? "UPDATE users SET class=? WHERE username=?" : "INSERT INTO users (class, username) VALUES (?, ?)"
        )) {
            s.setInt(1, id);
            s.setString(2, lowerUsername);
            s.executeUpdate();
            this.classesCache.put(lowerUsername, PlayerClass.getByID(id).getType());
        } catch (SQLException ex) {
            NewClasses.getInstance().getSLF4JLogger().warn("Не удалось установить профессию ID %d игроку «%s»".formatted(id, username), ex);
        }
    }

}
