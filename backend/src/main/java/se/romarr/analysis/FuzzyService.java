package se.romarr.analysis;

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

	public String fuzzyMatch(String gameTitle) {
		while (!startupBean.isReady()) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException ignored) {
			}
		}
		return Panache.getSession()
				.createNativeQuery("SELECT title FROM FUZZY_TITLE WHERE title MATCH :gameTitle", FuzzyTitle.class)
				.setParameter("gameTitle", gameTitle)
				.getResultStream()
				.findFirst()
				.map(a -> a.title)
				.orElse(null);
	}
}
