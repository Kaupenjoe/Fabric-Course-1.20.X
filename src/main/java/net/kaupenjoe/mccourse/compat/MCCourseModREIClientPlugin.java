package net.kaupenjoe.mccourse.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.recipe.GemEmpoweringRecipe;
import net.kaupenjoe.mccourse.recipe.KaupenFurnaceRecipe;
import net.kaupenjoe.mccourse.screen.GemEmpoweringScreen;
import net.kaupenjoe.mccourse.screen.KaupenFurnaceScreen;

public class MCCourseModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GemEmpoweringCategory());
        registry.add(new KaupenFurnaceCategory());

        registry.addWorkstations(GemEmpoweringCategory.GEM_EMPOWERING, EntryStacks.of(ModBlocks.GEM_EMPOWERING_STATION));
        registry.addWorkstations(KaupenFurnaceCategory.KAUPEN_FURNACE, EntryStacks.of(ModBlocks.KAUPEN_FURNACE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(GemEmpoweringRecipe.class, GemEmpoweringRecipe.Type.INSTANCE,
                GemEmpoweringDisplay::new);

        registry.registerRecipeFiller(KaupenFurnaceRecipe.class, KaupenFurnaceRecipe.Type.INSTANCE,
                KaupenFurnaceDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 78,
                        ((screen.height - 166) / 2) + 30, 20, 25),
                GemEmpoweringScreen.class,
                GemEmpoweringCategory.GEM_EMPOWERING);

        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 78,
                        ((screen.height - 166) / 2) + 30, 20, 25),
                KaupenFurnaceScreen.class,
                KaupenFurnaceCategory.KAUPEN_FURNACE);
    }
}
