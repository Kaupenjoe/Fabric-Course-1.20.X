package net.kaupenjoe.mccourse.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kaupenjoe.mccourse.world.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class ModGeodeGeneration {
    public static void generateGeodes() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.PINK_GARNET_GEODE_PLACED_KEY);
    }
}
