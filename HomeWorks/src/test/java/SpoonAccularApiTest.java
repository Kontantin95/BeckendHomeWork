import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.hamcrest.Matchers.lessThan;

public class SpoonAccularApiTest {

    private static final String API_KEY = "0970f5c615f14a2a91942df5a213e41c";
    private static final String BASE_URL = "https://api.spoonacular.com";

    private static SpoonacularClient client;

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = BASE_URL;

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", API_KEY)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(1000L))
                .build();

        client = new SpoonacularClient();
    }

    @Test
    void testGetComplexSearch() throws IOException {

        String actually = RestAssured.given()
                .queryParam("query", "pasta")
                .queryParam("cuisine", "italian")
                .log()
                .uri()
                .expect()
                .log()
                .body()
                .when()
                .get("recipes/complexSearch")
                .body()
                .asPrettyString();

        String expected = getResourceAsString("expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );

    }

    @Test
    void testGetComplexSearchPojo() throws IOException {

        RecipesSearchResponse actually = RestAssured.given()
                .queryParam("query", "pasta")
                .queryParam("cuisine", "italian")
                .log()
                .uri()
                .expect()
                .log()
                .body()
                .when()
                .get("recipes/complexSearch")
                .body()
                .as(RecipesSearchResponse.class);

        System.out.println(actually);

        for (RecipesSearchResponseItem item : actually.getResults()) {
            Assertions.assertNotNull(item.getId());
            Assertions.assertTrue(item.getTitle().toLowerCase(Locale.ROOT).contains("pasta"));
            Image image = ImageIO.read(new URL(item.getImage()));
            Assertions.assertNotNull(image);
        }

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

}
