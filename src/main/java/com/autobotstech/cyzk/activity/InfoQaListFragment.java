package com.autobotstech.cyzk.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.autobotstech.cyzk.adapter.RecyclerQaListAdapter;
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


public class InfoQaListFragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckFlowListTask mTask = null;

    private List<RecyclerItem> checkFlowList;
    RecyclerQaListAdapter recyclerAdapter;
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


        view = inflater.inflate(R.layout.activity_info_qa_list, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);

        backbutton.setVisibility(View.INVISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.titlebar_auditorium);

        Button messageButton = (Button) vg.findViewById(R.id.button_message);
        messageButton.setVisibility(View.VISIBLE);
        Drawable drawable = ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.ic_add_message, null);
        drawable.setBounds(0, 0, 100, 100);
        messageButton.setCompoundDrawables(null, null, drawable, null);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getContext(), InfoQaAdd.class);
                getContext().startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mSearchView = (SearchView) view.findViewById(R.id.searchView);

        listContainer = (LinearLayout) view.findViewById(R.id.listcontainer);
        mProgressView = (ProgressBar) view.findViewById(R.id.progressbar);
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
                    mTask = new CheckFlowListTask(token);
                    mTask.execute((Void) null);
                }
                return false;
            }
        });

        showProgress(true);

        mTask = new CheckFlowListTask(token);
        mTask.execute((Void) null);

        return view;


//        recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerviewinfo);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        appGlobals = (AppGlobals) getActivity().getApplication();
//
//        mCheckFlowListTask = new CheckFlowListTask(token);
//        mCheckFlowListTask.execute((Void) null);

    }

    public void search(String searchText) {
        searchText = searchText.trim();
        if ("".equals(searchText)) {
            return;
        }
        List<RecyclerItem> searchList = new ArrayList<RecyclerItem>();
        searchList.addAll(checkFlowList);
        checkFlowList.clear();
        for (int i = 0; i < searchList.size(); i++) {
            RecyclerItem recyclerItem = searchList.get(i);
            if (recyclerItem.getName().contains(searchText) || recyclerItem.getKeyword().contains(searchText)) {
                checkFlowList.add(recyclerItem);
            }
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(), "resume", Toast.LENGTH_SHORT).show();
        mTask = new CheckFlowListTask(token);
        mTask.execute((Void) null);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckFlowListTask extends AsyncTask<Void, Void, List> {

        private final String mToken;

        CheckFlowListTask(String token) {
            mToken = token;
        }

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            checkFlowList = new ArrayList<RecyclerItem>();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS, mToken);
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
                            boolean hasPortrait = flowArr.getJSONObject(i).getJSONObject("createPerson").has("portrait");
                            if (!hasPortrait) {
                                recyclerItem.setImage(ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.default_personal, null));
                            } else {
                                String imageString = flowArr.getJSONObject(i).getJSONObject("createPerson").getJSONObject("portrait").getString("small");
                                if ("".equals(imageString)) {
                                    recyclerItem.setImage(ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.default_personal, null));
//                                   recyclerItem.setImage(ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.default_personal, null));
                                } else {
                                    InputStream is = httpConnections.httpsGetPDFStream(imageString);
                                    bitmap = BitmapFactory.decodeStream(is);
                                    if (bitmap == null) {
                                        recyclerItem.setImage(ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.default_personal, null));
//                                       recyclerItem.setImage(ResourcesCompat.getDrawable(AppGlobals.getContext().getResources(), R.drawable.default_personal, null));
                                    } else {
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        recyclerItem.setImage(drawable);
                                    }
                                }
                            }

                            String author = flowArr.getJSONObject(i).getJSONObject("createPerson").getString("name");
                            recyclerItem.setAuthor(author);
                            String questionStr = (flowArr.getJSONObject(i).getString("question")).length() < 20 ? flowArr.getJSONObject(i).getString("question") : flowArr.getJSONObject(i).getString("question").substring(0, 20) + "...";
                            recyclerItem.setKeyword(questionStr);

                            checkFlowList.add(recyclerItem);

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

            return checkFlowList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                recyclerAdapter = new RecyclerQaListAdapter(result, appGlobals);
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
            int shortAnimTime = AppGlobals.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

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
