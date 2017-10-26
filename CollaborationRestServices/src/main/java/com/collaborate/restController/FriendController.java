package com.collaborate.restController;

import java.util.List;

import javax.servlet.http.HttpSession;

import oracle.net.aso.f;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.Services.FriendService;
import com.collaborate.Services.UserService;
import com.collaborate.model.Error;
import com.collaborate.model.Friend;
import com.collaborate.model.Users;

@RestController
public class FriendController 
{
	@Autowired
	FriendService friendService;
	
	@Autowired
	UserService userService;
	//======================get list of users suggestions==================================================
	@GetMapping(value="/getSuggestedUsers")
	public ResponseEntity<?>getSuggestedUsers(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<Users>suggestedUsers=friendService.listOfSuggestedUsers(userId);
		return new ResponseEntity<List<Users>>(suggestedUsers,HttpStatus.OK);
	}
	//=============================send friend request=======================================================
	@RequestMapping(value="/friendRequest/{toId}")
	public ResponseEntity<?>friendRequest(@PathVariable String toId,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		Friend friend=new Friend();
		friend.setFromId(userId);
		friend.setToId(toId);
		friend.setStatus('P');
		friendService.friendRequest(friend);//insert into friend table
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
		
	}
	
	//=================================list of pending request======================================================
	@GetMapping(value="/pendingRequests")
	public ResponseEntity<?>pendingRequests(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<Friend>pendingRequests=friendService.pendingRequets(userId);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
	}
	

	//=============================update pending requests from P to A / D===========================================================
	@PutMapping(value="/updatePendingRequests")
	public ResponseEntity<?>updatePendingRequests(@RequestBody Friend friend,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		System.out.println(friend.getFriendId()+"  "+friend.getFromId()+"  "+friend.getStatus());
		friendService.updatePendingRequests(friend);	//update status to A or D
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	//================get list of friends================================================
	@GetMapping(value="/listOfFriends")
	public ResponseEntity<?>listOfFriends(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<String>friends=friendService.listOfFriends(userId);
		return new ResponseEntity<List<String>>(friends,HttpStatus.OK);
	}
}
