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

import com.collaborate.DAO.JobDAO;
import com.collaborate.model.Job;

@RestController
public class JobController 
{
	@Autowired
	JobDAO jobDAO;
	
	//==============method for create job=========================
	@PostMapping(value="/createJob")
	public ResponseEntity<String>createJob(@RequestBody Job job)
	{
		job.setCreateDate(new Date());
		job.setStatus("NA");
		
		if(jobDAO.createJob(job))
		{
			return new ResponseEntity<String>("job created successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in job creation", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//============method for get job by id========================
	@GetMapping(value="/getJobById/{jobid}")
	public ResponseEntity<Job>getJobById(@PathVariable("jobid")int jobId)
	{
		Job job=jobDAO.getJob(jobId);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	//==============method for getting list of all jobs========================
	@GetMapping(value="/getAllJobs")
	public ResponseEntity<List<Job>>getAllJobs()
	{
		ArrayList<Job>listJobs=new ArrayList<Job>();
		listJobs=(ArrayList<Job>) jobDAO.getJob();
		return new ResponseEntity<List<Job>>(listJobs, HttpStatus.OK);
	}
	
	//=========method for approve job==========================
	@GetMapping(value="/approveJob/{jobid}")
	public ResponseEntity<String>approveJob(@PathVariable("jobid")int jobId)
	{
		Job job=jobDAO.getJob(jobId);
		if(jobDAO.approveJob(job))
		{
			return new ResponseEntity<String>("approve job successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in approve job", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//============method for approve list of job==========================
	@GetMapping(value="/getAllApproveJobs")
	public ResponseEntity<List<Job>>getAllApproveJobs()
	{
		ArrayList<Job>listjobs=new ArrayList<Job>();
		listjobs=(ArrayList<Job>) jobDAO.getApproveJob();
		return new ResponseEntity<List<Job>>(listjobs, HttpStatus.OK);
	}
	
	//===========method for update job=============================
	@GetMapping(value="/updateJob/{jobid}")
	public ResponseEntity<String>updateJob(@PathVariable("jobid")int jobId)
	{
		if(jobDAO.updateJob(jobId))
		{
			return new ResponseEntity<String>("job updated successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in update job", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//============================method for delete job==================
	@GetMapping(value="/deleteJob/{jobid}")
	public ResponseEntity<String>deleteJob(@PathVariable("jobid")int jobId)
	{
		if(jobDAO.deleteJob(jobId))
		{
			return new ResponseEntity<String>("job deleted successfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("problem in delete job", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
