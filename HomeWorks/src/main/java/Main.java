import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        public static void main(String[] args) throws InterruptedException, IOException {

            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                    .build(Resources.getResourceAsStream("myBatisConfig.xml"));

            SqlSession session = sessionFactory.openSession();

            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

            Products product = productsMapper.selectByPrimaryKey(3L);
            log.info("product: {}", product);

            ProductsExample filter = new ProductsExample();

            filter.createCriteria()
                    .andTitleGreaterThan("a")
                    .andTitleLessThan("h");

            List<Products> products = productsMapper.selectByExample(filter);
            products.forEach(p -> log.info("product: {}", p));

            Products newProduct = new Products();
            newProduct.setPrice(12);
            newProduct.setTitle("New product");
            newProduct.setCategoryId(1L);

            SpoonacularClient client = new SpoonacularClient();
            productsMapper.insertSelective(newProduct);

            AutoCompleteProductResponse pasta = client.autocomplete("pasta", 3L);
            filter.clear();
            filter.createCriteria()
                    .andTitleEqualTo("New product");

            System.out.println(pasta);
            List<Products> newProducts = productsMapper.selectByExample(filter);

            SearchGroceryProductsResponse products = client.findAllProducts(
                    SearchGroceryProductsRequest.builder()
                            .query("pasta")
                            .minCalories(10L)
                            .maxCalories(1000L)
                            .number(3L)
                            .build()
            );
            newProducts.forEach(p -> log.info("product new: {}", p));
            products.getProducts().forEach(System.out::println);
            session.commit();
        }
    }

import java.time.Duration;
import java.util.concurrent.TimeUnit;

    public class SpoonacularClient {

        private static final String API_URL = "https://api.spoonacular.com/";
        public class SpoonacularClient {

            public SpoonacularClient() {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::info);

                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(1000, TimeUnit.MILLISECONDS)
                 public SpoonacularClient() {
                .addInterceptor(loggingInterceptor)
                            .build();

                Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();

                    log.debug("client created!");

                    this.api = retrofit.create(SpoonacularApi.class);
                }

            }
