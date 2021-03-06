package com.collaborate.restController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.DAO.UsersDAO;
import com.collaborate.Services.UserService;
import com.collaborate.model.Users;
import com.collaborate.model.Error;


@RestController
public class UserController 
{
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	UserService userService;
	
	public UserController() 
	{
		System.out.println("UserController Instantialted...!!!");
		
	}
	
	//============method for creating user===============================
	@PostMapping(value="/createUsers")
	public ResponseEntity<?>createUsers(@Valid @RequestBody Users users,BindingResult bindingResult)
	{
		System.out.println("##################"+users.getUserId()+"##############");
		if(!userService.isUserIdValid(users.getUserId()))
		{
			Error error=new Error(2,"Username already exists...Please enter different username");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);//500
		}
		if(!userService.isEmailValid(users.getEmail()))
		{
			Error error=new Error(3, "Email already exists...Please enter different email-Id");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
		}
		boolean result=userService.createUsers(users);
		System.out.println("result is::"+result);
		if(result)
		{
			return new ResponseEntity<Users>(users,HttpStatus.OK);//200-299
		}
		else
		{
			Error error=new Error(1, "Unable to register user details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
	}
	//================================approved users==========================================
	@GetMapping(value="/getApproveUsers/{status}")
	public ResponseEntity<?>getUsers(@PathVariable int status,HttpSession session)
	{
		System.out.println("approving users..........");
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<Users>users=userService.getUsers(status);
		return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
	}
	
	//=========================update users(approved/reject)==========================================================
		@PutMapping(value="/approveUser")
		public ResponseEntity<?>approveUser(@RequestBody Users users,HttpSession session)
		{
			System.out.println("in usercontroller=====approve user==update");
			String userId=(String) session.getAttribute("userId");
			if(userId==null)
			{
				Error error=new Error(5, "unauthorized access...");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
			}
			//admin has reject the user but reason is not mentioned
			/*if(!users.isStatus() && users.getRejectReason()==null)	//status=0 and rejectReason=null
			{
				users.setRejectReason("Not Mentioned");
			}*/
			
			userService.update(users);
			return new ResponseEntity<Users>(users,HttpStatus.OK);
		}
	
	
	
	//===================================login===================================
	@PostMapping(value="/Login")
	public ResponseEntity<?>Login(@RequestBody Users users,HttpSession session)
	{
		Users validuser=usersDAO.login(users);
		System.out.println("***********validuser***"+validuser);
		if(validuser==null)		//invalid Username/password
		{
			Error error=new Error(4, "invalid Username/Password...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	//error 2nd callback function
			//response.data=error
			//response.status=401
		}
		System.out.println("Online status before update::"+validuser.getIsOnline());//No
		validuser.setIsOnline("Yes");
		try
		{
			System.out.println("is validated...........");
			userService.update(validuser);
		}
		catch(Exception e)
		{
			Error error=new Error(6, "unable to update online status...");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("Online status after update::"+validuser.getIsOnline());//yes
		session.setAttribute("userId",validuser.getUserId());
		return new ResponseEntity<Users>(validuser,HttpStatus.OK);	//success 1st callback function
	}
	//=========================logout=============================================================
	@PutMapping(value="/Logout")
	public ResponseEntity<?>Logout(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");//this statement return null value
		System.out.println("Name of user is::"+userId);
		if(userId==null)
		{
			Error error=new Error(5, "Unauthorized access...please login first");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		Users users=userService.getUserById(userId);
		users.setIsOnline("No");
		userService.update(users);
		session.removeAttribute("userId");
		session.invalidate();
		return new ResponseEntity<Users>(users, HttpStatus.OK);
		
	}
	//==================get user by id=======================
	@GetMapping(value="/getUserById/{userId}")
	public ResponseEntity<?>getUserById(@PathVariable String userId,HttpSession session)
	{
		System.out.println("get user by id::approved..........");
		String userId1=(String) session.getAttribute("userId");
		System.out.println(userId1);
		if(userId1==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		Users users=userService.getUserById(userId);
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	//===========================get user===========================================
	@GetMapping(value="/getUser")
	public ResponseEntity<?>getUser(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(7, "Unauthorized access...please login first");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		Users users=userService.getUserById(userId);
		return new ResponseEntity<Users>(users,HttpStatus.OK);
	}
	//================================update user profile==================================
	@PutMapping(value="/updateUser")
	public ResponseEntity<?>updateUser(@RequestBody Users users,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		System.out.println("*************userid in update()::"+userId);
		if(userId==null)
		{
			Error error=new Error(7, "Unauthorized access...please login first");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		if(!userService.isUpdatedEmailValid(users.getEmail(),users.getUserId()))
		{
			System.out.println("errrrrrrrrrrrrrrrrr");
			Error error=new Error(3, "Email already exists...Please enter different email-Id");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		try
		{
			System.out.println("in usercontroller............update");
			userService.update(users);
			return new ResponseEntity<Users>(users,HttpStatus.OK);
			
		}
		catch(Exception e)
		{
			System.out.println("catch exception...........");
			Error error=new Error(1, "Unable to register user details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
	}
	
	
	
	
	
	
	
	/*//=============method for getting user by userid=====================
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
	}*/
	

}
