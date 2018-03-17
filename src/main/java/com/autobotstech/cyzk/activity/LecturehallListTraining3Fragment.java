package com.autobotstech.cyzk.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.RecyclerLecturehallListAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LecturehallListTraining3Fragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckLecturehallListTask mTask = null;

    private List<RecyclerItem> lecturehallList;
    RecyclerLecturehallListAdapter recyclerAdapter;
    RecyclerView recyclerView;
    View view;
    Bitmap bitmap = null;

    SearchView mSearchView = null;

    LinearLayout listContainer;
    private View mProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        view = inflater.inflate(R.layout.activity_lecturehall_list, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
        backbutton.setText(R.string.titlebar_training);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.trainingtitle);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewlecturehall);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mSearchView = (SearchView) view.findViewById(R.id.searchView);

        listContainer = (LinearLayout) view.findViewById(R.id.listcontainer);
        mProgressView = (ProgressBar) view.findViewById(R.id.progressbar);
        showProgress(true);

        mTask = new CheckLecturehallListTask(token);
        mTask.execute((Void) null);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    search(newText);
                } else {
                    showProgress(true);
                    mTask = new CheckLecturehallListTask(token);
                    mTask.execute((Void) null);
                }
                return false;
            }
        });

        mSearchView.clearFocus();
        mSearchView.setFocusable(false);

        return view;
    }

    public void search(String searchText) {
        searchText = searchText.trim();
        if ("".equals(searchText)) {
            return;
        }
        List<RecyclerItem> searchList = new ArrayList<RecyclerItem>();
        searchList.addAll(lecturehallList);
        lecturehallList.clear();
        for (int i = 0; i < searchList.size(); i++) {
            RecyclerItem recyclerItem = searchList.get(i);
            if (recyclerItem.getName().contains(searchText)) {
                lecturehallList.add(recyclerItem);
            }
        }
        recyclerAdapter.notifyDataSetChanged();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckLecturehallListTask extends AsyncTask<Void, Void, List> {

        private final String mToken;

        CheckLecturehallListTask(String token) {
            mToken = token;
        }

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            JSONObject obj = new JSONObject();
            lecturehallList = new ArrayList<RecyclerItem>();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.LECTUREHALLTRAINING3, mToken);
                if (obj != null) {
                    try {
                        JSONArray flowArr = obj.getJSONArray("detail");
                        for (int i = 0; i < flowArr.length(); i++) {
                            RecyclerItem recyclerItem = new RecyclerItem();
                            recyclerItem.setId(flowArr.getJSONObject(i).getString("_id"));
                            recyclerItem.setName(flowArr.getJSONObject(i).getString("title"));
                            String createTimeString = flowArr.getJSONObject(i).getString("createTime");
                            Format f = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            String dateString = "";
                            try {
                                date = (Date) f.parseObject(createTimeString);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                dateString = sdf.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            recyclerItem.setCreateTime(dateString);
                            String keyword = flowArr.getJSONObject(i).getString("keyword");
                            recyclerItem.setKeyword("关键字：" + keyword);
                            recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                            boolean hasPortrait = flowArr.getJSONObject(i).getJSONObject("createPerson").has("portrait");
                            if (!hasPortrait) {
                                recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                            } else {
                                String imageString = flowArr.getJSONObject(i).getJSONObject("createPerson").getJSONObject("portrait").getString("small");
                                if ("".equals(imageString)) {
                                    recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                                } else {
                                    InputStream is = httpConnections.httpsGetPDFStream(imageString);
                                    bitmap = BitmapFactory.decodeStream(is);
                                    if (bitmap == null) {
                                        recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                                    } else {
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        recyclerItem.setImage(drawable);
                                    }
                                }
                            }
                            String author = flowArr.getJSONObject(i).getJSONObject("createPerson").getString("name");
                            recyclerItem.setAuthor(author);
                            lecturehallList.add(recyclerItem);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

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
            }

            return lecturehallList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                recyclerAdapter = new RecyclerLecturehallListAdapter(result, appGlobals);
                recyclerView.setAdapter(recyclerAdapter);

            } else {

            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            listContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
