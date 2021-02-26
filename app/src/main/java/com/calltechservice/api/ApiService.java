package com.calltechservice.api;

import com.calltechservice.model.CommonDataResponse;
import com.calltechservice.model.CountryModel;
import com.calltechservice.model.request.SendInvitationRequest;
import com.calltechservice.model.response.AboutUsResponse;
import com.calltechservice.model.response.AddRemoveCategoryModel;
import com.calltechservice.model.response.CommonResponse;
import com.calltechservice.model.response.CompleteJobResponse;
import com.calltechservice.model.response.InvitCartResponse;
import com.calltechservice.model.response.InvitationResponse;
import com.calltechservice.model.response.InvitationsModel;
import com.calltechservice.model.response.JobInvitationResponse;
import com.calltechservice.model.response.JobTrackResponse;
import com.calltechservice.model.response.NotificationResponse;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.model.response.SelectCategoryResponse;
import com.calltechservice.model.response.ServiceDashboardModel;
import com.calltechservice.model.response.ServiceProdersModel;
import com.calltechservice.model.response.ServiceSubCtegoryModel;
import com.calltechservice.model.response.TermsPolicyModel;
import com.calltechservice.model.response.UserModel;
import com.calltechservice.model.response.employee.EmployeeResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

@Singleton
public interface ApiService {

    @POST("~anshul/fixer/api/customer/customer_api.php/")
    Call<EmployeeResponse> callEmployee(@Body JsonObject JsonObject);

    @POST("~anshul/fixer/api/customer/customer_api.php/")
    Call<EmployeeResponse> callAgencyEmployee(@Body JsonObject jsonObject);

    @POST("~anshul/fixer/api/customer/customer_api.php/")
    Call<InvitCartResponse> callInvitCart(@Body JsonObject JsonObject);

    @POST("~anshul/fixer/api/customer/customer_api.php/")
    Call<CommonResponse> callRaiseComplaint(@Body JsonObject JsonObject);

    @POST("~anshul/fixer/api/customer/customer_api.php/")
    Call<CommonResponse> callAwardJob(@Body JsonObject JsonObject);

    //OTP
    @POST("serviceOtplogin")
    Observable<CommonDataResponse> callOTPAPI(@Body JsonObject request);

    @POST("serviceSignup")
    Observable<CommonDataResponse<UserModel>> callRegistrationAPI(@Body JsonObject request);

    @POST("servicelogin")
    Observable<CommonDataResponse<UserModel>> callLoginApi(@Body JsonObject request);

    //Forgot password
    @POST("forgotpass")
    Observable<CommonDataResponse> callForgotPasswordAPI(@Body JsonObject request);

    //updateServiceprofile
    @Multipart
    @POST("updateServiceprofile")
    Observable<CommonDataResponse<UserModel>> callUpdateProfileAPI(@Part MultipartBody.Part profile_pic,
                                                                   @Part("id") RequestBody user_id,
                                                                   @Part("name") RequestBody user_name,
                                                                   @Part("email") RequestBody user_email,
                                                                   @Part("mobile") RequestBody user_contact,
                                                                   @Part("address") RequestBody location);
    /*@POST("updateServiceprofile")
    Observable<CommonDataResponse> callUpdateServiceprofileAPI(@Body JsonObject request);*/

    //serviceProfile
    @FormUrlEncoded
    @POST("serviceProfile")
    Observable<CommonDataResponse<UserModel>> callServiceProfileAPI(@Field("provider_id") String provider_id);

    @POST("dashboard")
    Observable<CommonDataResponse<ServiceDashboardModel>> callServiceDashboardAPI(@Body JsonObject request);

    //Dashboard category
    @GET("getservices")
    Observable<CommonDataResponse<List<SelectCategoryResponse>>> callServiceCategoryAPI();

    //SubCategory
    @POST("subservices")
    Observable<CommonDataResponse<List<ServiceSubCtegoryModel>>> callSubCategoryAPI(@Body JsonObject request);

    //Add Services
    @POST("addServices")
    Observable<CommonDataResponse<ArrayList<AddRemoveCategoryModel>>> callAddServicesAPI(@Body JsonObject request);

    //Add Location
    @POST("addServiceArea")
    Observable<CommonDataResponse> callAddLocationAPI(@Body JsonObject request);

    @POST("myservices")
    Observable<CommonDataResponse<ArrayList<AddRemoveCategoryModel>>> callMyServicesAPI(@Body JsonObject request);

    @POST("deleteservice")
    Observable<CommonDataResponse<ArrayList<AddRemoveCategoryModel>>> callMyServicesdeleteAPI(@Body JsonObject request);

    @POST("invitation")
    Observable<CommonDataResponse<List<JobInvitationResponse>>> callInvitationAPI(@Body JsonObject request);

    //Invitation Details
    @FormUrlEncoded
    @POST("providerInvitationDetails")
    Observable<CommonDataResponse<InvitationResponse>> callInvitationDetailsAPI(@Field("provider_id") String provider_id,
                                                                                @Field("job_id") String job_id);

