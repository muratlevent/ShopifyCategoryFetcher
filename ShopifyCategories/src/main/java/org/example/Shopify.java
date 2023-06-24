package org.example;

import java.io.IOException;

public class Shopify {
    public static void main(String[] args) throws IOException {
        ShopifyCategories shopify = new ShopifyCategories();
        shopify.fetchShopifyCategories();
    }
}