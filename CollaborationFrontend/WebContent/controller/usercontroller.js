/**
 * UserController
 */
myApp.controller('UserController',function($scope,UserService,$location,$rootScope,$cookieStore)
		{
	
			$scope.createUsers=function()
			{
				console.log("USER DATA IS ::" + $scope.users)
				UserService.createUsers($scope.users).then
				(
						
						function(response)
						{
							alert("success response is::"+response);
							console.log(response.data)
							console.log(response.status)
							$location.path('/Home')
						}
						,function(response)
						{
							alert("failure response::"+response);
							console.log(response.data)
							console.log(response.status)
							$scope.error=response.data
							$location.path('/Register')
						}
				)
			}
			$scope.Login=function()
			{
				console.log("LOGIN DATA::"+$scope.userobj)
				UserService.Login($scope.userobj)
				.then
				(
						function(response)
						{
							alert("success response::"+response);
							$rootScope.currentUser=response.data
							$cookieStore.put('userDetails',response.data)
							console.log(response.data)
							console.log(response.status)
							$location.path('/Home')
						}
						,function(response)
						{
							alert("failure response::"+response);
							console.log(response.data)
							console.log(response.status)
							$scope.error=response.data.message
							$location.path('/Login')
						}
				)
			}
			$scope.updateUser=function()
			{
				console.log("Update::"+$scope.users)
				UserService.updateUser($scope.users)
				.then
				(
						
						function(response)
						{
							alert("user Details get updated successfully...!!!");
							$location.path('/Home')
						}
						,function(response)
						{
							/*alert("failure response");*/
							if(response.status==401)
							{
								$location.path('/Login')
							}
							else
							{
								$scope.error=response.data
								$location.path('/editProfile')
							}	
						}
				)
			}
			if($rootScope.currentUser!=undefined)
			{
				UserService.getUser()
				.then
				(
						function(response)
						{
							/*alert("success response"+users.getUser)*/
							$scope.users=response.data
						}
						,function(response)
						{
							console.log(response.status)
						}
				)
			}
		})