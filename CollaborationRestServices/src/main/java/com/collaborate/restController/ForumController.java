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
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.ForumDAO;
import com.collaborate.model.Forum;

@RestController
public class ForumController 
{
	@Autowired
	ForumDAO forumDAO;
	
	//====================creating Forum===================
	@PostMapping(value="/createForum")
	public ResponseEntity<String>createForum(@RequestBody Forum forum)
	{
		forum.setCreateDate(new Date());
		forum.setStatus("NA");
		
		if(forumDAO.createForum(forum))
		{
			return new ResponseEntity<String>("Forum created successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in creating forum", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//===============method for getting forum by forumid========================
	@GetMapping(value="/getForumById/{forumid}")
	public ResponseEntity<Forum>getForumById(@PathVariable("forumid")int forumId)
	{
		Forum forum=forumDAO.getForum(forumId);
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	//===============method for getting list of all forum=========================
	@GetMapping(value="/getAllForum")
	public ResponseEntity<List<Forum>>getAllForum()
	{
		ArrayList<Forum>listForum=new ArrayList<Forum>();
		listForum=(ArrayList<Forum>) forumDAO.getForum();
		return new ResponseEntity<List<Forum>>(listForum,HttpStatus.OK);
	}
	
	//================method for approved forum===============================
	@GetMapping(value="/approveForum/{forumid}")
	public ResponseEntity<String>approveForum(@PathVariable("forumid")int forumId)
	{
		Forum forum=forumDAO.getForum(forumId);
		if(forumDAO.approveForum(forum))
		{
			return new ResponseEntity<String>("Approve forum successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in Approving forum", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//================method for getting list of approve forum=================
	@GetMapping(value="/getAllApproveForum")
	public ResponseEntity<List<Forum>>getAllApproveForum()
	{
		ArrayList<Forum>listForum=new ArrayList<Forum>();
		listForum=(ArrayList<Forum>) forumDAO.getApproveForum();
		return new ResponseEntity<List<Forum>>(listForum,HttpStatus.OK);
	}
	
	//====================method for updating forum=============================
	@GetMapping(value="/updateForum/{forumid}")
	public ResponseEntity<String>updateForum(@PathVariable("forumid")int forumId)
	{
		if(forumDAO.updateForum(forumId))
		{
			return new ResponseEntity<String>("Update forum successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in updating forum", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//===========================method for delete forum=================================
	@GetMapping(value="/deleteForum/{forumid}")
	public ResponseEntity<String>deleteForum(@PathVariable("forumid")int forumId)
	{
		if(forumDAO.deleteForum(forumId))
		{
			return new ResponseEntity<String>("Delete forum successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in deleting forum", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
}
