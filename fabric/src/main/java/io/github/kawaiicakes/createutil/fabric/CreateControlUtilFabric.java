package io.github.kawaiicakes.createutil.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import io.github.kawaiicakes.createutil.CreateControlUtil;
import io.github.kawaiicakes.createutil.CreateControlUtilMenuTypes;
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
        CreateControlUtil.REGISTRATE.register();
    }
}
