package com.example.ninatran.represent;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ninatran on 3/11/16.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {
    private final Context mContext;
    private final String mData;
    private final String mCounty;
    private final String mState;
    private final String mObamaVote;
    private final String mRomneyVote;
    private ArrayList<MRow> mPages;

    private String[] extracted_data;
    public GridPagerAdapter(Context context, FragmentManager fm, String data) {
        super(fm);
        mContext = context;
        extracted_data = data.split("\\|");
        mData = extracted_data[0];
        mCounty = extracted_data[1];
        mState = extracted_data[2];
        mObamaVote = extracted_data[3];
        mRomneyVote = extracted_data[4];
        initPages();
    }

    private void initPages() {
        mPages = new ArrayList<MRow>();
        try {
            JSONObject jsonObject = new JSONObject(mData);
            JSONArray rep_array = jsonObject.getJSONArray("results");


            for (int i = 0; i < rep_array.length(); i++) {
                String full_title;
                JSONObject repOject = rep_array.getJSONObject(i);
                String first_name = repOject.getString("first_name");
                String last_name = repOject.getString("last_name");
                String full_name = first_name + " " + last_name;

                // Get Title
                String chamber = repOject.getString("chamber");
                String title = "";
                if (chamber.equals("senate")) {
                    title = "Sen.";
                }
                if (chamber.equals("house")) {
                    title = "Rep.";
                }
                full_title = title + " " + full_name;

                String mParty = repOject.getString("party");
                String party = "";
                if (mParty.equals("D")) {
                    party = "Democrat";
                } else if (mParty.equals("R")) {
                    party = "Republican";
                } else if (mParty.equals("I")){
                    party = "Independent";
                }

                // Add rep_id and term end
                String rep_id = repOject.getString("bioguide_id");
                String term_end = repOject.getString("term_end");

                MRow row = new MRow();
                row.addPages(new MPage(full_title, party, R.drawable.person, R.drawable.blue_star, term_end, rep_id, mCounty, mState,
                                    mObamaVote, mRomneyVote));
                mPages.add(row);
                if (title.equals("Rep.")) {
                    row.addPages(new MPage("Vote", " ", R.drawable.person, R.drawable.blue_star, term_end, rep_id, mCounty, mState,
                            mObamaVote, mRomneyVote));
                }



            }
        } catch (Exception e) {
            Log.d("E", "ERROR");
        }
    }
    @Override
    public MyCard getFragment(int row, int col) {
        MPage page = ((MRow)mPages.get(row)).getPages(col);
        MyCard fragment = MyCard.newInstance(page.mTitle, page.mText, page.mIconId, page.mTermEnd, page.mRepID, page.mCounty,
                                            page.mState, page.mObama, page.mRomney);
        return fragment;
    }

    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getResources().getDrawable(R.drawable.blue_star, null);
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
        public String mTermEnd;
        public String mRepID;
        public String mCounty;
        public String mState;
        public String mObama;
        public String mRomney;

        public MPage(String title, String text, int iconId, int backgroundId, String term_end, String repID, String county, String state,
                        String obama, String romney) {
            this.mTitle = title;
            this.mText = text;
            this.mIconId = iconId;
            this.mBackgroundId = backgroundId;
            this.mTermEnd = term_end;
            this.mRepID = repID;
            this.mCounty = county;
            this.mState = state;
            this.mObama = obama;
            this.mRomney = romney;
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
