package net.kaupenjoe.mccourse.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.kaupenjoe.mccourse.block.custom.GemEmpoweringStationBlock;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.networking.ModMessages;
import net.kaupenjoe.mccourse.recipe.GemEmpoweringRecipe;
import net.kaupenjoe.mccourse.screen.GemEmpoweringScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class GemEmpoweringStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_ITEM_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public GemEmpoweringStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEM_EMPOWERING_STATION_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemEmpoweringStationBlockEntity.this.progress;
                    case 1 -> GemEmpoweringStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: GemEmpoweringStationBlockEntity.this.progress = value;
                    case 1: GemEmpoweringStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public ItemStack getRenderStack() {
        if(this.getStack(OUTPUT_SLOT).isEmpty()) {
            return this.getStack(INPUT_SLOT);
        } else {
            return this.getStack(OUTPUT_SLOT);
        }
    }

    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for(int i = 0; i < list.size(); i++) {
            this.inventory.set(i, list.get(i));
        }
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(64000, 200, 200) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * 64; // 1 Bucket = 81000 Droplets = 1000mB || *64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(pos).get(GemEmpoweringStationBlock.FACING);

        if(side == Direction.DOWN) {
            return false;
        }

        if(side == Direction.UP) {
            return slot == INPUT_SLOT;
        }

        return switch (localDir) {
            default -> //NORTH
                        side.getOpposite() == Direction.NORTH && slot == INPUT_SLOT ||
                        side.getOpposite() == Direction.WEST && slot == INPUT_SLOT;
            case EAST ->
                        side.rotateYClockwise() == Direction.NORTH && slot == INPUT_SLOT ||
                        side.rotateYClockwise() == Direction.WEST && slot == INPUT_SLOT;
            case SOUTH ->
                        side == Direction.NORTH && slot == INPUT_SLOT ||
                        side == Direction.WEST && slot == INPUT_SLOT;
            case WEST ->
                        side.rotateYCounterclockwise() == Direction.NORTH && slot == INPUT_SLOT ||
                        side.rotateYCounterclockwise() == Direction.WEST && slot == INPUT_SLOT;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(GemEmpoweringStationBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract 2
        if(side == Direction.DOWN) {
            return slot == OUTPUT_SLOT;
        }

        // bottom extract 2
        // right extract 2
        return switch (localDir) {
            default ->  side.getOpposite() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.getOpposite() == Direction.EAST && slot == OUTPUT_SLOT;

            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;

            case SOUTH ->   side == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side == Direction.EAST && slot == OUTPUT_SLOT;

            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Gem Empowering Station");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GemEmpoweringScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("gem_empowering_station.progress", progress);
        nbt.putLong(("gem_empowering_station.energy"), energyStorage.amount);
        nbt.put("gem_empowering_station.variant", fluidStorage.variant.toNbt());
        nbt.putLong("gem_empowering_station.fluid_amount", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("gem_empowering_station.progress");
        energyStorage.amount = nbt.getLong("gem_empowering_station.energy");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("gem_empowering_station.variant"));
        fluidStorage.amount = nbt.getLong("gem_empowering_station.fluid_amount");
        super.readNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        fillUpOnEnergy(); // until we have machines/other mods that give us Energy
        fillUpOnFluid();

        if(canInsertIntoOutputSlot() && hasRecipe()) {
            increaseCraftingProgress();
            extractEnergy();
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                extractFluid();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void extractFluid() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.extract(FluidVariant.of(Fluids.WATER), 500, transaction);
            transaction.commit();
        }
    }

    private void fillUpOnFluid() {
        if(hasFluidSourceItemInFluidSlot(FLUID_ITEM_SLOT)) {
            transferItemFluidToTank(FLUID_ITEM_SLOT);
        }
    }

    private void transferItemFluidToTank(int fluidItemSlot) {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.insert(FluidVariant.of(Fluids.WATER),
                    (FluidConstants.BUCKET / 81), transaction);
            transaction.commit();

            this.setStack(fluidItemSlot, new ItemStack(Items.BUCKET));
        }
    }

    private boolean hasFluidSourceItemInFluidSlot(int fluidItemSlot) {
        return this.getStack(fluidItemSlot).getItem() == Items.WATER_BUCKET;
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(32L, transaction);
            transaction.commit();
        }
    }

    private void fillUpOnEnergy() {
        if(hasEnergyItemInEnergySlot(ENERGY_ITEM_SLOT)) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.energyStorage.insert(64, transaction);
                transaction.commit();
            }
        }
    }

    private boolean hasEnergyItemInEnergySlot(int energyItemSlot) {
        return this.getStack(energyItemSlot).getItem() == ModItems.CAULIFLOWER;
    }

    private void craftItem() {
        Optional<GemEmpoweringRecipe> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(null).getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().getOutput(null).getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<GemEmpoweringRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().getOutput(null);

        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output) && hasEnoughEnergyToCraft() && hasEnoughFluidToCraft();
    }

    private boolean hasEnoughFluidToCraft() {
        return this.fluidStorage.amount >= 500; // mB amount!
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= 32L * this.maxProgress;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
    }

    private Optional<GemEmpoweringRecipe> getCurrentRecipe() {
        SimpleInventory inventory = new SimpleInventory((this.size()));
        for(int i = 0; i < this.size(); i++) {
            inventory.setStack(i, this.getStack(i));
        }

        return this.getWorld().getRecipeManager().getFirstMatch(GemEmpoweringRecipe.Type.INSTANCE, inventory, this.getWorld());
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
