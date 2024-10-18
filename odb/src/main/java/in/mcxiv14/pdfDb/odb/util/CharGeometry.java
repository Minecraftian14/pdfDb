package in.mcxiv14.pdfDb.odb.util;

import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.regex.Pattern;

public class CharGeometry {

    private static final String rgx_spaceCoords = "\\n?[^\\n]*";

    public static Point find(String space, String word, boolean firstWordIs00) {
        var matcher = Pattern.compile(rgx_spaceCoords + word).matcher(space);
        if (!matcher.find()) throw new IllegalArgumentException(word + " not found in\n" + space);
        var match = matcher.group();
        var firstLine = match.startsWith("\n") ? 1 : 0;
        var zeroCorrect = firstWordIs00 ? 0 : 1;
        int ox = match.length() - (word.length() + 1) / 2 - firstLine + zeroCorrect;
        int oy = StringUtils.countOccurrencesOf(space.substring(0, matcher.start()), "\n") + firstLine + zeroCorrect;
        return new Point(ox, oy * 41 / 15);
    }

    public static double distance(String space, int x, int y, String word, boolean firstWordIs00) {
        var point = find(space, word, firstWordIs00);
        return point.distance(x, y * 41d / 15);
    }

    public static double distance(String space, String wordA, String wordB, boolean firstWordIs00) {
        var pointA = find(space, wordA, firstWordIs00);
        var pointB = find(space, wordB, firstWordIs00);
        return pointA.distance(pointB);
    }

}
