package systems.asteru.minecraft.koala_tea.lumberjack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import systems.asteru.minecraft.koala_tea.utils.BlockPosUtils;

import java.util.HashSet;
import java.util.LinkedList;

public class TreeMap {
    public static Logger LOGGER = LogManager.getLogger();
    private static final BlockState DEBUG_WOOD = Blocks.BROWN_STAINED_GLASS.getDefaultState();

    private final World world;
    private final LinkedList<BlockPos> woodBlocks;
    private final LinkedList<BlockPos> leafBlocks;
    private final LeafAction leafAction;

    public TreeMap(World world, LinkedList<BlockPos> wood, LinkedList<BlockPos> leaves, LeafAction leafAction) {
        this.world = world;
        this.woodBlocks = wood;
        this.leafBlocks = leaves;
        this.leafAction = leafAction;
    }

    public static class TreeMapBuilder {
        private final World world;
        private final BlockPos pos;
        private final BlockState state;
        private LeafAction leafAction;


        public TreeMapBuilder(World world, BlockPos pos, BlockState state) {
            this.world = world;
            this.pos = pos;
            this.state = state;
        }

        /**
         * Traverses the tree, identifying all the wood and leaf blocks then returns a walkable map of the objects.
         *
         * @return an object which can be destroyed or walked.
         */
        public TreeMap build() {
            LinkedList<BlockPos> woodLogs = walkForWoodLogs(this.pos, this.state.getBlock(), this.world);
            return new TreeMap(this.world, woodLogs, new LinkedList<>(), this.leafAction);
        }

        public TreeMapBuilder setLeafAction(LeafAction leafAction) {
            this.leafAction = leafAction;
            return this;
        }

        private LinkedList<BlockPos> walkForWoodLogs(BlockPos origin, Block type, World world) {
            LinkedList<BlockPos> queue = new LinkedList<>();
            queue.add(origin);

            LinkedList<BlockPos> discoveredBlocks = new LinkedList<>();
            discoveredBlocks.add(origin);
            HashSet<String> trackingSet = new HashSet<>();
            trackingSet.add(BlockPosUtils.positionString(origin));

            while (!queue.isEmpty()) {
                BlockPos pos = queue.removeFirst();
                for (BlockPos neighborPos : BlockPosUtils.iterateCube(pos, 1)) {
                    String posString = BlockPosUtils.positionString(neighborPos);
                    if (trackingSet.contains(posString)) {
                        // We've already seen this block
                        continue;
                    }
                    trackingSet.add(posString);

                    BlockState neighborState = world.getBlockState(neighborPos);
                    if (!neighborState.isOf(type)) {
                        // No more trunk to follow this direction.
                        continue;
                    }

                    discoveredBlocks.add(neighborPos);
                    queue.add(neighborPos);
                }
            }

            return discoveredBlocks;
        }
    }

    public LeafAction getLeafAction() {
        return leafAction;
    }

    public void explode(boolean debug) {
        for (BlockPos pos : this.woodBlocks) {
            if (debug) {
                // Add config for "replace/crystalize" mode
                this.world.setBlockState(pos, DEBUG_WOOD);
            } else {
                // Add config for "should drop logs"
                this.world.breakBlock(pos, true);
                // Add config for "how long to sleep"
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

}
