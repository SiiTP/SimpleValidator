import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        SimpleValidator validator = new SimpleValidator(true);
        validator.isBool("True")
                .isInRange("ios", new String[] {"not ios 1", "notios2", "android", "web"})
                .isEmail("notEmail");
        LinkedList<String> errors = validator.getErrors();
        errors.forEach(System.out::println);
    }
}
