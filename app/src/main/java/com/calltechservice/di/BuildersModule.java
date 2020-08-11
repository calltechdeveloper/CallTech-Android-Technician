package com.calltechservice.di;

import com.calltechservice.ui.activity.DemoActivity;
import com.calltechservice.ui.activity.FinalSubCategoryActivity;
import com.calltechservice.ui.activity.LoginActivity;
import com.calltechservice.ui.activity.NewJobActivity;
import com.calltechservice.ui.activity.NewUserActivity;
import com.calltechservice.ui.activity.PhoneNoActivity;
import com.calltechservice.ui.activity.RegistrationNewActivity;
import com.calltechservice.ui.activity.SelectSubCategoryActivity;
import com.calltechservice.ui.activity.SelectYourServiceAreaActivity;
import com.calltechservice.ui.activity.SplashActivity;
import com.calltechservice.ui.activity.VerifyOtpActivity;
import com.calltechservice.ui.fragment.AboutUsFragment;
import com.calltechservice.ui.fragment.ComingSoonFragment;
import com.calltechservice.ui.fragment.ComplainFragment;
import com.calltechservice.ui.fragment.DashboardFragment;
import com.calltechservice.ui.fragment.ForgotPasswordFragment;
import com.calltechservice.ui.fragment.HistoryDetailsFragment;
import com.calltechservice.ui.fragment.InvatationDetailsFragment;
import com.calltechservice.ui.fragment.InviteCartFragment;
import com.calltechservice.ui.fragment.InviteFriendFragment;
import com.calltechservice.ui.fragment.InvoicePriceDistribution;
import com.calltechservice.ui.fragment.JobHistoryFragment;
import com.calltechservice.ui.fragment.MessageFragment;
import com.calltechservice.ui.fragment.MyAccountFragment;
import com.calltechservice.ui.fragment.MyJobFragment;
import com.calltechservice.ui.fragment.MyServicesFragment;
import com.calltechservice.ui.fragment.NewJobFragment;
import com.calltechservice.ui.fragment.NotificationDetailsFragment;
import com.calltechservice.ui.fragment.NotificationFragment;
import com.calltechservice.ui.fragment.OffersFragment;
import com.calltechservice.ui.fragment.OnGoingDetailsFragment;
import com.calltechservice.ui.fragment.OnGoingFragment;
import com.calltechservice.ui.fragment.PaymentFragment;
import com.calltechservice.ui.fragment.PolicyFragment;
import com.calltechservice.ui.fragment.ProviderDetailsFragment;
import com.calltechservice.ui.fragment.RatingCommentFragment;
import com.calltechservice.ui.fragment.ServiceProviderDashboardFragment;
import com.calltechservice.ui.fragment.ServiceProviderFragment;
import com.calltechservice.ui.fragment.Termsprivacy;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract DemoActivity bindDemoActivity();

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector
    abstract PhoneNoActivity phoneNoActivity();

    @ContributesAndroidInjector
    abstract VerifyOtpActivity verifyOtpActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract ForgotPasswordFragment bindForgotPasswordFragment();

    @ContributesAndroidInjector
    abstract RegistrationNewActivity bindRegistrationNewActivity();

    @ContributesAndroidInjector
    abstract SelectSubCategoryActivity selectSubCategoryActivity();

    @ContributesAndroidInjector
    abstract FinalSubCategoryActivity finalSubCategoryActivity();

    @ContributesAndroidInjector
    abstract SelectYourServiceAreaActivity selectYourServiceAreaActivity();

    @ContributesAndroidInjector
    abstract ServiceProviderDashboardFragment serviceProviderDashboardFragment();

    @ContributesAndroidInjector
    abstract NotificationDetailsFragment NotificationDetailsFragment();




    @ContributesAndroidInjector
    abstract NotificationFragment notificationFragment();

    @ContributesAndroidInjector
    abstract PolicyFragment bindPolicyFragment();







    @ContributesAndroidInjector
    abstract DashboardFragment bindDashboardFragment();

    @ContributesAndroidInjector
    abstract ComplainFragment complainFragment();

    @ContributesAndroidInjector
    abstract HistoryDetailsFragment historyDetailsFragment();

    @ContributesAndroidInjector
    abstract InvatationDetailsFragment invatationDetailsFragment();

    @ContributesAndroidInjector
    abstract InviteCartFragment inviteCartFragment();

    @ContributesAndroidInjector
    abstract JobHistoryFragment jobHistoryFragment();

    @ContributesAndroidInjector
    abstract MessageFragment messageFragment();

    @ContributesAndroidInjector
    abstract MyAccountFragment myAccountFragment();

    @ContributesAndroidInjector
    abstract MyJobFragment myJobFragment();

    @ContributesAndroidInjector
    abstract OnGoingDetailsFragment onGoingDetailsFragment();

    @ContributesAndroidInjector
    abstract OnGoingFragment onGoingFragment();

    @ContributesAndroidInjector
    abstract RatingCommentFragment ratingCommentFragment();

    @ContributesAndroidInjector
    abstract ServiceProviderFragment serviceProviderFragment();

    @ContributesAndroidInjector
    abstract ProviderDetailsFragment providerDetailsFragment();


    @ContributesAndroidInjector
    abstract MyServicesFragment bindServiceFragment();

    @ContributesAndroidInjector
    abstract NewUserActivity bindNewUserActivity();

    @ContributesAndroidInjector
    abstract OffersFragment bindOffersFragment();

    @ContributesAndroidInjector
    abstract InviteFriendFragment bindInviteFriendFragment();

    @ContributesAndroidInjector
    abstract ComingSoonFragment bindComingSoonFragment();



    @ContributesAndroidInjector
    abstract PaymentFragment bindPaymentFragment();

    @ContributesAndroidInjector
    abstract NewJobActivity bindNewJobActivity();


    @ContributesAndroidInjector
    abstract NewJobFragment bindNewJobFragment();


    @ContributesAndroidInjector
    abstract InvoicePriceDistribution bindInvoicePriceDistribution();

    @ContributesAndroidInjector
    abstract AboutUsFragment bindAboutUsFragment();

    @ContributesAndroidInjector
    abstract Termsprivacy bindTermsprivacy();

}


