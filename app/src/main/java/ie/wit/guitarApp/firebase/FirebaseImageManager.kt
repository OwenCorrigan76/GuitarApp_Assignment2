package ie.wit.guitarApp.firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.io.ByteArrayOutputStream
import com.squareup.picasso.Target
import ie.wit.guitarApp.utils.customTransformation

object FirebaseImageManager {

    var storage = FirebaseStorage.getInstance().reference
    var imageUri = MutableLiveData<Uri>()
    var imageUri2 = MutableLiveData<Uri>()

    /** check if there is a profilepic already */
    fun checkStorageForExistingProfilePic(userid: String) {
        val imageRef = storage.child("photos").child("${userid}.jpg")
        val defaultImageRef = storage.child("homer.jpg")

        /** If the image exists */
        imageRef.metadata.addOnSuccessListener {
          /** download the image */
            imageRef.downloadUrl.addOnCompleteListener { task ->
                imageUri.value = task.result!!
            }
            /** If image doesn't exist, set to empty URI */
        }.addOnFailureListener {
            imageUri.value = Uri.EMPTY
        }
    }

  /*  fun checkStorageForExistingGuitarPic(userid: String) {
        val imageRef = storage.child("photos2").child("${userid}.jpg")
     //   val defaultImageRef = storage.child("homer.jpg")

        imageRef.metadata.addOnSuccessListener { //File Exists
            imageRef.downloadUrl.addOnCompleteListener { task ->
                imageUri.value = task.result!!
            }
            //File Doesn't Exist
        }.addOnFailureListener {
            imageUri.value = Uri.EMPTY
        }
    }*/



    fun uploadImageToFirebase(userid: String, bitmap: Bitmap, updating : Boolean) {
        val imageRef = storage.child("photos").child("${userid}.jpg")
        val baos = ByteArrayOutputStream()
        lateinit var uploadTask: UploadTask

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        imageRef.metadata.addOnSuccessListener { //File Exists
            if(updating)
                /** For updating existing Image */
            {
                uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { ut ->
                    ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                        imageUri.value = task.result!!
                    }
                }
            }
        }.addOnFailureListener { //File Doesn't Exist
            uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener { ut ->
                ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                    imageUri.value = task.result!!
                }
            }
        }
    }

    fun uploadGuitarImageToFirebase(userid: String, bitmap: Bitmap, updating : Boolean) {
        // Get the data from an ImageView as bytes
        val imageRef = storage.child("photos2").child("${userid}.jpg")
        //val bitmap = (imageView as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        lateinit var uploadTask: UploadTask

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        imageRef.metadata.addOnSuccessListener { //File Exists
            if(updating) // Update existing Image
            {
                uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { ut ->
                    ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                        imageUri.value = task.result!!
                        FirebaseDBManager.updateImageRef(userid,imageUri.value.toString())

                    }
                }
            }
        }.addOnFailureListener { //File Doesn't Exist
            uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener { ut ->
                ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                    imageUri.value = task.result!!
                }
            }
        }
    }


    fun updateUserImage(userid: String, imageUri : Uri?, imageView: ImageView, updating : Boolean) {
        Picasso.get().load(imageUri)
            .resize(200, 200)
            .transform(customTransformation())
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerCrop()
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("Guitar App onBitmapLoaded $bitmap")
                    uploadImageToFirebase(userid, bitmap!!,updating)
                    imageView.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("Guitar App onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }
    fun updateGuitarImage(userid: String, imageUri2 : Uri?, imageView2: ImageView, updating : Boolean) {
        Picasso.get().load(imageUri2)
            .resize(200, 200)
            .transform(customTransformation())
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerCrop()
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("Guitar App onBitmapLoaded $bitmap")
                    /** Don't update as it will change profile pic too */
                 /*   uploadGuitarImageToFirebase(userid, bitmap!!,updating)
                    imageView2.setImageBitmap(bitmap)*/
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("Guitar App onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }


    fun updateDefaultImage(userid: String, resource: Int, imageView: ImageView) {
        Picasso.get().load(resource)
            .resize(200, 200)
            .transform(customTransformation())
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerCrop()
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("Guitar App onBitmapLoaded $bitmap")
                    uploadImageToFirebase(userid, bitmap!!,false)
                    imageView.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("Guitar App onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }
}