package org.wenrong.news.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wenrong.news.DetailActivity;
import org.wenrong.news.R;
import org.wenrong.news.entity.NewsItem;

import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 */

public class NewsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<NewsItem> datas;

    /**
     * 单图片布局
     */
    public static final int TYPE_SINGLE = 1;

    /**
     * 多图片布局
     */
    public static final int TYPE_MULTIPART = 2;

    public NewsAdapter(Context context,List<NewsItem> datas){

        this.context = context;
        this.datas = datas;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_MULTIPART){

            View view = LayoutInflater.from(context).inflate(R.layout.item_multipart_pic,parent,false);
            return new MultipartPicViewHolder(view);

        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_single_pic,parent,false);
            return new SinglePicViewHolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        NewsItem item = datas.get(position);
        if(!TextUtils.isEmpty(item.getThumbnail_pic_s02())&&!TextUtils.isEmpty(item.getThumbnail_pic_s03())){

            return TYPE_MULTIPART;

        }else{

            return TYPE_SINGLE;

        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        final NewsItem item = datas.get(position);
        holder.setData(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //点击Item进入详情页
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.putExtra("title",item.getTitle());

                if(Build.VERSION.SDK_INT>=21){

                    String ll_item = v.getContext().getString(R.string.item_transition);
                    String title = v.getContext().getString(R.string.title);
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(((Activity) v.getContext())
                                    , Pair.create(v,ll_item)
                            ,Pair.create(v,title));
                    v.getContext().startActivity(intent,options.toBundle());

                }else{

                    context.startActivity(intent);
                }


            }
        });


    }

    @Override
    public int getItemCount() {

        if(datas != null){

            return datas.size();

        }
        return 0;
    }
}
