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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.BlogCommentDAO;
import com.collaborate.Services.BlogCommentService;
import com.collaborate.Services.BlogPostService;
import com.collaborate.Services.UserService;
import com.collaborate.model.BlogComment;
import com.collaborate.model.Error;
import com.collaborate.model.Users;

@RestController
public class BlogCommentController 
{

	@Autowired
	BlogCommentDAO blogCommentDAO;
	
	@Autowired
	BlogCommentService blogCommentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/addComment")
	public ResponseEntity<?>addComment(@RequestBody BlogComment blogComment,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		Users users=userService.getUserByUserId(userId);
		blogComment.setCommentedBy(users);
		blogComment.setCommentedOn(new Date());
		try
		{
			blogCommentService.addBlogComment(blogComment);
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
		catch(Exception e)
		{
			Error error=new Error(7, "Unable to post blogcomment...");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//exception
		}
		
	}
	
	@GetMapping(value="/getComments/{blogId}")
	public ResponseEntity<?>getComments(@PathVariable int blogId,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		
		List<BlogComment>blogComment=blogCommentService.getBlogComments(blogId);
		return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);
	}
}
