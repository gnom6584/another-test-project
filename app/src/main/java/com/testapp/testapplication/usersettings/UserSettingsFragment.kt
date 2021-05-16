package com.testapp.testapplication.usersettings

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.testapp.testapplication.R
import com.testapp.testapplication.ToastAdapter
import com.testapp.testapplication.databinding.BottomDialogLayoutBinding
import com.testapp.testapplication.databinding.FragmentUserSettingsScreenBinding
import com.testapp.testapplication.utility.MarshmallowPermission
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class UserSettingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by lazy {
        ViewModelProvider(this@UserSettingsFragment, viewModelFactory).get(UserSettingsViewModel::class.java)
    }

    lateinit var binding: FragmentUserSettingsScreenBinding

    lateinit var takePicture: ActivityResultLauncher<Uri>

    lateinit var takeFromGallery: ActivityResultLauncher<Int>

    lateinit var dialog : BottomSheetDialog

    lateinit var marshmallowPermission: MarshmallowPermission

    fun takePhotoFromCamera() {
        if(marshmallowPermission.checkPermissionForCamera())
            takePicture.launch(viewModel.cameraPhotoUri)
        else
            binding.toastLayout.showToast("No permission for camera", 5000)
        dialog.dismiss()
    }

    fun takePhotoFromGallery() {
        takeFromGallery.launch(RESULT_OK)
        dialog.dismiss()
    }

    fun openSelectPictureDialog() {

        with(marshmallowPermission) {
            requestPermissionForCamera()
            requestPermissionForExternalStorage(100)
            requestPermissionForExternalStorage(200)
        }

        dialog = BottomSheetDialog(requireActivity())
        val view =
            DataBindingUtil
                .inflate<BottomDialogLayoutBinding>(layoutInflater, R.layout.bottom_dialog_layout, null, false).apply {
                    bottomSheetDialog = dialog
                    fragment = this@UserSettingsFragment
                }

        dialog.setContentView(view.root)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success)
                viewModel.uploadAvatarByCamera()
        }

        takeFromGallery = registerForActivityResult(object :
            ActivityResultContract<Int, Uri?>() {

            override fun createIntent(context: Context, input: Int?): Intent {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                return intent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return if(resultCode == RESULT_OK)
                     intent?.data
                else
                    null
            }
        }) {
            it?.let { galleryPhotoUri ->
                viewModel.uploadAvatarByGallery(galleryPhotoUri)
            }
        }

        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        marshmallowPermission =
            MarshmallowPermission(
                requireActivity()
            )
        viewModel.requestPhotoFileInitialization(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentUserSettingsScreenBinding>(layoutInflater, R.layout.fragment_user_settings_screen, container, false).apply {

        this.viewModel = this@UserSettingsFragment.viewModel

        fragment = this@UserSettingsFragment
        binding = this
        adapter = ToastAdapter(requireContext())
        photo.setImageURI(this@UserSettingsFragment.viewModel.cameraPhotoUri)

    }.root

    companion object {
        fun newInstance() = UserSettingsFragment()
    }
}