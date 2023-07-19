package net.kaupenjoe.mccourse.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaupenjoe.mccourse.world.tree.ModFoliagePlacerTypes;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class DriftwoodFoliagePlacer extends FoliagePlacer {
    public static final Codec<DriftwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> fillFoliagePlacerFields(instance)
            .and(Codec.intRange(0, 12).fieldOf("height").forGetter((placer) -> placer.height)).apply(instance, DriftwoodFoliagePlacer::new));
    private final int height;

    public DriftwoodFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.DRIFTWOOD_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight,
                            TreeNode treeNode, int foliageHeight, int radius, int offset) {
        // CREATE THE FOLIAGE
        // treeNode.getCenter() is the first position ABOVE the last log placed!

        for(int i = 0; i < 4; i++) {
            // placeFoliageBlock(world, placer, random, config, treeNode.getCenter());
            // radius on how many blocks it extends into x and z direction
            // y how much offset in the y direction from treeNode.getCenter()
            // y if it is dependent on i, also offsets each new layer in the y direction

            this.generateSquare(world, placer, random, config, treeNode.getCenter().up(i), 2, i + 1, treeNode.isGiantTrunk());
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
