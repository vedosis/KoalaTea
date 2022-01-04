package systems.asteru.minecraft.koala_tea.lumberjack;

import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class TreeMap {
    public static Logger LOGGER = LogManager.getLogger();

    private final LinkedList<BlockState> woodBlocks;
    private final LinkedList<BlockState> leafBlocks;
    private final LeafAction leafAction;
    public TreeMap(LinkedList<BlockState> wood, LinkedList<BlockState> leaves, LeafAction leafAction){
        this.woodBlocks = wood;
        this.leafBlocks = leaves;
        this.leafAction = leafAction;
    }

    public static class TreeMapBuilder{
        private final World world;
        private final BlockPos baseTrunkPos;
        private final BlockState baseTrunkState;
        private LeafAction leafAction;


        public TreeMapBuilder(World world, BlockPos pos, BlockState state) {
            this.world = world;
            this.baseTrunkPos = pos;
            this.baseTrunkState = state;
        }

        public static boolean isSittingOnGround(World world, BlockPos pos){
            // Should check the block orientation and only start the process if the lowest block is on the ground.
            BlockState ground = world.getBlockState(pos.down());
            return ground.isIn(BlockTags.DIRT);
        }

        /**
         * Traverses the tree, identifying all the wood and leaf blocks then returns a walkable map of the objects.
         *
         * @return an object which can be destroyed or walked.
         */
        @Nullable
        public TreeMap build(){
            if (!TreeMapBuilder.isSittingOnGround(this.world, this.baseTrunkPos)){
                return null;
            }

            return new TreeMap(new LinkedList<>(), new LinkedList<>(), this.leafAction);
        }

        public TreeMapBuilder setLeafAction(LeafAction leafAction) {
            this.leafAction = leafAction;
            return this;
        }
    }

    public LeafAction getLeafAction() {
        return leafAction;
    }

    public void explode(){
        LOGGER.error("AAAAHHHH! BOOOM!!!");
    }

}
