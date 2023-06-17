package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.kaupenjoe.mccourse.util.InventoryUtil;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState blockState = context.getWorld().getBlockState(positionClicked.down(i));
                Block block = blockState.getBlock();

                if(isValuableBlock(blockState)) {
                    outputValuableCoordinates(positionClicked.down(i), player, block);
                    foundBlock = true;

                    if(InventoryUtil.hasPlayerStackInInventory(player, ModItems.DATA_TABLET)) {
                        addNbtDataToDataTablet(player, positionClicked.down(i), block);
                    }

                    context.getWorld().playSound(null, positionClicked, ModSounds.METAL_DETECTOR_FOUND_ORE,
                            SoundCategory.BLOCKS, 1f, 1f);

                    break;
                }
            }

            if(!foundBlock) {
                player.sendMessage(Text.translatable("item.mccourse.metal_detector.no_valuables"));
            }
        }

        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

        return ActionResult.SUCCESS;
    }

    private void addNbtDataToDataTablet(PlayerEntity player, BlockPos position, Block block) {
        ItemStack dataTabletStack = player.getInventory().getStack(InventoryUtil.getFirstInventoryIndex(player, ModItems.DATA_TABLET));

        NbtCompound nbtData = new NbtCompound();
        nbtData.putString("mccourse.last_valuable_found", "Valuable Found: " + block.getName().getString() + " at " +
                "(" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")");

        dataTabletStack.setNbt(nbtData);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.mccourse.metal_detector.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("tooltip.mccourse.metal_detector.tooltip"));
        }
    }

    private void outputValuableCoordinates(BlockPos position, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("Valuable Found: " + block.getName().getString() + " at " +
                "(" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS);
    }
}
