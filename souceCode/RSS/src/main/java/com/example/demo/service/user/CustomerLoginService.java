package com.example.demo.service.user;

import java.util.Map;

public interface CustomerLoginService {
    Map<String, Object> getLoginSession(String code);

}
