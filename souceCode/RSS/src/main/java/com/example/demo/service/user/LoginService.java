package com.example.demo.service.user;

import java.util.Map;

public interface LoginService {
    Map<String, Object> getLoginSession(String code);

}
