package io.github.kawaiicakes.create_cutil;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.kawaiicakes.create_cutil.dynamic_gearshift.DynamicGearshiftBlock;
import io.github.kawaiicakes.create_cutil.dynamic_gearshift.DynamicGearshiftBlockEntity;
import io.github.kawaiicakes.create_cutil.dynamic_gearshift.DynamicGearshiftRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MaterialColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class CreateControlUtilRegistry {
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateControlUtil.MOD_ID);

	public static final BlockEntry<DynamicGearshiftBlock> DYNAMIC_GEARSHIFT_BLOCK = REGISTRATE.block(
			"dynamic_gearshift", DynamicGearshiftBlock::new
	)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion().color(MaterialColor.PODZOL))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntityEntry<DynamicGearshiftBlockEntity> DYNAMIC_GEARSHIFT = REGISTRATE
			.blockEntity("dynamic_gearshift", DynamicGearshiftBlockEntity::new)
			.instance(() -> SplitShaftInstance::new)
			.validBlocks(DYNAMIC_GEARSHIFT_BLOCK)
			.renderer(() -> DynamicGearshiftRenderer::new)
			.register();

	public static void init() {
		// load the class and register everything
		CreateControlUtil.LOGGER.info("Registering blocks & block entities for " + CreateControlUtil.NAME);
	}
}
