package net.kaupenjoe.mccourse.world.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaupenjoe.mccourse.world.tree.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class DriftwoodTrunkPlacer extends TrunkPlacer {
    public static final Codec<DriftwoodTrunkPlacer> CODEC = RecordCodecBuilder.create(driftwoodTrunkPlacerInstance ->
            fillTrunkPlacerFields(driftwoodTrunkPlacerInstance).apply(driftwoodTrunkPlacerInstance, DriftwoodTrunkPlacer::new));

    public DriftwoodTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.DRIFTWOOD_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random,
                                                 int height, BlockPos startPos, TreeFeatureConfig config) {
        // BLOCK PLACING LOGIC GOES RIGHT HERE
        setToDirt(world, replacer, random, startPos.down(), config);
        // getAndSetState() places down a Log

       int height_ = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 3) +
       random.nextBetween(secondRandomHeight - 2, secondRandomHeight + 2);

        for(int i = 0; i < height_; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);

            if(i % 2 == 0 && random.nextBoolean()) {
                if(random.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        getAndSetState(world, replacer, random, startPos.up(i).offset(Direction.NORTH, x), config);
                    }
                }

                if(random.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        getAndSetState(world, replacer, random, startPos.up(i).offset(Direction.SOUTH, x), config);
                    }
                }

                if(random.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        getAndSetState(world, replacer, random, startPos.up(i).offset(Direction.EAST, x), config);
                    }
                }

                if(random.nextFloat() > 0.25f) {
                    for(int x = 0; x < 4; x++) {
                        getAndSetState(world, replacer, random, startPos.up(i).offset(Direction.WEST, x), config);
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height_), 0, false));
    }
}
