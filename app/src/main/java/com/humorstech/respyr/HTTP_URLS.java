package com.humorstech.respyr;

public class HTTP_URLS {

    public static String checkPhoneNumber = "https://humorstech.com/humors_app/app_final/check_phone_number.php?";
    //https://humorstech.com/humors_app/app_final/check_phone_number.php?phone_no=5451&country_code=91$IN

    public static String insertNewProfile = "https://humorstech.com/humors_app/app_final/add_new_profile.php?";
    //https://humorstech.com/humors_app/app_final/add_new_profile.php?login_id=01&phone=1234567890&name
    // =Johgfvhje&email=johndoe%40example.com&gender=
    // Male&dob=1990-01-01&age=30&height=180&weight=75&water_consumption=2000&bmi=24.3

    public static String checkEmailExist = "https://humorstech.com/humors_app/app_final/check_email_exist.php?";
    //https://humorstech.com/humors_app/app_final/check_email_exist.php?email=c

    public static String addBloodReport = "https://humorstech.com/humors_app/app_final/add_blood_report.php?";
    //https://humorstech.com/humors_app/app_final/add_blood_report.php?id=123&
    // login_id=user123&profile_id=profile456&diabetic=true&diabetic_values=1,3&dttm=2023-05-30

    public static String addHobbies = "https://humorstech.com/humors_app/app_final/add_hobbies.php?";

    public static String sendOTP = "https://humorstech.com/humors_app/app_final/send_otp.php?";
    //https://humorstech.com/humors_app/app_final/send_otp.php?phone_no=9902552385&country_code=91$IN

    public static String checkOTP = "https://humorstech.com/humors_app/app_final/check_otp.php?";
    //https://humorstech.com/humors_app/app_final/check_otp.php?phone_no=9902552385&country_code=91$IN&otp=671

    public static String checkProfileCount = "https://humorstech.com/humors_app/app_final/fetch_profile_counts.php?";
    //https://humorstech.com/humors_app/app_final/fetch_profile_counts.php?login_id=02--profile count

    public static String fetchProfilesOfLogin = "https://humorstech.com/humors_app/app_final/select_profile.php?";
    //https://humorstech.com/humors_app/app_final/select_profile.php?login_id=RESPYR001

    public static String fetchProfileDataByProfileId="https://humorstech.com/humors_app/app_final/fetch_profile_data.php";
    //https://humorstech.com/humors_app/app_final/fetch_profile_data.php?login_id=RESPYR001&profile_id=profile1


    public static String insertDailyRoutine = "https://humorstech.com/humors_app/app_final/insert_daily_routine_data.php?";
           // "insert_data.php?id=1&login_id=1234&profile_id=5678&dttm=2023-07-24%2012:00:00&water_consumed=200&cigarettes_unit=5&alcohol_unit=2.5&exercise_minutes=60&sleep_hours=8&
    // food_intake=1&food_name=Pizza&food_quantity=2&skip_meal=0"

}
