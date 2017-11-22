/**
 * UserController
 */
myApp.controller('UserController',function($scope,UserService,$location,$rootScope,$cookieStore,$routeParams)
		{
			
			$scope.createUsers=function()
			{
				console.log("USER DATA IS ::" + $scope.users)
				UserService.createUsers($scope.users).then
				(
						
						function(response)
						{
							/*alert("success response is::"+response);*/
							console.log(response.data)
							console.log(response.status)
							//alert("Successfully registered & waiting for approval..!!!")
							$location.path('/Home')
						}
						,function(response)
						{
							/*alert("failure response::"+response);*/
							console.log(response.data)
							console.log(response.status)
							$scope.error=response.data
							$location.path('/Register')
						}
				)
			}
			//=======================login==========================
			$scope.Login=function()
			{
				console.log("LOGIN DATA::"+$scope.userobj)
				UserService.Login($scope.userobj)
				.then
				(
						function(response)
						{
							/*alert("success response::"+response);*/
							$rootScope.currentUser=response.data
							$cookieStore.put('userDetails',response.data)
							console.log(response.data)
							console.log(response.status)
							$location.path('/Home')
						}
						,function(response)
						{
							/*alert("failure response::"+response);*/
							console.log(response.data)
							console.log(response.status)
							$scope.error=response.data.message
							$location.path('/Login')
						}
				)
			}
			//=====================updateUser============================================
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
			//===========getUser=====================================================
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
			//============list of users which are approved========================================
			function usersApproved()
			{
				UserService.usersApproved()
				.then(
					function(response)
					{
						$scope.listOfUsersApproved=response.data	//List<Users>approved(1)
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			//==================list of users waiting for approved====================================
			function userWaitingForApproval()
			{
				UserService.userWaitingForApproval()
				.then(
					function(response)	//success
					{
						$scope.listOfUsersWaitingForApproval=response.data	//List<Users> waiting for approval(0)
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			
			usersApproved()				//select * from Blogpost where status='1';
			userWaitingForApproval()	//select * from Blogpost where status='0';
			
			
			
			
	
			
			
		})