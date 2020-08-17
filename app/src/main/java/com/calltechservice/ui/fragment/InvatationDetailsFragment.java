package com.calltechservice.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.calltechservice.databinding.FragmentInviteDetailsCartBinding;
import com.calltechservice.databinding.ReciptDetailsBinding;
import com.calltechservice.model.response.InvitationResponse;
import com.calltechservice.utils.CommonUtils;
import com.google.gson.JsonObject;

import com.calltechservice.R;

import com.calltechservice.BaseFragment;
import com.calltechservice.ui.activity.HomeActivity;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InvatationDetailsFragment extends BaseFragment implements View.OnClickListener {
    private FragmentInviteDetailsCartBinding binding;
    private Context context;
    private InvitationResponse mInvitations;
    private String jobId;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite_details_cart, container, false);
        View view = binding.getRoot();

        binding.btSendInvoice.setOnClickListener(this);
        binding.ivAccept.setOnClickListener(this);
        binding.ivDecline.setOnClickListener(this);
        binding.infopayment.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) requireActivity()).changeIcon(false);
        requireActivity().setTitle("Invitations Details");

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            jobId=bundle.getString("jobId");
            userId=bundle.getString("userId");

            callInvitationDetailsAPI();

        }

        return view;
    }

    private void setDataViw() {
        binding.linearlayout.setVisibility(View.VISIBLE);

        if(mInvitations.getJobDetails().getIs_immediate().equalsIgnoreCase("1"))
        {
            binding.tvScheduleDate.setText(R.string.imediate_requirment);
            binding.tvScheduleDate.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else {
            if (mInvitations.getJobDetails().getSchedule_date()!= null && !mInvitations.getJobDetails().getSchedule_date().equalsIgnoreCase("")) {
                binding.tvScheduleDate.setText(mInvitations.getJobDetails().getSchedule_date());

            } else {
                binding.tvScheduleDate.setText("");
            }
        }

        binding.tvjobname.setText(mInvitations.getJobDetails().getTitle()!=null?mInvitations.getJobDetails().getTitle():"");
        binding.jobuser.setText(mInvitations.getProviderDetails().getName()!=null?mInvitations.getProviderDetails().getName():"");
        binding.tvdescription.setText(mInvitations.getJobDetails().getDescription()!=null?mInvitations.getJobDetails().getDescription():"");
        binding.tvhourrate.setText(mInvitations.getJobDetails().getWorking_hours()!=null?mInvitations.getJobDetails().getWorking_hours()+" hours":"");
        //binding.tvScheduleDate.setText(mInvitations.getJobDetails().getSchedule_date()!=null?mInvitations.getJobDetails().getSchedule_date():"");
        binding.tvlocation.setText(mInvitations.getJobDetails().getJob_location()!=null?mInvitations.getJobDetails().getJob_location():"");
       /* if(mInvitations.getInvoiceStatus()!=null && mInvitations.getInvoiceStatus().equalsIgnoreCase("0"))
        {
            setHasOptionsMenu(false);
            binding.llButton.setVisibility(View.VISIBLE);
            binding.lytInvoiceAmount.setVisibility(View.GONE);
        }
        else if(mInvitations.getInvoiceStatus()!=null && mInvitations.getInvoiceStatus().equalsIgnoreCase("1"))
        {
            setHasOptionsMenu(true);
            binding.llButton.setVisibility(View.VISIBLE);
            binding.lytInvoiceAmount.setVisibility(View.GONE);
        }*/



        if(mInvitations.getInvoiceStatus()!=null&&mInvitations.getInvoiceStatus().equalsIgnoreCase("0"))
        {


            setHasOptionsMenu(false);

            binding.btSendInvoice.setVisibility(View.GONE);
            binding.ivAccept.setVisibility(View.VISIBLE);
            binding.ivDecline.setVisibility(View.VISIBLE);
            binding.lytInvoiceAmount.setVisibility(View.GONE);


        }
        else if(mInvitations.getInvoiceStatus()!=null&&mInvitations.getInvoiceStatus().equalsIgnoreCase("1"))
        {
            setHasOptionsMenu(true);
            binding.btSendInvoice.setVisibility(View.VISIBLE);
            binding.ivAccept.setVisibility(View.GONE);
            binding.ivDecline.setVisibility(View.GONE);
            binding.lytInvoiceAmount.setVisibility(View.GONE);

        }



       else if (mInvitations.getInvoiceStatus()!=null&&mInvitations.getInvoiceStatus().equalsIgnoreCase("2")){
            binding.lytInvoiceAmount.setVisibility(View.VISIBLE);
            binding.btSendInvoice.setVisibility(View.GONE);
                binding.tvInvoiceAmount.setText("You sent proposal of : " +userPref.getUser().getCode()+" "+mInvitations.getTotal());

            binding.view.setVisibility(View.VISIBLE);
            binding.linearlayout.setVisibility(View.VISIBLE);
        }



       /*else {
        if(mInvitations.getInvoiceStatus().equals("Not Sent")){
            binding.btSendInvoice.setVisibility(View.VISIBLE);
            binding.lytInvoiceAmount.setVisibility(View.VISIBLE);
            binding.tvInvoiceAmount.setText("please sent invoice of : " +"R "+mInvitations.getTotal());
        }
            binding.linearlayout.setVisibility(View.VISIBLE);
            binding.view.setVisibility(View.VISIBLE);
        }*/






        /*if(mInvitations.getAward()!=null&&mInvitations.getAward().equalsIgnoreCase("Awarded")&&mInvitations.getInvoiceStatus()!=null&&mInvitations.getInvoiceStatus().equalsIgnoreCase("Not Sent"))
        {
            binding.btSendInvoice.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.btSendInvoice.setVisibility(View.GONE);
        }*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_chat:
                Fragment fragment=new MessageFragment();
                Bundle bundle=new Bundle();
                bundle.putString("key",(mInvitations.getJobDetails().getUser_id()+"_"+userPref.getUser().getUserId()+"_"+mInvitations.getJobDetails().getJob_id()));
                bundle.putString("job_id",mInvitations.getJobDetails().getJob_id());
                bundle.putString("provider_id",mInvitations.getJobDetails().getUser_id());
                bundle.putString("sender_name", mInvitations.getProviderDetails().getName());
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment,false, (FragmentActivity) context, R.id.flContainerHome);
               //utils.toaster("Coming Soon");

                return true;
        }
        return onOptionsItemSelected(item);
    }


//    private void sendInvoice() {
//        final ReciptDetailsBinding bindingReceipt=DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.recipt_details, null, false);
//        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(bindingReceipt.getRoot());
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.dimAmount = .7f;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        dialog.getWindow().setAttributes(layoutParams);
//        dialog.show();
//        bindingReceipt.tvAmmountClient.setText("Amount Shared by client: "+mInvitations.getAwardedAmount());
//        bindingReceipt.btSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(CommonUtils.isOnline(requireActivity()))
//                {
//
//                    if(!TextUtils.isEmpty(bindingReceipt.etAmount.getText().toString().trim()))
//                    {
//                        callSendInvoiceApi(mInvitations.getJobDetails().getUser_id(),mInvitations.getJobDetails().getJob_id(),bindingReceipt.etAmount.getText().toString().trim());
//                        dialog.dismiss();
//                    }
//                    else
//                    {
//                        utils.simpleAlert(getContext(),"Error", "Please enter amount");
//                        //callSendInvoiceApi(mInvitations.getJobDetails().getUserId(),mInvitations.getJobDetails().getJobId(),mInvitations.getAwardedAmount());
//                    }
//                }
//                else
//                {
//                    CommonUtils.showSnack(binding.getRoot(),getString(R.string.internet_connection));
//                }
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivAccept:
                callAcceptDecline(jobId,"1");
                break;
            case R.id.ivDecline:
                callAcceptDecline(jobId,"2");
                break;
            case R.id.btSendInvoice:
                //sendInvoice();

                Fragment fragment=new InvoicePriceDistribution();
                Bundle bundle1=new Bundle();

                bundle1.putString("providercharge",mInvitations.getAwardedAmount());
                bundle1.putString("servicefee",mInvitations.getService_amount());
                bundle1.putString("invoicetotal",mInvitations.getTotal());
                bundle1.putString("jobid",mInvitations.getJobDetails().getJob_id());
                bundle1.putString("jobuserid",mInvitations.getJobDetails().getUser_id());
                bundle1.putString("invoicestatus",mInvitations.getInvoiceStatus());
                bundle1.putString("providerid",mInvitations.getJobDetails().getProvider_id());
                bundle1.putString("kmcharges",mInvitations.getKmcharges());
                fragment.setArguments(bundle1);
                CommonUtils.setFragment(fragment,false,  requireActivity(), R.id.flContainerHome);
                break;


            case R.id.infopayment:
                Fragment fragment2=new InvoicePriceDistribution();
                Bundle bundle2=new Bundle();

                bundle2.putString("providercharge",mInvitations.getAwardedAmount());
                bundle2.putString("servicefee",mInvitations.getService_amount());
                bundle2.putString("invoicetotal",mInvitations.getTotal());
                bundle2.putString("jobid",mInvitations.getJobDetails().getJob_id());
                bundle2.putString("jobuserid",mInvitations.getJobDetails().getUser_id());
                bundle2.putString("invoicestatus",mInvitations.getInvoiceStatus());
                bundle2.putString("providerid",mInvitations.getJobDetails().getProvider_id());
                bundle2.putString("kmcharges",mInvitations.getKmcharges());
                fragment2.setArguments(bundle2);
                CommonUtils.setFragment(fragment2,false,  requireActivity(), R.id.flContainerHome);

                break;





            default:
                break;
        }
    }

    private void callSendInvoiceApi(String userId, String jobId, String cost) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",userPref.getUser().getUserId());
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("job_id",jobId);
        jsonObject.addProperty("cost",cost);

        apiService.callSendInvoiceApi(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData()!=null) {
                        utils.simpleAlert(requireActivity(),"Success",commonResponse.getMessage());
                        mInvitations = commonResponse.getData();


                        requireActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                setDataViw();

                            }

                        });


                       // binding.btSendInvoice.setVisibility(View.GONE);
                    } else{
                        utils.simpleAlert(requireActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
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
                    }
                });
    }


    private void callAcceptDecline(String jobId,String status) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",userPref.getUser().getUserId());
        jsonObject.addProperty("job_id",jobId);
        jsonObject.addProperty("status",status);

        apiService.callAcceptDeclineApi(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null) {


                        mInvitations = commonResponse.getData();

                        if(status.equalsIgnoreCase("1"))
                        {
                            setHasOptionsMenu(true);
                            binding.ivAccept.setVisibility(View.GONE);
                            binding.ivDecline.setVisibility(View.GONE);
                            binding.btSendInvoice.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            //TODO crash herejava.lang.NullPointerException: Attempt to invoke virtual method 'void android.support.v4.app.FragmentManager.popBackStack()' on a null object reference
                            //at com.ondemandemployee.android.ui.fragment.InvatationDetailsFragmentR 1.onResponse(InvatationDetailsFragment.java:132)
                            getFragmentManager().popBackStack();
                        }
                    } else{
                        utils.simpleAlert(requireActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
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
                    }
                });
    }


    private void callInvitationDetailsAPI() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",userPref.getUser().getUserId());
        jsonObject.addProperty("job_id",jobId);

        apiService.callInvitationDetailsAPI(userPref.getUser().getUserId(),jobId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null) {
                        mInvitations = commonResponse.getData();

                        setDataViw();

                    } else{
                        utils.simpleAlert(requireActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
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
                    }
                });
    }


}
