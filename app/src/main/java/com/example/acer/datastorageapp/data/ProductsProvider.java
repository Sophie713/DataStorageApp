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

    public static final String MY_TAG = "xyz";
    private static final int PRODUCTS_TABLE = 100;
    private static final int PRODUCT_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE, PRODUCTS_TABLE);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS_DATABASE + "/#", PRODUCT_ID);
    }

    private ProductsDatabaseHelper productsDatabaseHelper;

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
                Log.e(MY_TAG, uri.toString() + " invalid uri");
                throw new IllegalArgumentException();

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS_TABLE:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uri_match = uriMatcher.match(uri);
        switch ((uri_match)) {
            case PRODUCTS_TABLE:
                insertProduct(uri, values);
            default:
                Log.e("xyz", uri.toString() + " invalid uri");
                throw new IllegalArgumentException("URI " + uri + " has not been recognized");
        }
    }

    public Uri insertProduct(Uri uri, ContentValues values) {

        checkProductName(values);
        checkPrice(values);
        checkQuantity(values);

        SQLiteDatabase database = productsDatabaseHelper.getWritableDatabase();

        long id = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(MY_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = productsDatabaseHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS_TABLE:
                rowsDeleted = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS_TABLE:
                return updateProduct(uri, values, selection, selectionArgs);
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Couldn't update " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = productsDatabaseHelper.getWritableDatabase();

        int rowsUpdated = database.update(ProductContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private void checkProductName(ContentValues values) {
        String name = values.getAsString(ProductContract.ProductEntry.PRODUCT_NAME);
        if (name == null) {
            Log.e(MY_TAG, "Product name not found");
            throw new IllegalArgumentException();
        }
    }

    private void checkPrice(ContentValues values) {
        Integer mPrice = values.getAsInteger(ProductContract.ProductEntry.PRICE);
        if (mPrice == null || mPrice < 0) {
            Log.e(MY_TAG, "Price not found or invalid");
            throw new IllegalArgumentException("Invalid price");
        }
    }

    private void checkQuantity(ContentValues values) {
        Integer mQuantity = values.getAsInteger(ProductContract.ProductEntry.QUANTITY);
        if (mQuantity == null || mQuantity < 0) {
            Log.e(MY_TAG, "Quantity not found or invalid, setting to 0");
            throw new IllegalArgumentException();
        }
    }

}
