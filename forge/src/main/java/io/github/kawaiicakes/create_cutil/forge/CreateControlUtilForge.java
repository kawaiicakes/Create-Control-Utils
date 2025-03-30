package io.github.kawaiicakes.create_cutil.forge;

import io.github.kawaiicakes.create_cutil.CreateControlUtil;
import io.github.kawaiicakes.create_cutil.CreateControlUtilRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateControlUtil.MOD_ID)
public class CreateControlUtilForge {
    public CreateControlUtilForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CreateControlUtilRegistry.REGISTRATE.registerEventListeners(eventBus);
        CreateControlUtil.init();
    }
}
