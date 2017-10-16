/**
 * JobService.js
 */
myApp.factory('JobService',function($http)
		{
			var jobService={}	//create instance
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
			
			jobService.addJob=function(job)
			{
				return $http.post(BASE_URL + "/addJob",job)
			}
			jobService.getAllJobs=function()
			{
				return $http.get(BASE_URL + "/getAllJobs")
			}
			jobService.getJobDetails=function(jobId)
			{
				return $http.get(BASE_URL +"/getjob/"+jobId)
			}
			
			
			return jobService;
		})