# Shopify Category Fetcher

The Shopify Category Fetcher is a Java program that fetches category data from the Shopify and saves it to a CSV file. It uses the Jackson Core library for JSON processing.

## Technologies Used

- Java 17
- Jackson Core 2.15.2

## Usage

1. Ensure that you have Java 17 or a higher version installed on your system.

2. Jackson Core has been added to the dependency section of the pom.xml file to make the changes related to Json.

3. The `ShopifyCategories.jar` program is in the `/ShopifyCategories/out/artifacts/ShopifyCategories_jar`

4. Run the program using the following command below:<br>
```
java -jar /ShopifyCategories/out/artifacts/ShopifyCategories_jar/ShopifyCategories.jar
```
<br>**Note**: Please make sure to provide the correct file path to the JAR file.

4. The program will start fetching category data from the Shopify API. It will display the message "Fetching Shopify Categories..." in the console.

5. As the program fetches the data, it will display the progress of the download. For example:<br>
```
Fetching Shopify Categories...
Downloading: 0.0%
```

6. Once the fetching process is complete, the program will display the message `Download completed!` in the console.

7. The program will generate a CSV file named shopifyTaxonomy.csv. This file will contain the fetched Shopify category data.

## CSV Output Format

The generated CSV file shopifyTaxonomy.csv will have the following format:

```Animals & Pet Supplies
Animals & Pet Supplies > Live Animals
Animals & Pet Supplies > Pet Supplies
Animals & Pet Supplies > Pet Supplies > Bird Supplies
Animals & Pet Supplies > Pet Supplies > Bird Supplies > Bird Cage Accessories
Animals & Pet Supplies > Pet Supplies > Bird Supplies > Bird Cage Accessories > Bird Cage Bird Baths
Animals & Pet Supplies > Pet Supplies > Bird Supplies > Bird Cage Accessories > Bird Cage Food & Water Dishes
Animals & Pet Supplies > Pet Supplies > Bird Supplies > Bird Cages & Stands
Animals & Pet Supplies > Pet Supplies > Bird Supplies > Bird Food
.
.
.
```

The output represents the hierarchical structure of the fetched categories, with each category on a separate line.

Please note that the program fetches a large number of categories, so the execution time may vary depending on the network and API response times.


## Possible Issues

- Ensure that you have an active internet connection as the program fetches data from the Shopify.

- Verify that the provided file path to the JAR file is correct when running the program using the java -jar command.

- If there are any changes to the Shopify API or its response format, the program may need to be updated accordingly.

- Make sure you have proper access credentials and permissions to fetch data from the Shopify.

- If the program is interrupted, the categories will be saved to the file until a certain checkpoint.

- If you encounter any issues or errors, please check the console output for error messages.



## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

## Contact

For any questions or feedback, please contact Murat Levent.

Project Link: [https://github.com/muratlevent/shopify_category_fetcher/](https://github.com/muratlevent/shopify_category_fetcher/)



