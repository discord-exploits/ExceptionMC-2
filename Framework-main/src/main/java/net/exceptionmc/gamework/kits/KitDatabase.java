package net.exceptionmc.gamework.kits;

public class KitDatabase {

    public static String kitDatabase;
    public static String kitUser;
    public static String kitEncodedPassword;

    public static void setKitDatabase(String database, String user, String encodedPassword) {
        kitDatabase = database;
        kitUser = user;
        kitEncodedPassword = encodedPassword;
    }
}
