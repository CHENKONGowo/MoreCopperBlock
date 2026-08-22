package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Decomposes 1 alloy copper block into 9 copper ingots, mirroring the
 * ingot→nugget decomposition. The ingots inherit the block's naming
 * (nested composition units are preserved).
 */
public class CopperBlockDecomposeRecipe extends CustomRecipe {

    public CopperBlockDecomposeRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        // 工作台始终是3x3，CraftingInput.size()返回9（含空槽），
        // 因此用非空槽数量判断1x1输入。
        ItemStack stack = null;
        int nonEmpty = 0;
        for (int row = 0; row < input.height(); row++) {
            for (int col = 0; col < input.width(); col++) {
                ItemStack s = input.getItem(col, row);
                if (!s.isEmpty()) {
                    nonEmpty++;
                    stack = s;
                }
            }
        }
        return nonEmpty == 1 && stack != null && isAlloyCopperBlock(stack);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        // 找到非空槽位的合金铜块（matches已保证恰好1个）
        ItemStack inputStack = ItemStack.EMPTY;
        for (int row = 0; row < input.height(); row++) {
            for (int col = 0; col < input.width(); col++) {
                ItemStack s = input.getItem(col, row);
                if (!s.isEmpty()) {
                    inputStack = s;
                    break;
                }
            }
            if (!inputStack.isEmpty()) break;
        }
        Item ingotItem = getCorrespondingIngot(inputStack);

        ItemStack result = new ItemStack(ingotItem, 9);

        // 铜锭继承铜块的命名单元（嵌套composition或简单composition）
        List<String> units = getBlockUnits(inputStack);
        if (!AlloyCopperBlockItem.isAllZeroComposition(units)) {
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), units);
        }
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.COPPER_BLOCK_DECOMPOSE_RECIPE.get();
    }

    private boolean isAlloyCopperBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return block == MoreCopperBlock.ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get();
    }

    private Item getCorrespondingIngot(ItemStack stack) {
        Block block = ((BlockItem) stack.getItem()).getBlock();
        if (block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get())
            return ModItems.EXPOSED_COPPER_INGOT.get();
        if (block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get())
            return ModItems.WEATHERED_COPPER_INGOT.get();
        if (block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get())
            return ModItems.OXIDIZED_COPPER_INGOT.get();
        return Items.COPPER_INGOT;
    }

    /**
     * Gets the 9 composition unit strings of an alloy copper block:
     * prefers the nested units (from named ingots), then the simple
     * composition, and finally falls back to all zeros.
     */
    private static List<String> getBlockUnits(ItemStack stack) {
        List<String> nested = stack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (nested != null && nested.size() == 9) return nested;

        List<Integer> comp = stack.get(AlloyCopperBlockItem.COMPOSITION.get());
        if (comp != null && comp.size() == 9) {
            List<String> result = new ArrayList<>(9);
            for (int v : comp) result.add(String.valueOf(v));
            return result;
        }

        List<String> result = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) result.add("0");
        return result;
    }
}
