package se.romarr.scanner.folder;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GameUtil {
	private static final Pattern versionPattern = Pattern.compile("v(\\d+\\.)?(\\d+\\.)?(\\d+)");
	private static final Pattern trailingNoisePattern = Pattern.compile("( |\\(\\)|\\[]|[-_.])+$");
	private static final Pattern headingNoisePattern = Pattern.compile("^( |\\(\\)|\\[]|[-_.])+");
	private static final Pattern extensionPattern = Pattern.compile("\\.[0-9a-zA-Z]{2,4}$");

	private GameUtil(){}

	public static String tryToGetGameName(Path path) {
		String fileName = path.getFileName().toString();
		fileName = removePattern(fileName, versionPattern);
		fileName = removePattern(fileName, trailingNoisePattern);
		fileName = removePattern(fileName, headingNoisePattern);
		fileName = removePattern(fileName, extensionPattern);
		fileName = fileName.replaceAll("[._]", " ");
		return fileName;
	}

	private static String removePattern(String string, Pattern pattern) {
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			return matcher.replaceAll("");
		}
		return string;
	}

}
