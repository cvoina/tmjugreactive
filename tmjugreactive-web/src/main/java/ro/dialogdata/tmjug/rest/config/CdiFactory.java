package ro.dialogdata.tmjug.rest.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@ApplicationScoped
public class CdiFactory {
  private static String REST_BACKEND_SERVICES = "http://localhost:8080/tmjugreactive-web/rest/backend/orders";

  @Produces
  public WebTarget createBackendServiceTarget() {
    URI uri = createUri();
    return ClientBuilder.newClient().target(uri);
  }

  private URI createUri() {
    try {
      URL url = new URL(REST_BACKEND_SERVICES);
      return url.toURI();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
	}
  }
}
