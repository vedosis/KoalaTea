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
import systems.asteru.minecraft.koala_tea.KoalaTea;
import systems.asteru.minecraft.koala_tea.lumberjack.LeafAction;
import systems.asteru.minecraft.koala_tea.lumberjack.TreeMap;


public class BreakBlockListeners {
    public static Logger LOGGER = LogManager.getLogger(KoalaTea.MOD_ID);

    public static void lumberjack(World world, PlayerEntity player, BlockPos pos, BlockState state, /* Nullable */ BlockEntity blockEntity) {
        if (player.isCreative()){
            LOGGER.debug("Tree not being felled because player is in creative mode.");
            return;
        }

        if (!(player.getMainHandStack().getItem() instanceof AxeItem) || !state.isIn(BlockTags.LOGS)) {
            //Only Logs need to be "chopped" and only by things that implement AxeItem
            return;
        }

        LOGGER.debug(String.format("Starting Lumberjack at (%s) on behalf of (%s)", pos.toString(), player.getDisplayName().asString()));
        TreeMap treeMap = new TreeMap.TreeMapBuilder(world, pos, state).setLeafAction(LeafAction.IGNORE).build();
        if (treeMap == null){
            LOGGER.debug("Unable to Lumberjack. Aborting");
            return;
        }
        treeMap.explode();
    }

}
