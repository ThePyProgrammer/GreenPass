package com.thepyprogrammer.greenpass.model.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser
import android.net.Uri

object FirebaseUtil {
    private var FIRESTORE: FirebaseFirestore? = null
    private var STORAGE: FirebaseStorage? = null

    var user: VaccinatedUser? = null;

    // Connect to the Cloud Firestore
    val firestore: FirebaseFirestore
        get() {
            if (FIRESTORE == null) FIRESTORE = Firebase.firestore
            return FIRESTORE!!
        }

    val storage: FirebaseStorage
        get() {
            if (STORAGE == null) STORAGE = Firebase.storage
            return STORAGE!!
        }

    fun uploadImage(photoUri: Uri) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${user?.nric}.jpg")

        val uploadTask = imageRef.putFile(photoUri)
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            // nothing to be implemented
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }


    }


    fun userCollection() = firestore.collection("users")
}