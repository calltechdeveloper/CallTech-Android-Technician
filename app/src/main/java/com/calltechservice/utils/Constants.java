package com.calltechservice.utils;

public interface Constants {

    //Local server url
   // String BASE_URL = "http://182.76.237.228/~anshul/calltech/ci_winapi/index.php/service/";

    //Live server url
   String BASE_URL = "http:///ec2-54-189-118-240.us-west-2.compute.amazonaws.com/ci_winapi/index.php/service/";

    public static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#R %]).{6,20})";
    String NAME_REGEX = ".{2,}";
    String PASSWORD_REGEX = ".{4,}";
    String COUPON_CODE_REGEX = ".{3,}";
    String REF_REGEX = ".{4,}";
    String EMPTY_REGEX = ".{1,}";

    public static String IS_NOTIFICATION="is_notification";
    String CLIENT_ID= "IKIA335B188FDC3527EDB1E9300D35F6C51826DFC8A5";

    String SECRET_KEY = "4HOFYiMJitFQeHYUCH/pvTF6jpiIaZqzVKB/pheK4Cs=";

}