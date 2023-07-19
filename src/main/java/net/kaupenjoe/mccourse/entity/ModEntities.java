package net.kaupenjoe.mccourse.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.DiceProjectileEntity;
import net.kaupenjoe.mccourse.entity.custom.PorcupineEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<PorcupineEntity> PORCUPINE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MCCourseMod.MOD_ID, "porcupine"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PorcupineEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());


    public static final EntityType<DiceProjectileEntity> THROWN_DICE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MCCourseMod.MOD_ID, "dice_projectile"),
            FabricEntityTypeBuilder.<DiceProjectileEntity>create(SpawnGroup.CREATURE, DiceProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());



    public static void registerModEntities() {
        MCCourseMod.LOGGER.info("Registering Mod Entities for " + MCCourseMod.MOD_ID);
    }
}
