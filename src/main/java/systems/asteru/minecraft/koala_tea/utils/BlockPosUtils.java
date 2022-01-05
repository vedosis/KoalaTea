package systems.asteru.minecraft.koala_tea.utils;

import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;

public class BlockPosUtils {
    public static Iterable<BlockPos> iterateCube(BlockPos pos, int radius) {
        BlockPos immutable = pos.toImmutable();
        LinkedList<BlockPos> neighbors = new LinkedList<>();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    if (i == 0 && j == 0 && k == 0) {
                        continue;
                    }
                    neighbors.add(immutable.add(k, j, i));
                }
            }
        }
        return neighbors;
    }

    public static String positionString(BlockPos pos) {
        return String.format("%s,%s,%s", pos.getX(), pos.getY(), pos.getZ());
    }
}
