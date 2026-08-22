package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperCollection;

import java.util.ArrayList;
import java.util.List;

public class CutAlloySlabRecipe extends CustomRecipe {

    public CutAlloySlabRecipe() {
        super();
    }

    /**
     * Slab pattern: exactly 3 cut copper blocks in a single row.
     * Accepts both alloy cut copper blocks and vanilla cut copper blocks.
     * If all 3 are the same vanilla block, defers to the vanilla recipe.
     * Outputs 6 alloy slabs with averaged oxidation.
     */
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 3 || input.height() != 1) return false;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allSameVanilla = true;

        for (int col = 0; col < 3; col++) {
            ItemStack stack = input.getItem(col, 0);
            if (stack.isEmpty()) return false;
            if (!isCutCopperBlock(stack)) return false;

            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (isAlloyCutBlock(block)) {
                hasAlloy = true;
            } else {
                if (firstVanilla == null) firstVanilla = block;
                else if (block != firstVanilla) allSameVanilla = false;
            }
        }

        // If all 3 are the same vanilla block, let the vanilla recipe handle it
        if (!hasAlloy && allSameVanilla) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> slabComposition = new ArrayList<>(108);
        List<String> nestedUnits = new ArrayList<>(108);
        boolean hasNested = false;
        double sum = 0;

        for (int col = 0; col < 3; col++) {
            ItemStack stack = input.getItem(col, 0);
            Block block = ((BlockItem) stack.getItem()).getBlock();

            if (isAlloyCutBlock(block)) {
                List<Integer> cutComp = stack.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());
                if (cutComp != null && cutComp.size() == 36) {
                    slabComposition.addAll(cutComp);
                    for (int v : cutComp) sum += v % 4;
                } else {
                    for (int i = 0; i < 36; i++) slabComposition.add(0);
                }
            } else {
                int type = getVanillaCutBlockType(block);
                for (int i = 0; i < 36; i++) {
                    slabComposition.add(type);
                    sum += type % 4;
                }
            }

            // 嵌套单元（命名继承）
            List<String> units = getCutBlockNestedUnits(stack);
            nestedUnits.addAll(units);
            if (hasAnyNested(units)) hasNested = true;
        }

        double avg = sum / 108.0;
        Block outBlock = getSlabVariantByAvg(avg);

        ItemStack result = new ItemStack(outBlock, 6);
        result.set(AlloyCopperBlockItem.SLAB_COMPOSITION.get(), slabComposition);
        if (hasNested) {
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), nestedUnits);
        }
        return result;
    }

    /**
     * Gets the 36 nested unit strings of an input cut copper block.
     * Prefers stored nested units, then the simple composition, then block type.
     */
    private static List<String> getCutBlockNestedUnits(ItemStack stack) {
        List<String> nested = stack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (nested != null && nested.size() == 36) return nested;

        List<Integer> cutComp = stack.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());
        if (cutComp != null && cutComp.size() == 36) {
            List<String> result = new ArrayList<>(36);
            for (int v : cutComp) result.add(String.valueOf(v));
            return result;
        }

        // Vanilla cut copper block
        int type = getVanillaCutBlockType(((BlockItem) stack.getItem()).getBlock());
        List<String> result = new ArrayList<>(36);
        for (int i = 0; i < 36; i++) result.add(String.valueOf(type));
        return result;
    }

    /** True if any unit is a nested bracket notation (named ingot). */
    private static boolean hasAnyNested(List<String> units) {
        for (String s : units) {
            if (s.startsWith("[")) return true;
        }
        return false;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.CUT_ALLOY_SLAB_RECIPE.get();
    }

    /** Accepts both alloy and vanilla cut copper blocks. */
    private static boolean isCutCopperBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return isAlloyCutBlock(block) || isVanillaCutBlock(block);
    }

    private static boolean isAlloyCutBlock(Block block) {
        return block == MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get();
    }

    private static boolean isVanillaCutBlock(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    private static int getVanillaCutBlockType(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER;
        if (block == c.weathering().unaffected()) return 0;
        if (block == c.weathering().exposed()) return 1;
        if (block == c.weathering().weathered()) return 2;
        if (block == c.weathering().oxidized()) return 3;
        if (block == c.waxed().unaffected()) return 4;
        if (block == c.waxed().exposed()) return 5;
        if (block == c.waxed().weathered()) return 6;
        return 7;
    }

    private static Block getSlabVariantByAvg(double avg) {
        if (avg < 0.5) return MoreCopperBlock.CUT_ALLOY_COPPER_SLAB.get();
        if (avg < 1.5) return MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB.get();
        if (avg < 2.5) return MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB.get();
        return MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB.get();
    }
}
