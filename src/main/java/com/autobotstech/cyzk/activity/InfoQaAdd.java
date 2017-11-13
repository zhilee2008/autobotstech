package com.autobotstech.cyzk.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;
import com.autobotstech.cyzk.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;


public class InfoQaAdd extends AppCompatActivity {
    private AppGlobals appGlobals;
    SharedPreferences sp;
    private String token;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    String uploadImagePath;

    QaAddTask mTask;

    String title,question;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);
        iv_personal_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });

        Button save = (Button) findViewById(R.id.submit);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                title = ((TextView)findViewById(R.id.qatitle)).getText().toString();
                question = ((EditText)findViewById(R.id.qacontent)).getText().toString();

                mTask = new QaAddTask(token);
                mTask.execute((Void) null);

            }
        });

    }


    public class QaAddTask extends AsyncTask<Void, Void, String> {

        private final String mToken;

        QaAddTask(String token) {
            mToken = token;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            String result="";

            try {
                HttpConnections httpConnections = new HttpConnections(InfoQaAdd.this.getApplicationContext());
                obj = httpConnections.httpsPost(Constants.URL_PREFIX + Constants.FORUMS_ADD_QUESTION, title,question,uploadImagePath,"uploadfile.png", mToken);
                result = obj.getString("result");
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            mTask = null;
                if("success".equals(result)){
                    Toast.makeText(InfoQaAdd.this,"发布问题成功",Toast.LENGTH_SHORT).show();
                    InfoQaAdd.this.finish();

                } else {
                    Toast.makeText(InfoQaAdd.this,"发布问题失败",Toast.LENGTH_SHORT).show();
                }

        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }





    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.changeportrait_title));
        String[] items = {getResources().getString(R.string.changeportrait_chose), getResources().getString(R.string.changeportrait_photo)};
        builder.setNegativeButton(getResources().getString(R.string.changeportrait_cancel), null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            iv_personal_icon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
//        Toast.makeText(this,imagePath,Toast.LENGTH_SHORT).show();
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            // 拿着imagePath上传了
            // ...
            uploadImagePath=imagePath;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

}
