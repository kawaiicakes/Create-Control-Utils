package io.github.kawaiicakes.createutil;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateControlUtil {
    public static final String MOD_ID = "createutil";
    public static final String NAME = "Create: Control Utilities";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public static void init() {
        CreateControlUtilMenuTypes.register(); // hold registrate in a separate class to avoid loading early on forge
        CreateControlUtilBlocks.register();
        CreateControlUtilBlockEntityTypes.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
