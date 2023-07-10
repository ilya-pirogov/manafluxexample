package dev.pirogov.manafluxexample.api;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import static dev.pirogov.manafluxexample.ManaFluxExample.MODID;


@SuppressWarnings({"unused", "UnstableApiUsage"})
public interface ManaFluxStorage extends EnergyStorage {
    BlockApiLookup<ManaFluxStorage, @Nullable Direction> SIDED =
            BlockApiLookup.get(new Identifier(MODID, "sided_manaflux_energy"), ManaFluxStorage.class, Direction.class);

    ItemApiLookup<ManaFluxStorage, ContainerItemContext> ITEM =
            ItemApiLookup.get(new Identifier(MODID, "manaflux_energy"), ManaFluxStorage.class, ContainerItemContext.class);

}
