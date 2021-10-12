package net.exceptionmc;

public class LanguageTerminal {

    private static java.sql.Connection connection;

    private static final String[] languages = new String[]{
            "en", "de", "jp", "es", "fr"
    };

    private static final java.util.ArrayList<String> allowedTranslationLanguages
            = new java.util.ArrayList<>(java.util.Arrays.asList(languages));

    private static final
    String spacer = "================================================================================";

    private static void showUsage() {
        clearTerminal();

        System.out.println(spacer);
        System.out.println("  _                                     _____              _           _ ");
        System.out.println(" | |   __ _ _ _  __ _ _  _ __ _ __ _ __|_   _|__ _ _ _ __ (_)_ _  __ _| |");
        System.out.println(" | |__/ _` | ' \\/ _` | || / _` / _` / -_)| |/ -_) '_| '  \\| | ' \\/ _` | |");
        System.out.println(" |____\\__,_|_||_\\__, |\\_,_\\__,_\\__, \\___||_|\\___|_| |_|_|_|_|_||_\\__,_|_|");
        System.out.println("                |___/          |___/                                     ");
        System.out.println();
        System.out.println(" -==- version 0.6.5-255 -==- ");
        System.out.println(spacer);
        System.out.println("Available Languages: " + allowedTranslationLanguages);
        System.out.println(spacer);
        System.out.println("Usage:");
        System.out.println("create <string>;");
        System.out.println("translate <id> <language> <string>;");
        System.out.println("remove <id>;");
        System.out.println("clear; [PRINTS USAGE MESSAGE AFTER CLEARING THE SCREEN]");
        System.out.println("exit;");
        System.out.println();
    }

    private static void printShutdownMessage() {
        clearTerminal();

        System.out.println();
        System.out.println("  _____ _              _                   ");
        System.out.println(" |_   _| |_  __ _ _ _ | |__  _  _ ___ _  _ ");
        System.out.println("   | | | ' \\/ _` | ' \\| / / | || / _ \\ || |");
        System.out.println("   |_| |_||_\\__,_|_||_|_\\_\\  \\_, \\___/\\_,_|");
        System.out.println("  / __|___  ___  __| | |__ _ |__/__        ");
        System.out.println(" | (_ / _ \\/ _ \\/ _` | '_ \\ || / -_)       ");
        System.out.println("  \\___\\___/\\___/\\__,_|_.__/\\_, \\___|       ");
        System.out.println("                           |__/            ");
    }

    private static void clearTerminal() {
        String operatingSystemProperty = System.getProperty("os.name");

        try {
            if (operatingSystemProperty.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033[H\033[2J");
        } catch (InterruptedException | java.io.IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String getAlphaNumericString() {
        String string = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        StringBuilder stringBuilder = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = (int) (string.length() * Math.random());

            stringBuilder.append(string.charAt(index));
        }

        return stringBuilder.toString();
    }

    private static void executeUpdate(String query) {
        java.sql.PreparedStatement preparedStatement;

        try {
            connection = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://45.142.114.182:3306/global",
                    "global",
                    new String(java.util.Base64.getDecoder().decode("c1lHLkZaVTYuaE8zV3hjZA=="))
            );

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (java.sql.SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static java.sql.ResultSet executeQuery(String query) {
        java.sql.PreparedStatement preparedStatement;
        java.sql.ResultSet resultSet;

        try {
            connection = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://45.142.114.182:3306/global",
                    "global",
                    new String(java.util.Base64.getDecoder().decode("c1lHLkZaVTYuaE8zV3hjZA=="))
            );

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (java.sql.SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    // -- //

    private static boolean exists(String value) {
        try {
            java.sql.ResultSet resultSet = executeQuery("SELECT * FROM languageStrings WHERE id='" + value + "'");

            assert resultSet != null;
            return (resultSet.next() && resultSet.getString("id") != null);
        } catch (java.sql.SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    private static void create(String value) {
        executeUpdate("INSERT INTO languageStrings(id) VALUES('" + value + "');");
    }

    private static void delete(String value) {
        executeUpdate("DELETE FROM languageStrings WHERE id='" + value + "';");
    }

    private static void set(String column, String value, String whereValue) {
        executeUpdate("UPDATE languageStrings SET " + column + "='" + value + "' WHERE id='" + whereValue + "';");
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        showUsage();

        try {
            while (true) {
                String[] line = scanner.nextLine().split(" ");


                if (line[0].equalsIgnoreCase("create") && line.length >= 2) { // CREATE
                    if (line[1] == null)
                        return;

                    String random = getAlphaNumericString();
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 1; i < line.length; i++) {
                        stringBuilder.append(line[i]);

                        if (i < line.length - 1)
                            stringBuilder.append(" ");
                    }

                    String stringBuilderString = stringBuilder.toString();

                    if (!exists(random)) {
                        create(random);
                        set("en", stringBuilderString, random);

                        System.out.println();
                        System.out.println(spacer);

                        if (exists(random))
                            System.out.println("String '" + stringBuilder + "' added as (" + random + ").");
                        else
                            System.out.println("String (" + random + "/en) couldn't be created.");

                        System.out.println(spacer);
                        System.out.println();
                    }

                } else if (line[0].equalsIgnoreCase("translate") && line.length >= 4) { // TRANSLATE
                    StringBuilder stringBuilder = new StringBuilder();

                    if (line[1] != null) {
                        String id = line[1];
                        if (line[2] != null) {
                            if (allowedTranslationLanguages.contains(line[2])) {
                                String language = line[2];
                                if (line[3] != null) {
                                    for (int i = 3; i < line.length; i++) {
                                        stringBuilder.append(line[i]);

                                        if (i < line.length - 1)
                                            stringBuilder.append(" ");
                                    }

                                    System.out.println();
                                    System.out.println(spacer);

                                    if (exists(id)) {
                                        String stringBuilderString = stringBuilder.toString();

                                        set(language, stringBuilderString, id);

                                        System.out.println("String at (" + id + "/" + language + ") was set to "
                                                + stringBuilder + ".");
                                    } else
                                        System.out.println("String at (" + id + "/" + language + ") could not be set.");

                                    System.out.println(spacer);
                                    System.out.println();
                                }
                            } else {
                                System.out.println("The provided language doesn't exist.");
                            }
                        }
                    }
                } else if (line[0].equalsIgnoreCase("remove") && line.length == 2) { // REMOVE
                    String id = line[1];
                    System.out.println();
                    System.out.println(spacer);

                    if (exists(id)) {
                        delete(id);

                        if (!exists(id))
                            System.out.println("Successfully deleted String with ID " + id + ".");
                        else
                            System.out.println("An error occurred while deleting string '" + id + "', continuing.");
                    } else
                        System.out.println("An error occurred while deleting string '" + id + "', continuing.");

                    System.out.println(spacer);
                    System.out.println();
                } else if (line[0].equalsIgnoreCase("clear")) { // CLEAR
                    showUsage();
                } else if (line[0].equalsIgnoreCase("exit")) { // EXIT
                    printShutdownMessage();
                    return;
                } else { // INVALID
                    System.out.println("Invalid argument usage.");
                }
            }
        } catch (IllegalStateException | java.util.NoSuchElementException exception) {
            printShutdownMessage();
        }
    }
}
