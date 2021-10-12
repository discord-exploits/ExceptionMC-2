package net.exceptionmc.labymod;

@SuppressWarnings("unused")
public class LabyModCinematic {

    // ToDo: The documentation didn't tell how to create a cinematic properly

//    public void play(Player player, List<Location> locationList, long duration) {
//        JsonArray jsonArray = new JsonArray();
//        JsonObject cinematic = new JsonObject();
//
//        for (Location location : locationList) {
//            JsonObject point = new JsonObject();
//
//            point.addProperty("x", location.getX());
//            point.addProperty("y", location.getY());
//            point.addProperty("z", location.getZ());
//            point.addProperty("yaw", location.getYaw());
//            point.addProperty("pitch", location.getPitch());
//            point.addProperty("tilt", 0);
//
//            jsonArray.add(point);
//        }
//
//        cinematic.add("points", jsonArray);
//        cinematic.add("clear_chat", true);
//        cinematic.addProperty("duration", duration);
//
//        player.teleport((Entity) jsonArray.get(0));
//        player.setAllowFlight(true);
//
//        LabyModProtocol.sendLabyModMessage(player, "cinematic", cinematic);
//    }
//
//    public void cancel(Player player) {
//        LabyModProtocol.sendLabyModMessage(player, "cinematic", new JsonObject());
//    }
}
