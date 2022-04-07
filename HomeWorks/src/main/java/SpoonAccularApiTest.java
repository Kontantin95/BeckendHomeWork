import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class SpoonAccularApiTest {

    private static final String API_KEY = "36983fdf63194284b380b5b5e5a19878";
    private static final String API_KEY = "0970f5c615f14a2a91942df5a213e41c";
    private static final String BASE_URL = "https://api.spoonacular.com";

    private static SpoonacularClient client;

    @BeforeAll
    static void beforeAll() {

        @@ -38,6 +43,8 @@ static void beforeAll() {
                .expectStatusCode(200)
                    .expectResponseTime(lessThan(1000L))
                    .build();

            client = new SpoonacularClient();
        }

        @Test
        @@ -93,4 +100,26 @@ void testGetComplexSearchPojo() throws IOException {

        }

        @Test
        void testProductSearchGrocery() throws IOException {

            SearchGroceryProductsResponse products = client.findAllProducts(
                    SearchGroceryProductsRequest.builder()
                            .query("pasta")
                            .minCalories(10L)
                            .maxCalories(1000L)
                            .number(3L)
                            .build()
            );

            String expected = getResourceAsString("products.json");

            JsonAssert.assertJsonEquals(
                    expected,
                    products,
                    JsonAssert.when(IGNORING_ARRAY_ORDER)
            );

        }

    }
