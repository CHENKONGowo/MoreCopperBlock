package com.chenkong.morecopperblock;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.chenkong.morecopperblock.item.ModItems;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.recipe.ModRecipeSerializers;

// ---- Main Mod Class ----

@Mod(MoreCopperBlock.MODID)
public class MoreCopperBlock {
    public static final String MODID = "morecopperblock";
    public static final Logger LOGGER = LogUtils.getLogger();

    // ---- Blocks ----

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("alloy_copper_block",
                    p -> p.mapColor(MapColor.COLOR_ORANGE)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> EXPOSED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("exposed_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> WEATHERED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("weathered_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_GREEN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> OXIDIZED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("oxidized_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_CYAN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    // ---- Cut alloy blocks ----

    public static final DeferredBlock<Block> CUT_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("cut_alloy_copper_block",
                    p -> p.mapColor(MapColor.COLOR_ORANGE)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> EXPOSED_CUT_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("exposed_cut_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> WEATHERED_CUT_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("weathered_cut_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_GREEN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> OXIDIZED_CUT_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("oxidized_cut_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_CYAN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    // ---- Cut alloy stairs ----

    public static final DeferredBlock<StairBlock> CUT_ALLOY_COPPER_STAIRS =
            BLOCKS.register("cut_alloy_copper_stairs", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_ORANGE)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new StairBlock(CUT_ALLOY_COPPER_BLOCK.get().defaultBlockState(), props);
            });

    public static final DeferredBlock<StairBlock> EXPOSED_CUT_ALLOY_COPPER_STAIRS =
            BLOCKS.register("exposed_cut_alloy_copper_stairs", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new StairBlock(EXPOSED_CUT_ALLOY_COPPER_BLOCK.get().defaultBlockState(), props);
            });

    public static final DeferredBlock<StairBlock> WEATHERED_CUT_ALLOY_COPPER_STAIRS =
            BLOCKS.register("weathered_cut_alloy_copper_stairs", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_GREEN)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new StairBlock(WEATHERED_CUT_ALLOY_COPPER_BLOCK.get().defaultBlockState(), props);
            });

    public static final DeferredBlock<StairBlock> OXIDIZED_CUT_ALLOY_COPPER_STAIRS =
            BLOCKS.register("oxidized_cut_alloy_copper_stairs", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_CYAN)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new StairBlock(OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get().defaultBlockState(), props);
            });

    // ---- Cut alloy slabs ----

    public static final DeferredBlock<SlabBlock> CUT_ALLOY_COPPER_SLAB =
            BLOCKS.register("cut_alloy_copper_slab", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_ORANGE)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new SlabBlock(props);
            });

    public static final DeferredBlock<SlabBlock> EXPOSED_CUT_ALLOY_COPPER_SLAB =
            BLOCKS.register("exposed_cut_alloy_copper_slab", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new SlabBlock(props);
            });

    public static final DeferredBlock<SlabBlock> WEATHERED_CUT_ALLOY_COPPER_SLAB =
            BLOCKS.register("weathered_cut_alloy_copper_slab", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_GREEN)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new SlabBlock(props);
            });

    public static final DeferredBlock<SlabBlock> OXIDIZED_CUT_ALLOY_COPPER_SLAB =
            BLOCKS.register("oxidized_cut_alloy_copper_slab", key -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.TERRACOTTA_CYAN)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .setId(ResourceKey.create(Registries.BLOCK, key));
                return new SlabBlock(props);
            });

    // ---- Chiseled alloy blocks ----

    public static final DeferredBlock<Block> CHISELED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("chiseled_alloy_copper_block",
                    p -> p.mapColor(MapColor.COLOR_ORANGE)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> EXPOSED_CHISELED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("exposed_chiseled_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> WEATHERED_CHISELED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("weathered_chiseled_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_GREEN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK =
            BLOCKS.registerSimpleBlock("oxidized_chiseled_alloy_copper_block",
                    p -> p.mapColor(MapColor.TERRACOTTA_CYAN)
                            .strength(3.0F, 6.0F)
                            .requiresCorrectToolForDrops());

    // ---- Items ----

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BlockItem> ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> EXPOSED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(EXPOSED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> WEATHERED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(WEATHERED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> OXIDIZED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(OXIDIZED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> CUT_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(CUT_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> EXPOSED_CUT_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(EXPOSED_CUT_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> WEATHERED_CUT_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(WEATHERED_CUT_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> OXIDIZED_CUT_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(OXIDIZED_CUT_ALLOY_COPPER_BLOCK);

    // ---- Cut alloy stairs items ----

    public static final DeferredItem<BlockItem> CUT_ALLOY_COPPER_STAIRS_ITEM =
            ITEMS.registerSimpleBlockItem(CUT_ALLOY_COPPER_STAIRS);

    public static final DeferredItem<BlockItem> EXPOSED_CUT_ALLOY_COPPER_STAIRS_ITEM =
            ITEMS.registerSimpleBlockItem(EXPOSED_CUT_ALLOY_COPPER_STAIRS);

    public static final DeferredItem<BlockItem> WEATHERED_CUT_ALLOY_COPPER_STAIRS_ITEM =
            ITEMS.registerSimpleBlockItem(WEATHERED_CUT_ALLOY_COPPER_STAIRS);

    public static final DeferredItem<BlockItem> OXIDIZED_CUT_ALLOY_COPPER_STAIRS_ITEM =
            ITEMS.registerSimpleBlockItem(OXIDIZED_CUT_ALLOY_COPPER_STAIRS);

    // ---- Cut alloy slab items ----

    public static final DeferredItem<BlockItem> CUT_ALLOY_COPPER_SLAB_ITEM =
            ITEMS.registerSimpleBlockItem(CUT_ALLOY_COPPER_SLAB);

    public static final DeferredItem<BlockItem> EXPOSED_CUT_ALLOY_COPPER_SLAB_ITEM =
            ITEMS.registerSimpleBlockItem(EXPOSED_CUT_ALLOY_COPPER_SLAB);

    public static final DeferredItem<BlockItem> WEATHERED_CUT_ALLOY_COPPER_SLAB_ITEM =
            ITEMS.registerSimpleBlockItem(WEATHERED_CUT_ALLOY_COPPER_SLAB);

    public static final DeferredItem<BlockItem> OXIDIZED_CUT_ALLOY_COPPER_SLAB_ITEM =
            ITEMS.registerSimpleBlockItem(OXIDIZED_CUT_ALLOY_COPPER_SLAB);

    // ---- Chiseled alloy block items ----

    public static final DeferredItem<BlockItem> CHISELED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(CHISELED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> EXPOSED_CHISELED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(EXPOSED_CHISELED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> WEATHERED_CHISELED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(WEATHERED_CHISELED_ALLOY_COPPER_BLOCK);

    public static final DeferredItem<BlockItem> OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK);

    // ---- Creative Tab ----

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COPPER_TAB =
            CREATIVE_MODE_TABS.register("copper_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.morecopperblock"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.EXPOSED_COPPER_INGOT.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.EXPOSED_COPPER_INGOT.get());
                        output.accept(ModItems.WEATHERED_COPPER_INGOT.get());
                        output.accept(ModItems.OXIDIZED_COPPER_INGOT.get());
                        output.accept(ModItems.WAXED_COPPER_INGOT.get());
                        output.accept(ModItems.WAXED_EXPOSED_COPPER_INGOT.get());
                        output.accept(ModItems.WAXED_WEATHERED_COPPER_INGOT.get());
                        output.accept(ModItems.WAXED_OXIDIZED_COPPER_INGOT.get());
                        output.accept(ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(EXPOSED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(WEATHERED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(OXIDIZED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(CUT_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(EXPOSED_CUT_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(WEATHERED_CUT_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(OXIDIZED_CUT_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(CUT_ALLOY_COPPER_STAIRS_ITEM.get());
                        output.accept(EXPOSED_CUT_ALLOY_COPPER_STAIRS_ITEM.get());
                        output.accept(WEATHERED_CUT_ALLOY_COPPER_STAIRS_ITEM.get());
                        output.accept(OXIDIZED_CUT_ALLOY_COPPER_STAIRS_ITEM.get());
                        output.accept(CUT_ALLOY_COPPER_SLAB_ITEM.get());
                        output.accept(EXPOSED_CUT_ALLOY_COPPER_SLAB_ITEM.get());
                        output.accept(WEATHERED_CUT_ALLOY_COPPER_SLAB_ITEM.get());
                        output.accept(OXIDIZED_CUT_ALLOY_COPPER_SLAB_ITEM.get());
                        output.accept(CHISELED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(EXPOSED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(WEATHERED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get());
                        output.accept(OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get());
                    }).build());

    // ---- Init ----

    public MoreCopperBlock(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        AlloyCopperBlockItem.DATA_COMPONENTS.register(modEventBus);
        ModItems.register(ITEMS);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        if (Config.LOG_DIRT_BLOCK.getAsBoolean())
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
        Config.ITEM_STRINGS.get().forEach(item -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
