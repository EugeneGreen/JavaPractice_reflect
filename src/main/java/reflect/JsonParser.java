package reflect;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonParser {

    public String toJson(Object object) throws IllegalAccessException {
        StringBuilder str = new StringBuilder("{");
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if(Objects.nonNull(field.get(object))) {
                String fieldName = field.getName();
                String fieldValue = field.get(object).toString();
                if(field.isAnnotationPresent(JsonValue.class)) {
                    fieldName = field.getAnnotation(JsonValue.class).name();
                }
                if(field.isAnnotationPresent(DateFormatter.class)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(field.getAnnotation(DateFormatter.class).format());
                    fieldValue = ((LocalDate) field.get(object)).format(formatter);
                }
                str.append("\"").append(fieldName).append("\":\"").append(fieldValue).append("\",");
            }
            field.setAccessible(false);
        }
        if(str.lastIndexOf(",") == str.length() - 1) {
            str.deleteCharAt(str.length()-1);
        }
        str.append("}");
        return str.toString();
    }

    public <T> T fromJson(String json, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        String[] myJson = (json.replaceAll("\\{","").replaceAll("}","").replaceAll("\"","")).split(",");
        Map<String, String> map = Arrays.stream(myJson)
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(array -> array[0], array -> array[1]));
        T object = clazz.newInstance();
        for(Field field : clazz.getDeclaredFields()) {
            String name = field.getName();
            if(field.isAnnotationPresent(JsonValue.class)) {
                name = field.getAnnotation(JsonValue.class).name();
            }
            if(map.containsKey(name)) {
                field.setAccessible(true);
                if(field.isAnnotationPresent(DateFormatter.class)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(field.getAnnotation(DateFormatter.class).format());
                    LocalDate value = LocalDate.parse(map.get(name), formatter);
                    field.set(object, value);
                } else {
                    String value = map.get(name);
                    field.set(object, value);
                }
                field.setAccessible(false);
            }
        }
        return object;
    }
}
