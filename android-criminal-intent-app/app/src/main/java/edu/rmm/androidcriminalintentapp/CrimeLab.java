package edu.rmm.androidcriminalintentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import edu.rmm.androidcriminalintentapp.database.CrimeBaseHelper;
import edu.rmm.androidcriminalintentapp.database.CrimeCursorWrapper;
import edu.rmm.androidcriminalintentapp.database.CrimeDbSchema;
import edu.rmm.androidcriminalintentapp.database.CrimeDbSchema.CrimeTable;

public class CrimeLab {
    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }
    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }
    public Crime getCrime(final UUID uuid){
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Columns.UUID + " = ?",
                new String[] {uuid.toString()}
        );
        try {
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Columns.UUID,crime.getId().toString());
        contentValues.put(CrimeTable.Columns.TITLE,crime.getTitle());
        contentValues.put(CrimeTable.Columns.DATE,crime.getDate().getTime());
        contentValues.put(CrimeTable.Columns.SOLVED,crime.isSolved() ? 1 : 0);
        contentValues.put(CrimeTable.Columns.SUSPECT,crime.getSuspect());
        return contentValues;
    }
    public void addCrime(Crime c){
        ContentValues contentValues = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,contentValues);
    }
    public File getPhotoFile(Crime crime){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir,crime.getPhotoFilename());
    }
    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues contentValues = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, contentValues, CrimeTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }
    private CrimeCursorWrapper queryCrimes (String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
}
