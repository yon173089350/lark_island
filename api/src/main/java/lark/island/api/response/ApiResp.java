package lark.island.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiResp {
    private Date timestamp;
    private String error;
    private Object context;
}
