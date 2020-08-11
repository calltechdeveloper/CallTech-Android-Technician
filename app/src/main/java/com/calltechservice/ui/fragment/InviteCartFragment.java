package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.calltechservice.model.response.JobInvitationResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.google.gson.JsonObject;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentInviteCartBinding;
import com.calltechservice.BaseFragment;
import com.calltechservice.ui.adapter.InviteCartAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.RecyclerItemClickListener;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class InviteCartFragment extends BaseFragment {
    private FragmentInviteCartBinding binding;
    private LinearLayoutManager layoutManager;
    private InviteCartAdapter inviteCartAdapter;
    private Context context;
    private List<JobInvitationResponse> invitationsModelList, filteredList;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callInvitationAPI();
//         /*bundle = this.getArguments();
//        if (bundle != null) {
//            invitationsModelList = bundle.getParcelableArrayList("recentList");
//            filteredList = bundle.getParcelableArrayList("recentList");
//        } else {
//            callInvitationAPI();
//        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite_cart, container, false);
        View view = binding.getRoot();

        setHasOptionsMenu(false);

        ((HomeActivity) getActivity()).changeIcon(true);


       /* if(CommonUtils.isOnline(getActivity()))
        {
            callServicesProviderAPI();
            //callgetEmployeeApi();
        }
        else
        {
            CommonUtils.showSnack(getActivity().findViewById(android.R.id.content),getActivity().getString(R.string.internet_connection));

        }*/
        return view;
    }

    private void setRecyslerViw()
    {
       /* mInvitations=new ArrayList<>();*/
        layoutManager=new LinearLayoutManager(getActivity());
        binding.rvInviteCart.setLayoutManager(layoutManager);
        inviteCartAdapter =new InviteCartAdapter(context,invitationsModelList);
        inviteCartAdapter.setHasStableIds(true);
        binding.rvInviteCart.setAdapter(inviteCartAdapter);

        binding.rvInviteCart.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Fragment fragment=new InvatationDetailsFragment();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("jobId",invitationsModelList.get(position).getJob_id());
                        bundle1.putString("userId",invitationsModelList.get(position).getUser_id());
                        fragment.setArguments(bundle1);
                        CommonUtils.setFragment(fragment,false,  getActivity(), R.id.flContainerHome);
                    }
                })
        );
    }



    @Override
    public void onResume() {
        super.onResume();
         Bundle bundle = this.getArguments();
        if (bundle != null) {
            invitationsModelList = new ArrayList<>();
            filteredList = new ArrayList<>();
            invitationsModelList = bundle.getParcelableArrayList("recentList");
            filteredList = bundle.getParcelableArrayList("recentList");
            setRecyslerViw();
        } else {
            callInvitationAPI();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    private void callInvitationAPI() {
        invitationsModelList = new ArrayList<>();
        filteredList = new ArrayList<>();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",userPref.getUser().getUserId());
        apiService.callInvitationAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&commonResponse.getData().size()>0) {

                        invitationsModelList.clear();
                        invitationsModelList.addAll(commonResponse.getData());
                        setRecyslerViw();
                    } else{
                        //utils.simpleAlert(getActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
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
                    }
                });
    }
}
