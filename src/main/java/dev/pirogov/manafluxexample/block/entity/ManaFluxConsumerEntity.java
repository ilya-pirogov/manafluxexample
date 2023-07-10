package dev.pirogov.manafluxexample.block.entity;

import dev.pirogov.manafluxexample.ManaFluxExample;
import dev.pirogov.manafluxexample.block.MFBlocks;
import dev.pirogov.manafluxexample.api.SimpleManaFluxStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ManaFluxConsumerEntity extends BlockEntity {
    public final SimpleManaFluxStorage mfStorage = new SimpleManaFluxStorage(1000, 100, 0);

    static Random rnd = Random.create();

    boolean isWorking = false;

    public ManaFluxConsumerEntity(BlockPos pos, BlockState state) {
        super(MFBlocks.MANAFLUXCONSUMER_ENTITY_TYPE, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.mfStorage.amount = nbt.getLong("mfStorage");
        this.isWorking = nbt.getBoolean("isWorking");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("isWorking", this.isWorking);
        nbt.putLong("mfStorage", this.mfStorage.amount);

        super.writeNbt(nbt);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        ManaFluxExample.logger.info("toUpdatePacket()");
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        ManaFluxExample.logger.info("toInitialChunkDataNbt()");
        return createNbt();
    }

    protected void startMachine() {
        if (world == null || world.isClient || isWorking) {
            return;
        }

        this.isWorking = true;
        this.markDirty();
        world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    protected void applyEffect() {
        if (world == null || world.isClient || !isWorking) {
            return;
        }

        var players = world.getPlayers(TargetPredicate.createNonAttackable(), null, new Box(pos).expand(8));
        ManaFluxExample.logger.info("Found {} players", players.size());
        for (var player: players) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100));
        }
    }

    protected void stopMachine() {
        if (world == null || world.isClient || !isWorking) {
            return;
        }

        this.isWorking = false;
        this.markDirty();
        world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    static long n = 0;

    public static void tick(World world, BlockPos pos, BlockState state, ManaFluxConsumerEntity be) {
        if (world.isClient && be.isWorking) {
            double x = MathHelper.nextDouble(rnd, pos.getX(), pos.getX() + 1);
            double y = MathHelper.nextDouble(rnd, pos.getY(), pos.getY() + 1);
            double z = MathHelper.nextDouble(rnd, pos.getZ(), pos.getZ() + 1);
            double dx = MathHelper.nextDouble(rnd, -0.1, 0.1);
            double dy = MathHelper.nextDouble(rnd, 0.01, 0.05);
            double dz = MathHelper.nextDouble(rnd, -0.1, 0.1);
            world.addParticle(ParticleTypes.SMOKE, x, y, z, dx, dy, dz);
        }

        if (!world.isClient) {
            if (be.mfStorage.amount >= 1) {
                be.mfStorage.amount -= 1;
                be.markDirty();

                if (n++ % 20 == 0) {
                    be.applyEffect();
                }

                be.startMachine();
            } else {
                be.stopMachine();
            }
        }

    }
}
