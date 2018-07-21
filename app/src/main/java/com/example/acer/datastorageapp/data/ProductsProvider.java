package com.example.acer.datastorageapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

import static com.example.acer.datastorageapp.data.ProductContract.CONTENT_AUTHORITY;
import static com.example.acer.datastorageapp.data.ProductContract.PATH_PRODUCTS_DATABASE;

public class ProductsProvider extends ContentProvider {

    private ProductsDatabaseHelper productsDatabaseHelper;

    private static final int PRODUCTS_TABLE = 100;
    private static final int PRODUCT_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE, PRODUCTS_TABLE);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE + "/#", PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        productsDatabaseHelper = new ProductsDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = productsDatabaseHelper.getReadableDatabase();
        Cursor cursor;
        int uri_match = uriMatcher.match(uri);
        switch (uri_match) {
            case PRODUCTS_TABLE:
                cursor = database.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                Log.e("xyz", uri.toString() + " invalid uri");
                throw new IllegalArgumentException("URI " + uri + " has not been recognized");

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = productsDatabaseHelper.getWritableDatabase();
        Cursor cursor;
        int uri_match = uriMatcher.match(uri);
        switch ((uri_match)) {
            case PRODUCTS_TABLE:
                
            default:
                Log.e("xyz", uri.toString() + " invalid uri");
                throw new IllegalArgumentException("URI " + uri + " has not been recognized");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
