package com.dj.photoview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends BaseActivity {
    private ViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        mViewPager = (HackyViewPager) findViewById(R.id.iv_photo);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setCurrentItem(2);
    }


    static class SamplePagerAdapter extends PagerAdapter {
        //这里暂时写死了，实际情况中要从服务端获取图片地址结合，传过来
        private static final String[] url = {"https://yn.hllx1.cn/uploads/userfiles/1/images/pageimg/20200824/1-200R4094Fb.jpg",
                "https://yn.hllx1.cn/uploads/userfiles/1/images/pageimg/20200824/1-200R4094H55.jpg",
                "https://yn.hllx1.cn/uploads/userfiles/1/images/pageimg/20200824/1-200R40950294.jpg",
                "https://yn.hllx1.cn/uploads/userfiles/1/images/pageimg/20200824/1-200R41000441.jpg",
                "https://yn.hllx1.cn/uploads/userfiles/1/images/pageimg/20200824/1-200R41000503.jpg"};

        @Override
        public int getCount() {
            return url.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

//            Picasso.with(container.getContext())
//                    .load(url[position])
//                    .into(photoView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            attacher.update();
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });

            Glide.with(container.getContext())
                    .load(url[position])
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            attacher.update();
                            return false;
                        }
                    })
                    .into(photoView);

            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            LogUtils.e("销毁view");
            //每次当之前的view滑出去之后，进行remove操作，防止内存溢出
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
