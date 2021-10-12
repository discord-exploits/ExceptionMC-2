package net.exceptionmc.util;

@SuppressWarnings("unused")
public class PrivacyUtil {

    DatabaseUtil databaseUtil
            = new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void createTable() {
        databaseUtil.executeUpdate(
                "CREATE TABLE IF NOT EXISTS privacyAgreement(uniqueId VARCHAR(255), value TINYINT)"
        );
    }

    public void createPlayer(String uuid) {
        if (!databaseUtil.exists("privacyAgreement", "uniqueId", uuid)) {
            databaseUtil.create("privacyAgreement", "uniqueId", uuid);
            databaseUtil.setInteger("privacyAgreement", "value", 0, "uniqueId", uuid);
        }
    }

    public void agree(String uuid) {
        if (databaseUtil.exists("privacyAgreement", "uniqueId", uuid))
            databaseUtil.setInteger("privacyAgreement", "value", 1, "uniqueId", uuid);
    }

    public Boolean hasAgreedPrivacyPolicy(String uuid) {
        if (databaseUtil.exists("privacyAgreement", "uniqueId", uuid))
            return databaseUtil.getInteger("privacyAgreement", "value", "uniqueId", uuid) != 0;

        return false;
    }
}
