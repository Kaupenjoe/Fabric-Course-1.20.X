package net.kaupenjoe.mccourse.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class ModWoodTypes {
    public static final WoodType DRIFTWOOD = WoodTypeRegistry.register(new Identifier(MCCourseMod.MOD_ID, "driftwood"), BlockSetType.OAK);
}
