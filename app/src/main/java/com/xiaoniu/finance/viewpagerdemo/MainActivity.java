package com.xiaoniu.finance.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVp;
    private TextView mTitle;


    private int beforeDot;//上次点的位置
    private int Data[] = {R.mipmap.user_guide_1, R.mipmap.user_guide_2, R.mipmap.user_guide_3, R.mipmap.user_guide_4,};
    //标题数组
    private String[] mTitles = {"为梦想坚持", "我相信我", "xasdasd", "sdaas"};
    private LinearLayout mDots;

    //调整初始位置
    int initPosition = Integer.MAX_VALUE / 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = findViewById(R.id.vp);
        mTitle = findViewById(R.id.title);
        mDots = findViewById(R.id.dot_container);
        mVp.setAdapter(mPagerAdapter);
        initdot();

        //将位置调整到0
        initPosition = initPosition - initPosition % Data.length;
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             *
             * @param position  当前位置
             * @param positionOffset  偏移像素 除以屏幕密度（320dp）
             * @param positionOffsetPixels  偏移像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTitle.setText(mTitles[position % Data.length]);
                View childAt = mDots.getChildAt(position % Data.length);

                childAt.setBackgroundResource(R.drawable.dot);
                //同时要把之前的点设置恢复

                View childAt1 = mDots.getChildAt(beforeDot);
                childAt1.setBackgroundResource(R.drawable.dot_shape);
                beforeDot = position % Data.length;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initdot() {
        //动态初始化点的个数
        for (int i = 0; i < Data.length; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.dot_size), getResources().getDimensionPixelSize(R.dimen.dot_size));
            dot.setLayoutParams(layoutParams);
            //最后一个点不要间距
            if (i != Data.length - 1) {
                //配置点的间距
                layoutParams.rightMargin = 8;
            }
            //默认第一个点被选中
            if (i == 0) {
                dot.setBackgroundResource(R.drawable.dot);
            } else {
                dot.setBackgroundResource(R.drawable.dot_shape);
            }
            //将点添加点的容器
            mDots.addView(dot);
        }

    }


    private PagerAdapter mPagerAdapter = new PagerAdapter() {


        /**
         *  这个方法不会自己出来要单独重写
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = position % Data.length;
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(Data[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView); //必须要添加进去
            return imageView;
        }

        /**
         *  这个方法不会自己出来要单独重写
         * @param container
         * @param position
         * @return
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
//            return Data.length;
            return Integer.MAX_VALUE;
        }

        /**
         *
         * @param view ViewPager里的孩子
         * @param object 孩子是否被标记
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

}
