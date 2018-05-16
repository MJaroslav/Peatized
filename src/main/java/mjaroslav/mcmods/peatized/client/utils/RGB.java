package mjaroslav.mcmods.peatized.client.utils;

public enum RGB {
    A(24), R(16), G(8), B(0);
    final int offset;

    RGB(int off) {
        offset = off;
    }

    public int i(int color) {
        return color >> offset & 0xFF;
    }

    public float f(int color) {
        return i(color) / 255F;
    }
}