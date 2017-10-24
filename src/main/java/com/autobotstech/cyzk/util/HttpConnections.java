package com.autobotstech.cyzk.util;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by zhi on 28/06/2017.
 */

public class HttpConnections {
    private static final String TYPE = "X.509";
    private static final String PROTOCOL = "TLS";
    private static SSLContext sslContext;
    private static SSLSocketFactory sslSocketFactory;


    public HttpConnections(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance(TYPE);
        InputStream in = context.getAssets().open("android.crt");
        Certificate cartificate = cf.generateCertificate(in);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setCertificateEntry("trust", cartificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        sslContext = SSLContext.getInstance(PROTOCOL);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        sslSocketFactory = sslContext.getSocketFactory();
    }


    public static HttpsURLConnection httpsUrlConnection(Context context,String httpsUrl) throws CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException {

        URL url = new URL(httpsUrl);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        return urlConnection;
    }


    public JSONObject httpsPostLogin(String httpsUrl,String mobile,String password){

        JSONObject obj =new JSONObject();
        try {
            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);

            String param= "mobile=" + URLEncoder.encode(mobile, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");
            //设置参数
            urlConnection.setDoOutput(true);   //需要输出
            urlConnection.setDoInput(true);   //需要输入
            urlConnection.setUseCaches(false);  //不允许缓存
            urlConnection.setRequestMethod("POST");   //设置POST方式连接
            //设置请求属性
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            urlConnection.setRequestProperty("Charset", "UTF-8");
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            urlConnection.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos=new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode=urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONObject httpsGet(String httpsUrl,String token){



        JSONObject obj =new JSONObject();
        try {
            //?businessType=1&carStandard=G1&useProperty=S1&vehicleType=J1
//            httpsUrl="https://autobotstech.com:9443/inspects/querybycondition"+conditionString;
//            httpsUrl=Constants.HTTPS_PREFIX+Constants.CHECK_FLOW+conditionString;
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);
            //设置请求属性
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Authorization", token);
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            urlConnection.connect();

            //获得响应状态
            int resultCode=urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
