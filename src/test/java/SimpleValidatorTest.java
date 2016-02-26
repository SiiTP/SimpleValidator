
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SimpleValidatorTest {
    SimpleValidator simpleValidator;

    @Before
    public void setUp() throws Exception {
        simpleValidator = new SimpleValidator();
    }

    @Test
    public void testIsIntegerValid() throws Exception {
        simpleValidator.isInteger("100")
                .isInteger("-1213")
                .isInteger("0")
                .isInteger("-0");
        assertTrue(simpleValidator.isValid());
    }

    @Test
    public void testIsIntegerNotValid() throws Exception {
        simpleValidator.isInteger("100.0");
        Boolean isRightTest = !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("-1213123123123123123");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("9/5");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("12e4");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("not_int");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("   232    ");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInteger("2 3");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        assertTrue(isRightTest);
    }

    @Test
    public void testIsDateValid() throws Exception {
        simpleValidator.isDate("2014-11-14", "yyyy-MM-dd")
                .isDate("1995", "yyyy", true)
                .isDate("23:30", "kk:mm")
                .isDate("11:30", "hh:mm")
                .isDate("29.02.2016", "dd.MM.yyyy")
                .isDate("20161201225858", "yyyyMMddHHmmss", true)
                .isDate("2016.02.1.22.58.58", "yyyy.MM.dd.HH.mm.ss")
                .isDate("12", "M")
                .isDate("16_01_01", "yy_MM_dd", true);
        assertTrue(simpleValidator.isValid());
    }

    @Test
    public void testIsDateNotVaild() throws Exception {
        simpleValidator.isDate("01.40.2016", "dd.MM.yyyy");
        Boolean isRightTest = !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("01", "M", true);
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("29.02.2015", "dd.MM.yyyy");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("23:30", "hh:mm");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("11:60", "hh:mm");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("11.58", "hh:mm");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("12:30:", "hh:mm:ss");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate("12:30:61", "hh:mm:ss");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isDate(null, "hh:mm:ss");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        assertTrue(isRightTest);

    }

    @Test
    public void testIsInRangeValid() throws Exception {
        simpleValidator.isInRange("all", new String[] {"ios", "android", "web", "all"})
                .isInRange("android", new String[]{"ios", "android", "web", "all"})
                .isInRange("android", new String[] {"android", "android", "android"})
                .isInRange("all", new String[] {"android", "android", "android", "all"})
                .isInRange("ios", new String[]{" ", " ios  ", "ios"})
                .isInRange("ios", new String[] {"ios"})
                .isInRange("ios", new String[] {"ios", "android", "web", "all"});
        assertTrue(simpleValidator.isValid());
    }

    @Test
    public void testIsInRangeNotValid() throws Exception {
        simpleValidator.isInRange("notinrange", new String[] {"ios", "android", "web", "all"});
        Boolean isRightTest = !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange("  ios  ", new String[] {"ios", "android", "web", "all"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange(" ", new String[] {"ios", "android", "web", "all"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange("ios", new String[] {"android"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange("WEB", new String[] {"ios", "android", "web", "all"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange("Ios", new String[] {"ios", "android", "web", "all"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isInRange(null, new String[] {"ios", "android", "web", "all"});
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        assertTrue(isRightTest);
    }

    @Test
    public void testIsBool() throws Exception {
        simpleValidator.isBool("true")
                .isBool("false");
        assertTrue(simpleValidator.isValid());
    }

    @Test
    public void testIsBoolNotValid() throws Exception {
        simpleValidator.isBool("FALSE");
        Boolean isRightTest = !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool("True");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool(" ");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool("fa lse");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool("notBool");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool("0");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool("null");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isBool(null);
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        assertTrue(isRightTest);
    }

    @Test
    public void testIsEmail() throws Exception {
        simpleValidator
                .isEmail("my@gmail.com")
                .isEmail("dev.ivanse@gmail.com")
                .isEmail("my@a.co.in.bb")
                .isEmail("IVAN@azaza.org")
                .isEmail("ivan@bmstu.ru")
                .isEmail("azaza1995@mail.ru")
                .isEmail("email_my1@yandex.ru");
        assertTrue(simpleValidator.isValid());
    }

    @Test
    public void testIsEmailNotValid() throws Exception {
        simpleValidator.isEmail("not_email");
        Boolean isRightTest = !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail(null);
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("azzaza  @mail.ru");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("     myEmail@gmail.com");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEmail@gmail.c");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEmail@gmail.");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEmailmail.ru");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEmail@gmailcom");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("my@Email@gmail.ru");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEm&ail@gmail.com");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("___ ___@gmail.com");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail(" ");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        simpleValidator.isEmail("myEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmailmyEmail@ya.ru");
        isRightTest &= !getIsValidAndResetValidator(simpleValidator);
        assertTrue(isRightTest);
    }

    @Test
    public void testErrors() throws Exception {
        simpleValidator = new SimpleValidator(true);
        simpleValidator.isBool("True")
                .isEmail("ne emain")
                .isString(null, 128)
                .isInteger(null)
                .isBool(null)
                .isString("too large too large too large", 8)
                .isInRange("not in range", new String[]{"a", "b", "c"})
                .isInRange(null, new String[]{"a", "b", "c"})
                .isInteger("a");
        assertTrue(simpleValidator.getErrors().size() == 9);
    }

    @Test
    public void testNotCheckingErrors() throws Exception {
        simpleValidator.isBool("True")
                .isEmail("ne email")
                .isString(null, 128)
                .isInteger(null)
                .isBool(null)
                .isString("too large too large too large", 8)
                .isInRange("not in range", new String[]{"a", "b", "c"});

        assertTrue(simpleValidator.getErrors().size() == 1);
    }

    private boolean getIsValidAndResetValidator(SimpleValidator validator) {
        boolean isValid = validator.isValid();
        validator.reset();
        return isValid;
    }
}
