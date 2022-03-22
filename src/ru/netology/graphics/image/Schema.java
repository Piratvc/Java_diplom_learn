package ru.netology.graphics.image;
public class Schema implements TextColorSchema {
    private final char[] symbols = {'#', '$', '@', '%', '*', '+', '-', '.'};
    @Override
    public char convert(int color) {
        return symbols[(int) Math.floor(color / 256. * symbols.length)];
    }
}


