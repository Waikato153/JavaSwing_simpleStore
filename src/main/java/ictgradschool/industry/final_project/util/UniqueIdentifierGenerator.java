package ictgradschool.industry.final_project.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * The SmartScroller will attempt to keep the viewport positioned based on
 * the users interaction with the scrollbar. The normal behaviour is to keep
 * the viewport positioned to see new data as it is dynamically added.
 * <p>
 * Assuming vertical scrolling and data is added to the bottom:
 * <p>
 * - when the viewport is at the bottom and new data is added,
 * then automatically scroll the viewport to the bottom
 * - when the viewport is not at the bottom and new data is added,
 * then do nothing with the viewport
 * <p>
 * Assuming vertical scrolling and data is added to the top:
 * <p>
 * - when the viewport is at the top and new data is added,
 * then do nothing with the viewport
 * - when the viewport is not at the top and new data is added, then adjust
 * the viewport to the relative position it was at before the data was added
 * <p>
 * Similiar logic would apply for horizontal scrolling.
 */
public class UniqueIdentifierGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateIdentifier() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String getCharacters(List existingIdentifiers) {
        while (true) {
            String identifier = generateIdentifier();
            if (!existingIdentifiers.contains(identifier)) {
                return identifier;
            }
        }
    }
}
