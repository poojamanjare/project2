/**
 * UserController
 */
myApp.controller('UserController',function($scope,UserService,$location)
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
		})

/*$scope.login=function()
{
	console.log($scope.userobj)
	UserService.login($scope.userobj).then(function(response){
	
		$location.path('/home')
	},function(response)
	{
		$scope.error="Invalid Username/password"   you can write this also
			$scope.error=response.data.message
			$location.path('/login')
			
	}
	
	
	
	)
	
	}
*/


