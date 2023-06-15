package net.kaupenjoe.mccourse.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup PINK_GARNET_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MCCourseMod.MOD_ID, "pink_garnet_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.pink_garnet_group"))
                    .icon(() -> new ItemStack(ModItems.PINK_GARNET)).entries((displayContext, entries) -> {
                       entries.add(ModItems.PINK_GARNET);
                       entries.add(ModItems.RAW_PINK_GARNET);
                       entries.add(ModItems.METAL_DETECTOR);

                       entries.add(ModBlocks.PINK_GARNET_BLOCK);
                       entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);

                       entries.add(ModBlocks.PINK_GARNET_ORE);
                       entries.add(ModBlocks.DEEPSLATE_PINK_GARNET_ORE);
                       entries.add(ModBlocks.END_STONE_PINK_GARNET_ORE);
                       entries.add(ModBlocks.NETHER_PINK_GARNET_ORE);

                    }).build());

    public static void registerItemGroups() {

    }
}
