package se.romarr.api;

import java.util.Set;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import se.romarr.analysis.FuzzyService;

@Path("/analysis")
public class AnalysisResource {
	@Inject
	FuzzyService fuzzyService;

	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<String> triggerTGDBDataDump(@QueryParam("game_name") String gameName) {
		return fuzzyService.fuzzyMatch(gameName).orElse(Set.of());
	}

}
