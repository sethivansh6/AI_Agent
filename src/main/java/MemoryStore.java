package com.vansh;
// memory handling
import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class MemoryStore {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Path FILE = Paths.get("src/main/resources/memory.json");
    
    public static List<Map<String, String>> load() throws Exception {
        if (!Files.exists(FILE)) {
            return new ArrayList<>();
        }
        return mapper.readValue(
            FILE.toFile(),
            mapper.getTypeFactory()
            .constructCollectionType(List.class, Map.class)
        );
    }

    public static void save(List<Map<String, String>> memory) throws Exception {
        mapper.writerWithDefaultPrettyPrinter()
        .writeValue(FILE.toFile(), memory);
    }

}