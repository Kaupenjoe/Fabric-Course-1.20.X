package net.kaupenjoe.mccourse.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
    public static boolean hasPlayerStackInInventory(PlayerEntity player, Item item) {
        for(int i = 0; i < player.getInventory().size(); i++) {
            ItemStack currentStack = player.getInventory().getStack(i);
            if (!currentStack.isEmpty() && currentStack.isOf(item)) {
                return true;
            }
        }

        return false;
    }

    public static int getFirstInventoryIndex(PlayerEntity player, Item item) {
        for(int i = 0; i < player.getInventory().size(); i++) {
            ItemStack currentStack = player.getInventory().getStack(i);
            if (!currentStack.isEmpty() && currentStack.isOf(item)) {
                return i;
            }
        }

        return -1;
    }
}
