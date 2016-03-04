package com.example.ninatran.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ninatran on 3/3/16.
 */
public class MyGridPagerAdapter extends FragmentGridPagerAdapter{
    private final Context mContext;
    private ArrayList<MRow> mPages;

    public MyGridPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        initPages();
    }
    private void initPages() {
        mPages = new ArrayList<MRow>();

        MRow row1 = new MRow();
        row1.addPages(new MPage("Sen. Dianne Feinstein", "Democrat", R.drawable.dianne, R.drawable.capitol));

        MRow row2 = new MRow();
        row2.addPages(new MPage("Sen. Barbara Boxer", "Democrat", R.drawable.barbara_boxer, R.drawable.capitol));

        MRow row3 = new MRow();
        row3.addPages(new MPage("Rep. Barbara Lee", "Democrat", R.drawable.lee, R.drawable.capitol));
        row3.addPages(new MPage("Vote", "Barbara Lee", R.drawable.lee, R.drawable.capitol));



        mPages.add(row1);
        mPages.add(row2);
        mPages.add(row3);
    }
    @Override
    public MyCard getFragment(int row, int col) {
        MPage page = ((MRow)mPages.get(row)).getPages(col);
        MyCard fragment = MyCard.newInstance(page.mTitle, page.mText, page.mIconId);
        return fragment;
    }

    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getResources().getDrawable(R.drawable.capitol, null);
    }

    public Drawable getBackgroundForPage(int row, int col) {
        MPage page = ((MRow)mPages.get(row)).getPages(col);
        return mContext.getResources().getDrawable(page.mBackgroundId, null);
    }

    @Override
    public int getRowCount() {
        return mPages.size();
    }

    @Override
    public int getColumnCount(int row) {
        return mPages.get(row).size();
    }

    public class MPage {

        public String mTitle;
        public String mText;
        public int mIconId;
        public int mBackgroundId;

        public MPage(String title, String text, int iconId, int backgroundId) {
            this.mTitle = title;
            this.mText = text;
            this.mIconId = iconId;
            this.mBackgroundId = backgroundId;
        }

    }
    public class MRow {

        ArrayList<MPage> mPagesRow = new ArrayList<MPage>();

        public void addPages(MPage page) {
            mPagesRow.add(page);
        }

        public MPage getPages(int index) {
            return mPagesRow.get(index);
        }

        public int size(){
            return mPagesRow.size();
        }
    }
}
