package com.thepyprogrammer.greenpass.model.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseUtil {
    private var FIRESTORE: FirebaseFirestore? = null
    private var uid = ""

    // Connect to the Cloud Firestore
    val firestore: FirebaseFirestore?
        get() {
            if (FIRESTORE == null) FIRESTORE = Firebase.firestore
            return FIRESTORE
        }
}