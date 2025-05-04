package io.github.kawaiicakes.createutil.dynamic_link;

import com.simibubi.create.content.redstone.link.controller.LinkedControllerMenu;
import io.github.kawaiicakes.createutil.CreateControlUtilMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class DynamicControllerMenu extends LinkedControllerMenu {
    public DynamicControllerMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public DynamicControllerMenu(MenuType<?> type, int id, Inventory inv, ItemStack filterItem) {
        super(type, id, inv, filterItem);
    }


    public static DynamicControllerMenu create(int id, Inventory inv, ItemStack filterItem) {
        return new DynamicControllerMenu(CreateControlUtilMenuTypes.DYNAMIC_CONTROLLER.get(), id, inv, filterItem);
    }
}
