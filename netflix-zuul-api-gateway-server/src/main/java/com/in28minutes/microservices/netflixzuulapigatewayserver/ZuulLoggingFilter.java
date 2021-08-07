package com.in28minutes.microservices.netflixzuulapigatewayserver;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
// note this @component is important other wise in logs it does not comes
@Component
public class ZuulLoggingFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("request =============================== -> {} request URI -> {}", request, request.getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		// all time filter requests other values , post,error
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
