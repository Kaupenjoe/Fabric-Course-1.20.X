package net.kaupenjoe.mccourse.world.biome.surface;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.world.biome.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule PINK_GARNET = makeStateRule(ModBlocks.PINK_GARNET_BLOCK);
    private static final MaterialRules.MaterialRule RAW_PINK_GARNET = makeStateRule(ModBlocks.RAW_PINK_GARNET_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return MaterialRules.sequence(
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.TEST_BIOME),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, RAW_PINK_GARNET)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_GARNET)),

                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.TEST_BIOME_2),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, RAW_PINK_GARNET)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_GARNET)),


                // Default to a grass and dirt surface
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
