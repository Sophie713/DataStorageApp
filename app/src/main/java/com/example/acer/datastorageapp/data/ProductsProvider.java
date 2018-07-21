package com.example.acer.datastorageapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

import static com.example.acer.datastorageapp.data.ProductContract.CONTENT_AUTHORITY;
import static com.example.acer.datastorageapp.data.ProductContract.PATH_PRODUCTS_DATABASE;

public class ProductsProvider extends ContentProvider {

    private ProductsDatabaseHelper productsDatabaseHelper;

    private static final int PRODUCTS_TABLE = 100;
    private static final int PRODUCTS_ITEM_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE, 100);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE + "/#", 101);
    }

    @Override
    public boolean onCreate() {
        productsDatabaseHelper = new ProductsDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
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
