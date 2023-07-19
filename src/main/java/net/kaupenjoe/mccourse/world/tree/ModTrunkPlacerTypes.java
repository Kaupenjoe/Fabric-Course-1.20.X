package net.kaupenjoe.mccourse.world.tree;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.mixin.TrunkPlacerTypeInvoker;
import net.kaupenjoe.mccourse.world.tree.custom.DriftwoodTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> DRIFTWOOD_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("driftwood_trunk_placer",
            DriftwoodTrunkPlacer.CODEC);

    public static void register() {
        MCCourseMod.LOGGER.info("Registering Trunk Placer for " + MCCourseMod.MOD_ID);
    }
}
