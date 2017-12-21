package org.wenrong.news.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wenrong.news.R;
import org.wenrong.news.adapter.NewsAdapter;
import org.wenrong.news.entity.NewsItem;
import org.wenrong.news.iview.INewsView;
import org.wenrong.news.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 */

public abstract class BaseFragment extends Fragment implements INewsView {

    protected NewsPresenter presenter;
    public List<NewsItem> news = new ArrayList<>();
    public NewsAdapter adapter;
    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        presenter = new NewsPresenter(this.getContext(), this);
        View root = inflater.from(this.getContext()).inflate(R.layout.fragment_news, null);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNewsFromNet(setNewsType());
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutManager = new LinearLayoutManager(this.getContext());
        initData();
        presenter.getNewsFromLocal(setNewsType());

    }


    private void initData() {

        adapter = new NewsAdapter(this.getContext(), news);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public abstract String setNewsType();

    @Override
    public void getNewsFromNet(List<? extends NewsItem> news) {

        int updataCount = 0;

        for (NewsItem item : news) {

            if (!this.news.contains(item)) {

                this.news.add(0, item);
                updataCount++;

            }

        }

        if (updataCount == 0) {

            BaseApplication.getApplication().getmToast().show("暂无更新");

        } else {

            BaseApplication.getApplication().getmToast().show("已更新" + updataCount + "条新闻");
            this.adapter.notifyDataSetChanged();
        }


        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPause() {

        if (refreshLayout != null && refreshLayout.isRefreshing()) {

            refreshLayout.setRefreshing(false);
        }

        super.onPause();
    }

    @Override
    public void loadMore(List<? extends NewsItem> news) {

    }

    @Override
    public void refresh(List<? extends NewsItem> news) {

    }

    @Override
    public void getNewsFromLocal(List<? extends NewsItem> news) {

        this.news.addAll(news);
        this.adapter.notifyDataSetChanged();

        //本地无数据,从网上获取数据
        if (news.size() == 0) {

            presenter.getNewsFromNet(setNewsType());

        }

        refreshLayout.setRefreshing(false);
    }
}
