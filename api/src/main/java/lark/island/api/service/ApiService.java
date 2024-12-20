package lark.island.api.service;

import java.util.Map;

public interface ApiService {
    Object process(Map<String,String> reqHdr, Object req);
}
