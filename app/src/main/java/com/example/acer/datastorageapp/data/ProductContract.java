package com.example.acer.datastorageapp.data;

import android.provider.BaseColumns;

public final class ProductContract {

    private ProductContract(){}

    public static final class ProductEntry implements BaseColumns{
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String SUPPLIER_NAME = "supplier_name";
        public static final String SUPPLIER_PHONE = "supplier_phone";

        /**
         * SUPPLIERS LIST
         */
        public static final int SUPPLIER_UNKNOWN = 0;
        public static final int SUPPLIER_GOOGLE = 1;
        public static final int SUPPLIER_UDACITY = 2;
        public static final int SUPPLIER_FACEBOOK = 3;
        public static final int SUPPLIER_YOUTUBE = 4;

    }
}