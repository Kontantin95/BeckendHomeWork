import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

      public class SpoonacularClient {

    private static final String API_URL = "https://api.spoonacular.com/";
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