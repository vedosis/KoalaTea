package systems.asteru.minecraft.koala_tea;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import systems.asteru.minecraft.koala_tea.listeners.BreakBlockListeners;

public class KoalaTea implements ModInitializer {
    public static final String MOD_ID = "koala_tea";

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.AFTER.register(BreakBlockListeners::lumberjack);
    }
}
