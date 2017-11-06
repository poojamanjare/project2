package com.collaborate.restController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.BlogPostDAO;
import com.collaborate.Services.BlogPostService;
import com.collaborate.Services.UserService;
import com.collaborate.model.BlogPost;
import com.collaborate.model.Error;
import com.collaborate.model.Users;

@RestController
public class BlogPostController 
{
	@Autowired
	private BlogPostDAO blogPostDAO;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private UserService userService;
	
	//================method for add new blog==================================
	@PostMapping(value="/addBlogPost")
	public ResponseEntity<?>addBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		
		blogPost.setPostedOn(new Date());
		Users postedBy=userService.getUserByUserId(userId);
		blogPost.setPostedBy(postedBy); 		//postedBy is an object of type Users
		try
		{
			blogPostService.addBlogPost(blogPost);
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);//success
		}
		catch(Exception e)
		{
			Error error=new Error(7, "Unable to save Blog Details...");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//exception
		}
		
	}
	//==========================get list of approved blogs and waiting for approval=============================================================
	@GetMapping(value="/getBlogs/{approved}")
	public ResponseEntity<?>getBlogs(@PathVariable int approved,HttpSession session)
	{
		System.out.println("getblogs..........");
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<BlogPost>blogs=blogPostService.getBlogs(approved);
		return new ResponseEntity<List<BlogPost>>(blogs, HttpStatus.OK);
	}
	//====================get BlogPost by BlogId====================================================================
	@GetMapping(value="/getBlogById/{blogId}")
	public ResponseEntity<?>getBlogById(@PathVariable int blogId,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		BlogPost blogPost=blogPostService.getBlogById(blogId);
		System.out.println("getBlogByid::"+blogPost.getBlogId());
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		
	}
	//=========================update blogpost==========================================================
	@PutMapping(value="/update")
	public ResponseEntity<?>updateBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		//admin has reject the blogpost but reason is not mentioned
		if(!blogPost.isApproved() && blogPost.getRejectionReason()==null)	//approved=0 and rejectionReason=null
		{
			blogPost.setRejectionReason("Not Mentioned");
		}
		
		blogPostService.updateBlogPost(blogPost);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	//=====================Get Notification==========================================================
	@GetMapping(value="/getNotification")
	public ResponseEntity<?>getNotification(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		
		List<BlogPost>blogPostNotification=blogPostService.getNotification(userId);
		return new ResponseEntity<List<BlogPost>>(blogPostNotification,HttpStatus.OK);
	}
	
	
 
}
