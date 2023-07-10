package dev.pirogov.manafluxexample.block;

import dev.pirogov.manafluxexample.block.entity.ManaFluxProviderEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ManaFluxProvider extends BlockWithEntity implements BlockEntityProvider {
    protected ManaFluxProvider(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ManaFluxProviderEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, MFBlocks.MANAFLUXPROVIDER_ENTITY_TYPE, ManaFluxProviderEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // shows how many mf were transferred last tick
        if (!world.isClient && hand == Hand.OFF_HAND) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ManaFluxProviderEntity manaFlux){
                player.sendMessage(Text.literal("Output: "+manaFlux.lastTransferred), false);

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }
}
