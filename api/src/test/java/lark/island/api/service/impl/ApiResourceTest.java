package lark.island.api;

import jakarta.enterprise.inject.Instance;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;
import lark.island.api.response.ApiResp;
import lark.island.api.service.ApiService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApiResourceTest {

    @Mock
    private Instance<ApiService> apiServiceInstances;

    @Mock
    private ApiService apiService;

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private ApiResource apiResource;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void setDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void reqHandle_validRequest_returnsOkResponse() {

        Instance<ApiService> apiServiceInstance = mock(Instance.class);
        when(apiServiceInstances.select(any())).thenReturn(apiServiceInstance);
        when(apiServiceInstance.get()).thenReturn(apiService);

        when(apiService.process(any(), any())).thenReturn(Collections.singletonMap("key", "value"));

        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("header", "value");
        when(httpHeaders.getRequestHeaders()).thenReturn(map);

        try (Response response = apiResource.reqHandle("invalidReqId", httpHeaders, new Object())) {
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            ApiResp resp = (ApiResp) response.getEntity();
            assertEquals(Date.class, resp.getTimestamp().getClass());
            assertEquals("value", ((Map<?, ?>) (resp.getContext())).get("key"));
        }
    }

    @Test
    void reqHandle_invalidRequest_returnsBadRequestResponse() {
        when(apiServiceInstances.select(any())).thenThrow(new RuntimeException("Service not found"));

        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("header", "value");
        when(httpHeaders.getRequestHeaders()).thenReturn(map);

        try (Response response = apiResource.reqHandle("invalidReqId", httpHeaders, new Object());) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
            ApiResp resp = (ApiResp) response.getEntity();
            assertEquals("Service not found", resp.getError());
        }
    }
}
