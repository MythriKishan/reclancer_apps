package com.mythri.reclancer_app.Network;


public class ApiUtil {

    private ApiUtil() {}

    public static final String BASE_URL = "https://reclancer.com/";


    public static APIfreelancer getAPIfrelancer() {

        return RetrofitClient.getClient(BASE_URL).create(APIfreelancer.class);
    }

    public static APIFreeLogin getAPIfreelogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIFreeLogin.class);
    }

    public static APIFProfile getAPIfprofileupdate() {

        return RetrofitClient.getClient(BASE_URL).create(APIFProfile.class);
    }

    public static APIFreePost getFreepostad(){
        return RetrofitClient.getClient(BASE_URL).create(APIFreePost.class);
    }

    public static APIRecReg getAPIReg() {

        return RetrofitClient.getClient(BASE_URL).create(APIRecReg.class);
    }

    public static APIRecLogin getAPIRecLogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIRecLogin.class);
    }

    public static APIRProfile getAPIRProfile() {

        return RetrofitClient.getClient(BASE_URL).create(APIRProfile.class);
    }

    public static APIRecPost getAPIRecPost() {

        return RetrofitClient.getClient(BASE_URL).create(APIRecPost.class);
    }


    public static APIFSearch getAPIFSearch() {

        return RetrofitClient.getClient(BASE_URL).create(APIFSearch.class);
    }


    public static APIFP_settings getAPIFPSet() {

        return RetrofitClient.getClient(BASE_URL).create(APIFP_settings.class);
    }

    public static APIRP_settings getAPIRPSet() {

        return RetrofitClient.getClient(BASE_URL).create(APIRP_settings.class);
    }

    public static APIFreead_update getAPIFreeadupdate() {

        return RetrofitClient.getClient(BASE_URL).create(APIFreead_update.class);
    }

    public static APIRecad_update getAPIRecadupdate() {

        return RetrofitClient.getClient(BASE_URL).create(APIRecad_update.class);
    }

    public static APIFAdStatus getAPIF_adstatus() {

        return RetrofitClient.getClient(BASE_URL).create(APIFAdStatus.class);
    }

    public static APIRAdStatus getAPIR_adstatus() {

        return RetrofitClient.getClient(BASE_URL).create(APIRAdStatus.class);
    }

    public static APIRFilter getAPIR_filter() {

        return RetrofitClient.getClient(BASE_URL).create(APIRFilter.class);
    }

    public static APIFLogout getAPI_Flogout() {

        return RetrofitClient.getClient(BASE_URL).create(APIFLogout.class);
    }

    public static APIRLogout getAPI_Rlogout() {

        return RetrofitClient.getClient(BASE_URL).create(APIRLogout.class);
    }

    public static APIFMLogin getAPI_FMLogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIFMLogin.class);
    }

    public static APIRMLogin getAPI_RMLogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIRMLogin.class);
    }

    public static APIFMLoginOTP getAPIotp_FMLogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIFMLoginOTP.class);
    }

    public static APIRMLoginOTP  getAPIotp_RMLogin() {

        return RetrofitClient.getClient(BASE_URL).create(APIRMLoginOTP.class);
    }




}
