package com.thepyprogrammer.greenpass.model.firebase

import com.google.firebase.firestore.FirebaseFirestore


object FirebaseUtil {
    private var FIRESTORE: FirebaseFirestore? = null

    val firestore: FirebaseFirestore?
        get() {
            if (FIRESTORE == null) FIRESTORE = FirebaseFirestore.getInstance()

            return FIRESTORE
        }

}