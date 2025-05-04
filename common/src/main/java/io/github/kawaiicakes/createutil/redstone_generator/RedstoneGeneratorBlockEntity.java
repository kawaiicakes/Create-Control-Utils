package io.github.kawaiicakes.createutil.redstone_generator;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

import static io.github.kawaiicakes.createutil.redstone_generator.RedstoneGeneratorBlock.POWERED;

public class RedstoneGeneratorBlockEntity extends SplitShaftBlockEntity {
    public RedstoneGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);

        Objects.requireNonNull(this.getLevel()).setBlock(
                this.getBlockPos(),
                this.getBlockState().cycle(POWERED),
                2
        );

        /*

        for (Direction direction : Direction.values()) {
            if (!RedstoneGeneratorBlock.signalAxis(this.getBlockState()).test(direction)) continue;

            this.getLevel().neighborChanged(
                    this.getLevel().getBlockState(this.getBlockPos().relative(direction)),
                    this.getBlockPos().relative(direction),
                    this.getBlockState().getBlock(),
                    this.getBlockPos(),
                    false
            );
        }

         */

        this.notifyUpdate();
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        return 1;
    }
}
