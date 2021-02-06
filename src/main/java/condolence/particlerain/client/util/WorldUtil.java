package condolence.particlerain.client.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import java.util.Random;

public class WorldUtil {
    /**
     * Returns a position (BlockPos) which represents a random position in a circle of the given
     * radius, centered at centerPos.
     *
     * The minimum height and maximum height define a range of numbers. The height of the position chosen will
     * be the Y pos of centerPos added to a random number between minHeight and maxHeight - both bounds being
     * inclusive.
     *
     * Should be better performing than the Fabric mod's position method, which selects a random position
     * in a sphere rather than a circle. The min and max height feature should also mean that particles won't
     * spawn in front of the player, which is particularly noticeable in snow.
     *
     * @param rand a Random object (preferably, from world.getRandom())
     * @param centerPos a BlockPos for the area to be centered at.
     * @param radius the radius of the area for the position to be selected from
     * @param minHeight the minimum height for the position
     * @param maxHeight the maximum height for the position
     * @return a mutable BlockPos at the random position generated
     */
    public static BlockPos.Mutable getRandomPosInRadius(Random rand, BlockPos centerPos, int radius, int minHeight, int maxHeight) {
        double a = rand.nextDouble() * 2 * Math.PI;
        double r = radius * Math.sqrt(rand.nextDouble());

        return new BlockPos.Mutable(
                r * Math.cos(a) + centerPos.getX(),
                minHeight == maxHeight ? centerPos.getY() + minHeight : centerPos.getY() + MathUtil.nextIntInRange(rand, minHeight, maxHeight),
                r * Math.sin(a) + centerPos.getZ()
        );
    }



    /**
     * Returns a boolean which can be used to determine whether or not a
     * 'weather' particle (rain/snow/etc.) should be spawned at the given
     * position.
     *
     * @param world the current world that precipitation is occurring in
     * @param pos the position to be checked
     * @return true if precipitation is occurring, false if not
     */
    public static boolean isPrecipitatingAt(World world, BlockPos pos) {
        // Check if sky is visible from given position
        if (!world.canSeeSky(pos)) { return false; }

        // Not entirely sure what this does, but it is included in the isRainingAt() method, so it should be included here
        return world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos).getY() <= pos.getY();
    }
}
