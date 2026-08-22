package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom stonecutter recipe: one cut alloy copper block → 2 stairs or 2 slabs,
 * preserving CUT_COMPOSITION data component.
 */
public class CutAlloyStonecutterRecipe implements Recipe<SingleRecipeInput> {

    private final boolean isStairs;

    public CutAlloyStonecutterRecipe(boolean isStairs) {
        this.isStairs = isStairs;
    }

    /** Returns an Ingredient matching any of the 4 cut alloy copper blocks. */
    private static Ingredient cutAlloyIngredient() {
        return Ingredient.of(
                MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK_ITEM.get(),
                MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK_ITEM.get(),
                MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK_ITEM.get(),
                MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK_ITEM.get()
        );
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        ItemStack stack = input.item();
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return block == MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get();
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        ItemStack in = input.item();
        Block inBlock = ((BlockItem) in.getItem()).getBlock();
        List<Integer> cutComposition = in.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());

        Block outBlock;
        if (isStairs) {
            outBlock = getStairsVariant(inBlock);
        } else {
            outBlock = getSlabVariant(inBlock);
        }

        ItemStack result = new ItemStack(outBlock, 2);
        if (cutComposition != null && cutComposition.size() == 36) {
            if (isStairs) {
                // Stairs: duplicate cut composition 6 times → 216 values
                List<Integer> stairsComp = new ArrayList<>(216);
                for (int i = 0; i < 6; i++) {
                    stairsComp.addAll(cutComposition);
                }
                result.set(AlloyCopperBlockItem.STAIRS_COMPOSITION.get(), stairsComp);
            } else {
                // Slabs: duplicate cut composition 3 times → 108 values
                List<Integer> slabComp = new ArrayList<>(108);
                for (int i = 0; i < 3; i++) {
                    slabComp.addAll(cutComposition);
                }
                result.set(AlloyCopperBlockItem.SLAB_COMPOSITION.get(), slabComp);
            }
        }

        // 嵌套单元（命名继承）：36 复制为 216（楼梯）/ 108（台阶）
        List<String> nested = AlloyCopperBlockItem.getNestedUnits(in, 36);
        if (nested != null) {
            List<String> outNested = new ArrayList<>();
            for (int i = 0; i < (isStairs ? 6 : 3); i++) {
                outNested.addAll(nested);
            }
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), outNested);
        }
        return result;
    }

    public ItemStack getResultItem(RegistryAccess registryAccess) {
        if (isStairs) {
            return new ItemStack(MoreCopperBlock.CUT_ALLOY_COPPER_STAIRS.get(), 2);
        } else {
            return new ItemStack(MoreCopperBlock.CUT_ALLOY_COPPER_SLAB.get(), 2);
        }
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.createWithCapacity(1);
        list.add(cutAlloyIngredient());
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<CutAlloyStonecutterRecipe> getSerializer() {
        return isStairs
                ? ModRecipeSerializers.CUT_ALLOY_STAIRS_STONECUTTING.get()
                : ModRecipeSerializers.CUT_ALLOY_SLAB_STONECUTTING.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<CutAlloyStonecutterRecipe> getType() {
        return (RecipeType<CutAlloyStonecutterRecipe>) (Object) RecipeType.STONECUTTING;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    private static Block getStairsVariant(Block cutBlock) {
        if (cutBlock == MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.CUT_ALLOY_COPPER_STAIRS.get();
        if (cutBlock == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_STAIRS.get();
        if (cutBlock == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_STAIRS.get();
        return MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_STAIRS.get();
    }

    private static Block getSlabVariant(Block cutBlock) {
        if (cutBlock == MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.CUT_ALLOY_COPPER_SLAB.get();
        if (cutBlock == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB.get();
        if (cutBlock == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get())
            return MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB.get();
        return MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB.get();
    }
}
