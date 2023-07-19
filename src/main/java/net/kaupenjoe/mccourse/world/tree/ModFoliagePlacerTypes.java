package net.kaupenjoe.mccourse.world.tree;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.mixin.FoliagePlacerTypeInvoker;
import net.kaupenjoe.mccourse.world.tree.custom.DriftwoodFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> DRIFTWOOD_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister(
            "driftwood_foliage_placer", DriftwoodFoliagePlacer.CODEC);

    public static void register() {
        MCCourseMod.LOGGER.info("Registering the Foliage Placers for " + MCCourseMod.MOD_ID);
    }
}
