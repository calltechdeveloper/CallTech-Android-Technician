package com.calltechservice.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.loader.content.CursorLoader;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentMyAccountBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.db.SharedPref;
import com.calltechservice.model.response.UserModel;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.utils.AppConstant;
import com.calltechservice.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.ConnectException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MyAccountFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMyAccountBinding binding;
    private MenuItem menuEdit;
    private MenuItem menuSave;
    private String imageName = "";
    private File output = null;
    private  MultipartBody.Part imageFile=null;
    private final int PICK_IMAGE_CAMERA = 1;
    private final int PICK_IMAGE_GALLERY = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((HomeActivity) requireActivity()).changeIcon(true);
        lickListener();
        getProfile();
        return view;
    }

    private void setData() {

        if (SharedPref.getPreferencesString(requireActivity(), AppConstant.NAME) != null) {
            binding.etName.getEditText().setText(SharedPref.getPreferencesString(requireActivity(), AppConstant.NAME));
        }
        if (SharedPref.getPreferencesString(requireActivity(), AppConstant.EMAIL) != null) {
            binding.etEmail.getEditText().setText(SharedPref.getPreferencesString(requireActivity(), AppConstant.EMAIL));
        }
        if (SharedPref.getPreferencesString(requireActivity(), AppConstant.MOBILE_NO) != null) {
            binding.etPhoneNo.getEditText().setText(SharedPref.getPreferencesString(requireActivity(), AppConstant.MOBILE_NO));
        }

        if (SharedPref.getPreferencesString(requireActivity(), AppConstant.PROFILE_PIC) != null && !SharedPref.getPreferencesString(requireActivity(), AppConstant.PROFILE_PIC).equalsIgnoreCase("")) {
            Glide.with(requireActivity()).load(SharedPref.getPreferencesString(requireActivity(), AppConstant.PROFILE_PIC)).apply(new RequestOptions().placeholder(R.drawable.user).dontAnimate()).into(binding.ivProfile);

        } else {
            binding.ivProfile.setImageResource(R.drawable.user);
        }
    }

    private void setData(UserModel model) {
        binding.etName.getEditText().setText(model.getName());
        binding.etEmail.getEditText().setText(model.getEmail());
        binding.etPhoneNo.getEditText().setText(model.getTelephone());
        binding.etAddress.getEditText().setText(model.getAddress());


        if (model.getImage() != null && !model.getImage().equalsIgnoreCase("")) {

            Glide.with(requireActivity())
                    .load(Uri.parse(model.getImage()))
                    .apply(RequestOptions.placeholderOf(R.drawable.user))
                    .apply(RequestOptions.errorOf(R.drawable.user))
                    .into(binding.ivProfile);

            //Glide.with(requireActivity()).load(Uri.parse(model.getUserImage())).apply(new RequestOptions().placeholder(R.drawable.user).dontAnimate()).into(binding.ivProfile);

        }else {
            binding.ivProfile.setImageResource(R.drawable.user);
        }
    }

    public void lickListener() {
        binding.btSubmit.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.account);
        ((HomeActivity) requireActivity()).changeIcon(true);
        binding.ivProfile.setOnClickListener(this);
        requireActivity().setTitle("My Account");

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile, menu);
        menuEdit = menu.findItem(R.id.action_edit);
        menuSave = menu.findItem(R.id.action_save);
        menuSave.setVisible(false);
        setVisibility(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                setVisibility(true);
                menuEdit.setVisible(false);
                menuSave.setVisible(true);
                return true;
            case R.id.action_save:
                if (validation()) {
                    if (CommonUtils.isOnline(requireActivity())) {
                        updateProfile();
                    } else {
                        Snackbar.make(binding.getRoot(), getString(R.string.internet_connection), Snackbar.LENGTH_SHORT).show();
                    }
                }

                return true;
        }
        return onOptionsItemSelected(item);
    }

    private boolean validation() {
        if (TextUtils.isEmpty(binding.etName.getEditText().getText().toString().trim())) {
            CommonUtils.showSnack(requireActivity().findViewById(android.R.id.content), getContext().getString(R.string.please_enter_name));
            return false;
        } else if (TextUtils.isEmpty(binding.etEmail.getEditText().getText().toString().trim())) {
            CommonUtils.showSnack(requireActivity().findViewById(android.R.id.content), getContext().getString(R.string.please_enter_email));
            return false;
        } else if (CommonUtils.isValidEmail(binding.etEmail.getEditText().getText().toString().trim())) {
            CommonUtils.showSnack(requireActivity().findViewById(android.R.id.content), getContext().getString(R.string.please_enter_valid_email));
            return false;
        } else {
            return true;
        }
    }

    private void setVisibility(boolean value) {
        binding.etEmail.setEnabled(false);
        binding.etName.setEnabled(value);
        binding.etPhoneNo.setEnabled(value);
        binding.ivProfile.setEnabled(value);
        binding.etAddress.setEnabled(value);
        binding.etState.setEnabled(value);
        if (value) {
            requireActivity().setTitle("Edit Account");
        } else {
            requireActivity().setTitle("My Account");
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivProfile:
                selectImage();
                break;
            case R.id.btSubmit:
                updateProfile();
                break;
            default:
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        PackageManager pm = requireActivity().getPackageManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                dialog.dismiss();
                int cameraPermission = pm.checkPermission(Manifest.permission.CAMERA, requireActivity().getPackageName());
                int storagePermission = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, requireActivity().getPackageName());
                if (cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    this.startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            } else if (options[item].equals("Choose From Gallery")) {
                dialog.dismiss();
                int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, requireActivity().getPackageName());
                if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    this.startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            Uri uri = getImageUri(bitmap);
            binding.ivProfile.setImageURI(uri);
            File file= new File(getPath(uri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            imageFile = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
        }

        if (requestCode == PICK_IMAGE_GALLERY && data != null) {
            Uri selectedImage = data.getData();
            try {
                Log.e("selectedImage","<<selectedImage>> " +selectedImage);
                binding.ivProfile.setImageURI(selectedImage);
                File file = new File(getPath(selectedImage));
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                imageFile = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri) {
        String[] data = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), inImage, "profile_pic", null);
        return Uri.parse(path);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else {
                    String cameraPermission = permissions[0];
                    boolean showRationaleCamera = shouldShowRequestPermissionRationale(cameraPermission);
                    if (!showRationaleCamera) {
                        permissionAlert("Required CAMERA permission to open your camera");
                    } else {
                        utils.toaster("Permission denied to open your camera");
                    }
                }
                break;

            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else {
                    String permission = permissions[0];
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        permissionAlert("Required STORAGE permission to access gallery");
                    } else {
                        utils.toaster("Permission denied to read your External storage");

                    }

                }
                break;
            }
        }
    }


    private void permissionAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Need Permission");
        builder.setMessage(message);
        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 1);
        });

        builder.create();
        builder.show();
    }



    private void getProfile() {
        apiService.callServiceProfileAPI(userPref.getUser().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1) {
                        userPref.setUser(commonResponse.getData());

                        setData(commonResponse.getData());
                    } else {
                        utils.simpleAlert(getContext(), "Error", commonResponse.getMessage());
                    }

                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),requireActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),throwable.getMessage());
                    }                    });
    }


    private void updateProfile() {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userPref.getUser().getUserId());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), binding.etEmail.getEditText().getText().toString().trim());
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), binding.etName.getEditText().getText().toString());
        RequestBody telephone = RequestBody.create(MediaType.parse("text/plain"), binding.etPhoneNo.getEditText().getText().toString().trim());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), binding.etAddress.getEditText().getText().toString().trim());

        apiService.callUpdateProfileAPI(imageFile, userId,name,email,telephone,address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1) {
                        userPref.setUser(commonResponse.getData());
                        utils.simpleAlert(getContext(), "Success", commonResponse.getMessage());
                    } else utils.simpleAlert(getContext(), "Error", commonResponse.getMessage());
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),requireActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),throwable.getMessage());
                    }});
    }


}
