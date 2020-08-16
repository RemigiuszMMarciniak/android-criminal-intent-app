package edu.rmm.androidcriminalintentapp;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public String convertDate(Date date){
        String pattern = "EEEE, dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean ismRequiresPolice() {
        return mRequiresPolice;
    }

    public void setmRequiresPolice(boolean mRequiresPolice) {
        this.mRequiresPolice = mRequiresPolice;
    }
}
