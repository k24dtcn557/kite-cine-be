package vn.id.hph.kitecine.configuration;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class CommonUtils {
    public static final String VIETNAM_TIMEZONE = "GMT+07";
    private static final String SAFE_ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("dd/MM");

    private static final String[] days = {
        "Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy",
    };

    // Convert instant to Vietnam LocalDate
    public static LocalDate getVietnamLocalDate() {
        return Instant.now().atZone(ZoneId.of(VIETNAM_TIMEZONE)).toLocalDate();
    }

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
        String date = localDate.format(dateFormatter);
        date = days[localDate.getDayOfWeek().getValue() % 7] + ", " + date;
        return date;
    }

    public static String formatShortDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        // Format the date
        String date = localDate.format(dateFormatter);
        return date;
    }

    public static String formatDayMonth(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        // Format the date
        String date = localDate.format(dayMonthFormatter);
        return date;
    }

    public static String formatNumber(BigDecimal value) {
        if (Objects.isNull(value)) return "";

        DecimalFormat df = new DecimalFormat("###,###,###");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.forLanguageTag("VI")));

        return df.format(value.longValue());
    }
}
