package com.thepyprogrammer.greenpass.ui.image

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.main.MainActivity
import com.thepyprogrammer.greenpass.ui.image.ImagePickerActivity.Companion.showImagePickerOptions
import com.thepyprogrammer.greenpass.ui.image.ImagePickerActivity.PickerOptionListener
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*


class ImageDetailsActivity : AppCompatActivity() {
    var REQUEST_IMAGE = 2169
    var CAMERA_PERMISSION_CODE = 6969
    var READ_EXTERNAL_STORAGE_PERMISSION_CODE = 4206
    var WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 4209
    var INTERNET_PERMISSION_CODE = 666
    var imageButton: ImageButton? = null
    var imageInfoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        imageButton = findViewById(R.id.imageDetailsImageView)
        imageInfoFile = File(filesDir, "profileImageURI.txt")
    }

    override fun onStart() {
        super.onStart()
        loadImage()
    }

    // my button click function
    fun onProfileImageClick(view: View) {
        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        showImagePickerOptions()
                    } else {
                        // TODO - handle permission denied case
                        checkPermission(
                            Manifest.permission.CAMERA,
                            CAMERA_PERMISSION_CODE
                        )
                        checkPermission(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            READ_EXTERNAL_STORAGE_PERMISSION_CODE
                        )
                        checkPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE
                        )
                        checkPermission(
                            Manifest.permission.INTERNET,
                            INTERNET_PERMISSION_CODE
                        )
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    fun checkPermission(permission: String, requestCode: Int) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this, arrayOf(permission),
                    requestCode
                )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        // Checking whether user granted the permission or not.
        if (grantResults.size > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            Toast.makeText(
                this,
                "To select an icon, these permissions are required.",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun showImagePickerOptions() {
        showImagePickerOptions(this, object : PickerOptionListener {
            override fun onTakeCameraSelected() {
                launchCameraIntent()
            }

            override fun onChooseGallerySelected() {
                launchGalleryIntent()
            }
        })
    }

    private fun launchCameraIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(
                ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
                ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun launchGalleryIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(
                ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
                ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                val uri: Uri? = data?.getParcelableExtra("path")
                try {
                    // You can update this bitmap to your server
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)


                    //save uri to internal storage
                    writeData(uri.toString())

                    // loading profile image from local cache
                    loadImage()

                    //todo fix this one loadProfile(uri.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun writeData(s: String) {
        val output: PrintWriter = PrintWriter(imageInfoFile)
        output.println(s)
        output.close()
        println(s)
    }

    fun readData(): String {
        if (!imageInfoFile!!.exists()) {
            return ""
        }
        val scanner = Scanner(imageInfoFile)
        val string = StringBuilder(scanner.nextLine())

        while (scanner.hasNextLine())
            string.append("\n" + scanner.nextLine())


        scanner.close()
        return string.toString()
    }

    fun loadImage() {
        var string: String = readData()
        if (!string.isEmpty()) {
            imageButton!!.setImageURI(Uri.parse(readData()))
        } else {
            imageButton!!.setImageResource(R.drawable.edden_face)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                val toast = Toast.makeText(
                    applicationContext,
                    "Moving back to Main Page",
                    Toast.LENGTH_LONG
                )
                toast.show()
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}