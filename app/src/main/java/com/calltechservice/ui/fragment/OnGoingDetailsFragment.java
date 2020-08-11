package com.calltechservice.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.CursorLoader;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.databinding.OnGoingDetailsBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.RatingViewBinding;
import com.calltechservice.model.response.JobTrackResponse;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.InvitedEmpolyeeListAdapter;
import com.calltechservice.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.ConnectException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnGoingDetailsFragment extends BaseFragment implements View.OnClickListener{
    private OnGoingDetailsBinding binding;
    private LinearLayoutManager layoutManager;
    private InvitedEmpolyeeListAdapter inviteCartAdapter;
    private Context context;
    private OnGoingJobResponse onGoingJobResponse;
    int type;
    String jobId;
    String userId;
    private  MultipartBody.Part imageFile=null;
    private final int PICK_IMAGE_CAMERA = 1;
    private final int PICK_IMAGE_GALLERY = 2;
    private JobTrackResponse response;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
            jobId = bundle.getString("jobId");
            userId = bundle.getString("userId");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.on_going_details, container, false);
        View view = binding.getRoot();
        setRecyslerViw();
        setListener();
        Log.e("customer id", "<<>>"+userId +"jobId " +jobId);
        return view;
    }


    //TODO set listener  view
    private void setListener()
    {
        //binding.tvMobileNo.setOnClickListener(this);
        //binding.tvFileComplain.setOnClickListener(this);
        //binding.btFeedback.setOnClickListener(this);
        //binding.tvFileComplain.startAnimation(CommonUtils.animate());
    }

    //TODO set recycler view
    private void setRecyslerViw()
    {
        NavigationView navigationView =  getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) getActivity()).changeIcon(false);
        if (type==0){
            getActivity().setTitle("In Progress");
        }else {
            getActivity().setTitle("Completed");
        }

        binding.btStartTask.setOnClickListener(this);
        binding.btCompleteTask.setOnClickListener(this);
        binding.lytAddPhoto.setOnClickListener(this);
        binding.tvRating.setOnClickListener(this);



       /* onGoingJobResponse=getArguments().getParcelable("details");
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) getActivity()).changeIcon(false);
        getActivity().setTitle("Job Details");
        binding.tvTitle.setText(onGoingJobResponse.getTitle()!=null?onGoingJobResponse.getTitle():"");
        binding.tvDescription.setText(onGoingJobResponse.getDescription()!=null?onGoingJobResponse.getDescription():"");
        layoutManager=new LinearLayoutManager(getActivity());
        if(onGoingJobResponse.getScheduleDate()!=null&&!onGoingJobResponse.getScheduleDate().equalsIgnoreCase(""))
        {
            String status="";
            if(onGoingJobResponse.getStatus()!=null&&!onGoingJobResponse.getStatus().equalsIgnoreCase(""))
            {
                if(onGoingJobResponse.getStatus().equalsIgnoreCase("2"))
                {
                    status=getString(R.string.job_schedlued);
                    binding.btFeedback.setVisibility(View.GONE);
                }
                if(onGoingJobResponse.getStatus().equalsIgnoreCase("3"))
                {
                    status=getString(R.string.job_completed);
                    binding.btFeedback.setVisibility(View.VISIBLE);
                }
            }
            binding.tvScheduleDate.setText(status+" "+onGoingJobResponse.getScheduleDate());
        }
        if(onGoingJobResponse.getEmployee().getAssignedEmp()==null)
        {

            if(onGoingJobResponse.getEmployee().getmProfilePic()!=null&&!onGoingJobResponse.getEmployee().getmProfilePic().equalsIgnoreCase(""))
            {
                Glide.with(context).load(onGoingJobResponse.getEmployee().getmProfilePic()).into(binding.ivEmpProfile);
            }
            else
            {
                binding.ivEmpProfile.setImageResource(R.drawable.user);
            }

            binding.tvName.setText(onGoingJobResponse.getEmployee().getmName()!=null?onGoingJobResponse.getEmployee().getmName():"");
            binding.tvMobileNo.setText(onGoingJobResponse.getEmployee().getmMobile()!=null?onGoingJobResponse.getEmployee().getmMobile():"");
        }
        else
        {
            if(onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic()!=null&&!onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic().equalsIgnoreCase(""))
            {
                Glide.with(context).load(onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic()).into(binding.ivEmpProfile);
            }
            else
            {
                binding.ivEmpProfile.setImageResource(R.drawable.user);
            }
            binding.tvName.setText(onGoingJobResponse.getEmployee().getAssignedEmp().getName()!=null?onGoingJobResponse.getEmployee().getAssignedEmp().getName():"");
            binding.tvMobileNo.setText(onGoingJobResponse.getEmployee().getAssignedEmp().getMobile()!=null?onGoingJobResponse.getEmployee().getAssignedEmp().getMobile():"");

        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.tvRating:
                sendRating();
                break;

            case R.id.lytAddPhoto:
                selectImage();
                break;

            case R.id.btStartTask:
                getStartJOb("1");
               /* binding.btCompleteTask.setBackgroundResource(R.drawable.rounded_corner);
                binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                binding.ivStart.setImageResource(R.drawable.circle_blue);
                binding.tvStart.setVisibility(View.VISIBLE);
                binding.tvStartDate.setVisibility(View.VISIBLE);
                binding.btStartTask.setVisibility(View.GONE);*/
                break;

            case R.id.btCompleteTask:
                callCompleteJobAPI(userId,jobId,"2");
                break;


            /*case R.id.tvMobileNo:
                if (PermissionUtils.checkPermissionCall(getActivity())) {
                    CommonUtils.call(getActivity(),binding.tvMobileNo.getText().toString().trim());
                }
                else
                {
                    PermissionUtils.requestPermissionCall((Activity) getActivity());
                }
                break;
            case R.id.tvFileComplain:
                Fragment fragment=new ComplainFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("details",onGoingJobResponse);
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment,false, (FragmentActivity) context, R.id.flContainerHome);

                break;
            case R.id.btFeedback:
                Fragment ratingFragment=new RatingCommentFragment();
                Bundle bundleRating=new Bundle();
                bundleRating.putParcelable("details",onGoingJobResponse);
                ratingFragment.setArguments(bundleRating);
                CommonUtils.setFragment(ratingFragment,false, (FragmentActivity) context, R.id.flContainerHome);

                break;
            default:
                break;*/
        }
    }




    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        PackageManager pm = getActivity().getPackageManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                dialog.dismiss();
                int cameraPermission = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
                int storagePermission = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getActivity().getPackageName());
                if (cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    this.startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            } else if (options[item].equals("Choose From Gallery")) {
                dialog.dismiss();
                int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getActivity().getPackageName());
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
            binding.ivIcon.setImageURI(uri);
            File file= new File(getPath(uri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            imageFile = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
        }

        if (requestCode == PICK_IMAGE_GALLERY && data != null) {
            Uri selectedImage = data.getData();
            try {
                binding.ivIcon.setImageURI(selectedImage);
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
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), inImage, "ProfilePic", null);
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
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 1);
        });

        builder.create();
        builder.show();
    }

    @SuppressLint("ResourceAsColor")
    private void getStartJOb(String status) {
        apiService.callStartJobAPI(userPref.getUser().getUserId(),jobId,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&& commonResponse.getData()!=null) {

                        setData(commonResponse.getData());
                       /* utils.simpleAlert(getContext(), "Success", commonResponse.getMessage());
                        binding.btCompleteTask.setBackgroundResource(R.drawable.rounded_corner);
                        binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                        binding.ivStart.setImageResource(R.drawable.circle_blue);
                        binding.tvStart.setVisibility(View.VISIBLE);
                        binding.tvStartDate.setVisibility(View.VISIBLE);
                        binding.btStartTask.setVisibility(View.GONE);*/
                    } else {
                        utils.simpleAlert(getContext(), "Error", commonResponse.getMessage());
                    }

                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),getActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),throwable.getMessage());
                    }                    });
    }

    private void callCompleteJobAPI(String user_id, String job_id,String jobStatus) {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody provider_id = RequestBody.create(MediaType.parse("text/plain"), userPref.getUser().getUserId());
        RequestBody jobId = RequestBody.create(MediaType.parse("text/plain"), job_id);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), jobStatus);

        apiService.callCompleteJobAPI(imageFile,userId,provider_id,jobId,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&& commonResponse.getData()!=null) {

                        setData(commonResponse.getData());
                        /*binding.btCompleteTask.setBackgroundResource(R.drawable.rounded_corner);
                        binding.ivComplete.setImageResource(R.drawable.circle_blue);
                        binding.tvComplete.setVisibility(View.VISIBLE);
                        binding.tvCompleteDate.setVisibility(View.VISIBLE);

                        binding.ivStart.setImageResource(R.drawable.circle_blue);
                        binding.tvStart.setVisibility(View.VISIBLE);
                        binding.tvStartDate.setVisibility(View.VISIBLE);
                        binding.btStartTask.setVisibility(View.GONE);
                        binding.btCompleteTask.setVisibility(View.GONE);*/

                    } else {
                        utils.simpleAlert(getContext(), "Error", commonResponse.getMessage());
                    }

                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),getActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),throwable.getMessage());
                    }                    });
    }


    @SuppressLint("ResourceAsColor")
    private void callServiceTrackJobAPI() {
        apiService.callServiceTrackJobAPI(userPref.getUser().getUserId(),jobId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData()!=null) {

                        setData(commonResponse.getData());

                        //utils.simpleAlert(getContext(), "Success", commonResponse.getMessage());
                        /*binding.btCompleteTask.setBackgroundResource(R.drawable.rounded_corner);
                        binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                        binding.ivStart.setImageResource(R.drawable.circle_blue);
                        binding.tvStart.setVisibility(View.VISIBLE);
                        binding.tvStartDate.setVisibility(View.VISIBLE);
                        binding.btStartTask.setVisibility(View.GONE);*/
                    } else {
                        utils.simpleAlert(getContext(), "Error", commonResponse.getMessage());
                    }

                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),getActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),throwable.getMessage());
                    }                    });
    }



    private void setData(JobTrackResponse response){
        // User Details
        binding.tvName.setText(response.getUserdetail().getName());
        binding.tvCategoryName.setText(response.getJob().getTitle());
        binding.tvDec.setText(response.getJob().getDescription());
        binding.tvDate.setText(response.getJob().getDate());
        binding.tvLocation.setText(response.getJob().getJob_location());
        binding.tvBookingDate.setText(response.getBooking_date().getBooking_date());
        binding.tvStartDate.setText(response.getStartjob().getStart());
        binding.tvCompleteDate.setText(response.getCommplete_job().getEnd());
        binding.hours.setText(response.getJob().getWorking_hours()+" hours");

        if (response.getUserdetail().getProfile_pic() != null && !response.getUserdetail().getProfile_pic().equalsIgnoreCase("")) {

            Glide.with(getActivity())
                    .load(Uri.parse(response.getUserdetail().getProfile_pic()))
                    .apply(RequestOptions.placeholderOf(R.drawable.user))
                    .apply(RequestOptions.errorOf(R.drawable.user))
                    .into(binding.ivUserImage);

            //Glide.with(getActivity()).load(Uri.parse(model.getUserImage())).apply(new RequestOptions().placeholder(R.drawable.user).dontAnimate()).into(binding.ivProfile);

        }else {
            binding.ivUserImage.setImageResource(R.drawable.user);
        }



        if (response.getStartjob().getStart_status().equals("0")){

            binding.btCompleteTask.setBackgroundResource(R.drawable.corner_black);
            binding.btCompleteTask.setEnabled(false);
            binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.gray));
            binding.ivComplete.setImageResource(R.drawable.circle_shape);
            binding.tvComplete.setVisibility(View.GONE);
            binding.tvCompleteDate.setVisibility(View.GONE);
            binding.lytPhoto.setVisibility(View.GONE);
            binding.tvRating.setVisibility(View.GONE);

            binding.ivStart.setImageResource(R.drawable.circle_blue);
            binding.tvStart.setVisibility(View.GONE);
            binding.tvStartDate.setVisibility(View.GONE);
            binding.btStartTask.setVisibility(View.VISIBLE);
            binding.tvBookingDate.setVisibility(View.VISIBLE);


        }
        else if (response.getStartjob().getStart_status().equals("1")){
            binding.btCompleteTask.setBackgroundResource(R.drawable.rounded_corner);
            binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
            binding.btCompleteTask.setVisibility(View.VISIBLE);
            binding.ivComplete.setImageResource(R.drawable.circle_shape);
            binding.tvComplete.setVisibility(View.GONE);
            binding.tvCompleteDate.setVisibility(View.GONE);
            binding.tvRating.setVisibility(View.GONE);

            binding.lytPhoto.setVisibility(View.VISIBLE);
            binding.btCompleteTask.setEnabled(true);
            binding.ivStart.setImageResource(R.drawable.circle_blue);
            binding.tvStart.setVisibility(View.VISIBLE);
            binding.tvStartDate.setVisibility(View.VISIBLE);
           // binding.tvStartDate.setText(response.getStartjob().getStart());
            binding.btStartTask.setVisibility(View.GONE);
            binding.tvBookingDate.setVisibility(View.VISIBLE);


        }else if (response.getStartjob().getStart_status().equals("2")){

            binding.tvComplete.setVisibility(View.VISIBLE);
            binding.tvCompleteDate.setVisibility(View.VISIBLE);
            //binding.tvCompleteDate.setText(response.getCommplete_job().getEnd());
            binding.ivComplete.setImageResource(R.drawable.circle_blue);
            binding.lytPhoto.setVisibility(View.VISIBLE);
            binding.lytAddPhoto.setVisibility(View.GONE);
            binding.btCompleteTask.setEnabled(false);
            binding.tvRating.setVisibility(View.GONE);


            if (response.getCommplete_job().getInvitation_doc() != null && !response.getCommplete_job().getInvitation_doc().equalsIgnoreCase("")) {

                Glide.with(getActivity())
                        .load(Uri.parse(response.getCommplete_job().getInvitation_doc()))
                        .apply(RequestOptions.placeholderOf(R.drawable.gal))
                        .apply(RequestOptions.errorOf(R.drawable.gal))
                        .into(binding.ivIcon);

            }else {
                binding.ivUserImage.setImageResource(R.drawable.user);
            }

            binding.ivStart.setImageResource(R.drawable.circle_blue);
            binding.tvStart.setVisibility(View.VISIBLE);
            binding.tvStartDate.setVisibility(View.VISIBLE);
            //binding.tvStartDate.setText(response.getStartjob().getStart());
            binding.btStartTask.setVisibility(View.GONE);
            binding.btCompleteTask.setVisibility(View.GONE);

        }else {
            binding.btCompleteTask.setBackgroundResource(R.drawable.corner_black);
            binding.btCompleteTask.setEnabled(false);
            binding.btCompleteTask.setTextColor(getActivity().getResources().getColor(R.color.gray));
            binding.ivComplete.setImageResource(R.drawable.circle_shape);
            binding.tvComplete.setVisibility(View.GONE);
            binding.tvCompleteDate.setVisibility(View.GONE);
            binding.lytPhoto.setVisibility(View.GONE);
            binding.tvRating.setVisibility(View.GONE);

            binding.ivStart.setImageResource(R.drawable.circle_blue);
            binding.tvStart.setVisibility(View.GONE);
            binding.tvStartDate.setVisibility(View.GONE);
            binding.btStartTask.setVisibility(View.VISIBLE);
            binding.tvBookingDate.setVisibility(View.VISIBLE);
        }


        binding.llMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MessageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key", (userPref.getUser().getUserId() + "_" + response.getUserdetail().getUserId() + "_" + jobId));
                bundle.putString("job_id", jobId);
                bundle.putString("provider_id", response.getUserdetail().getUserId());
                bundle.putString("sender_name", userPref.getUser().getName());
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment, false, (FragmentActivity) context, R.id.flContainerHome);
            }
        });

        binding.llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", response.getUserdetail().getMobile(), null)));

            }

        });
    }

    private void sendRating() {
        final RatingViewBinding bindingRating=DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.rating_view, null, false);
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(bindingRating.getRoot());
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);


        //finding the specific RatingBar with its unique ID
        LayerDrawable stars=(LayerDrawable)bindingRating.ratingBar.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //bindingRating.ratingBar.setIsIndicator(true);

        dialog.show();


        bindingRating.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {

                //ratingValue.setText(String.valueOf(rating));
                //Toast.makeText(getContext(),""+String.valueOf(rating),Toast.LENGTH_SHORT).show();

            }
        });

        /*bindingReceipt.tvAmmountClient.setText("Amount Shared by client: "+mInvitations.getAwardedAmount());*/
        bindingRating.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),""+String.valueOf(rating),Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), String.valueOf(bindingRating.ratingBar.getRating()), Toast.LENGTH_LONG).show();
                dialog.dismiss();

               /* if(CommonUtils.isOnline(getActivity()))
                {

                    if(!TextUtils.isEmpty(bindingRating.etAmount.getText().toString().trim()))
                    {
                        //callJobPaymentAPI(position);
                        dialog.dismiss();
                    }
                    else
                    {
                        utils.simpleAlert(getContext(),"Error", "Please enter amount");
                        //callSendInvoiceApi(mInvitations.getJobDetails().getUserId(),mInvitations.getJobDetails().getJobId(),mInvitations.getAwardedAmount());
                    }
                }
                else
                {
                    CommonUtils.showSnack(binding.getRoot(),getString(R.string.internet_connection));
                }*/
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        callServiceTrackJobAPI();
    }

}
