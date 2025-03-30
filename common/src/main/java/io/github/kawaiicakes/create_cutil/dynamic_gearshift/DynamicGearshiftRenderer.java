package io.github.kawaiicakes.create_cutil.dynamic_gearshift;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.content.redstone.link.LinkRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DynamicGearshiftRenderer extends SplitShaftRenderer {
    public DynamicGearshiftRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(
            SplitShaftBlockEntity be,
            float partialTicks,
            PoseStack ms,
            MultiBufferSource buffer, int light, int overlay
    ) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);
        LinkRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);
    }
}
