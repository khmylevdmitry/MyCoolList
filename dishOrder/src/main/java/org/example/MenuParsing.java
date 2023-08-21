package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;

public class MenuParsing {

    public Menu parse() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(Paths.get("menu.json").toFile(), Menu.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

