package io.github.kawaiicakes.createutil;

import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.kawaiicakes.createutil.redstone_generator.RedstoneGeneratorBlockEntity;

import static io.github.kawaiicakes.createutil.CreateControlUtil.REGISTRATE;

public class CreateControlUtilBlockEntityTypes {
    public static final BlockEntityEntry<RedstoneGeneratorBlockEntity> REDSTONE_GENERATOR = REGISTRATE
            .blockEntity("redstone_generator", RedstoneGeneratorBlockEntity::new)
            .instance(() -> SplitShaftInstance::new, false)
            .validBlocks(CreateControlUtilBlocks.REDSTONE_GENERATOR)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static void register() {}
}
