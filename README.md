# entity-api

Entity api for minecraft.

**IMPORTANT: This api is only for spigot 1.16+ and you need [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/).**

### 🛠 Development

**Gradle:**

Gradle is following soon.

**Maven:**

Maven is following soon.

**Creating a fake player (NPC):**

```
    EntityAPI.getInstance().getFakePlayerEntityFactory().create(
                new FakePlayerEntityConfiguration()
                        .withDisplayName("§aTest123")
                        .withHologramLine(player -> "§bHallo " + player.getName())
                        .withHologramLines("Test", "Test2")
                        .withLocation(null)
                        .withLookAtPlayer(false)
                        .withEquipmentItem(EquipmentItemSlot.MAINHAND, new ItemStack(Material.IRON_SWORD))
                        .withSkinData(
                                new SkinData(
                                        "ewogICJ0aW1lc3RhbXAiIDogMTYwODQ2Mzk2MDIxNSwKICAicHJvZmlsZUlkIiA6ICJmNzg4YzU5ZGY2MzU0M2MxOGMzY2M5YjczMzM4NGZlNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJCd2VlZjY5IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U3YjhiYjBlNmYyYWVjMDJmMTQ1OGI0MDM2NDg2YjFiMzcwODU2M2U3YTVlY2QxNjYzZWI3MWFlOGMxYWI3ZmIiCiAgICB9CiAgfQp9",
                                        "p35JRZPm3XS2JM4IH46OVcX6vTZ990YEAEWhhn4Qz8mKxy5rKEfE0mapcryABLMKOmVnyjspDPieHUqTREqoZcpO+hJA4PvolaF5Hb7i3VLeWOHDg5+tpIsXIs9Pr/hiZlGCHUG57Nc34Ejs8XHpxyPfxlRxl5X6D9mwYRL+w6LBLJfY4Fbx1OQug++oIbMAyn67YQ37ROFKhZhZTO4qQzmTKct5eeAzv+frrGQSv+wTWIk6H6IOcTevq1FNwLRcVgXRPbATi5ROllcSWfZvpNLntgt8+LBPd1mbnBWzNZpvYUu30eMKo0qpHXtcPCeX90xyGUsNVnfOUOOcf0GXhPZyY0HhX7481vzDReobsiYql5DcLafmzO+sn71TXC0PsmvTd11gJ/zavFwbWfqTbtYyJQgWxARxgHRUwIwqG23snDJk8Xoj5nDA9H8rNODJUl9gqhyGiGMT5la9A07ASCMIMH5zeUWR/oRMakA2jcwnYpBUvwOg3cO6Bwm6qxE+QAkJxOZbe13LDErYy63RagInqx2htQV2vc7PerVl1tn6kqKRJLxVLsNVcfd6VW1Zy2EXndFsBjfD4siTXMUWsJ5A2FKmEbBzFSaBU2Wit3UUUboaFWBP6swjWfY6Ye9ol1BCq0TZf0LyaBHYs/O8wNSt983wxLdoOFTdfaQO/4k="
                                )
                        )
                        .withInteractHandler(entityInteractResult -> {
                            Player player = entityInteractResult.getPlayer();
                            player.sendMessage(entityInteractResult.getAction().toString());
                        })
        ).startRendering();

```

**Thanks for using my api.**