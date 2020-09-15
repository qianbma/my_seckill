package com.mqb.seckill.config;

import com.mqb.seckill.constants.MiaoshaUserConstants;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private MiaoshaUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == MiaoshaUser.class;
    }

    /**
     * 从request中获取token，根据token查询redis，并注入到controller入参中
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserConstants.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserConstants.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser user = userService.getByToken(response, token);
        return user;
    }

    // 从request中获取cookie中token
    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieNameToken.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
