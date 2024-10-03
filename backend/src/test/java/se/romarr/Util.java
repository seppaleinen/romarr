package se.romarr;

import java.util.List;

import se.romarr.tgdb.Data;
import se.romarr.tgdb.GameDump;
import se.romarr.tgdb.TGDBGame;

public final class Util {
	private Util() {
	}

	public static GameDump createDump() {
		return new GameDump(200, "OK", "1", new Data(5,
				List.of(createGame(1, "game1"))));
	}

	public static TGDBGame createGame(int id, String gameTitle) {
		return new TGDBGame(id, gameTitle, null, 0, 0, 0, null, null, 0, null, null, null, null, null, null, null, null);
	}
}
