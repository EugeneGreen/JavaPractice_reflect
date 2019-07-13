package reflect;

import java.time.LocalDate;

public class JavaPracticeReflect {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Human human = new Human();
        JsonParser jsonParser = new JsonParser();

        human.setFirstName("Ivan");
        human.setLastName("Ivanov");
        human.setBirthDate(LocalDate.parse("1988-07-03"));
        human.setHobby("Alcohol");

        System.out.println(human.toString());
        String json = jsonParser.toJson(human);
        System.out.println(json);
        human = jsonParser.fromJson(json, Human.class);
        System.out.println(human.toString());
    }
}
