package com.thepyprogrammer.greenpass.model.firebase

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thepyprogrammer.greenpass.BuildConfig


/**
 * Utility class for initializing Firebase services and connecting them to the Firebase Emulator
 * Suite if necessary.
 */
object FirebaseUtil {
    /** Use emulators only in debug builds  */
    private val sUseEmulators: Boolean = BuildConfig.DEBUG
    private var FIRESTORE: FirebaseFirestore? = null
    private var AUTH: FirebaseAuth? = null
    private var AUTH_UI: AuthUI? = null

    // Connect to the Cloud Firestore
    val firestore: FirebaseFirestore?
        get() {
            if (FIRESTORE == null) FIRESTORE = FirebaseFirestore.getInstance()
            return FIRESTORE
        }

    // Connect to the Firebase Auth
    val auth: FirebaseAuth?
        get() {
            if (AUTH == null) AUTH = FirebaseAuth.getInstance()
            return AUTH
        }

    // Connect to the Firebase Auth
    val authUI: AuthUI?
        get() {
            if (AUTH_UI == null) AUTH_UI = AuthUI.getInstance()
            return AUTH_UI
        }
}