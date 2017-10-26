package com.collaborate.restController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.collaborate.Services.ProfilePictureService;
import com.collaborate.model.Error;
import com.collaborate.model.ProfilePicture;
import com.collaborate.model.Users;
//import com.collaborate.model.Users;

@RestController
public class ProfilePictureController 
{
	@Autowired
	ProfilePictureService profilePictureService;
	
	@PostMapping(value="/uploadProfilePicture")
	public ResponseEntity<?>uploadProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session)
	{
		System.out.println("in upload profile picture####################################");
		String userId=(String) session.getAttribute("userId");
		if(userId==null)		
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setUsername(userId);
		profilePicture.setImage(image.getBytes());
		profilePictureService.uploadProfilePicture(profilePicture);
		return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
	}
	
	@GetMapping(value="/getProfilePicture/{username}")
	public @ResponseBody byte[] getProfilePicture(@PathVariable String username,HttpSession session)
	{
		String userId=(String) session.getAttribute("username");
		if(userId==null)
			return null;
		else
		{
			ProfilePicture profilePicture=profilePictureService.getProfilePicture(username);
			if(profilePicture==null)	//no profile pic for the logged in user
				return null;
			else
				return profilePicture.getImage();	//it will return image of logged in user
		}
		
	}
}