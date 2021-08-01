import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashwathy menon on 25-12-2017.
 */

public class Database extends SQLiteOpenHelper{

    SQLiteDatabase db;
    Context context;
    public Database(Context context)
    {
        super(context,"BMIdb",null,1);
        this.context=context;
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("Create table bmi(recDate TEXT PRIMARY KEY,finalbmi TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
db.execSQL("DROP TABLE IF EXISTS BMIdb");
        onCreate(db);

    }
    public  long addRecord(String recDate,String finalbmi)
    {
        ContentValues values=new ContentValues();
        values.put("recDate",recDate);
        values.put("finalbmi",finalbmi);
        long rid=db.insert("bmi",null,values);
        if(rid<0)
            Toast.makeText(context,"BMI for today has already been recorded",Toast.LENGTH_SHORT);
        else
            Toast.makeText(context,"BMI Recorded",Toast.LENGTH_SHORT);
        return rid;
    }
    public ArrayList<String> viewHis()
    {
        Cursor cursor=db.query("bmi",null,null,null,null,null,null);
        cursor.moveToFirst();
        ArrayList <String> a=new ArrayList<>();
        if(cursor.getCount()>0&&cursor!=null)
        {
            do {
                String recDate=cursor.getString(0);
                String finalbmi=cursor.getString(1);
                String f=recDate+"\n"+finalbmi;
                a.add(f);
            }while (cursor.moveToNext());
        }
        return  a;
    }
}
