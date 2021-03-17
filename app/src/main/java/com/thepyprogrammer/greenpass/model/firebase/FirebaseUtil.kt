package com.thepyprogrammer.greenpass.model.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser

object FirebaseUtil {
    private var FIRESTORE: FirebaseFirestore? = null
    private var STORAGE: FirebaseStorage? = null

    var user: VaccinatedUser? = null;

    // Connect to the Cloud Firestore
    val firestore: FirebaseFirestore?
        get() {
            if (FIRESTORE == null) FIRESTORE = Firebase.firestore
            return FIRESTORE
        }

    val storage: FirebaseStorage?
        get() {
            if (STORAGE == null) STORAGE = Firebase.storage
            return STORAGE
        }

    fun userCollection() = firestore?.collection("users")
}