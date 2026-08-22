package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

public class CopperNuggetDecomposeRecipe extends CustomRecipe {

    public CopperNuggetDecomposeRecipe() {
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
        return nonEmpty == 1 && stack != null && isCopperIngot(stack);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        // 找到非空槽位的铜锭（matches已保证恰好1个）
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
        Item nuggetItem = getCorrespondingNugget(inputStack);

        // 检查铜锭是否有composition数据
        List<String> composition = inputStack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());

        // 如果有composition数据，保留到铜粒
        if (composition != null && !AlloyCopperBlockItem.isDefaultIngotComposition(composition)) {
            ItemStack result = new ItemStack(nuggetItem, 9);
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), composition);
            return result;
        }

        // 普通铜锭，输出普通铜粒
        return new ItemStack(nuggetItem, 9);
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.COPPER_NUGGET_DECOMPOSE_RECIPE.get();
    }

    private boolean isCopperIngot(ItemStack stack) {
        return stack.is(Items.COPPER_INGOT)
                || stack.is(ModItems.EXPOSED_COPPER_INGOT.get())
                || stack.is(ModItems.WEATHERED_COPPER_INGOT.get())
                || stack.is(ModItems.OXIDIZED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_EXPOSED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_WEATHERED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_OXIDIZED_COPPER_INGOT.get());
    }

    private Item getCorrespondingNugget(ItemStack ingot) {
        if (ingot.is(Items.COPPER_INGOT)) return Items.COPPER_NUGGET;
        if (ingot.is(ModItems.EXPOSED_COPPER_INGOT.get())) return ModItems.EXPOSED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.WEATHERED_COPPER_INGOT.get())) return ModItems.WEATHERED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.OXIDIZED_COPPER_INGOT.get())) return ModItems.OXIDIZED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.WAXED_COPPER_INGOT.get())) return ModItems.WAXED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.WAXED_EXPOSED_COPPER_INGOT.get())) return ModItems.WAXED_EXPOSED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.WAXED_WEATHERED_COPPER_INGOT.get())) return ModItems.WAXED_WEATHERED_COPPER_NUGGET.get();
        if (ingot.is(ModItems.WAXED_OXIDIZED_COPPER_INGOT.get())) return ModItems.WAXED_OXIDIZED_COPPER_NUGGET.get();
        return Items.COPPER_NUGGET;
    }
}
