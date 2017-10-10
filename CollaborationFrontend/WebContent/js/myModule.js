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
			
			.when("/addBlogPost",{
				templateUrl:"views/BlogPostForm.html",
				controller:"BlogPostController"				
			})
			
			.when("/getBlogs",{
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
				UserService.Logout().then(
									function(response)
									{
											delete $rootScope.currentUser;
											$cookieStore.remove('userDetails')
											$location.path('/Login')
									}
									,function(response)
									{
											console.log(response.status)
											if(response.status==401)
												{
														delete $rootScope.currentUser;
														$cookieStore.remove('userDetails')
														$location.path('/Login')
												}
									})
			}
		})