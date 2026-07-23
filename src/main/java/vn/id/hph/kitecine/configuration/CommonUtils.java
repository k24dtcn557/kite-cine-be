package vn.id.hph.kitecine.configuration;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class CommonUtils {
    // 32 characters: No 0, O, 1, or I
    private static final String SAFE_ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String generateTicketCode() {
        StringBuilder sb = new StringBuilder(9); // 8 chars + 1 hyphen
        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                sb.append("-"); // Split it down the middle visually
            }
            int randomIndex = RANDOM.nextInt(SAFE_ALPHABET.length());
            sb.append(SAFE_ALPHABET.charAt(randomIndex));
        }

        // Output: "X7K9-P2M4"
        return sb.toString();
    }

    public static String formatDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        // Format the date
        return localDate.format(dateFormatter);
    }

    public static String formatNumber(BigDecimal value) {
        if (Objects.isNull(value)) return "";

        DecimalFormat df = new DecimalFormat("###,###,###");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.forLanguageTag("VI")));

        return df.format(value.longValue());
    }
}
