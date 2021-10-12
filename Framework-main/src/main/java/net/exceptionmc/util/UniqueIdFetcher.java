package net.exceptionmc.util;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class UniqueIdFetcher {

    DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void createTable() {
        databaseUtil.executeUpdate(
                "CREATE TABLE IF NOT EXISTS registeredUniqueIds(uniqueId VARCHAR(255), lastName VARCHAR(255))"
        );
    }

    public boolean playerExists(String uuid) {
        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE uniqueId='" + uuid + "'");

        try {
            assert resultSet != null;
            if (resultSet.next())
                return resultSet.getString("uniqueId") != null;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }

    public void createPlayer(Player player) {
        if (!(playerExists(player.getUniqueId().toString()))) {
            databaseUtil.executeUpdate(
                    "INSERT INTO registeredUniqueIds(uniqueId, lastName) " +
                            "VALUES ('" + player.getUniqueId() + "', '" + player.getName() + "');"
            );
        }
    }

    public void setLastUsedName(String uuid, String value) {
        if (playerExists(uuid)) {
            databaseUtil.executeUpdate(
                    "UPDATE registeredUniqueIds SET lastName= '" + value + "' WHERE uniqueId= '" + uuid + "'"
            );
        }
    }

    public String getLastUsedName(String uuid) {
        String output = null;
        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE uniqueId= '" + uuid + "'");

        try {
            assert resultSet != null;
            if (resultSet.next())
                output = resultSet.getString("lastName");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return output;
    }

    public String getUniqueId(String player) {
        String output = null;
        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE lastName= '" + player + "'");

        try {
            assert resultSet != null;
            if (resultSet.next())
                output = resultSet.getString("uniqueId");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return output;
    }
}
