package com.collaborate.restController;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.BlogCommentDAO;
import com.collaborate.model.BlogComment;


@RestController
public class BlogCommentController 
{
	@Autowired
	BlogCommentDAO blogCommentDAO;
	
	//==========method for creating comment===============================
	@PostMapping("/createBlogComment")
	public ResponseEntity<String>createBlogComment(@RequestBody BlogComment blogComment)
	{
		blogComment.setCreateDate(new Date());
		if(blogCommentDAO.createBlogComment(blogComment))
		{
			return new ResponseEntity<String>("BlogComment create successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in blogcomment creation", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	

}
