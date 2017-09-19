package com.collaborate.restController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.BlogDAO;
import com.collaborate.model.Blog;


@RestController
public class BlogController 
{
	@Autowired
	BlogDAO blogDAO;
	                                                                             
	//==============method for creating blocks===========================================
	@PostMapping(value="/createBlogs")
	public ResponseEntity<String>createBlogs(@RequestBody Blog blog)
	{
		blog.setStatus("NA");
		blog.setLikes(0);
		blog.setCreateDate(new Date());
		
		if(blogDAO.createBlog(blog))
		{
			return new ResponseEntity<String>("Blog created successfully",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in creating Blog", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//=============method for geeting blog by blogid=====================
	@GetMapping(value="/getBlogById/{blogid}")
	public ResponseEntity<Blog>getBlogById(@PathVariable("blogid")int blogId)
	{
		Blog blog=blogDAO.getBlog(blogId);
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	}
	
	//=========method for getting all blogs=================================
		@GetMapping(value="/getAllBlogs")
		public ResponseEntity<List<Blog>>getAllBlogs()		//<List<Blog>>=list of blogs will be send
		{
			ArrayList<Blog>listBlogs=new ArrayList<Blog>();
			listBlogs=(ArrayList<Blog>)blogDAO.getBlog();		//to call getBlog() method
			return new ResponseEntity<List<Blog>>(listBlogs,HttpStatus.OK);
		}
		
	//===========method for getting list of all approve blogs====================
		@GetMapping(value="/getAllApproveBlogs")
		public ResponseEntity<List<Blog>>getAllApproveBlogs()
		{
			ArrayList<Blog>listBlogs=new ArrayList<Blog>();
			listBlogs=(ArrayList<Blog>)blogDAO.getApproveBlog();				//to call getBlog() method
			return new ResponseEntity<List<Blog>>(listBlogs,HttpStatus.OK);
		}
	
	//====================method for approve blog============================
	@GetMapping(value="/approveBlog/{blogId}")
	public ResponseEntity<String>approveBlog(@PathVariable("blogId") int blogId)
	{
		Blog blog=blogDAO.getBlog(blogId);
		if(blogDAO.approveBlog(blog))
		{
			return new ResponseEntity<String>("approved blogs successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in approving blogs", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	//=============method for update blog==================================
		@GetMapping(value="/updateBlog/{blogid}")
		public ResponseEntity<String>updateBlog(@PathVariable("blogid")int blogId)
		{
			if(blogDAO.updateBlog(blogId))
			{
				return new ResponseEntity<String>("blog updated successfully", HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("problem in updating blogs", HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
	//=======================method for delete blog============================
	@GetMapping(value="/deleteBlog/{blogid}")
	public ResponseEntity<String>deleteBlog(@PathVariable("blogid") int blogId)
	{
		if(blogDAO.deleteBlog(blogId))
		{
			return new ResponseEntity<String>("blog deleted successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in deleting blogs", HttpStatus.NOT_ACCEPTABLE);
		}
	}
		
	
	//=============This method is to check that whether controller runs properly or not(to test controller)===========
	@GetMapping(value="/test")
	public ResponseEntity<String>testMethod()
	{
		System.out.println("test method");
		return new ResponseEntity<String>("Test RestController", HttpStatus.OK);
	}

}
