package net.kaupenjoe.mccourse.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class MetalDetectorItem extends Item {
	public MetalDetectorItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		if (player == null) {
			return ActionResult.PASS;
		}
		if (!context.getWorld().isClient()) {
			BlockPos positionClicked = context.getBlockPos();
			boolean foundBlock = false;

			for (int i = 0; i <= positionClicked.getY() + 64; i++) {
				BlockState blockState = context.getWorld().getBlockState(positionClicked.down(i));
				Block block = blockState.getBlock();

				if (isValuableBlock(blockState)) {
					outputValuableCoordinates(positionClicked.down(i), player, block);
					foundBlock = true;

					break;
				}
			}

			if (!foundBlock) {
				player.sendMessage(Text.translatable("item.mccourse.metal_detector.no_valuables"));
			}
		}

		context.getStack().damage(1, player,
			playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

		return ActionResult.SUCCESS;
	}

	private void outputValuableCoordinates(BlockPos position, PlayerEntity player, Block block) {
		player.sendMessage(Text.literal("Valuable Found: " + block.getName().getString() + " at " +
			"(" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")"));
	}

	private boolean isValuableBlock(BlockState blockState) {
		return blockState.getBlock() == Blocks.IRON_ORE || blockState.getBlock() == Blocks.GOLD_ORE ||
			blockState.getBlock() == Blocks.DIAMOND_ORE || blockState.getBlock() == Blocks.REDSTONE_ORE;
	}
}
