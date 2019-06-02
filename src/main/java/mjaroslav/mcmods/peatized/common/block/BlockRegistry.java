package mjaroslav.mcmods.peatized.common.block;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class BlockRegistry {
    static final List<BlockBase> BLOCKS = new ArrayList<>();

    public static Block BOG_DIRT = new BlockBogDirt();

    public static void forEach(Consumer<BlockBase> action) {
        BLOCKS.forEach(action);
    }
}
