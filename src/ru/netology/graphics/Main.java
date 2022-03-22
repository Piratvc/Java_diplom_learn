package ru.netology.graphics;

import ru.netology.graphics.image.*;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Convert();

        GServer server = new GServer(converter);
        server.start();


    }
}
