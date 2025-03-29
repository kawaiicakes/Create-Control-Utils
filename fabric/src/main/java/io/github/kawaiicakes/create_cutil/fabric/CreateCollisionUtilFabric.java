package io.github.kawaiicakes.create_cutil.fabric;

import io.github.kawaiicakes.create_cutil.CreateCollisionUtil;
import net.fabricmc.api.ModInitializer;

public class CreateCollisionUtilFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateCollisionUtil.init();
        /*
        ExampleMod.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), ExampleMod.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        ExampleBlocks.REGISTRATE.register();
         */
    }
}
