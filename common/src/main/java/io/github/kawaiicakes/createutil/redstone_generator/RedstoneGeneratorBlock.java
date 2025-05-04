package io.github.kawaiicakes.createutil.redstone_generator;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.AbstractEncasedShaftBlock;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import io.github.kawaiicakes.createutil.CreateControlUtilBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class RedstoneGeneratorBlock extends AbstractEncasedShaftBlock implements IBE<SplitShaftBlockEntity> {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneGeneratorBlock(Properties properties) {
        super(properties.isRedstoneConductor((a,b,c) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    /*
        CREATE STUFF
     */

    @Override
    public Class<SplitShaftBlockEntity> getBlockEntityClass() {
        return SplitShaftBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return CreateControlUtilBlockEntityTypes.REDSTONE_GENERATOR.get();
    }

    /*
        MINECRAFT STUFF
     */

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        if (!signalAxis(blockState).test(direction)) return 0;

        int signal = this.getSignalFromSpeed(blockGetter, blockPos);

        if (!blockState.getValue(POWERED)) return 0;
        if (signal == 0) return 0;
        boolean powerOtherFaceOnAxis = signal < 0;
        signal = Math.abs(signal);

        return switch (direction) {
            case DOWN, UP -> 0;
            case NORTH, EAST -> powerOtherFaceOnAxis ? 0 : signal;
            case SOUTH, WEST -> powerOtherFaceOnAxis ? signal : 0;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(
                POWERED,
                this.isSpinning(context.getLevel(), context.getClickedPos())
        );
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        withBlockEntityDo(
                worldIn,
                pos,
                (be) -> RotationPropagator.handleAdded(worldIn, pos, be)
        );
    }

    /*
        HELPER
     */

    public boolean isSpinning(BlockGetter level, BlockPos pos) {
        return this.getSpeed(level, pos) != 0;
    }

    public float getSpeed(BlockGetter level, BlockPos pos) {
        SplitShaftBlockEntity be = getBlockEntity(level, pos);
        if (be == null) return 0;
        return be.getSpeed();
    }

    public int getSignalFromSpeed(BlockGetter level, BlockPos pos) {
        float speedFraction = this.getSpeed(level, pos) / 256.0F;

        if (speedFraction == 0) return 0;

        return (int) (15.0F * speedFraction);
    }

    // TODO - you should be able to rotate about the y axis
    public static Direction.Axis signalAxis(BlockState state) {
        return switch (state.getValue(AXIS)) {
            case X, Y -> Direction.Axis.Z;
            case Z -> Direction.Axis.X;
        };
    }

    /*
        BOILERPLATE STUFF
     */

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getSignal(blockGetter, blockPos, direction);
    }

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        super.createBlockStateDefinition(builder);
    }
}
