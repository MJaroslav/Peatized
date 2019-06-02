package mjaroslav.mcmods.peatized.common.item;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemRegistry {
    static final List<ItemBase> ITEMS = new ArrayList<>();

    public static Item PEAT_BALL = new ItemPeatBall();

    public static void forEach(Consumer<ItemBase> action) {
        ITEMS.forEach(action);
    }
}
