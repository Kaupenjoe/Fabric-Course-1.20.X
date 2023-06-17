package net.kaupenjoe.mccourse.sound;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent METAL_DETECTOR_FOUND_ORE = registerSoundEvent("metal_detector_found_ore");

    public static final SoundEvent PINK_GARNET_LAMP_BREAK = registerSoundEvent("pink_garnet_lamp_break");
    public static final SoundEvent PINK_GARNET_LAMP_STEP = registerSoundEvent("pink_garnet_lamp_step");
    public static final SoundEvent PINK_GARNET_LAMP_PLACE = registerSoundEvent("pink_garnet_lamp_place");
    public static final SoundEvent PINK_GARNET_LAMP_HIT = registerSoundEvent("pink_garnet_lamp_hit");
    public static final SoundEvent PINK_GARNET_LAMP_FALL = registerSoundEvent("pink_garnet_lamp_fall");


    public static final BlockSoundGroup PINK_GARNET_LAMP_SOUNDS = new BlockSoundGroup(1f, 1f,
            PINK_GARNET_LAMP_BREAK, PINK_GARNET_LAMP_STEP, PINK_GARNET_LAMP_PLACE, PINK_GARNET_LAMP_HIT, PINK_GARNET_LAMP_FALL);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = new Identifier(MCCourseMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void registerSounds() {
        MCCourseMod.LOGGER.info("Registering Mod Sounds for " + MCCourseMod.MOD_ID);
    }
}
