package com.vansh;

import java.util.*;
/**
1. Load memory from file
2. Add new user message
3. Send memory + message to LLM
4. Get response
5. Save everything back to memory
6. Repeat
*/
public class Agent {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Java AI Agent (type 'exit' to quit)\n");

        while(true) {
            System.out.print("You: ");
            String in = sc.nextLine();
            if ("exit".equalsIgnoreCase(in)) {
                break;
            }
            //1. Load memory
            List<Map<String, String>> memory = MemoryStore.load();
            System.out.println("Memory so far:");

            for (Map<String, String> message : memory) {
                String role = message.get("role");
                String content = message.get("content");
                System.out.println(role + ": " + content);
            }
            //2. Build prompt
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of(
                "role", "system",
                "content", "You are a helpful assistant."
            ));
            messages.addAll(memory);
            messages.add(Map.of(
                "role", "user",
                "content", in
            ));
            
            //3. Call LLM
            String reply = LLMClient.chat(messages);
            //4. Output
            System.out.println("Agent: " + reply);

            //5. Save memory
            memory.add(Map.of("role", "user", "content", in));
            memory.add(Map.of("role", "assistant", "content", reply));
            MemoryStore.save(memory);
        }
    }
}