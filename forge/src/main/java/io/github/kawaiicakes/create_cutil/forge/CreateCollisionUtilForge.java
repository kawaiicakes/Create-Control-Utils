package io.github.kawaiicakes.create_cutil.forge;

import io.github.kawaiicakes.create_cutil.CreateCollisionUtil;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateCollisionUtil.MOD_ID)
public class CreateCollisionUtilForge {
    public CreateCollisionUtilForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
