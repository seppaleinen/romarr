package se.romarr;

import java.util.Set;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import se.romarr.domain.GameSystem;
import se.romarr.folder.FolderService;

@Path("/folder")
public class FolderResource {

	private FolderService folderService;

	public FolderResource(FolderService folderService) {
		this.folderService = folderService;
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<GameSystem> hello(@QueryParam("folder") String folder) {
		return folderService.doStuff(folder);
	}
}