    @POST("sendInvoice")
    Observable<CommonDataResponse<InvitationResponse>> callSendInvoiceApi(@Body JsonObject request);

//    @POST("invitationStatusChange")
//    Observable<CommonDataResponse<List<JobDetailsData>>> callAcceptDeclineApi(@Body JsonObject request);

    @POST("invitationStatusChange")
    Observable<CommonDataResponse<InvitationResponse>> callAcceptDeclineApi(@Body JsonObject request);

    //In Progress List
    @POST("inprogressjobs")
    Observable<CommonDataResponse<List<OnGoingJobResponse>>> callInProgressListAPI(@Body JsonObject request);

    //Completejob List
    @POST("serviceCompletejob")
    Observable<CommonDataResponse<List<CompleteJobResponse>>> callCompletejobListAPI(@Body JsonObject request);

    //Service provider
    @POST("findServices")
    Observable<CommonDataResponse<List<ServiceProdersModel>>> callServicesProviderAPI(@Body JsonObject request);

    //Send Invitation
    @POST("sendInvitation")
    Observable<CommonDataResponse> callSendInvitationAPI(@Body SendInvitationRequest request);

    //Invitation List
    @POST("invitationList")
    Observable<CommonDataResponse<List<InvitationsModel>>> callInvitationListAPI(@Body JsonObject request);

    // term
    @GET("termscondition/terms/about/{about}")
    Observable<CommonDataResponse<TermsPolicyModel>> callTermsPolicyAPI(@Path("about") String type);

    // http://54.69.238.186/ci_winapi/index.php/service/deleteservice{"status":0,"message":"No Service Found.","data":null}
    @FormUrlEncoded
    @POST("serviceStart")
    Observable<CommonDataResponse<JobTrackResponse>> callStartJobAPI(@Field("provider_id") String provider_id,
                                                                     @Field("job_id") String job_id,
                                                                     @Field("status") String status);

    @Multipart
    @POST("completejob")
    Observable<CommonDataResponse<JobTrackResponse>> callCompleteJobAPI(@Part MultipartBody.Part doc_pic,
                                                                        @Part("user_id") RequestBody user_id,
                                                                        @Part("provider_id") RequestBody provider_id,
                                                                        @Part("job_id") RequestBody job_id,
                                                                        @Part("status") RequestBody status);

    @FormUrlEncoded
    @POST("serviceTrack")
    Observable<CommonDataResponse<JobTrackResponse>> callServiceTrackJobAPI(@Field("provider_id") String provider_id,
                                                                            @Field("job_id") String job_id);

    @FormUrlEncoded
    @POST("send_message")
    Observable<CommonDataResponse> callphoneotp(
            @Field("mobile_no") String mobile_no,
            @Field("message") String message,
            @Field("country_code") String country_code);

    @FormUrlEncoded
    @POST("check_mobile_number")
    Observable<CommonDataResponse> callphoneverified(@Field("mobile_no") String mobile_no);

    @GET("termscondition/terms/about")
    Observable<CommonDataResponse<AboutUsResponse>> callaboutusapi();

    @GET("termscondition/terms/term")
    Observable<CommonDataResponse<AboutUsResponse>> calltermapi();

    @FormUrlEncoded
    @POST("get_notification")
    Observable<CommonDataResponse<List<NotificationResponse>>> callnotificationapi(@Field("provider_id") String provider_id);

    //notificatiodetails
    @FormUrlEncoded
    @POST("update_notification")
    Observable<CommonDataResponse<NotificationResponse>> callnotificationdetailsApi(@Field("id") String id);

    @GET("getcountry")
    Observable<CommonDataResponse<List<CountryModel>>> callCountryAPI();

    @FormUrlEncoded
    @POST("upload_individual_docs")
    Observable<CommonDataResponse<String>> uploadIndividualDocs(@Field("service_provider_id") String service_provider_id,
                                                                          @Field("cv") String cv,
                                                                          @Field("cv_type") String cvType,
                                                                          @Field("police_clearance") String policeClearance,
                                                                          @Field("police_clearance_type") String policeClearanceType,
                                                                   @Field("drivers_licence") String driversLicence,
                                                                   @Field("drivers_licence_type") String driversLicenceType);

    @FormUrlEncoded
    @POST("upload_company_docs")
    Observable<CommonDataResponse<String>> uploadCompanyDocs(@Field("service_provider_id") String service_provider_id,
                                                             @Field("company_reg_doc") String companyRegistrationDocument,
                                                             @Field("company_reg_doc_type") String companyRegistrationDocumentType,
                                                             @Field("tax_clearance") String taxClearance,
                                                             @Field("tax_clearance_type") String taxClearanceType,
                                                             @Field("bee_certificate") String beeCertificate,
                                                             @Field("bee_certificate_type") String beeCertificateType);
}