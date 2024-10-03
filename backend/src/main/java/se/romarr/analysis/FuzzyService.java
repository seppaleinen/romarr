package se.romarr.analysis;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.romarr.StartupBean;
import se.romarr.persistence.FuzzyTitle;

@ApplicationScoped
public class FuzzyService {
	private final StartupBean startupBean;

	@Inject
	public FuzzyService(final StartupBean startupBean) {
		this.startupBean = startupBean;
	}

	public Optional<Set<String>> fuzzyMatch(String gameTitle) {
		while (!startupBean.isReady()) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException ignored) {
			}
		}
		try {
			Set<String> result = Panache.getSession()
					.createNativeQuery("SELECT title FROM FUZZY_TITLE WHERE title MATCH :gameTitle", FuzzyTitle.class)
					.setParameter("gameTitle", gameTitle)
					.getResultStream()
					.map(a -> a.title)
					.collect(Collectors.toSet());
			return result.isEmpty() ? Optional.empty() : Optional.of(result);
		} catch (Exception e) {
			System.out.println("GAMETITLE: " + gameTitle);
			throw e;
		}
	}
}
