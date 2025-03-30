package io.github.kawaiicakes.create_cutil;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateControlUtil {
    public static final String MOD_ID = "create_cutil";
    public static final String NAME = "Create: Control Utilities";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static void init() {
        CreateControlUtilRegistry.init(); // hold registrate in a separate class to avoid loading early on forge
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
