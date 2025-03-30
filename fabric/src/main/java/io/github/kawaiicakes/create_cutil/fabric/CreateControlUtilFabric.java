package io.github.kawaiicakes.create_cutil.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import io.github.kawaiicakes.create_cutil.CreateControlUtil;
import io.github.kawaiicakes.create_cutil.CreateControlUtilRegistry;
import net.fabricmc.api.ModInitializer;

public class CreateControlUtilFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateControlUtil.init();
        CreateControlUtil.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), CreateControlUtil.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        CreateControlUtilRegistry.REGISTRATE.register();
    }
}
