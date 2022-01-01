package systems.asteru.minecraft.koala_tea.listeners;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import systems.asteru.minecraft.koala_tea.KoalaTea;


public class BreakBlockListeners {
    public static Logger LOGGER = LogManager.getLogger(KoalaTea.MOD_ID);

    public static void lumberjack(World world, PlayerEntity player, BlockPos pos, BlockState state, /* Nullable */ BlockEntity blockEntity) {
        if (player.isCreative()){
            LOGGER.debug("Tree not being felled because player is in creative mode.");
            return;
        }

        LOGGER.info(String.format("Block broken, %s", state.toString()));

    }
}
