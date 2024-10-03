package se.romarr.api;

import java.util.Set;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import se.romarr.domain.GameSystem;
import se.romarr.scanner.folder.FolderService;

@Path("/folder")
public class FolderResource {

	@Inject
	FolderService folderService;

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<GameSystem> hello(@QueryParam("folder") String folder) {
		return folderService.doStuff(folder);
	}
}
