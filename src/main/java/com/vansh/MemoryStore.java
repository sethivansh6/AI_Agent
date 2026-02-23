package com.vansh;
// memory handling
import java.util.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

public class MemoryStore {
    private static final String COLLECTION = "memory";

    static {
        FirestoreConfig.initFirestore();
    }
    
    public static List<Map<String, String>> load() {
        List<Map<String, String>> memory = new ArrayList<>();
        Firestore firestore = FirestoreConfig.getFirestore();

        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                memory.add(Map.of(
                    "role", doc.getString("role"),
                    "content", doc.getString("content")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return memory;
    }

    public static void save(List<Map<String, String>> memory) throws Exception {
        Firestore firestore = FirestoreConfig.getFirestore();

        for (Map<String, String> msg : memory) {
            try {
                firestore.collection(COLLECTION).add(msg).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}