package lark.island.api;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import lark.island.api.response.ApiResp;
import lark.island.api.service.ApiService;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Path("/api")
public class ApiResource {

    private final Instance<ApiService> apiServiceInstant;

    @Inject
    public ApiResource(Instance<ApiService> apiServiceInstant) {
        this.apiServiceInstant = apiServiceInstant;
    }

    @Path("/{reqId}")
    @POST
    public Response reqHandle(@PathParam("reqId") String reqId, @Context HttpHeaders httpHeaders, Object req) {

        Map<String, String> reqHdr = httpHeaders.getRequestHeaders().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFirst()));
        ApiResp resp = ApiResp.builder().timestamp(new Date()).build();
        Response response;
        try {
            ApiService apiService = apiServiceInstant.select(NamedLiteral.of(reqId + "Service")).get();
            resp.setContext(apiService.process(reqHdr, req));
            response = Response.ok(resp).build();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            resp.setError(ex.getMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
        }

        return response;

    }
}
