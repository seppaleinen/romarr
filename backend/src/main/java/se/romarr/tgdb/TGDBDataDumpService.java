package se.romarr.tgdb;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/json/database-latest.json")
@RegisterRestClient
public interface TGDBDataDumpService {
	@GET
	GameDump getDump();
}
