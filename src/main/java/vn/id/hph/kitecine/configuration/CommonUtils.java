package vn.id.hph.kitecine.configuration;

import java.security.SecureRandom;

public class CommonUtils {
    // 32 characters: No 0, O, 1, or I
    private static final String SAFE_ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

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
}
