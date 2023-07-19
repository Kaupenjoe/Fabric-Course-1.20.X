package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.entity.custom.MagicProjectileEntity;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RadiationStaffItem extends Item {
    public RadiationStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.METAL_DETECTOR_FOUND_ORE, SoundCategory.NEUTRAL,1.5F, 1F);
        user.getItemCooldownManager().set(this, 40);

        if (!world.isClient()) {
            MagicProjectileEntity magicProjectile = new MagicProjectileEntity(world, user);
            magicProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0.25F);
            world.spawnEntity(magicProjectile);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemstack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        }

        return TypedActionResult.success(itemstack, world.isClient());
    }
}
