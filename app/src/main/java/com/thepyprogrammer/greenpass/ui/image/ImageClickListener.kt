package com.thepyprogrammer.greenpass.ui.image

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment

class ImageClickListener(val context: Context?) : View.OnClickListener {
    constructor(fragment: Fragment) : this(fragment.context)

    override fun onClick(v: View?) {
        val intent = Intent(context, ImageDetailsActivity::class.java)
        context?.startActivity(intent)
    }
}