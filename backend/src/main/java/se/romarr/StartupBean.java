package se.romarr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import se.romarr.persistence.FuzzyTitle;
import se.romarr.tgdb.TGDBDataDumpService;
import se.romarr.tgdb.TGDBGame;

@Singleton
public class StartupBean {
	private static final AtomicBoolean initialized = new AtomicBoolean(false);

	private final TGDBDataDumpService tgdbDataDumpService;

	@Inject
	public StartupBean(@RestClient TGDBDataDumpService tgdbDataDumpService) {
		this.tgdbDataDumpService = tgdbDataDumpService;
	}

	public void initialize() {
		StopWatch persistenceWatch = StopWatch.createStarted();

		if (FuzzyTitle.count() == 0) {
			List<TGDBGame> games = getGames();

			persistFuzzies(games);

			System.out.println("Persisted " + games.size() + " games in " + persistenceWatch.getTime() + "ms");
		}
	}

	public List<TGDBGame> getGames() {
		try {
			return tgdbDataDumpService.getDump().data().games();
		} catch (WebApplicationException e) {
			System.err.println("Could not get data dump from thegamedb: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	@Transactional
	public void persistFuzzies(List<TGDBGame> games) {
		FuzzyTitle.persist(games
				.stream()
				.map(TGDBGame::gameTitle)
				.distinct()
				.map(FuzzyTitle::new)
				.filter(entity -> !entity.isPersistent()));
	}

	public boolean isReady() {
		if (!initialized.get()) {
			initialize();
		}
		return true;
	}
}
