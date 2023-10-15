package net.kaupenjoe.mccourse.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.command.ReturnHomeCommand;
import net.kaupenjoe.mccourse.command.SetHomeCommand;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.custom.PorcupineEntity;
import net.kaupenjoe.mccourse.event.AttackEntityHandler;
import net.kaupenjoe.mccourse.event.PlayerCopyHandler;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.mixin.BrewingRecipeRegistryMixin;
import net.kaupenjoe.mccourse.potion.ModPotions;
import net.kaupenjoe.mccourse.villager.ModVillagers;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
        registerModCompostables();
        registerCommands();
        registerEvents();
        registerPotionRecipes();
        registerCustomTrades();
        registerStrippables();
        registerFlammables();
        registerAttributes();
        createPortal();
        registerOxidizables();
    }

    private static void registerOxidizables() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.RUBY_BLOCK, ModBlocks.RUBY_BLOCK_1);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.RUBY_BLOCK_1, ModBlocks.RUBY_BLOCK_2);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.RUBY_BLOCK_2, ModBlocks.RUBY_BLOCK_3);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.RUBY_BLOCK, ModBlocks.WAXED_RUBY_BLOCK);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.RUBY_BLOCK_1, ModBlocks.WAXED_RUBY_BLOCK_1);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.RUBY_BLOCK_2, ModBlocks.WAXED_RUBY_BLOCK_2);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.RUBY_BLOCK_3, ModBlocks.WAXED_RUBY_BLOCK_3);
    }

    private static void createPortal() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.PINK_GARNET_BLOCK)
                .lightWithItem(ModItems.CATTAIL)
                .destDimID(new Identifier(MCCourseMod.MOD_ID, "kaupendim"))
                .tintColor(0xc76efa)
                .registerPortal();
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModItems.PEAT_BRICK, 200);
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
    }

    private static void registerModCompostables() {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.CAULIFLOWER, 0.5f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.CAULIFLOWER_SEEDS, 0.25f);
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(ReturnHomeCommand::register);
    }

    private static void registerFlammables() {
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DRIFTWOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DRIFTWOOD_WOOD, 5, 5);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_LEAVES, 30, 60);
    }

    private static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.DRIFTWOOD_LOG, ModBlocks.STRIPPED_DRIFTWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.DRIFTWOOD_WOOD, ModBlocks.STRIPPED_DRIFTWOOD_WOOD);
    }

    private static void registerEvents() {
        AttackEntityCallback.EVENT.register(new AttackEntityHandler());
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler());
    }

    private static void registerPotionRecipes() {
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.PINK_GARNET, ModPotions.SLIMEY_POTION);
    }

    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                       new ItemStack(Items.EMERALD, 2),
                       new ItemStack(ModItems.CAULIFLOWER, 2), 6, 2, 0.02f
                    ));
                });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                       new ItemStack(Items.EMERALD, 16),
                       new ItemStack(ModItems.PINK_GARNET_PAXEL, 1), 2, 6, 0.08f
                    ));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                       new ItemStack(Items.EMERALD, 4),
                       new ItemStack(ModItems.RADIATION_STAFF, 1), 6, 19, 0.08f
                    ));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                       new ItemStack(Items.EMERALD, 16),
                       new ItemStack(ModItems.METAL_DETECTOR, 1), 1, 5, 0.08f
                    ));
                });
    }
}
