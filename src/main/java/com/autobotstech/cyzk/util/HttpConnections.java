package com.autobotstech.cyzk.util;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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


    public static HttpsURLConnection httpsUrlConnection(Context context, String httpsUrl) throws CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException {

        URL url = new URL(httpsUrl);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        return urlConnection;
    }


    public JSONObject httpsPostLogin(String httpsUrl, String mobile, String password) {

        JSONObject obj = new JSONObject();
        try {
            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);

            String param = "mobile=" + URLEncoder.encode(mobile, "UTF-8")
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
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


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

    public JSONObject httpsGet(String httpsUrl, String token) {


        JSONObject obj = new JSONObject();
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
            int resultCode = urlConnection.getResponseCode();


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


    public JSONObject httpsPost(String httpsUrl, String content, String token) {

        JSONObject obj = new JSONObject();
        try {
            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);

            String param = "answer=" + URLEncoder.encode(content, "UTF-8");
            //设置参数
            urlConnection.setDoOutput(true);   //需要输出
            urlConnection.setDoInput(true);   //需要输入
            urlConnection.setUseCaches(false);  //不允许缓存
            urlConnection.setRequestMethod("POST");   //设置POST方式连接
            //设置请求属性
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Authorization", token);
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            urlConnection.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


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

    public JSONObject httpsPost(String httpsUrl, String title, String question, String token) {

        JSONObject obj = new JSONObject();
        try {
            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);

            String param = "title=" + URLEncoder.encode(title, "UTF-8")
                    + "&question=" + URLEncoder.encode(question, "UTF-8");
            //设置参数
            urlConnection.setDoOutput(true);   //需要输出
            urlConnection.setDoInput(true);   //需要输入
            urlConnection.setUseCaches(false);  //不允许缓存
            urlConnection.setRequestMethod("POST");   //设置POST方式连接
            //设置请求属性
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Authorization", token);
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            urlConnection.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


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

    public JSONObject httpsPost(String httpsUrl, String title, String question, String imagePath, String renameFile, String token) {

        JSONObject obj = new JSONObject();
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(imagePath);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);


            urlConnection.setDoInput(true); // Allow Inputs
            urlConnection.setDoOutput(true); // Allow Outputs
            urlConnection.setUseCaches(false); // Don't use a Cached Copy
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            urlConnection.setRequestProperty("files", renameFile);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // title
            dos.writeBytes("Content-Disposition: form-data; name=\"title\""
                    + lineEnd);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            dos.writeBytes("Content-Length: " + title.length() + lineEnd);
            dos.writeBytes(lineEnd);
            // assign value
            dos.writeBytes(title);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // content
            dos.writeBytes("Content-Disposition: form-data; name=\"question\""
                    + lineEnd);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            dos.writeBytes("Content-Length: " + question.length() + lineEnd);
            dos.writeBytes(lineEnd);
            // assign value
            dos.writeBytes(question);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // send image
            dos.writeBytes("Content-Disposition: form-data; name='files';filename="
                    + renameFile + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte bufferRes[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(bufferRes)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(bufferRes, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONObject httpsUpload(String httpsUrl, String imagePath, String renameFile, String token) {

        JSONObject obj = new JSONObject();
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(imagePath);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            //建立连接
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslSocketFactory);


            urlConnection.setDoInput(true); // Allow Inputs
            urlConnection.setDoOutput(true); // Allow Outputs
            urlConnection.setUseCaches(false); // Don't use a Cached Copy
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            urlConnection.setRequestProperty("files", renameFile);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // send image
            dos.writeBytes("Content-Disposition: form-data; name='files';filename="
                    + renameFile + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte bufferRes[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(bufferRes)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(bufferRes, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONObject httpUpload(String httpsUrl, String imagePath, String renameFile, String token) {


        JSONObject obj = new JSONObject();
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(imagePath);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            //建立连接
            URL url = new URL(httpsUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setDoInput(true); // Allow Inputs
            urlConnection.setDoOutput(true); // Allow Outputs
            urlConnection.setUseCaches(false); // Don't use a Cached Copy
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            urlConnection.setRequestProperty("files", renameFile);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // send image
            dos.writeBytes("Content-Disposition: form-data; name='files';filename="
                    + renameFile + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte bufferRes[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(bufferRes)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(bufferRes, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }



    public JSONObject httpPost(String httpsUrl, String title, String question, String imagePath, String renameFile, String token) {


        JSONObject obj = new JSONObject();
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(imagePath);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            //建立连接
            URL url = new URL(httpsUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setDoInput(true); // Allow Inputs
            urlConnection.setDoOutput(true); // Allow Outputs
            urlConnection.setUseCaches(false); // Don't use a Cached Copy
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            urlConnection.setRequestProperty("files", renameFile);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"question\""
                    + lineEnd);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            dos.writeBytes("Content-Length: " + question.length() + lineEnd);
            dos.writeBytes(lineEnd);

            // assign value
            dos.writeBytes(question);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            // add parameters
            dos.writeBytes("Content-Disposition: form-data; name=\"title\""
                    + lineEnd);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            dos.writeBytes("Content-Length: " + title.length() + lineEnd);
            dos.writeBytes(lineEnd);
            // assign value
            dos.writeBytes(title);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            // send image
            dos.writeBytes("Content-Disposition: form-data; name='files';filename="
                    + renameFile + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte bufferRes[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(bufferRes)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(bufferRes, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                String rev = new String(baos.toByteArray());
                obj = new JSONObject(rev);

            } else {
                System.out.println("连接失败.........");
            }
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public InputStream httpsGetPDFStream(String httpsUrl) {


        InputStream is = null;
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
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            urlConnection.connect();

            //获得响应状态
            int resultCode = urlConnection.getResponseCode();


            if (urlConnection.getResponseCode() < 400) {
                // 获取响应的输入流对象
                is = urlConnection.getInputStream();
                // 创建字节输出流对象
                // 释放资源
//                is.close();


            } else {
                System.out.println("连接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return is;
    }

}
