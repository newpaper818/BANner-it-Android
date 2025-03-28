package com.fitfit.core.model.enums

enum class ScreenDestination(
    val route: String
){
    //sign in
    SIGN_IN("signIn"),

    //main
    MAIN_REPORT("mainReport"),
    MAIN_LOGS("mainLogs"),
    MAIN_MORE("mainMore"),



    //from MAIN_REPORT
    REPORT("report"),
    CAMERA("camera"),
    SEND_REPORT("send_report"),

    //form MAIN_LOGS
    REPORT_LOG_DETAIL("ReportLogDetail"),
    IMAGE("image"),

    //from MAIN_MORE
    SET_DATE_TIME_FORMAT("setDateTimeFormat"),
    SET_THEME("setTheme"),
    ACCOUNT("account"),
    ABOUT("about"),
    DELETE_ACCOUNT("deleteAccount"),
    EDIT_PROFILE("editProfile")
}