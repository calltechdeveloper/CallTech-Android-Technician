
package com.calltechservice.ui.fragment;

        import androidx.databinding.DataBindingUtil;
        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.calltechservice.databinding.InvoicePricedistributionBinding;
        import com.calltechservice.R;

        import com.calltechservice.BaseFragment;
        import com.calltechservice.ui.activity.HomeActivity;
        import com.google.gson.JsonObject;

        import java.net.ConnectException;

        import rx.android.schedulers.AndroidSchedulers;
        import rx.schedulers.Schedulers;

public class InvoicePriceDistribution extends BaseFragment implements View.OnClickListener {

    private InvoicePricedistributionBinding binding;
 String providercharge,servicefee,invoicetotalamount,jobid,jobuserid,invoicestatus,providerid,kmcharges;

    public InvoicePriceDistribution() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            providercharge=bundle.getString("providercharge");
            servicefee=bundle.getString("servicefee");
            invoicetotalamount=bundle.getString("invoicetotal");
            jobid=bundle.getString("jobid");
            jobuserid=bundle.getString("jobuserid");
            invoicestatus=bundle.getString("invoicestatus");
            providerid=bundle.getString("providerid");
            kmcharges=bundle.getString("kmcharges");





        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.invoice_pricedistribution, container, false);
        requireActivity().setTitle("Proposal");
        ((HomeActivity) requireActivity()).changeIcon(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btSendInvoice.setOnClickListener(this);
        setdata(providercharge,servicefee,invoicetotalamount,kmcharges);

        if(invoicestatus.equals("Sent")){
            binding.btSendInvoice.setVisibility(View.GONE);
        }

    }

    public void setdata(String providercharge,String servicefee,String invoicetotalamount,String kmcharges){

        binding.tvProvicercharge.setText(userPref.getUser().getCode()+" "+providercharge);
        binding.tvServicecharge.setText(userPref.getUser().getCode()+" "+servicefee);
        binding.tvInvoicetotalamount.setText(userPref.getUser().getCode()+" "+invoicetotalamount);
        binding.tvKmcharge.setText(userPref.getUser().getCode()+" "+kmcharges);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSendInvoice:
                callSendInvoiceApi(jobuserid,jobid,invoicetotalamount);
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

                        binding.btSendInvoice.setVisibility(View.GONE);




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
}
