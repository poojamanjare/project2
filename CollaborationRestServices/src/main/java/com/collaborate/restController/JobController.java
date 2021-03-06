package com.collaborate.restController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collaborate.Services.JobService;
import com.collaborate.Services.UserService;
import com.collaborate.model.Error;
import com.collaborate.model.Job;
import com.collaborate.model.Users;

@RestController
public class JobController 
{
	@Autowired
	JobService jobService;
	
	@Autowired
	UserService userService;
	
	//=================add job===============================
	@PostMapping(value="/addJob")
	public ResponseEntity<?>addJob(@RequestBody Job job,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		
		//check user role
		Users users=userService.getUserById(userId);
		if(!users.getRole().equals("ADMIN"))	//if userRole is not equals to ADMIN
		{
			Error error=new Error(6, "Access Denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		try
		{
			job.setPostedOn(new Date());
			jobService.addJob(job);
			return new ResponseEntity<Job>(job,HttpStatus.OK);
			
		}
		catch(Exception e)
		{
			Error error=new Error(7, "Unable to insert job Details...");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);//exception
		}
	}
	//====================get all jobs====================================
	@GetMapping(value="/getAllJobs")
	public ResponseEntity<?>getAllJobs(HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		List<Job>jobs=jobService.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}

	//=========================get job details by jobid================================
	@GetMapping(value="/getjob/{jobId}")
	public ResponseEntity<?>getjob(@PathVariable int jobId,HttpSession session)
	{
		String userId=(String) session.getAttribute("userId");
		if(userId==null)
		{
			Error error=new Error(5, "unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//unauthorized
		}
		Job job=jobService.getJob(jobId);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
}
