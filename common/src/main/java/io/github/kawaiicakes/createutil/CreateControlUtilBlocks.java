package io.github.kawaiicakes.createutil;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.kawaiicakes.createutil.redstone_generator.RedstoneGeneratorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MaterialColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static io.github.kawaiicakes.createutil.CreateControlUtil.REGISTRATE;

public class CreateControlUtilBlocks {
    public static final BlockEntry<RedstoneGeneratorBlock> REDSTONE_GENERATOR = REGISTRATE
            .block("redstone_generator", RedstoneGeneratorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().color(MaterialColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {}
}
