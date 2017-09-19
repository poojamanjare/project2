package com.collaborate.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.UsersDAO;
import com.collaborate.model.Users;

@RestController
public class UserController 
{
	@Autowired
	UsersDAO usersDAO;
	
	//============method for creating user===============================
	@PostMapping(value="/createUsers")
	public ResponseEntity<String>createUsers(@RequestBody Users users)
	{
		users.setRole("USER_ROLE");
		users.setStatus("NA");
		users.setIsOnline("NO");
		
		if(usersDAO.createUsers(users))
		{
			return new ResponseEntity<String>("User created successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in creating users", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	//=============method for getting user by userid=====================
	@GetMapping(value="/getUserById/{userid}")
	public ResponseEntity<Users>getUserById(@PathVariable("userid")String userId)
	{
		Users users=usersDAO.getUsers(userId);
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
	//================method for getting list of all users================
	@GetMapping(value="/getAllUsers")
	public ResponseEntity<List<Users>>getAllUsers()
	{
		ArrayList<Users>listUsers=new ArrayList<Users>();
		listUsers=(ArrayList<Users>) usersDAO.getUsers();
		return new ResponseEntity<List<Users>>(listUsers,HttpStatus.OK);
	}
	
	//===============method for approve user======================
	@GetMapping(value="/approveUsers/{userid}")
	public ResponseEntity<String>approveUsers(@PathVariable("userid")String userId)
	{
		Users users=usersDAO.getUsers(userId);
		if(usersDAO.approveUsers(users))
		{
			return new ResponseEntity<String>("User approved successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in approving user", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//==============method for getting list of approve users=============================
	@GetMapping(value="/getAllApproveUsers")
	public ResponseEntity<List<Users>>getAllApproveUsers()
	{
		ArrayList<Users>listUsers=new ArrayList<Users>();
		listUsers=(ArrayList<Users>) usersDAO.getApproveUsers();
		return new ResponseEntity<List<Users>>(listUsers,HttpStatus.OK);
	}
	
	//==============methods for updating user=================
	@GetMapping(value="/updateUsers/{userid}")
	public ResponseEntity<String>updateUsers(@PathVariable("userid")String userId)
	{
		System.out.println(userId);
		if(usersDAO.updateUsers(userId))
		{
			
			return new ResponseEntity<String>("Update user successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in updating user", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//==============method for delete user=================================
	@GetMapping(value="/deleteUsers/{userid}")
	public ResponseEntity<String>deleteUsers(@PathVariable("userid")String userId)
	{
		if(usersDAO.deleteUsers(userId))
		{
			return new ResponseEntity<String>("Delete user successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Problem in deleting user", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
