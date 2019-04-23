package com.example.demo.service.user.Imp;

import com.example.demo.domain.info.AppInfo;
import com.example.demo.service.info.imp.AppInfoService;
import com.example.demo.service.user.CustomerLoginService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service("customerLoginService")
public class CustomerLoginServiceImp implements CustomerLoginService {
    @Autowired
    private AppInfoService appInfoService;


    @Override
    public Map<String, Object> getLoginSession(String code) {
        AppInfo appInfo = appInfoService.getAppInfo();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appInfo.getAppID()+"&secret="+appInfo.getSecret()+"&js_code="+code+"&grant_type=authorization_code";

        RestTemplate rest = new RestTemplate();
        //FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();

        rest.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String string = rest.postForObject(url, param, String.class);

        JSONObject object = JSONObject.fromObject(string);

        return object;
    }
}
