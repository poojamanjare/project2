package com.collaborate.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configurable
@EnableWebMvc
@ComponentScan("com.collaborate")
public class WebResolver
{
	@Bean
	public InternalResourceViewResolver getViewResolver()
	{
		InternalResourceViewResolver internalResourceViewResolver=new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/");
		internalResourceViewResolver.setSuffix(".html");
		System.out.println("view Resolver created successfully(webResolver)....");
		return internalResourceViewResolver;
	}
	@Bean(name="multiPartResolver")
	public  CommonsMultipartResolver getCommonsMultipartResolver()
	{
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(20971520);//20MB
		commonsMultipartResolver.setMaxInMemorySize(1048576);//1MB
		return commonsMultipartResolver;
	}
	/*@Bean(name="mailSender")
	public JavaMailSenderImpl getJavaMailSenderImpl()
	{
		JavaMailSenderImpl javaMailSenderImpl=new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPort(587);
		javaMailSenderImpl.getUsername();
		javaMailSenderImpl.getPassword();
		
		return javaMailSenderImpl;
		
	}*/
	
}