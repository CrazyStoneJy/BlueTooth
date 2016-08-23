package com.code.space.androidlib.persistent.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.code.space.androidlib.persistent.dao.IDataBaseOperation;

import java.util.concurrent.ExecutorService;

/**
 * Created by crazystone on 2016/5/27.
 */
public abstract class DataBaseOpeartion implements IDataBaseOperation {

    protected SQLiteOpenHelper mHelper;
    protected SQLiteDatabase db;
    protected Cursor mCursor;

    //must be used
    public void init(SQLiteOpenHelper helper) {
        this.mHelper = helper;
    }

    public Cursor rawQuery(String sql) {
        return rawQuery(sql, null);
    }

    public Cursor rawQuery(String sql, Object[] bindArgs) {
        if (prepare()) {
            if (bindArgs != null && bindArgs.length > 0) {
                String[] args = new String[bindArgs.length];
                for (int i = 0; i < bindArgs.length; i++) {
                    args[i] = bindArgs[i].toString();
                }
                mCursor = db.rawQuery(sql, args);

            } else {
                mCursor = db.rawQuery(sql, null);
            }
        }
        return mCursor;
    }


    public void exeSQL(String sql, Object[] bindArgs) {
        try {
            if (prepare()) {
                db.beginTransaction();
                db.execSQL(sql, bindArgs);
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    public void exeSQL(String sql) {
        try {
            if (prepare()) {
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    public boolean insertWithConflict(String table, ContentValues values, int conflictAlgorithm) {
        if (prepare()) {
            long insertId = db.insertWithOnConflict(table, null, values, conflictAlgorithm);
            if (insertId > 0) return true;
        }
        return false;
    }


    public boolean insert(String table, ContentValues values) {
        return insertWithConflict(table, values, SQLiteDatabase.CONFLICT_NONE);
    }

    @Override
    public boolean prepare() {
        if (mHelper != null) {
            db = mHelper.getWritableDatabase();
            return true;
        }
        return false;
    }


    @Override
    public void close() {
        close(mCursor);
        close(db);
        close(mHelper);
    }

    private void close(SQLiteDatabase db) {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    private void close(SQLiteOpenHelper helper) {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    private void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }


}
