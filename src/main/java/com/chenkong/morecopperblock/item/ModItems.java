package com.chenkong.morecopperblock.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    // 斑驳的铜锭
    public static DeferredItem<Item> EXPOSED_COPPER_INGOT;
    // 锈蚀的铜锭
    public static DeferredItem<Item> WEATHERED_COPPER_INGOT;
    // 氧化的铜锭
    public static DeferredItem<Item> OXIDIZED_COPPER_INGOT;
    // 涂蜡的铜锭
    public static DeferredItem<Item> WAXED_COPPER_INGOT;
    // 涂蜡的斑驳铜锭
    public static DeferredItem<Item> WAXED_EXPOSED_COPPER_INGOT;
    // 涂蜡的锈蚀铜锭
    public static DeferredItem<Item> WAXED_WEATHERED_COPPER_INGOT;
    // 涂蜡的氧化铜锭
    public static DeferredItem<Item> WAXED_OXIDIZED_COPPER_INGOT;

    // 斑驳的铜粒
    public static DeferredItem<Item> EXPOSED_COPPER_NUGGET;
    // 锈蚀的铜粒
    public static DeferredItem<Item> WEATHERED_COPPER_NUGGET;
    // 氧化的铜粒
    public static DeferredItem<Item> OXIDIZED_COPPER_NUGGET;
    // 涂蜡的铜粒
    public static DeferredItem<Item> WAXED_COPPER_NUGGET;
    // 涂蜡的斑驳铜粒
    public static DeferredItem<Item> WAXED_EXPOSED_COPPER_NUGGET;
    // 涂蜡的锈蚀铜粒
    public static DeferredItem<Item> WAXED_WEATHERED_COPPER_NUGGET;
    // 涂蜡的氧化铜粒
    public static DeferredItem<Item> WAXED_OXIDIZED_COPPER_NUGGET;

    public static void register(DeferredRegister.Items items) {
        EXPOSED_COPPER_INGOT = items.registerSimpleItem("exposed_copper_ingot");
        WEATHERED_COPPER_INGOT = items.registerSimpleItem("weathered_copper_ingot");
        OXIDIZED_COPPER_INGOT = items.registerSimpleItem("oxidized_copper_ingot");
        WAXED_COPPER_INGOT = items.registerSimpleItem("waxed_copper_ingot");
        WAXED_EXPOSED_COPPER_INGOT = items.registerSimpleItem("waxed_exposed_copper_ingot");
        WAXED_WEATHERED_COPPER_INGOT = items.registerSimpleItem("waxed_weathered_copper_ingot");
        WAXED_OXIDIZED_COPPER_INGOT = items.registerSimpleItem("waxed_oxidized_copper_ingot");

        EXPOSED_COPPER_NUGGET = items.registerSimpleItem("exposed_copper_nugget");
        WEATHERED_COPPER_NUGGET = items.registerSimpleItem("weathered_copper_nugget");
        OXIDIZED_COPPER_NUGGET = items.registerSimpleItem("oxidized_copper_nugget");
        WAXED_COPPER_NUGGET = items.registerSimpleItem("waxed_copper_nugget");
        WAXED_EXPOSED_COPPER_NUGGET = items.registerSimpleItem("waxed_exposed_copper_nugget");
        WAXED_WEATHERED_COPPER_NUGGET = items.registerSimpleItem("waxed_weathered_copper_nugget");
        WAXED_OXIDIZED_COPPER_NUGGET = items.registerSimpleItem("waxed_oxidized_copper_nugget");
    }
}
