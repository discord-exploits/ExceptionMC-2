package net.exceptionmc.util;

import java.util.HashMap;

public class OnlineTimeUtil {

    private static final long seconds = 1000;
    private static final long minutes = seconds * 60;
    private static final long hours = minutes * 60;

    private static final HashMap<String, Long> playerIntegerHashMap = new HashMap<>();

    private static final DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public static void createTable() {
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS onlineTime(uuid VARCHAR(255), millis BIGINT)");
    }

    public static boolean playerExists(String uuid) {
        return databaseUtil.exists("onlineTime", "uuid", uuid);
    }

    public static void createPlayer(String uuid) {
        if (!playerExists(uuid)) {
            databaseUtil.create("onlineTime", "uuid", uuid);
            databaseUtil.setInteger("onlineTime", "millis", 1000, "uuid", uuid);
        }
    }

    public static void setOnlineTimeMillis(String uuid, Long millis) {
        if (playerExists(uuid))
            databaseUtil.setLong("onlineTime", "millis", millis, "uuid", uuid);
    }

    public static void addOnlineTimeMillis(String uuid, Long millis) {
        setOnlineTimeMillis(uuid, getOnlineTimeMillis(uuid) + millis);
    }

    public static void subtractOnlineTimeMillis(String uuid, Long millis) {
        setOnlineTimeMillis(uuid, getOnlineTimeMillis(uuid) - millis);
    }

    public static Long getOnlineTimeMillis(String uuid) {
        if (playerExists(uuid))
            if (playerIntegerHashMap.containsKey(uuid))
                return databaseUtil.getLong("onlineTime", "millis", "uuid", uuid)
                        + (System.currentTimeMillis() - playerIntegerHashMap.get(uuid));
            else
                return databaseUtil.getLong("onlineTime", "millis", "uuid", uuid);

        return 0L;
    }

    public static String formatOnlineTime(String uuid, Long millis, Boolean exact) {
        long[] result = new long[5];
        long rest = millis;

        result[0] = rest / hours;
        rest = rest - result[0] * hours;
        result[1] = rest / minutes;
        rest = rest - result[1] * minutes;
        result[2] = rest / seconds;
        rest = rest - result[2] * seconds;
        result[3] = rest;

        String colorCode = "§" + new LanguageUtil().getColor(uuid);

        if (exact) {
            String hour = new LanguageUtil().getString(uuid, "uwQw3qrn"),
                    hours = new LanguageUtil().getString(uuid, "comlKEJP"),
                    minute = new LanguageUtil().getString(uuid, "9CDW6ON4"),
                    minutes = new LanguageUtil().getString(uuid, "Tat0JqN9"),
                    second = new LanguageUtil().getString(uuid, "Vn339g9i"),
                    seconds = new LanguageUtil().getString(uuid, "V2IaAKaL");

            StringBuilder exactFormattedOnlineTime = new StringBuilder();

            if (result[0] != 0) {
                String string;

                if (result[0] == 1)
                    string = colorCode + result[0] + " §7" + hour;
                else
                    string = colorCode + result[0] + " §7" + hours;

                exactFormattedOnlineTime.append(string).append(" ");
            }

            if (result[1] != 0) {
                String string;

                if (result[1] == 1)
                    string = colorCode + result[1] + " §7" + minute;
                else
                    string = colorCode + result[1] + " §7" + minutes;

                exactFormattedOnlineTime.append(string).append(" ");
            }

            if (result[2] != 0) {
                String string;

                if (result[2] == 1)
                    string = colorCode + result[2] + " §7" + second;
                else
                    string = colorCode + result[2] + " §7" + seconds;

                exactFormattedOnlineTime.append(string);
            }

            return exactFormattedOnlineTime.toString();
        } else {
            String hour = new LanguageUtil().getString(uuid, "txLkqcYT"),
                    minute = new LanguageUtil().getString(uuid, "Os4zXTXH"),
                    second = new LanguageUtil().getString(uuid, "DBYHglNY");

            if (result[0] != 0)
                return colorCode + result[0] + "§7" + hour;

            if (result[1] != 0)
                return colorCode + result[1] + "§7" + minute;

            if (result[2] != 0)
                return colorCode + result[2] + "§7" + second;
        }

        return null;
    }

    public static void startOnlineTimeCounter(String uuid) {
        if (!playerIntegerHashMap.containsKey(uuid))
            playerIntegerHashMap.put(uuid, System.currentTimeMillis());
    }

    public static void stopOnlineTimeCounter(String uuid, boolean save) {
        if (playerIntegerHashMap.containsKey(uuid)) {
            if (save) {
                Long millis = System.currentTimeMillis() - playerIntegerHashMap.get(uuid);
                addOnlineTimeMillis(uuid, millis);
            }

            playerIntegerHashMap.remove(uuid);
        }
    }
}
