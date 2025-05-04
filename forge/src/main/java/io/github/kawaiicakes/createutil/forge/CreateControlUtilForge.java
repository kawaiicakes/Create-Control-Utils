package io.github.kawaiicakes.createutil.forge;

import io.github.kawaiicakes.createutil.CreateControlUtil;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateControlUtil.MOD_ID)
public class CreateControlUtilForge {
    public CreateControlUtilForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CreateControlUtil.REGISTRATE.registerEventListeners(eventBus);
        CreateControlUtil.init();
    }
}
