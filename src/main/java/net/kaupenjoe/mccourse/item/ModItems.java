package net.kaupenjoe.mccourse.item;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.custom.MetalDetectorItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class ModItems {
	public static final Item PINK_GARNET = registerItem("pink_garnet",
		new Item(new FabricItemSettings()));
	public static final Item RAW_PINK_GARNET = registerItem("raw_pink_garnet",
		new Item(new FabricItemSettings()));

	public static final Item METAL_DETECTOR = registerItem("metal_detector",
		new MetalDetectorItem(new FabricItemSettings().maxDamage(256)));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(MCCourseMod.MOD_ID, name), item);
	}

	private static void itemGroupIngredients(FabricItemGroupEntries entries) {
		entries.add(PINK_GARNET);
		entries.add(RAW_PINK_GARNET);

		entries.add(ModBlocks.PINK_GARNET_BLOCK);
		entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
	}

	public static void registerModItems() {
		MCCourseMod.LOGGER.info("Registering Mod Items for " + MCCourseMod.MOD_ID);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::itemGroupIngredients);
	}
}
