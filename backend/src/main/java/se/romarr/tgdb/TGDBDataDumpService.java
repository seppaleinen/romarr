package se.romarr.tgdb;


import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.faulttolerance.api.ExponentialBackoff;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;

@Path("/json/database-latest.json")
@RegisterRestClient
public interface TGDBDataDumpService {
	@GET
	@Retry(maxRetries = 5, delay = 500, retryOn = WebApplicationException.class)
	@ExponentialBackoff
	GameDump getDump();
}
