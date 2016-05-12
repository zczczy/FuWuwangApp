package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.AllCity;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.sortlistview.CharacterParser;
import com.zczczy.leo.fuwuwangapp.sortlistview.ClearEditText;
import com.zczczy.leo.fuwuwangapp.sortlistview.PinyinComparator;
import com.zczczy.leo.fuwuwangapp.sortlistview.SideBar;
import com.zczczy.leo.fuwuwangapp.sortlistview.SortAdapter;
import com.zczczy.leo.fuwuwangapp.sortlistview.SortModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_city_choose)
public class CityChooseActivity extends BaseActivity {

    @ViewById
    ListView country_lvcountry;

    @ViewById
    SideBar sideBar;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    ClearEditText filter_edit;

    @ViewById
    TextView dialog;

    @Extra
    String flagType;

    CharacterParser characterParser;

    List<SortModel> SourceDateList;

    PinyinComparator pinyinComparator;

    SortAdapter adapter;

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        initViews();
    }

    void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    country_lvcountry.setSelection(position);
                }

            }
        });

        //全国
        if ("2".equals(flagType)) {
            TextView textViews = new TextView(this);
            textViews.setBackgroundColor(Color.parseColor("#E0E0E0"));
            textViews.setHeight(55);
            country_lvcountry.addHeaderView(textViews, null, false);
            TextView textView = new TextView(this);
            textView.setText("全国");
            textView.setPadding(35, 10, 0, 10);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(20, 10, 0, 10);
            textView.setHeight(75);
            textView.setTextColor(Color.parseColor("#336598"));

            country_lvcountry.addHeaderView(textView);

        }

        country_lvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if ("2".equals(flagType)) {
                    if (isNetworkAvailable(CityChooseActivity.this)) {
                        closeInputMethod(CityChooseActivity.this);
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();

                        if (position == 1) {
                            bundle.putString("ncitycode", "");
                            bundle.putString("ncity", "全国");
                        } else {
                            bundle.putString("ncitycode", ((SortModel) adapter.getItem(position - 2)).getCode());
                            bundle.putString("ncity", ((SortModel) adapter.getItem(position - 2)).getName());
                        }
                        resultIntent.putExtras(bundle);
                        CityChooseActivity.this.setResult(RESULT_OK, resultIntent);
                        CityChooseActivity.this.finish();
                    } else {
                        Toast.makeText(CityChooseActivity.this, no_net, Toast.LENGTH_SHORT).show();
                    }
                }
                if ("1".equals(flagType)) {
                    if (isNetworkAvailable(CityChooseActivity.this)) {
                        closeInputMethod(CityChooseActivity.this);
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();

                        if (position == 1) {
                            bundle.putString("ncitycode", "");
                            bundle.putString("ncity", "全国");

                        } else {
                            bundle.putString("ncitycode", ((SortModel) adapter.getItem(position)).getCode());
                            bundle.putString("ncity", ((SortModel) adapter.getItem(position)).getName());
                        }
                        resultIntent.putExtras(bundle);
                        CityChooseActivity.this.setResult(RESULT_OK, resultIntent);
                        CityChooseActivity.this.finish();
                    } else {
                        Toast.makeText(CityChooseActivity.this, no_net, Toast.LENGTH_SHORT).show();
                    }
                }
                if ("3".equals(flagType)) {
                    if (isNetworkAvailable(CityChooseActivity.this)) {
                        closeInputMethod(CityChooseActivity.this);
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();

                        if (position == 1) {
                            bundle.putString("ncitycode", "");
                            bundle.putString("ncity", "全国");

                        } else {
                            bundle.putString("ncitycode", ((SortModel) adapter.getItem(position)).getCode());
                            bundle.putString("ncity", ((SortModel) adapter.getItem(position)).getName());
                        }
                        resultIntent.putExtras(bundle);
                        CityChooseActivity.this.setResult(RESULT_OK, resultIntent);
                        CityChooseActivity.this.finish();
                    } else {
                        Toast.makeText(CityChooseActivity.this, no_net, Toast.LENGTH_SHORT).show();
                    }
                }
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                //Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName
                // (), Toast.LENGTH_SHORT).show();
            }
        });

        //SourceDateList = filledData(getResources().getStringArray(R.array.date));
        if ("3".equals(flagType)) {
            getBindOfService();
        } else {
            getBind();
        }
        filter_edit = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        filter_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Background
    void getBindOfService() {
        BaseModelJson<List<NewCity>> bmj = myRestClient.getCityListByProvinceId("");
        if (bmj == null) {
            setBind(null);
        } else {
            BaseModelJson<List<AllCity>> result = new BaseModelJson<>();
            result.Data = new ArrayList<>();
            result.Successful = bmj.Successful;
            result.Error = bmj.Error;
            for (NewCity newCity : bmj.Data) {
                AllCity allCity = new AllCity();
                allCity.ccode = newCity.CityId;
                allCity.cname = newCity.CityName;
                result.Data.add(allCity);
            }
            setBind(result);
        }
    }


    @Background
    void getBind() {
        BaseModelJson<List<AllCity>> bmj = myRestClient.GetAllCity();
        setBind(bmj);
    }

    @UiThread
    void setBind(BaseModelJson<List<AllCity>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null && bmj.Successful) {
            SourceDateList = filledData(bmj.Data);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            adapter = new SortAdapter(this, SourceDateList);
            country_lvcountry.setAdapter(adapter);
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<AllCity> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getCname());
            sortModel.setCode(date.get(i).getCcode());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getCname());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

}
