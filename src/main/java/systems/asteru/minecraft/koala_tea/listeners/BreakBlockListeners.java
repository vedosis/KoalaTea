package systems.asteru.minecraft.koala_tea.listeners;

import com.sun.source.tree.Tree;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import systems.asteru.minecraft.koala_tea.KoalaTea;
import systems.asteru.minecraft.koala_tea.lumberjack.LeafAction;
import systems.asteru.minecraft.koala_tea.lumberjack.TreeMap;


public class BreakBlockListeners {
    public static Logger LOGGER = LogManager.getLogger(KoalaTea.MOD_ID);

    public static void lumberjack(World world, @NotNull PlayerEntity player, BlockPos pos, BlockState state, /* Nullable */ BlockEntity blockEntity) {
        if (player.isCreative()
                || !(player.getMainHandStack().getItem() instanceof AxeItem)
                || !state.isIn(BlockTags.LOGS)
                || !world.getBlockState(pos.down()).isIn(BlockTags.DIRT)) {
            //Only Logs need to be "chopped" and only by things that implement AxeItem
            LOGGER.debug("Lumberjack preconditions not met. Player must not be in creative mode, " +
                    "holding an axe and breaking a wood log on the ground.");
            return;
        }

        TreeMap treeMap = new TreeMap.TreeMapBuilder(world, pos, state).setLeafAction(LeafAction.IGNORE).build();
        treeMap.explode(false);
    }

}
