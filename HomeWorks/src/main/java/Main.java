public class Main {

    public static void main(String[] args) {

        User user = User.builder()
                .setAge(12)
                .setName("Ivan")
                .setSurname("Ivanov")
                .setPhone("8911111111")
                .build();

    }
}
