package se.romarr;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import se.romarr.persistence.FuzzyTitle;
import se.romarr.tgdb.TGDBDataDumpService;
import se.romarr.tgdb.TGDBGame;

@ApplicationScoped
@Startup
public class StartupBean {
	private static final AtomicBoolean initialized = new AtomicBoolean(false);

	private final TGDBDataDumpService tgdbDataDumpService;

	@Inject
	public StartupBean(@RestClient TGDBDataDumpService tgdbDataDumpService) {
		this.tgdbDataDumpService = tgdbDataDumpService;
	}

	@PostConstruct
	public void initialize() {
		StopWatch persistenceWatch = StopWatch.createStarted();

		List<TGDBGame> games = tgdbDataDumpService.getDump().data().games();

		persistFuzzies(games);

		initialized.set(true);

		System.out.println("Persisted " + games.size() + " games in " + persistenceWatch.getTime() + "ms");
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
		return initialized.get();
	}
}
