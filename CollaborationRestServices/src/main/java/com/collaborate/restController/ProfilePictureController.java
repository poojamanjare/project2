package com.collaborate.restController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.collaborate.DAO.ProfilePictureDAO;
import com.collaborate.DAO.UsersDAO;
import com.collaborate.Services.ProfilePictureService;
import com.collaborate.model.Error;
import com.collaborate.model.ProfilePicture;
import com.collaborate.model.Users;
//import com.collaborate.model.Users;
//import com.collaborate.model.Users;

@Controller
public class ProfilePictureController
{
	@Autowired
	ProfilePictureDAO profilePictureDAO;
	@Autowired
	UsersDAO usersDAO;
	@RequestMapping(value="/uploadProfilePicture",method=RequestMethod.POST) //To upload the new Profile pic
	public ResponseEntity<?> uploadProfilePic(@RequestParam CommonsMultipartFile pimage,HttpSession session)
	{
		
		String userId=(String) session.getAttribute("userId");
		Users users=usersDAO.getUserByUserId(userId);
		if(users==null)
		{
			Error error=new Error(5,"Unauthorized");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setUserId(users.getUserId());
		profilePicture.setPimage(pimage.getBytes());
		profilePictureDAO.uploadProfilePicture(profilePicture);
		return new ResponseEntity<Users>(users,HttpStatus.OK);
		
	}
	//=================================================================================================
	@RequestMapping(value="/getProfilePicture/{userId}", method=RequestMethod.GET)		//To get the profile pic of particular User
	public @ResponseBody byte[] getProfilePic(@PathVariable String userId,HttpSession session){
		String username1=(String) session.getAttribute("userId");
		Users users=usersDAO.getUserByUserId(username1);
		if(users==null)
			return null;
		else
		{
			ProfilePicture profilePicture=profilePictureDAO.getProfilePicture(userId);
			if(profilePicture==null)
				return null;
			else
				return profilePicture.getPimage();
		}
		
}
}