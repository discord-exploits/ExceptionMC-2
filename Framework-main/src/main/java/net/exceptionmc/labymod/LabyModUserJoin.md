## LabyMod User Join

---

The client will send you this information on join in the `labymod3:main` plugin channel.

The message key `INFO` returns the following example output:

```json
{
  // LabyMod client version (Current: 2.8.39)
  "version": "3.8.39",
  // Chunk caching feature
  "ccp": {
    "enabled": true,
    "version": 2
  },
  // Anti cheat feature
  "shadow": {
    "enabled": true,
    "version": 1
  },
  // Addons that the user has installed
  "addons": [
    {
      "uuid": "8a2eb0b0-5df9-4687-b36f-df2258b61062",
      // Unique addon id, which is issued by LabyMedia GmbH
      "name": "Name of the Addon"
      // Name of the Addon
    }
  ],
  // Modifications that the user has installed [FORGE]
  "mods": [
    {
      "hash": "sha256:9aba15fe05724639ad4b4665e1a76970fd7f740bf751eaa708e90287ce3a171d",
      // Modification checksum (sha256)
      "name": "name_of_the_mod.jar"
      // Name of the Modification
    }
  ]
}
```

---

## Code Example:

```java
@Override
public void onEnable(){
        // Register the incoming plugin channel
        getServer().getMessenger().registerIncomingPluginChannel(this,"labymod3:main",this);
        }

@Override
public void onPluginMessageReceived(String channel,Player player,byte[]message){
        if(!channel.equals("labymod3:main"))
        return;

        DataInputStream dataInputStream=new DataInputStream(new ByteArrayInputStream(message));

        try{
        ByteBuf byteBuf=Unpooled.wrappedBuffer(message);
        String key=LabyModProtocol.readString(byteBuf,Short.MAX_VALUE);
        String json=LabyModProtocol.readString(byteBuf,Short.MAX_VALUE);

        // LabyMod user joins the server
        if(key.equals("INFO")){
        // Handle the json message
        }
        }catch(IOException ioException){
        ioException.printStackTrace();
        }
        }
```


