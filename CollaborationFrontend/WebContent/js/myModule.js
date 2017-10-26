	var myApp=angular.module("myModule",["ngRoute","ngCookies"])
myApp.config(function($routeProvider)
	{
		$routeProvider
			.when('/Register',{
				templateUrl:'views/Register.html',
				controller:'UserController'
			})
				
			.when("/Login",{
				templateUrl:"views/Login.html",
				controller:"UserController"
			})
			
			.when("/Home",{
				templateUrl:"views/Home.html"
			})
			
			.when("/AboutUs",{
				templateUrl:"views/AboutUs.html",
				controller:"UserController"				
			})
			
			.when("/ContactUs",{
				templateUrl:"views/ContactUs.html",
				controller:"UserController"				
			})
			
			.when("/editProfile",{
				templateUrl:"views/EditProfile.html",
				controller:"UserController"				
			})
			
			.when("/addBlogPost",{							//view to controller
				templateUrl:"views/BlogPostForm.html",
				controller:"BlogPostController"				
			})
			
			.when("/getBlogs",{						//cintroller to view
				templateUrl:"views/blogslist.html",
				controller:"BlogPostController"				
			})
			
			
			
			.when("/getBlogById/:blogId",{					//blogs approved
				templateUrl:"views/blogDetails.html",		//details of approved blogs //blogPost + textarea
				controller:"BlogPostDetailController"		//$scope.blogPost=select * from blogPost where id=?		
			})
			
			.when("/getApprovalForm/:blogId",{ 				//blogs waiting for approval
				templateUrl:"views/blogApprovalForm.html",	//blogPost + approval form
				controller:"BlogPostDetailController"		//$scope.blogPost=select * from blogPost where id=?			
			})
			
			.when("/addJob",{					//v to c 		$scope.addJob()->insert query-->from frontend to backend
				templateUrl:"views/jobForm.html",
				controller:"JobController"				
			})
			
			.when("/getAllJobs",{			//c to v		function getAllJobs($scope.jobs=[])->select query
				templateUrl:"views/joblist.html",
				controller:"JobController"				
			})
			
			.when("/uploadProfilePicture",{
				templateUrl:"views/profilePicture.html"
			})
			
			.when("/getSuggestedUsers",{				//c to v-->select query-->from backend to frontend
				templateUrl:"views/suggestedUsers.html",
				controller:"FriendController"				
			})
			
			.when("/pendingRequests",{				//c to v-->select query
				templateUrl:"views/pendingRequests.html",
				controller:"FriendController"				
			})
			
			.when("/listOfFriends",{				//c to v-->select query
				templateUrl:"views/listOfFriends.html",
				controller:"FriendController"				
			})
			
						
			.otherwise({
				templateUrl:"views/Home.html"
			})
				
	})
	
	myApp.run(function($rootScope,$cookieStore,UserService,$location)
	
		{
			if($rootScope.currentUser==undefined)
				$rootScope.currentUser=$cookieStore.get('userDetails')
				
			$rootScope.Logout=function()
			{
				console.log('entering logout function')
				UserService.Logout().then(
									function(response)
									{
											console.log('Logged out successfully')
											delete $rootScope.currentUser;
											$cookieStore.remove('userDetails')
											$location.path('/Login')
									}
									,function(response)
									{
											console.log(response.status)
											if(response.status==401)
												{
														console.log('error in logout')
														delete $rootScope.currentUser;
														$cookieStore.remove('userDetails')
														$location.path('/Login')
												}
									})
			}
		})