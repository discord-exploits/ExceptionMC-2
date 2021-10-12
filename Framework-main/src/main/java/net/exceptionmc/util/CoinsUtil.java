package net.exceptionmc.util;

@SuppressWarnings("unused")
public class CoinsUtil {

    DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void createTable() {
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS coins(uuid VARCHAR(255), amount INT)");
    }

    public boolean playerExists(String uuid) {
        return databaseUtil.exists("coins", "uuid", uuid);
    }

    public void createPlayer(String uuid) {
        if (!playerExists(uuid)) {
            databaseUtil.create("coins", "uuid", uuid);
            databaseUtil.setInteger("coins", "amount", 1000, "uuid", uuid);
        }
    }

    public void setCoins(String uuid, Integer amount) {
        if (playerExists(uuid))
            databaseUtil.setInteger("coins", "amount", amount, "uuid", uuid);
    }

    public void addCoins(String uuid, Integer amount) {
        setCoins(uuid, getCoins(uuid) + amount);
    }

    public void subtractCoins(String uuid, Integer amount) {
        setCoins(uuid, getCoins(uuid) - amount);
    }

    public Integer getCoins(String uuid) {
        if (playerExists(uuid))
            return databaseUtil.getInteger("coins", "amount", "uuid", uuid);

        return 0;
    }
}
