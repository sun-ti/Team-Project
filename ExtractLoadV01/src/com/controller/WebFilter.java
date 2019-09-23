package com.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.Util_Net;

public class WebFilter implements Filter{

	
	//	初始化;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	
	//	跨域过滤;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest  res 	= (HttpServletRequest) request;
		HttpServletResponse resp	= (HttpServletResponse) response;
		Util_Net			util_Net= new Util_Net(res,resp);
		// 设置过滤内容;
		util_Net.setWebFilter();
		
		// 添加过滤器;
		chain.doFilter(res, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}
}
