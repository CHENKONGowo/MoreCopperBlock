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

public class ChiseledAlloyCopperRecipe extends CustomRecipe {

    public ChiseledAlloyCopperRecipe() {
        super();
    }

    /**
     * Chiseled pattern: exactly 2 cut copper slabs stacked vertically (1×2).
     * Accepts both alloy and vanilla cut copper slabs.
     * If both are the same vanilla block, defers to the vanilla recipe.
     * Outputs 1 chiseled alloy copper block with averaged oxidation.
     */
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 1 || input.height() != 2) return false;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allSameVanilla = true;

        for (int row = 0; row < 2; row++) {
            ItemStack stack = input.getItem(0, row);
            if (stack.isEmpty()) return false;
            if (!isCutCopperSlab(stack)) return false;

            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (isAlloyCutSlab(block)) {
                hasAlloy = true;
            } else {
                if (firstVanilla == null) firstVanilla = block;
                else if (block != firstVanilla) allSameVanilla = false;
            }
        }

        // If both are the same vanilla slab, let the vanilla recipe handle it
        if (!hasAlloy && allSameVanilla) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> chiseledComposition = new ArrayList<>(216);
        List<String> nestedUnits = new ArrayList<>(216);
        boolean hasNested = false;
        double sum = 0;

        for (int row = 0; row < 2; row++) {
            ItemStack stack = input.getItem(0, row);
            Block block = ((BlockItem) stack.getItem()).getBlock();

            if (isAlloyCutSlab(block)) {
                List<Integer> slabComp = stack.get(AlloyCopperBlockItem.SLAB_COMPOSITION.get());
                if (slabComp != null && slabComp.size() == 108) {
                    chiseledComposition.addAll(slabComp);
                    for (int v : slabComp) sum += v % 4;
                } else {
                    for (int i = 0; i < 108; i++) chiseledComposition.add(0);
                }
            } else {
                int type = getVanillaCutSlabType(block);
                for (int i = 0; i < 108; i++) {
                    chiseledComposition.add(type);
                    sum += type % 4;
                }
            }

            // 嵌套单元（命名继承）
            List<String> units = getSlabNestedUnits(stack);
            nestedUnits.addAll(units);
            if (hasAnyNested(units)) hasNested = true;
        }

        double avg = sum / 216.0;
        Block outBlock = getChiseledVariantByAvg(avg);

        ItemStack result = new ItemStack(outBlock, 1);
        result.set(AlloyCopperBlockItem.CHISELED_COMPOSITION.get(), chiseledComposition);
        if (hasNested) {
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), nestedUnits);
        }
        return result;
    }

    /**
     * Gets the 108 nested unit strings of an input cut copper slab.
     * Prefers stored nested units, then the simple composition, then block type.
     */
    private static List<String> getSlabNestedUnits(ItemStack stack) {
        List<String> nested = stack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (nested != null && nested.size() == 108) return nested;

        List<Integer> slabComp = stack.get(AlloyCopperBlockItem.SLAB_COMPOSITION.get());
        if (slabComp != null && slabComp.size() == 108) {
            List<String> result = new ArrayList<>(108);
            for (int v : slabComp) result.add(String.valueOf(v));
            return result;
        }

        // Vanilla cut copper slab
        int type = getVanillaCutSlabType(((BlockItem) stack.getItem()).getBlock());
        List<String> result = new ArrayList<>(108);
        for (int i = 0; i < 108; i++) result.add(String.valueOf(type));
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
        return ModRecipeSerializers.CHISELED_ALLOY_COPPER_RECIPE.get();
    }

    /** Accepts both alloy and vanilla cut copper slabs. */
    private static boolean isCutCopperSlab(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return isAlloyCutSlab(block) || isVanillaCutSlab(block);
    }

    private static boolean isAlloyCutSlab(Block block) {
        return block == MoreCopperBlock.CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB.get();
    }

    private static boolean isVanillaCutSlab(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER_SLAB;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    private static int getVanillaCutSlabType(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER_SLAB;
        if (block == c.weathering().unaffected()) return 0;
        if (block == c.weathering().exposed()) return 1;
        if (block == c.weathering().weathered()) return 2;
        if (block == c.weathering().oxidized()) return 3;
        if (block == c.waxed().unaffected()) return 4;
        if (block == c.waxed().exposed()) return 5;
        if (block == c.waxed().weathered()) return 6;
        return 7; // waxed oxidized
    }

    private static Block getChiseledVariantByAvg(double avg) {
        if (avg < 0.5) return MoreCopperBlock.CHISELED_ALLOY_COPPER_BLOCK.get();
        if (avg < 1.5) return MoreCopperBlock.EXPOSED_CHISELED_ALLOY_COPPER_BLOCK.get();
        if (avg < 2.5) return MoreCopperBlock.WEATHERED_CHISELED_ALLOY_COPPER_BLOCK.get();
        return MoreCopperBlock.OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK.get();
    }
}
