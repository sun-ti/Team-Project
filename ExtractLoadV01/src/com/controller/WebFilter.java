package com.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		HttpServletResponse resp=(HttpServletResponse) response;
		resp.setHeader("Access-Control-Allow-Headers", "*");
		//允许所有的方法访问  如post , get方法
		resp.setHeader("Access-Control-Allow-Method", "GET,POST,PUT,DELETE");
//		resp.setHeader("Access-Control-Allow-Method", "*");
		//允许所有的域
		resp.setHeader("Access-Control-Allow-Origin","*");
		//放行
		chain.doFilter(request, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}
}
