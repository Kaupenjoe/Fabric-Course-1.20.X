package net.kaupenjoe.mccourse.screen;

import net.kaupenjoe.mccourse.block.entity.KaupenFurnaceBlockEntity;
import net.kaupenjoe.mccourse.recipe.KaupenFurnaceRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class KaupenFurnaceScreenHandler extends AbstractFurnaceScreenHandler {
    public KaupenFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.KAUPEN_FURNACE_SCREEN_HANDLER, KaupenFurnaceRecipe.Type.INSTANCE, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public KaupenFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, KaupenFurnaceBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.KAUPEN_FURNACE_SCREEN_HANDLER, KaupenFurnaceRecipe.Type.INSTANCE, RecipeBookCategory.FURNACE, syncId, playerInventory, blockEntity, propertyDelegate);
    }

    @Override
    protected boolean isFuel(ItemStack itemStack) {
        return true;
    }
}
