package lark.island.api.service.impl;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Named;
import lark.island.api.service.ApiService;

import java.util.Map;

@ApplicationScoped
@Named("dashboardService")
public class DashboardService implements ApiService {
    @Override
    public Object process(Map<String, String> reqHdr, Object req) {
        return null;
    }
}
