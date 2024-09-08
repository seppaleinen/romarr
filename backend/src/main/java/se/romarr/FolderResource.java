package se.romarr;

import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import se.romarr.analysis.FuzzyService;
import se.romarr.domain.GameSystem;
import se.romarr.folder.FolderService;
import se.romarr.tgdb.TGDBDataDumpService;

@Path("/folder")
public class FolderResource {

	@Inject
	FolderService folderService;
	@Inject
	@RestClient
	TGDBDataDumpService tgdbDataDumpService;
	@Inject
	FuzzyService fuzzyService;

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<GameSystem> hello(@QueryParam("folder") String folder) {
		return folderService.doStuff(folder);
	}

	@Path("/test")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String triggerTGDBDataDump(@QueryParam("game_name") String gameName) {
		return fuzzyService.fuzzyMatch(gameName);
	}
}
