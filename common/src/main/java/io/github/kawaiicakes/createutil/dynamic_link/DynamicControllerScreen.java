package io.github.kawaiicakes.createutil.dynamic_link;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DynamicControllerScreen extends AbstractSimiContainerScreen<DynamicControllerMenu> {
    public DynamicControllerScreen(DynamicControllerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {

    }
}
