package io.github.kawaiicakes.create_cutil.dynamic_gearshift;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.content.redstone.link.LinkBehaviour;
import com.simibubi.create.content.redstone.link.RedstoneLinkFrequencySlot;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import io.github.kawaiicakes.create_cutil.CreateControlUtilRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DynamicGearshiftBlockEntity extends SplitShaftBlockEntity {
    protected boolean receivedSignalChanged;
    protected int receivedSignal;
    protected LinkBehaviour link;
    protected boolean receivedAltSignalChanged;
    protected int receivedAltSignal;
    protected LinkBehaviour altLink;
    protected boolean firstTick;

    public DynamicGearshiftBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehavioursDeferred(List<BlockEntityBehaviour> behaviours) {
        createLink();
        behaviours.add(this.link);
        behaviours.add(this.altLink);
    }

    protected void createLink() {
        Pair<ValueBoxTransform, ValueBoxTransform> slots =
                ValueBoxTransform.Dual.makeSlots(FrequencySlots::new);

        Pair<ValueBoxTransform, ValueBoxTransform> altSlots =
                ValueBoxTransform.Dual.makeSlots((f) -> new FrequencySlots(f, true));

        this.link = LinkBehaviour.receiver(this, slots, this::setSignal);
        this.altLink = LinkBehaviour.receiver(this, altSlots, this::setAltSignal);
    }

    public void setSignal(int power) {
        if (this.receivedSignal != power)
            this.receivedSignalChanged = true;
        this.receivedSignal = power;
    }

    public void setAltSignal(int power) {
        if (this.receivedAltSignal != power)
            this.receivedAltSignalChanged = true;
        this.receivedAltSignal = power;
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("Receive", this.getReceivedSignal());
        compound.putBoolean("ReceivedChanged", this.receivedSignalChanged);
        compound.putInt("ReceiveAlt", this.getReceivedAltSignal());
        compound.putBoolean("ReceivedAltChanged", this.receivedAltSignalChanged);
        compound.putBoolean("firstTick", this.firstTick);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);

        this.receivedSignal = compound.getInt("Receive");
        this.receivedSignalChanged = compound.getBoolean("ReceivedChanged");
        this.receivedAltSignal = compound.getInt("ReceiveAlt");
        this.receivedAltSignalChanged = compound.getBoolean("ReceivedAltChanged");
        this.firstTick = compound.getBoolean("firstTick");
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.firstTick) {
            this.firstTick = true;

            LinkBehaviour old = this.link;
            LinkBehaviour oldAlt = this.altLink;

            removeBehaviour(LinkBehaviour.TYPE);
            createLink();

            this.link.copyItemsFrom(old);
            this.altLink.copyItemsFrom(oldAlt);
            attachBehaviourLate(this.link);
            attachBehaviourLate(this.altLink);
        }

        if (!(this.level instanceof ServerLevel))
            return;

        BlockState blockState = getBlockState();
        if (!CreateControlUtilRegistry.DYNAMIC_GEARSHIFT_BLOCK.has(blockState))
            return;

        if ((getReceivedSignal() > 0) != blockState.getValue(DynamicGearshiftBlock.POWERED)) {
            this.receivedSignalChanged = true;
            this.level.setBlockAndUpdate(this.worldPosition, blockState.cycle(DynamicGearshiftBlock.POWERED));
        }

        if ((getReceivedAltSignal() > 0) != blockState.getValue(DynamicGearshiftBlock.ALT_POWERED)) {
            this.receivedAltSignalChanged = true;
            this.level.setBlockAndUpdate(this.worldPosition, blockState.cycle(DynamicGearshiftBlock.ALT_POWERED));
        }

        if (this.receivedSignalChanged) {
            this.level.blockUpdated(
                    this.worldPosition,
                    this.level
                            .getBlockState(this.worldPosition)
                            .getBlock()
            );
            this.receivedSignalChanged = false;
        }

        if (this.receivedAltSignalChanged) {
            this.level.blockUpdated(
                    this.worldPosition,
                    this.level
                            .getBlockState(this.worldPosition)
                            .getBlock()
            );
            this.receivedAltSignalChanged = false;
        }
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        if (!this.hasSource()) return 0;
        return sixteenths(this.getReceivedSignal() - this.getReceivedAltSignal());
    }

    public int getReceivedSignal() {
        return this.receivedSignal;
    }

    public int getReceivedAltSignal() {
        return this.receivedAltSignal;
    }

    // Division can be expensive. Multiplication is faster. That said, IDK if this is even worth it lol
    public static float sixteenths(int numerator) {
        return numerator * 0.0625F;
    }

    public static class FrequencySlots extends RedstoneLinkFrequencySlot {
        protected final boolean alt;

        public FrequencySlots(boolean first) {
            this(first, false);
        }

        public FrequencySlots(boolean first, boolean alt) {
            super(first);
            this.alt = alt;
        }

        @Override
        public Vec3 getLocalOffset(BlockState state) {
            Direction.Axis axis = state.getValue(DynamicGearshiftBlock.AXIS);
            Direction facing = this.alt
                    ? getPositiveDirectionOnAxis(axis)
                    : getPositiveDirectionOnAxis(axis).getOpposite();

            Vec3 location = VecHelper.voxelSpace(8f, 5.5f, 15.99f);

            if (isFirst())
                location = location.add(0, 5 / 16f, 0);

            return rotateHorizontally(facing, location);
        }

        @Override
        public void rotate(BlockState state, PoseStack ms) {
            Direction.Axis axis = state.getValue(DynamicGearshiftBlock.AXIS);
            Direction facing = this.alt
                    ? getPositiveDirectionOnAxis(axis)
                    : getPositiveDirectionOnAxis(axis).getOpposite();
            float yRot = facing.getAxis()
                    .isVertical() ? 0 : AngleHelper.horizontalAngle(facing) + 180;
            TransformStack.cast(ms)
                    .rotateY(yRot);
        }

        public static Direction getPositiveDirectionOnAxis(Direction.Axis axis) {
            return switch (axis) {
                case X, Y -> Direction.SOUTH;
                case Z -> Direction.EAST;
            };
        }

        public static Vec3 rotateHorizontally(Direction facing, Vec3 vec) {
            return VecHelper.rotateCentered(vec, AngleHelper.horizontalAngle(facing), Direction.Axis.Y);
        }
    }
}
