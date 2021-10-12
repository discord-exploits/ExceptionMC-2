package net.exceptionmc.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClanUtil {

    DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void createTables() {
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS clans" +
                "(clanId VARCHAR(255), clanName VARCHAR(255), clanTag VARCHAR(255), clanColor VARCHAR(255))");
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS clanInvites" +
                "(clanId VARCHAR(255), uniqueId VARCHAR(255))");
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS clanRequests" +
                "(clanId VARCHAR(255), uniqueId VARCHAR(255))");
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS clanUsers" +
                "(uniqueId VARCHAR(255), clanId VARCHAR(255), role INT)");
    }

    public boolean clanIdExists(String clanId) {
        return databaseUtil.exists("clans", "clanId", clanId);
    }

    public boolean clanNameExists(String clanName) {
        return databaseUtil.exists("clans", "clanName", clanName);
    }

    public boolean clanTagExists(String clanTag) {
        return databaseUtil.exists("clans", "clanTag", clanTag);
    }

    public boolean isInvited(String uuid, String clanId) {
        return databaseUtil.exists("clanInvites", "clanId", clanId, "uniqueId", uuid);
    }

    public boolean isRequested(String uuid, String clanId) {
        return databaseUtil.exists("clanRequests", "clanId", clanId, "uniqueId", uuid);
    }

    public boolean isInClan(String uuid) {
        return databaseUtil.exists("clanUsers", "uniqueId", uuid);
    }

    public void createClan(String uuid, String clanName, String clanTag) {
        String clanId = new GeneratorUtil().getAlphaNumericString(8);

        if (!clanIdExists(clanId)) {
            databaseUtil.create("clans", "clanId", clanId);
            databaseUtil.create("clanUsers", "uniqueId", uuid, "clanId", clanId);

            databaseUtil.setString("clans", "clanName", clanName, "clanId", clanId);
            databaseUtil.setString("clans", "clanTag", clanTag, "clanId", clanId);
            databaseUtil.setString("clans", "clanColor", "9", "clanId", clanId);
            databaseUtil.setInteger("clanUsers", "role", 2, "uniqueId", uuid);
        } else {
            createClan(uuid, clanName, clanTag);
        }
    }

    public void delete(String uuid) {
        databaseUtil.delete("clans", "clanId", getClan(uuid));
    }

    public void invite(String uuid, String clanId) {
        databaseUtil.create("clanInvites", "clanId", clanId, "uniqueId", uuid);
    }

    public void request(String uuid, String clanId) {
        databaseUtil.create("clanRequests", "clanId", clanId, "uniqueId", uuid);
    }

    public void acceptInvitation(String uuid, String clanId) {
        databaseUtil.delete("clanInvites", "clanId", clanId, "uniqueId", uuid);
        databaseUtil.create("clanUsers", "uniqueId", uuid);
        databaseUtil.setString("clanUsers", "clanId", clanId, "uniqueId", uuid);
        databaseUtil.setInteger("clanUsers", "role", 0, "uniqueId", uuid);
    }

    public void declineInvitation(String uuid, String clanId) {
        databaseUtil.delete("clanInvites", "clanId", clanId, "uniqueId", uuid);
    }

    public void acceptRequest(String uuid, String clanId) {
        databaseUtil.delete("clanRequests", "clanId", clanId, "uniqueId", uuid);
        databaseUtil.create("clanUsers", "uniqueId", uuid);
        databaseUtil.setString("clanUsers", "clanId", clanId, "uniqueId", uuid);
        databaseUtil.setInteger("clanUsers", "role", 0, "uniqueId", uuid);
    }

    public void declineRequest(String uuid, String clanId) {
        databaseUtil.delete("clanRequests", "clanId", clanId, "uniqueId", uuid);
    }

    public void setClanName(String uuid, String clanName) {
        databaseUtil.setString("clans", "clanName", clanName, "clanId", getClan(uuid));
    }

    public void setClanTag(String uuid, String clanTag) {
        databaseUtil.setString("clans", "clanTag", clanTag, "clanId", getClan(uuid));
    }

    public void setClanColorCode(String uuid, String colorCode) {
        databaseUtil.setString("clans", "clanColor", colorCode, "clanId", getClan(uuid));
    }

    public String getClan(String uuid) {
        return databaseUtil.getString("clanUsers", "clanId", "uniqueId", uuid);
    }

    public String getClanName(String clanId) {
        return databaseUtil.getString("clans", "clanName", "clanId", clanId);
    }

    public String getClanTag(String clanId) {
        return databaseUtil.getString("clans", "clanTag", "clanId", clanId);
    }

    public Integer getRoleId(String uuid) {
        return databaseUtil.getInteger("clanUsers", "role", "uniqueId", uuid);
    }

    public String getRoleName(String uuid) {
        Integer roleId = getRoleId(uuid);

        switch (roleId) {
            case 0:
                return new LanguageUtil().getString(uuid, "HE3l74fw");
            case 1:
                return new LanguageUtil().getString(uuid, "oYZYgCIP");
            case 2:
                return new LanguageUtil().getString(uuid, "zzAT9duh");
        }

        return null;
    }

    public String getClanColor(String clanId) {
        return databaseUtil.getString("clans", "clanColor", "clanId", clanId);
    }

    public ArrayList<String> getClanMembers(String clanId) {
        return databaseUtil.getStrings("clanUsers", "uniqueId", "clanId", clanId);
    }

    public void setSuffix(Player player) {
        if (isInClan(player.getUniqueId().toString()))
            TablistPrefixUtil.setSuffix(player, " §8[§" +
                    new ClanUtil().getClanColor(new ClanUtil().getClan(player.getUniqueId().toString())) +
                    getClanTag(getClan(player.getUniqueId().toString())) + "§8]");
        else
            TablistPrefixUtil.setSuffix(player, "");
    }
}