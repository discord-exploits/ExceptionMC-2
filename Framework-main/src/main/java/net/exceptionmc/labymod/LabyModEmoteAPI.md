## LabyMod Emote API

---

LabyMod users can play client-side an emote for all other players on the Minecraft server. Minecraft servers can also
force-play emotes for specific player's using the plugin messages.

Servers can make NPC's do emotes. To prevent abuse, this **does not** work for real players. To enforce this, emotes can
only be forced for players that have the second half of their UUID entirely being 0 (-> 64 least significant bits are 0,
or the second long value equals 0).

You therefore need to spawn them with a unique like this.

```java
public void spawn(Player player, UUID npcUuid) {
        // Create NPC uuid with least significant bits set to 0
        UUID uuid = new UUID(new Random().nextLong(), 0);

        // Add NPC to the tablist
        GameProfile gameProfile = new GameProfile(uuid, "NPC");

        addPlayerToTablist(gameProfile, gameProfile.getName());

        // Spawn the entity in the world (Maybe with a little delay of few ticks)
        spawnPlayerInWorld(1337, uuid, player.getLocation());

        // Play hello emote (Another delay here)
        playEmote(player, npcUuid, 4);
        }

private void spawnPlayerInWorld(Integer entityId, UUID uuid, Location location) {
        PacketContainer packetContainer
        = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);

        // Entity id and uuid
        packetContainer.getIntegers().write(0, entityId);
        packetContainer.getUUIDs().write(0, uuid);

        // Location
        packetContainer.getIntegers().write(1, (int) Math.floor(location.getX() * 32D));
        packetContainer.getIntegers().write(2, (int) Math.floor(location.getY() * 32D));
        packetContainer.getIntegers().write(3, (int) Math.floor(location.getZ() * 32D));
        packetContainer.getBytes().write(0, (byte) (location.getYaw() * 256F / 360F));
        packetContainer.getBytes().write(1, (byte) (location.getPitch() * 256F / 360F));

        packetContainer.getIntegers().write(4, (int) 0 /* Item in hand id */);

        // Data watcher for second skin layer
        WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
        wrappedDataWatcher.setObject(0, (byte) 0);
        wrappedDataWatcher.setObject(10, (byte) 127);
        packetContainer.getDataWatcherModifier().write(0, wrappedDataWatcher);

        // Send packet to all players
        for (Player player : Bukkit.getOnlinePlayers())
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        }

private void addPlayerToTablist(GameProfile gameProfile, String displayName) {
        PacketContainer packetContainer
        = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);

        // Write action type
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        // Write gameProfile
        WrappedGameProfile wrappedGameProfile = WrappedGameProfile.fromHandle(gameProfile);
        EnumWrappers.NativeGameMode nativeGameMode = EnumWrappers.NativeGameMode.fromBukkit(GameMode.SURVIVAL);
        packetContainer.getPlayerInfoDataLists()
        .write(0, Collections.singletonList(new PacketPlayOutPlayerInfo.PlayerInfoData(wrappedGameProfile,
        20, nativeGameMode, WrappedChatComponent.fromText(displayName))));

        // Send packet to all players
        for (Player player : Bukkit.getOnlinePlayers())
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        }
```

---

## Emote Ids:

