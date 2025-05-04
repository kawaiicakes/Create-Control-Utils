package io.github.kawaiicakes.createutil;

import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import io.github.kawaiicakes.createutil.dynamic_link.DynamicControllerMenu;
import io.github.kawaiicakes.createutil.dynamic_link.DynamicControllerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

import static io.github.kawaiicakes.createutil.CreateControlUtil.REGISTRATE;

public class CreateControlUtilMenuTypes {
	public static final MenuEntry<DynamicControllerMenu> DYNAMIC_CONTROLLER =
			registerMenu("dynamic_controller", DynamicControllerMenu::new, () -> DynamicControllerScreen::new);

	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> registerMenu(
			String name, MenuBuilder.ForgeMenuFactory<C> factory,
			NonNullSupplier<MenuBuilder.ScreenFactory<C, S>> screenFactory
	) {
		return REGISTRATE
				.menu(name, factory, screenFactory)
				.register();
	}

	public static void register() {}
}
