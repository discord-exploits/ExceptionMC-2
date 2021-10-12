package net.exceptionmc.util;

@SuppressWarnings("unused")
public class LanguageUtil {

    DatabaseUtil databaseUtil
            = new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void createTable() {
        databaseUtil.executeUpdate(
                "CREATE TABLE IF NOT EXISTS languageStrings(id VARCHAR(255), en VARCHAR(1020), " +
                        "de VARCHAR(1020), jp VARCHAR(1020), es VARCHAR(1020), fr VARCHAR(1020))"
        );

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS languagePreferences(uniqueId VARCHAR(255), " +
                "language VARCHAR(255), colorCode VARCHAR(255))");
    }

    public boolean playerExists(String uuid) {
        return databaseUtil.exists("languagePreferences", "uniqueId", uuid);
    }

    public boolean stringExists(String id) {
        return databaseUtil.exists("languageStrings", "id", id);
    }

    public void createPlayer(String uuid) {
        if (!(playerExists(uuid))) {
            databaseUtil.create("languagePreferences", "uniqueId", uuid);
            databaseUtil.setString("languagePreferences", "language", "en", "uniqueId", uuid);
            databaseUtil.setString("languagePreferences", "colorCode", "9", "uniqueId", uuid);
        }
    }

    public void setLanguage(String uuid, String value) {
        if (playerExists(uuid))
            databaseUtil.setString("languagePreferences", "language", value, "uniqueId", uuid);
    }

    public void setColor(String uuid, String value) {
        if (playerExists(uuid))
            databaseUtil.setString("languagePreferences", "colorCode", value, "uniqueId", uuid);
    }

    public String getLanguage(String uuid) {
        return databaseUtil.getString("languagePreferences", "language", "uniqueId", uuid);
    }

    public String getColor(String uuid) {
        return databaseUtil.getString("languagePreferences", "colorCode", "uniqueId", uuid);
    }

    public String getString(String uuid, String id) {
        String language = getLanguage(uuid);
        String colorCode = getColor(uuid);
        String output = databaseUtil.getString("languageStrings", language, "id", id);

        if (output != null)
            return output.replace("$color", "§" + colorCode).replace("&", "§");

        return "§cAn error occurred please contact the server administrators about this issue§8.";
    }
}
