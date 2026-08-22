package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MoreCopperBlock.MODID);

    // ---- Alloy Copper Block (9-ingot 3×3) ----

    private static final MapCodec<AlloyCopperRecipe> ALLOY_CODEC =
            MapCodec.unit(new AlloyCopperRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, AlloyCopperRecipe> ALLOY_STREAM_CODEC =
            StreamCodec.unit(new AlloyCopperRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyCopperRecipe>>
            ALLOY_COPPER_RECIPE = RECIPE_SERIALIZERS.register("crafting_alloy_copper_block",
                    () -> new RecipeSerializer<>(ALLOY_CODEC, ALLOY_STREAM_CODEC));

    // ---- Cut Alloy Copper Block (2×2, 4 blocks → 4 cut blocks) ----

    private static final MapCodec<CutAlloyCopperRecipe> CUT_ALLOY_CODEC =
            MapCodec.unit(new CutAlloyCopperRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CutAlloyCopperRecipe> CUT_ALLOY_STREAM_CODEC =
            StreamCodec.unit(new CutAlloyCopperRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CutAlloyCopperRecipe>>
            CUT_ALLOY_COPPER_RECIPE = RECIPE_SERIALIZERS.register("crafting_cut_alloy_copper_block",
                    () -> new RecipeSerializer<>(CUT_ALLOY_CODEC, CUT_ALLOY_STREAM_CODEC));

    // ---- Cut Alloy Stairs (3×3 stair pattern, 6 blocks → 4 stairs) ----

    private static final MapCodec<CutAlloyStairsRecipe> CUT_STAIRS_CODEC =
            MapCodec.unit(new CutAlloyStairsRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CutAlloyStairsRecipe> CUT_STAIRS_STREAM_CODEC =
            StreamCodec.unit(new CutAlloyStairsRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CutAlloyStairsRecipe>>
            CUT_ALLOY_STAIRS_RECIPE = RECIPE_SERIALIZERS.register("crafting_cut_alloy_stairs",
                    () -> new RecipeSerializer<>(CUT_STAIRS_CODEC, CUT_STAIRS_STREAM_CODEC));

    // ---- Cut Alloy Slabs (3×3, 3 blocks in a row → 6 slabs) ----

    private static final MapCodec<CutAlloySlabRecipe> CUT_SLAB_CODEC =
            MapCodec.unit(new CutAlloySlabRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CutAlloySlabRecipe> CUT_SLAB_STREAM_CODEC =
            StreamCodec.unit(new CutAlloySlabRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CutAlloySlabRecipe>>
            CUT_ALLOY_SLAB_RECIPE = RECIPE_SERIALIZERS.register("crafting_cut_alloy_slab",
                    () -> new RecipeSerializer<>(CUT_SLAB_CODEC, CUT_SLAB_STREAM_CODEC));

    // ---- Stonecutter: Cut Alloy Block → 2 Stairs ----

    private static final MapCodec<CutAlloyStonecutterRecipe> STONECUT_STAIRS_CODEC =
            MapCodec.unit(new CutAlloyStonecutterRecipe(true));
    private static final StreamCodec<RegistryFriendlyByteBuf, CutAlloyStonecutterRecipe> STONECUT_STAIRS_STREAM_CODEC =
            StreamCodec.unit(new CutAlloyStonecutterRecipe(true));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CutAlloyStonecutterRecipe>>
            CUT_ALLOY_STAIRS_STONECUTTING = RECIPE_SERIALIZERS.register("stonecutting_cut_alloy_stairs",
                    () -> new RecipeSerializer<>(STONECUT_STAIRS_CODEC, STONECUT_STAIRS_STREAM_CODEC));

    // ---- Stonecutter: Cut Alloy Block → 2 Slabs ----

    private static final MapCodec<CutAlloyStonecutterRecipe> STONECUT_SLAB_CODEC =
            MapCodec.unit(new CutAlloyStonecutterRecipe(false));
    private static final StreamCodec<RegistryFriendlyByteBuf, CutAlloyStonecutterRecipe> STONECUT_SLAB_STREAM_CODEC =
            StreamCodec.unit(new CutAlloyStonecutterRecipe(false));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CutAlloyStonecutterRecipe>>
            CUT_ALLOY_SLAB_STONECUTTING = RECIPE_SERIALIZERS.register("stonecutting_cut_alloy_slab",
                    () -> new RecipeSerializer<>(STONECUT_SLAB_CODEC, STONECUT_SLAB_STREAM_CODEC));

    // ---- Chiseled Alloy Copper Block (1×2, 2 slabs → 1 chiseled) ----

    private static final MapCodec<ChiseledAlloyCopperRecipe> CHISELED_CODEC =
            MapCodec.unit(new ChiseledAlloyCopperRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, ChiseledAlloyCopperRecipe> CHISELED_STREAM_CODEC =
            StreamCodec.unit(new ChiseledAlloyCopperRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChiseledAlloyCopperRecipe>>
            CHISELED_ALLOY_COPPER_RECIPE = RECIPE_SERIALIZERS.register("crafting_chiseled_alloy_copper_block",
                    () -> new RecipeSerializer<>(CHISELED_CODEC, CHISELED_STREAM_CODEC));

    // ---- Copper Nugget Decompose (1 ingot → 9 nuggets) ----

    private static final MapCodec<CopperNuggetDecomposeRecipe> NUGGET_DECOMPOSE_CODEC =
            MapCodec.unit(new CopperNuggetDecomposeRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CopperNuggetDecomposeRecipe> NUGGET_DECOMPOSE_STREAM_CODEC =
            StreamCodec.unit(new CopperNuggetDecomposeRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CopperNuggetDecomposeRecipe>>
            COPPER_NUGGET_DECOMPOSE_RECIPE = RECIPE_SERIALIZERS.register("crafting_copper_nugget_decompose",
                    () -> new RecipeSerializer<>(NUGGET_DECOMPOSE_CODEC, NUGGET_DECOMPOSE_STREAM_CODEC));

    // ---- Copper Block Decompose (1 alloy block → 9 ingots) ----

    private static final MapCodec<CopperBlockDecomposeRecipe> BLOCK_DECOMPOSE_CODEC =
            MapCodec.unit(new CopperBlockDecomposeRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CopperBlockDecomposeRecipe> BLOCK_DECOMPOSE_STREAM_CODEC =
            StreamCodec.unit(new CopperBlockDecomposeRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CopperBlockDecomposeRecipe>>
            COPPER_BLOCK_DECOMPOSE_RECIPE = RECIPE_SERIALIZERS.register("crafting_copper_block_decompose",
                    () -> new RecipeSerializer<>(BLOCK_DECOMPOSE_CODEC, BLOCK_DECOMPOSE_STREAM_CODEC));

    // ---- Copper Nugget Compose (9 nuggets → 1 ingot) ----

    private static final MapCodec<CopperNuggetComposeRecipe> NUGGET_COMPOSE_CODEC =
            MapCodec.unit(new CopperNuggetComposeRecipe());
    private static final StreamCodec<RegistryFriendlyByteBuf, CopperNuggetComposeRecipe> NUGGET_COMPOSE_STREAM_CODEC =
            StreamCodec.unit(new CopperNuggetComposeRecipe());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CopperNuggetComposeRecipe>>
            COPPER_NUGGET_COMPOSE_RECIPE = RECIPE_SERIALIZERS.register("crafting_copper_nugget_compose",
                    () -> new RecipeSerializer<>(NUGGET_COMPOSE_CODEC, NUGGET_COMPOSE_STREAM_CODEC));
}
