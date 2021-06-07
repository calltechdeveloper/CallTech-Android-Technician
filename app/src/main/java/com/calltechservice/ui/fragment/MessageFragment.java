package com.calltechservice.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import com.calltechservice.R;
import com.calltechservice.api.APIExecutor;
import com.calltechservice.cropimage.CropImage;
import com.calltechservice.databinding.AwardJobBinding;
import com.calltechservice.databinding.FragmentMessageBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.response.ChatModel;
import com.calltechservice.model.response.CommonResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.MessageAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.PermissionUtils;
import com.calltechservice.utils.TakePictureUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.calltechservice.utils.TakePictureUtils.TAKE_PICTURE;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMessageBinding binding;
    private LinearLayoutManager layoutManager;
    private MessageAdapter messageAdapter;
    private DatabaseReference databaseReferenceChild;
    private String parentKey;
    private String sender_name;
    private List<ChatModel> messageList;
    private MultipartBody.Part image;
    private String imageName = "";
    private String providerId = "";
    private String jobId = "";
    private boolean isFirstTime;

    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        //setHasOptionsMenu(true);
        parentKey = getArguments().getString("key");
        jobId = getArguments().getString("job_id");
        providerId = getArguments().getString("provider_id");
        sender_name = getArguments().getString("sender_name");
        setListener();
        setRecyslerViw();
        return binding.getRoot();
    }

    private void setListener() {
        ((HomeActivity) requireActivity()).changeIcon(false);
        binding.btSend.setOnClickListener(this);
        binding.btSendImage.setOnClickListener(this);
        childConfig();

        callMessageListener();
    }

    private void callMessageListener() {
        if (!isFirstTime) {
            showProgressDialog();
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(parentKey)) {
                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });

        databaseReferenceChild.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!isFirstTime) {
                    isFirstTime = true;
                    hideProgressDialog();
                }
                updateMessage(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (!isFirstTime) {
                    isFirstTime = true;
                    hideProgressDialog();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                hideProgressDialog();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });

    }

    private void updateMessage(DataSnapshot dataSnapshot) {

        Log.e("child", dataSnapshot.child("name").getKey());
        Log.e("child", dataSnapshot.child("name").getValue() + "");
        Log.e("child", dataSnapshot.child("message").getKey());
        Log.e("child", dataSnapshot.child("message").getValue() + "");
        ChatModel chatModel = new ChatModel();
        chatModel.setName(dataSnapshot.child("name") != null ? (String) dataSnapshot.child("name").getValue() : "");
        chatModel.setType(dataSnapshot.child("type") != null ? (String) dataSnapshot.child("type").getValue() : "");
        chatModel.setMessage(dataSnapshot.child("message") != null ? (String) dataSnapshot.child("message").getValue() : "");
        chatModel.setDate(dataSnapshot.child("date") != null ? (String) dataSnapshot.child("date").getValue() : "");
        chatModel.setSenderId(dataSnapshot.child("sender_id") != null ? (String) dataSnapshot.child("sender_id").getValue() : "");
        messageList.add(chatModel);
        messageAdapter.notifyDataSetChanged();
        binding.rvMessage.scrollToPosition(messageList.size() - 1);
    }

    private void setRecyslerViw() {
        messageList = new ArrayList<>();
        requireActivity().setTitle(sender_name);
        layoutManager = new LinearLayoutManager(requireActivity());
        binding.rvMessage.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(requireActivity(), messageList, userPref);
        binding.rvMessage.setAdapter(messageAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSend:
                if (!TextUtils.isEmpty(binding.etMessage.getText().toString().trim())) {

                    onSendMessage(binding.etMessage.getText().toString().trim(), "1", "");
                    binding.etMessage.setText("");
                } else {
                    CommonUtils.showSnack(binding.getRoot(), "Please enter message.");
                }
//355019700005261
                break;
            case R.id.btSendImage:
                checkPermission();
                break;
            default:
                break;
        }
    }

    // Child Config
    private void childConfig() {
        databaseReferenceChild = FirebaseDatabase.getInstance().getReference().getRoot().child(parentKey);

    }

    private void onSendMessage(String message, String type, String ammount) {
        DatabaseReference reference = databaseReferenceChild.push();
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("type", type);
        stringObjectMap.put("sender_id", userPref.getUser().getUserId());
        stringObjectMap.put("message", message);
        stringObjectMap.put("ammount", ammount);
        stringObjectMap.put("date", (System.currentTimeMillis()) + "");
        stringObjectMap.put("name", userPref.getUser().getName());
        reference.updateChildren(stringObjectMap);
    }

    private void callUploadImage() {
       /* showProgressDialog();
        RequestBody rquest;
        rquest = RequestBody.create(MediaType.parse("text/plain"), "uploadChatImage");
        Call<UploadImageResponse> registrationModelCall= APIExecutor.getApiService(requireActivity()).callUploadImage(image,rquest);
        registrationModelCall.enqueue(new Callback<UploadImageResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                if(response!=null&&response.body()!=null&&response.body().getStatus()!=null&&response.body().getStatus().equalsIgnoreCase("1")) {
                    onSendMessage(response.body().getData(),"0","");
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Log.e("Onfaild","");
                showProgressDialog();
            }
        });*/
    }

    private void checkPermission() {
        CommonUtils.hideSoftKeyboard(requireActivity());
        if (!PermissionUtils.checkPermissionCamera(requireActivity())) {
            PermissionUtils.requestCameraPermission(requireActivity(), PermissionUtils.PERMISSION_REQUEST_CODE);
        } else {
            if (!PermissionUtils.checkPermissionStorage(requireActivity())) {
                PermissionUtils.requestPermissionStorage(requireActivity());
            } else {
                addPhotoDialog();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                addPhotoDialog();
            } else {
                Toast.makeText(getContext(), getString(R.string.camera_permission_denial), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, TakePictureUtils.PICK_GALLERY);
    }

    private void takePicture(String imageName) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            Uri photoURI = null;
            File photoFile = new File(requireActivity().getExternalFilesDir("temp"), imageName + ".jpg");
            photoURI = FileProvider.getUriForFile(requireActivity(), getString(R.string.file_provider_authority), photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, TAKE_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TakePictureUtils.PICK_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    InputStream inputStream = requireActivity().getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(requireActivity().getExternalFilesDir("temp"), imageName + ".jpg"));
                    TakePictureUtils.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage(requireActivity(), imageName + ".jpg");
                } catch (Exception e) {
                    Toast.makeText(requireActivity(), "Error in picture", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
           /*     Bitmap photo = (Bitmap) data.getExtras().get("data");
                binding.ivProfile.setImageBitmap(photo);*/
                //Glide.with(getContext()).load(new File(data.get)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.ivProfile);
                startCropImage(requireActivity(), imageName + ".jpg");
            }
        } else if (requestCode == TakePictureUtils.CROP_FROM_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    path = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (path != null) {
                        File imageFile = new File(path);
                        if (imageFile != null && imageFile.exists()) {
                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), imageFile);
                            image = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
                            if (CommonUtils.isOnline(requireActivity())) {
                                callUploadImage();
                            } else {
                                CommonUtils.showSnack(binding.getRoot(), getString(R.string.internet_connection));

                            }

                        }
                    }
                }
            }
        }
    }

    private void addPhotoDialog() {
        final CharSequence[] items = {getString(R.string.take_a_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        builder.setTitle(getString(R.string.choose_photo));
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals(getString(R.string.take_a_photo))) {
                imageName = "picture";
                takePicture(imageName);
            } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                imageName = "picture";
                openGallery();
            } else {
                dialog.dismiss();
            }
        });
        builder.create().show();
        builder.setCancelable(true);
    }

    public void startCropImage(Activity context, String fileName) {
        Intent intent = new Intent(context, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, new File(context.getExternalFilesDir("temp"), fileName).getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 1);
        intent.putExtra(CropImage.ASPECT_Y, 1);
        intent.putExtra(CropImage.OUTPUT_X, 200);
        intent.putExtra(CropImage.OUTPUT_Y, 200);
        startActivityForResult(intent, TakePictureUtils.CROP_FROM_CAMERA);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.award_job, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_award) {
            askLocationForService();
            return true;
        }
        return onOptionsItemSelected(item);
    }

    private void callAwardJobApi(final String ammount) {
        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("job_id", jobId);
        jsonObject.addProperty("provider_id", providerId);
        jsonObject.addProperty("amount", ammount);
        jsonObject.addProperty("rquest", "award_job_to_emp");
        Call<CommonResponse> commonResponseCall = APIExecutor.getApiService().callAwardJob(jsonObject);

        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                if (response.body() != null && response.body().getmStatus() != null && response.body().getmStatus().equalsIgnoreCase("1")) {
                    onSendMessage("Congrats for the new opportunity:Job Cost:R" + ammount, "2", ammount);
                    CommonUtils.commonAlertBackStack(requireActivity(), response.body().getmMessage() != null ? response.body().getmMessage() : "");
                } else {
                    CommonUtils.showSnack(binding.getRoot(), requireActivity().getString(R.string.server_not_responding));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private void askLocationForService() {
        final AwardJobBinding bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.award_job, null, false);
        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(bindingDialog.getRoot());
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
        bindingDialog.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(bindingDialog.etEnterPrice.getText().toString().trim())) {
                    CommonUtils.showSnack(bindingDialog.getRoot(), requireActivity().getString(R.string.enter_amount_that_you_discuss_with_employee_to_pay_for_this_job));
                } else {
                    dialog.dismiss();
                    callAwardJobApi(bindingDialog.etEnterPrice.getText().toString().trim());
                }
            }
        });
    }

}