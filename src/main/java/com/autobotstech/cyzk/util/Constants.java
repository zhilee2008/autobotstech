package com.autobotstech.cyzk.util;

/**
 * Created by zhi on 28/06/2017.
 */

public class Constants {


    public static String URL_PREFIX_DEV = "https://www.autobotstech.com:9443";
    public static String URL_PREFIX = "https://chayan.autobotstech.com:9443";
    public static String URL_PREFIX_HTTP = "http://10.0.2.2:9000";

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
    //添加答案 /addanswer/:questionid
    public static String FORUMS_ADD_ANSWER = "/forums/addanswer/";

    public static String FORUMS_MY_QUESTION = "/forums/my";

    //添加问题
    public static String FORUMS_ADD_QUESTION = "/forums/addquestion2";

    public static String SPECIALTOPICS = "/specialtopics/queryAll";

    public static String SPECIALTOPICS_INFOTYPE1 = "/specialtopics/queryAllByConsultType/车管动态";
    public static String SPECIALTOPICS_INFOTYPE2 = "/specialtopics/queryAllByConsultType/专题跟踪";
    public static String SPECIALTOPICS_INFOTYPE3 = "/specialtopics/queryAllByConsultType/专家解读";
    public static String SPECIALTOPICS_INFOTYPE4 = "/specialtopics/queryAllByConsultType/疑例解析";

    public static String SPECIALTOPICS_DETAIL = "/specialtopics/queryOneById/";

    public static String EXPERIENCES = "/experiences/queryAll?notification=true";

    public static String EXPERIENCES_DETAIL = "/experiences/queryOneById/";

    public static String TECHSUPPORTS = "/techsupports/queryAllByFenlei/faguibiaozhun?pagecount=1000";
    public static String TECHSUPPORTS_XYCL = "/techsupports/queryAllByFenlei/xianyicheliang";

    public static String UPLOAD_IMAGE = "/users/upload";
    public static String GET_USERE = "/users/mobile/";

    public static String CHANGE_PWD = "/users/change/password/";

    public static String LECTUREHALLTRAINING2 = "/leturehalls//queryByZhuanlan/zhengcefagui";
    public static String LECTUREHALLTRAINING3 = "/leturehalls/queryByZhuanlan/jishubiaozhun";
    public static String LECTUREHALLTRAINING4 = "/leturehalls/queryByZhuanlan/jianchashizhan";
    public static String LECTUREHALLTRAINING5 = "/leturehalls/queryByZhuanlan/others";


}
