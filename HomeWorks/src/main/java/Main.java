import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        SpoonacularClient client = new SpoonacularClient();

        AutoCompleteProductResponse pasta = client.autocomplete("pasta", 3L);

        System.out.println(pasta);

        SearchGroceryProductsResponse products = client.findAllProducts(
                SearchGroceryProductsRequest.builder()
                        .query("pasta")
                        .minCalories(10L)
                        .maxCalories(1000L)
                        .number(3L)
                        .build()
        );

        products.getProducts().forEach(System.out::println);
    }
}
