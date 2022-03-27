public class JacksonJavaSerialExample {

    public static void main(String[] args) throws IOException {

        byte[] bytes = new FileInputStream("item.json").readAllBytes();
        String json = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();

        RecipesSearchResponseItem item = mapper.readValue(json, RecipesSearchResponseItem.class);
        System.out.println(item);

    }
}
