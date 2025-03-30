package io.github.kawaiicakes.create_cutil.dynamic_gearshift;

import com.simibubi.create.content.kinetics.transmission.ClutchBlock;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import io.github.kawaiicakes.create_cutil.CreateControlUtilRegistry;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class DynamicGearshiftBlock extends ClutchBlock {
    public static final BooleanProperty ALT_POWERED = BooleanProperty.create("alt_powered");

    public DynamicGearshiftBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(POWERED, Boolean.FALSE)
                        .setValue(ALT_POWERED, Boolean.FALSE)
        );
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState blockState) {
        return false;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return CreateControlUtilRegistry.DYNAMIC_GEARSHIFT.get();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ALT_POWERED, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ALT_POWERED);
        super.createBlockStateDefinition(builder);
    }
}
