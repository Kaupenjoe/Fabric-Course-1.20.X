package net.kaupenjoe.mccourse.block.entity;

import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.recipe.KaupenFurnaceRecipe;
import net.kaupenjoe.mccourse.screen.KaupenFurnaceScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class KaupenFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private Map<Item, Integer> BURN_DURATION_MAP = Map.of(
            ModItems.PEAT_BRICK, 100,
            ModItems.DICE, 200,
            Items.BLAZE_POWDER, 600);

    public KaupenFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KAUPEN_FURNACE_BE, pos, state, KaupenFurnaceRecipe.Type.INSTANCE);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.mccourse.kaupen_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new KaupenFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return BURN_DURATION_MAP.getOrDefault(fuel.getItem(), 0);
    }
}
