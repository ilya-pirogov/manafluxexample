package dev.pirogov.manafluxexample;

import dev.pirogov.manafluxexample.block.MFBlocks;
import dev.pirogov.manafluxexample.api.ManaFluxStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManaFluxExample implements ModInitializer {
    public static final String MODID = "manafluxexample";

    public static final Logger logger = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        // initializing blocks
        Registry.register(Registries.BLOCK, new Identifier(MODID, "manafluxconsumer_block"), MFBlocks.MANAFLUXCONSUMER_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MODID, "manafluxconsumer_block"), new BlockItem(
                MFBlocks.MANAFLUXCONSUMER_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, new Identifier(MODID, "manafluxprovider_block"), MFBlocks.MANAFLUXPROVIDER_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MODID, "manafluxprovider_block"), new BlockItem(
                MFBlocks.MANAFLUXPROVIDER_BLOCK, new FabricItemSettings()));

        // initializing ManaFluxStorage BlockApiLookup for all our BE
        ManaFluxStorage.SIDED.registerForBlockEntity((be, dir) -> be.mfStorage, MFBlocks.MANAFLUXCONSUMER_ENTITY_TYPE);
        ManaFluxStorage.SIDED.registerForBlockEntity((be, dir) -> be.mfStorage, MFBlocks.MANAFLUXPROVIDER_ENTITY_TYPE);
    }
}
