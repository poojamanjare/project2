/**
 * JobController
 */

myApp.controller('JobController',function($scope,$location,JobService)
		{
			$scope.showJobDetails=false;
			$scope.addJob=function()	//this function execute automatically when admin clock on post job button
			{
				JobService.addJob($scope.job)//here call the service
				.then
				(
					function(response)
					{
						console.log(response.data)//here it print job object
						console.log(response.status)//it print 200 status code
						$location.path('/getAllJobs')
					},function(response)
					{
						console.log(response.data)//here it print error object
						console.log(response.status)//it print 401/500 status code
						$scope.errorMsg=response.data.message
						if(response.status==401)
							{
								$scope.error=response.data.message	//error data get display
								$location.path('/Login')
							}
						else
							{
							$scope.error=response.data.message
							$location.path('/addJob')
							}
					})
			}
			function getAllJobs()
			{
				JobService.getAllJobs()
				.then
				(
					function(response)
					{
						$scope.jobs=response.data	//List<job>
					},function(response)
					{
						console.log(response.data)
						if(response.status==401)
							{
								//$scope.error=response.data.message	//error data get display
								$location.path('/Login')
							}
					})
			}
			getAllJobs()//this statement gets executed automatically whenever the controller get instantiated
			
			//===================to get job details by jobid=======================================
			$scope.getJobDetails=function(jobId)	//jobId is the function parameter u can give any variable name
			{
				$scope.showJobDetails=true;
				JobService.getJobDetails(jobId)
				.then
				(
					function(response)
					{
						$scope.job=response.data //jobobject result of select * from job where jobId=?
					},function(response)
					{

						console.log(response.data)
						if(response.status==401)
							{
								//$scope.error=response.data.message	//error data get display
								$location.path('/Login')
							}
					})
				
			}
		})