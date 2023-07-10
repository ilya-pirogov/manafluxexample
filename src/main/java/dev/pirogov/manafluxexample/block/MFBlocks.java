package dev.pirogov.manafluxexample.block;

import dev.pirogov.manafluxexample.block.entity.ManaFluxConsumerEntity;
import dev.pirogov.manafluxexample.block.entity.ManaFluxProviderEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.pirogov.manafluxexample.ManaFluxExample.MODID;

public final class MFBlocks {
    public static ManaFluxConsumer MANAFLUXCONSUMER_BLOCK = new ManaFluxConsumer(FabricBlockSettings.of(Material.METAL));
    public static ManaFluxProvider MANAFLUXPROVIDER_BLOCK = new ManaFluxProvider(FabricBlockSettings.of(Material.METAL));

    public static final BlockEntityType<ManaFluxConsumerEntity> MANAFLUXCONSUMER_ENTITY_TYPE =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(MODID, "manafluxconsumer_entity"),
                    FabricBlockEntityTypeBuilder.create(ManaFluxConsumerEntity::new, MANAFLUXCONSUMER_BLOCK).build()
            );
    public static final BlockEntityType<ManaFluxProviderEntity> MANAFLUXPROVIDER_ENTITY_TYPE =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(MODID, "manafluxprovider_entity"),
                    FabricBlockEntityTypeBuilder.create(ManaFluxProviderEntity::new, MANAFLUXPROVIDER_BLOCK).build()
            );
}
