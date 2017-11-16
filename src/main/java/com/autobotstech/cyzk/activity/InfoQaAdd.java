package com.autobotstech.cyzk.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import com.autobotstech.cyzk.util.ImageUtils.PermissionsActivity;
import com.autobotstech.cyzk.util.ImageUtils.PermissionsChecker;
import com.autobotstech.cyzk.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
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
    protected static Uri imageUri;
    private String imagePath;
    private ImageView iv_personal_icon;
    String uploadImagePath;

    private static final int REQUEST_PERMISSION = 1;
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    Bitmap bitmap;
    boolean isClickCamera = false;

    QaAddTask mTask;

    String title,question;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        mPermissionsChecker = new PermissionsChecker(this);
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
     * 打开系统相机
     */
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(InfoQaAdd.this, "com.autobotstech.cyzk.fileprovider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }

        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 从相册选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InfoQaAdd.this);
        builder.setTitle(getResources().getString(R.string.changeportrait_title));
        String[] items = {getResources().getString(R.string.changeportrait_chose), getResources().getString(R.string.changeportrait_photo)};
        builder.setNegativeButton(getResources().getString(R.string.changeportrait_cancel), null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                                startPermissionsActivity();
                            } else {
                                selectFromAlbum();
                            }
                        } else {
                            selectFromAlbum();
                        }
                        break;
                    case TAKE_PICTURE: // 拍照
//检查权限(6.0以上做权限判断)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                                startPermissionsActivity();
                            } else {
                                openCamera();
                            }
                        } else {
                            openCamera();
                        }
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param
     */
    protected void startPhotoZoom() {

        File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
//        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
//        Bitmap photo = Utils.toRoundBitmap(bitmap); // 这个时候的图片已经被处理成圆形的了
        iv_personal_icon.setImageBitmap(bitmap);
        uploadPic(bitmap);
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
//        Toast.makeText(getContext(),imagePath,Toast.LENGTH_SHORT).show();
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            uploadImagePath=imagePath;
        }
    }

    ////////////andoird 4.4以后
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(InfoQaAdd.this, imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = imageUri.getPath();
        }

        startPhotoZoom();
    }
    ////////////andoird 4.4之前
    private void handleImageBeforeKitKat(Intent intent) {
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        startPhotoZoom();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONS);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = InfoQaAdd.this.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (hasSdcard()) {
                        isClickCamera=true;
                        if (resultCode == RESULT_OK) {
                            startPhotoZoom();
                        }
                    } else {
                        Toast.makeText(InfoQaAdd.this, "没有SDCard!", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case CHOOSE_PICTURE:
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                    break;
                case CROP_SMALL_PICTURE:

                    if (isClickCamera) {

                        try {
                            bitmap = BitmapFactory.decodeStream(InfoQaAdd.this.getContentResolver().openInputStream(imageUri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bitmap = BitmapFactory.decodeFile(imagePath);
                    }
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }


}