|  Id  |              Emote               |
| ---- | -------------------------------- |
|  -1  | << STOP CURRENT PLAYING EMOTE >> |
|  0   |                                  |
|  1   |                                  |
|  2   |             Backflip             |
|  3   |               Dab                |
|  4   |              Hello               |
|  5   |            Bow thanks            |
|  6   |               Hype               |
|  7   |          Trying to fly           |
|  8   |           Infinity sit           |
|  9   |                                  |
|  10  |                                  |
|  11  |              Zombie              |
|  12  |                                  |
|  13  |            Hula Hoop             |
|  14  |             Calling              |
|  15  |             Facepalm             |
|  16  |                                  |
|  17  |                                  |
|  18  |       Brush your shoulders       |
|  19  |              Split               |
|  20  |              Salute              |
|  21  |                                  |
|  22  |             Balarina             |
|  23  |                                  |
|  24  |                                  |
|  25  |                                  |
|  26  |                                  |
|  27  |                                  |
|  28  |                                  |
|  29  |                                  |
|  30  |                                  |
|  31  |            Handstand             |
|  32  |            Helicopter            |
|  33  |               Holy               |
|  34  |             Waveover             |
|  35  |                                  |
|  36  |          Deeper deeper           |
|  37  |              Karate              |
|  38  |             Moonwalk             |
|  39  |                                  |
|  40  |             Freezing             |
|  41  |            Jubilation            |
|  42  |                                  |
|  43  |              Turtle              |
|  44  |                                  |
|  45  |             Headspin             |
|  46  |           Infinity dab           |
|  47  |             Chicken              |
|  48  |                                  |
|  49  |            The Floss             |
|  50  |         The mega thrust          |
|  51  |           The cleaner            |
|  52  |              Bridge              |
|  53  |           Milk the cow           |
|  54  |              Rurik               |
|  55  |               Wave               |
|  56  |                                  |
|  57  |            Moneyrain             |
|  58  |                                  |
|  59  |           The pointer            |
|  60  |           Frightening            |
|  61  |               Sad                |
|  62  |            Air guitar            |
|  63  |              Witch               |
|  64  |                                  |
|  65  |                                  |
|  66  |                                  |
|  67  |                                  |
|  68  |                                  |
|  69  |               Left               |
|  70  |              Right               |
|  71  |                                  |
|  72  |                                  |
|  73  |                                  |
|  74  |              Buuuh               |
|  75  |          Spitting bars           |
|  76  |           Count money            |
|  77  |               Hug                |
|  78  |             Applause             |
|  79  |              Boxing              |
|  80  |                                  |
|  81  |                                  |
|  82  |                                  |
|  83  |              Shoot               |
|  84  |         The pointing man         |
|  85  |              Heart               |
|  86  |          Near the fall           |
|  87  |                                  |
|  88  |                                  |
|  89  |             Waiting              |
|  90  |                                  |
|  91  |                                  |
|  92  |         Praise your item         |
|  93  |               Look               |
|  94  |                                  |
|  95  |                                  |
|  96  |                                  |
|  97  |            I love you            |
|  98  |          Sarcastic clap          |
|  99  |                                  |
| 100  |                                  |
| 101  |               You                |
| 102  |                                  |
| 103  |                                  |
| 104  |                                  |
| 105  |           Head on wall           |
| 106  |                                  |
| 107  |                                  |
| 108  |                                  |
| 109  |                                  |
| 110  |                                  |
| 111  |                                  |
| 112  |             Balance              |
| 113  |             Levelup              |
| 114  |            Take the L            |
| 115  |                                  |
| 116  |                                  |
| 117  |                                  |
| 118  |                                  |
| 119  |                                  |
| 120  |                                  |
| 121  |             My idol              |
| 122  |             Airplane             |
| 123  |                                  |
| 124  |              Eagle               |
| 125  |                                  |
| 126  |          Job well done           |
| 127  |                                  |
| 128  |             Elephant             |
| 129  |                                  |
| 130  |             Present              |
| 131  |           Eyes on you            |
| 132  |                                  |
| 133  |             Bow down             |
| 134  |           Maneki-neko            |
| 135  |            Conductor             |
| 136  |          Didi challenge          |
| 137  |            Snow Angel            |
| 138  |             Snowball             |
| 139  |            Sprinkler             |
| 140  |            Calculated            |
| 141  |       One-armed handstand        |
| 142  |               Eat                |
| 143  |               Shy                |
| 144  |                                  |
| 145  |             Sit-Ups              |
| 146  |            Breakdance            |
| 147  |                                  |
| 148  |             Mindblow             |
| 149  |               Fall               |
| 150  |              T Pose              |
| 151  |                                  |
| 152  |                                  |
| 153  |           Jumping Jack           |
| 154  |            Backstroke            |
| 155  |                                  |
| 156  |            Ice-Hockey            |
| 157  |        Look at fireworks         |
| 158  |         Finish the tree          |
| 159  |           Ice-Skating            |
| 160  |                                  |
| 161  |            Fancy Feet            |
| 162  |             Ronaldo              |
| 163  |            True Heart            |
| 164  |           Pumpernickel           |
| 165  |                                  |
| 166  |            Baby Shark            |
| 167  |           Open present           |
| 168  |                                  |
| 169  |                                  |
| 170  |                Dj                |
| 171  |           Have to pee            |
| 172  |                                  |
| 173  |              Sneeze              |
| 174  |                                  |
| 175  |                                  |
| 176  |                                  |
| 177  |                                  |
| 178  |           Cheerleader            |
| 179  |                                  |
| 180  |            Naruto Run            |
| 181  |            Pati Patu             |
| 182  |            Axe Swing             |
| 183  |           Fusion Left            |
| 184  |             Fishing              |
| 185  |           Fusion Right           |
| 186  |                                  |
| 187  |            Breathless            |
| 188  |                                  |
| 189  |            Genkidama             |
| 190  |                                  |
| 191  |              Singer              |
| 192  |             Magikarp             |
| 193  |               Rage               |
| 194  |               Slap               |
| 195  |            Air kisses            |
| 196  |             Knockout             |
| 197  |              Matrix              |
| 198  |             Jetpack              |
| 199  |                                  |
| 200  |               Golf               |
| 201  |           Stadium wave           |
| 202  |            Kickboxer             |
| 203  |            Handshake             |
| 204  |        Cleaning the floor        |
