package com.example.demo.Configuration;

import com.example.demo.domain.user.Admin;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class CustomMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/admin/*").excludePathPatterns("/login");
        //registry.addInterceptor(new SecurityInterceptor_ConsoleCheck()).addPathPatterns("/console/*").excludePathPatterns("/login");
    }

    /**
     * 拦截器
     * */

    static class SecurityInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) throws Exception {

            Admin user = (Admin) request.getSession().getAttribute("admin");

            if (user != null) {


            } else {
                response.sendRedirect("/login");

                return false;
            }

            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object handler, Exception ex) throws Exception {
        }
    }
}
