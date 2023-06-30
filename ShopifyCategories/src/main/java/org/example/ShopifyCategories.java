package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShopifyCategories {
    private static final int TOTAL_REQUESTS = 5600;

    private BufferedWriter writer;
    private ArrayList<String> taxonomyList;

    private void formatAndWriteData() throws IOException {
        for (String item : taxonomyList) {
            writer.write(item);
            writer.newLine();
        }
        writer.flush();
        taxonomyList.clear();
    }

    private void fetchDataAndProcess(int categoryNode) throws IOException {
        try {
            URL url = new URL("https://ab6506.myshopify.com/admin/internal/web/graphql/core?operation=ProductTaxonomyList&type=query");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("authority", "ab6506.myshopify.com");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("accept-language", "en-US,en;q=0.9,tr;q=0.8");
            connection.setRequestProperty("cache-control", "no-cache");
            connection.setRequestProperty("caller-pathname", "/admin/products/new");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("cookie", "_master_udr=eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaEpJaWsxWW1ZME9Ua3dZaTAwTVRJM0xUUXdORE10T1RBME5DMDJaRGxsTlRKbVl6TXlNMkVHT2daRlJnPT0iLCJleHAiOiIyMDI1LTA2LTI0VDEyOjAzOjM2LjY5MVoiLCJwdXIiOiJjb29raWUuX21hc3Rlcl91ZHIifX0%3D--4efd61ce69fecdef566e5b1d53f3b336071d6df9; _secure_admin_session_id=4b3db6c130e47441580f3a68b0894a2a; _secure_admin_session_id_csrf=4b3db6c130e47441580f3a68b0894a2a; new_admin=1; iid=1431cb05; koa.sid=MYf1aRB8Nf6BcC5yBgCcTiX6w14zy7mh; koa.sid.sig=PPTgwsc3dnA3anLBNfITMo3lfXo; identity-state=BAhbAA%3D%3D--db43e3715865ca03e3123219ec91e34189be9380; _ab=1; storefront_digest=713ab7235016b15e60c0212d2429aade129037ea1dadec2e18710f8373600688; __ssid=08a422c8-18ef-44a1-86da-72ef87800222");
            connection.setRequestProperty("origin", "https://ab6506.myshopify.com");
            connection.setRequestProperty("pragma", "no-cache");
            connection.setRequestProperty("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-ch-ua-platform", "\"macOS\"");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
            connection.setRequestProperty("x-csrf-token", "DoE0VJsz-GmF15qHbyIkDGRbEQEe1CbmG5do");
            connection.setRequestProperty("x-shopify-web-force-proxy", "1");
            connection.setDoOutput(true);

            String payload = "{\"operationName\":\"ProductTaxonomyList\",\"variables\":{\"taxonomyId\":\"gid://shopify/ProductTaxonomyNode/" + categoryNode + "\",\"childrenOf\":\"gid://shopify/ProductTaxonomyNode/1\"},\"query\":\"query ProductTaxonomyList($childrenOf: ID, $taxonomyId: ID = \\\"gid://shopify/ProductTaxonomyNode/1\\\", $parentTaxonomyId: ID = \\\"gid://shopify/ProductTaxonomyNode/1\\\") {\\n  productTaxonomy(childrenOf: $childrenOf) {\\n    id\\n    name\\n    isRoot\\n    isLeaf\\n    parentId\\n    fullName\\n    level\\n    __typename\\n  }\\n  taxonomyNode: node(id: $taxonomyId) {\\n    ... on ProductTaxonomyNode {\\n      id\\n      name\\n      isRoot\\n      isLeaf\\n      parentId\\n      fullName\\n      level\\n      __typename\\n    }\\n    __typename\\n  }\\n  parentTaxonomyNode: node(id: $parentTaxonomyId) {\\n    ... on ProductTaxonomyNode {\\n      id\\n      name\\n      isRoot\\n      isLeaf\\n      parentId\\n      fullName\\n      level\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            outputStream.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            double percentComplete = ((double) categoryNode / totalRequests) * 100;
            System.out.printf("\rDownloading: %.1f%%", percentComplete);
            System.out.flush();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                JsonNode fullNameNode = rootNode.path("data").path("taxonomyNode").path("fullName");
                String fullName = fullNameNode.asText();

                taxonomyList.add(fullName);

                if (categoryNode % 200 == 0) {
                    formatAndWriteData();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchShopifyCategories() throws IOException {
        String fileName = "shopifyTaxonomy.csv";
        writer = new BufferedWriter(new FileWriter(fileName));
        taxonomyList = new ArrayList<>();
        System.out.println("Fetching Shopify Categories...");

        for (int categoryNode = 1; categoryNode < totalRequests; categoryNode++) {
            fetchDataAndProcess(categoryNode);
        }

        formatAndWriteData();
        writer.close();
        System.out.println("\nDownload completed!");
    }
}
