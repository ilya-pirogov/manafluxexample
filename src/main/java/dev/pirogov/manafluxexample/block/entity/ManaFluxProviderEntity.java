package dev.pirogov.manafluxexample.block.entity;

import dev.pirogov.manafluxexample.api.ManaFluxStorage;
import dev.pirogov.manafluxexample.block.MFBlocks;
import dev.pirogov.manafluxexample.api.SimpleManaFluxStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorageUtil;

@SuppressWarnings("UnstableApiUsage")
public class ManaFluxProviderEntity extends BlockEntity {
    public final SimpleManaFluxStorage mfStorage = new SimpleManaFluxStorage(1000, 0, 100);

    public long lastTransferred = 0;

    public ManaFluxProviderEntity(BlockPos pos, BlockState state) {
        super(MFBlocks.MANAFLUXPROVIDER_ENTITY_TYPE, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.mfStorage.amount = nbt.getLong("mfStorage");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putLong("mfStorage", this.mfStorage.amount);

        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ManaFluxProviderEntity be) {
        if (!world.isClient ) {
            boolean changed = false;

            if (be.mfStorage.amount < be.mfStorage.capacity) {
                be.mfStorage.amount = be.mfStorage.capacity;
                changed = true;
            }

            // looking for consumers in all directions
            long lastTransferred = 0;
            for (Direction dir: Direction.values()) {
                BlockPos conPos = pos.offset(dir);
                ManaFluxStorage consumer = ManaFluxStorage.SIDED.find(world, conPos, dir);

                // if found then transfer mf
                if (consumer != null) {
                    long transferred = EnergyStorageUtil.move(be.mfStorage, consumer, 2, null);
                    if (transferred > 0) {
                        lastTransferred += transferred;
                        changed = true;
                    }
                }
            }
            be.lastTransferred = lastTransferred;

            if (changed) {
                // notify server that we have updates we want to save and sync with client
                be.markDirty();
            }
        }

    }
}
