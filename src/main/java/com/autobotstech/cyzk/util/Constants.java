package com.autobotstech.cyzk.util;

/**
 * Created by zhi on 28/06/2017.
 */

public class Constants {


    //    public static String HTTP_PREFIX="https://www.autobotstech.com:9443";
//    public static String HTTPS_PREFIX="https://www.autobotstech.com:5000";
//    public static String URL_PREFIX="https://www.autobotstech.com:9443";
    public static String URL_PREFIX = "https://www.autobotstech.com:9443";
//    public static String URL_PREFIX_HTTP="https://www.autobotstech.com:9443";

    public static String LOGIN = "/users/login";

    //?businessType=1&vehicleType=J2&carStandard=G1&useProperty=S1
    public static String CHECK_FLOW = "/inspects/querybycondition";

    public static String CHECK_FLOW_BY_ID = "/inspects/query";

    public static String CHECK_FLOW_CHART = "/flowcharts/query";

    public static String LECTUREHALL = "/leturehalls/all";

    public static String LECTUREHALL_DETAIL = "/leturehalls/queryOneById/";

    public static String FORUMS = "/forums/all";

    public static String FORUMS_DETAIL = "/forums/queryOneById/";

    public static String FORUMS_DETAIL_ANSWERS = "/forums/queryAnswerById/";

    public static String SPECIALTOPICS = "/specialtopics/queryAll";

    public static String SPECIALTOPICS_INFOTYPE1 = "/specialtopics/queryAllByConsultType/车管动态";
    public static String SPECIALTOPICS_INFOTYPE2 = "/specialtopics/queryAllByConsultType/专题跟踪";
    public static String SPECIALTOPICS_INFOTYPE3 = "/specialtopics/queryAllByConsultType/专家解读";

    public static String SPECIALTOPICS_DETAIL = "/specialtopics/queryOneById/";

    public static String EXPERIENCES = "/experiences/queryAll?notification=true";

    public static String EXPERIENCES_DETAIL = "/experiences/queryOneById/";

    public static String TECHSUPPORTS = "/techsupports/all";


}
