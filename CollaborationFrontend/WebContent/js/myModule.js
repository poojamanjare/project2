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