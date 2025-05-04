package io.github.kawaiicakes.createutil.dynamic_link;

import com.simibubi.create.content.redstone.link.controller.LinkedControllerItem;
import com.simibubi.create.foundation.utility.AdventureUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class DynamicControllerItem extends LinkedControllerItem {
    public DynamicControllerItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        if (AdventureUtil.isAdventure(player))
            return null;
        ItemStack heldItem = player.getMainHandItem();
        return DynamicControllerMenu.create(id, inv, heldItem);
    }
}
