import org.apache.commons.validator.GenericValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleValidator {
    private static final Integer CODE_NOT_ENTITY = 1;
    private static final Integer CODE_TOO_LARGE = 2;
    private static final Integer CODE_NOT_IN_RANGE = 3;
    private static final Integer CODE_IS_EMPTY = 4;

    private boolean isValid;
    private LinkedList<String> errors;
    private boolean checkingErrors;

    private HashMap<Integer, String> availableErrors;

    private static final int MAX_EMAIL_LENGTH = 63;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public SimpleValidator() {
        this(false);
    }

    public SimpleValidator(boolean checkingErrors) {
        this.checkingErrors = checkingErrors;
        isValid = true;
        errors = new LinkedList<>();
        availableErrors = new HashMap<>();
        availableErrors.put(1, "value is not ");
        availableErrors.put(2, "value is too large, max length : ");
        availableErrors.put(3, "value is not in range : ");
        availableErrors.put(4, "empty entity : ");
    }

    public LinkedList<String> getErrors() {
        return errors;
    }

    public SimpleValidator isBool(@Nullable String val) {
        if (!this.isValid && !checkingErrors) return this;
        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("Boolean");
            this.isValid = false;
            return this;
        }

        if (!val.equals(Boolean.TRUE.toString()) && ! val.equals(Boolean.FALSE.toString())) {
            addErrorNotEntity(val, "Boolean");
            isValid = false;
        }
        return this;
    }

    public SimpleValidator isInteger(@Nullable String val) {
        if (!this.isValid && !checkingErrors) return this;

        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("Integer");
            this.isValid = false;
            return this;
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(val);
        } catch (NumberFormatException e) {
            addErrorNotEntity(val, "Integer");
            isValid = false;
        }
        return this;
    }

    public SimpleValidator isDate(@Nullable String val, @NotNull String pattern) {
        return this.isDate(val, pattern, false);
    }
    public SimpleValidator isDate(@Nullable String val, @NotNull String pattern, boolean isStrict) {
        if (!this.isValid && !checkingErrors) return this;

        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("date");
            this.isValid = false;
            return this;
        }

        if (!GenericValidator.isDate(val, pattern, isStrict)) {
            addErrorNotEntity(val, "Date");
            this.isValid = false;
        }
        return this;
    }

    public SimpleValidator isEmail(@Nullable String val) {
        if (!this.isValid && !checkingErrors) return this;

        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("email");
            this.isValid = false;
            return this;
        }

        if (val.length() > MAX_EMAIL_LENGTH) {
            addError(Integer.toString(MAX_EMAIL_LENGTH), CODE_TOO_LARGE);
            this.isValid = false;
            return this;
        }

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(val);
        isValid = matcher.find();
        if (!isValid) {
            addErrorNotEntity(val, "Email");
        }
        return this;
    }

    public SimpleValidator isInRange(@Nullable String val, @NotNull String [] range) {
        if (!this.isValid && !checkingErrors) return this;
        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("range value");
            this.isValid = false;
            return this;
        }

        for (String item : range) {
            if (val.equals(item)) {
                return this;
            }
        }
        addError(val + " not in " + rangeToString(range), CODE_NOT_IN_RANGE);
        this.isValid = false;
        return this;
    }

    public SimpleValidator isString(@Nullable String val, int maxLen) {
        if (!this.isValid && !checkingErrors) return this;
        if (GenericValidator.isBlankOrNull(val)) {
            addErrorIsEmpty("String");
            this.isValid = false;
            return this;
        }

        if (val.length() > maxLen) {
            addError(Integer.toString(maxLen), CODE_TOO_LARGE);
            this.isValid = false;
        }
        return this;
    }

    public boolean isValid() {
        return isValid;
    }

    private void addErrorNotEntity(@Nullable String value, String entity) {
        addError(entity + " : " + value, CODE_NOT_ENTITY);
    }

    private void addErrorIsEmpty(String entity) {
        addError(entity, CODE_IS_EMPTY);
    }

    private void addError(@Nullable String value, Integer code) {
        String strValue = (value == null) ? " " : value;
        String message = availableErrors.get(code);
        errors.add(message + strValue);
    }

    private String rangeToString(String[] range) {
        StringBuilder builder = new StringBuilder();
        builder.append(" [ ");
        for(String s : range) {
            builder.append(s).append(' ');
        }
        builder.append(']');
        return builder.toString();
    }

    @TestOnly
    public void reset() {
        this.isValid = true;
    }
}

