package com.vansh;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;

public class FirestoreConfig {
    private static Firestore firestore;

    public static void initFirestore() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./firebase_key_memory_store.json");
            firestore = FirestoreOptions.newBuilder()
                .setProjectId("ai-agent-f4638")
                .setCredentials(
                    com.google.auth.oauth2.GoogleCredentials.fromStream(serviceAccount)
                )
                .build()
                .getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Firestore getFirestore() {
        if (firestore == null) initFirestore();
        return firestore;
    }
}