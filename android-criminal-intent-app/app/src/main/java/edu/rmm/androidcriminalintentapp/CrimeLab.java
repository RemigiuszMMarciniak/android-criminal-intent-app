package edu.rmm.androidcriminalintentapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
//        for (int i = 0; i < 100; i++){
//            Crime crime = new Crime();
//            crime.setTitle("Case #" + i);
//            crime.setSolved(i % 2 == 0);
//            crime.setmRequiresPolice(i % 2 == 1);
//            mCrimes.add(crime);
//        }
    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime(final UUID uuid){
        return getCrimes().stream()
                .filter(c -> uuid.equals(c.getId()))
                .findAny()
                .orElse(null);
    }
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
}
