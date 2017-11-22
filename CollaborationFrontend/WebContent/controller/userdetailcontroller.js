/**
 * UserDetailController.js
 */
myApp.controller('UserDetailController',function($scope,$location,UserService,$routeParams)
		{
			$scope.isRejected=false
			var userId=$routeParams.userId
			UserService.getUserById(userId)
			.then(
					function(response)
					{
						console.log(response.data)
						$scope.users=response.data		
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					}
				)

		
		//=====================approveUser((update)===============================================
			$scope.approveUser=function()
			{
			alert('update')
			UserService.approveUser($scope.users)
					.then
					(
						function(response)
						{
							$location.path('/approveUser')
						},function(response)
						{
							console.log(response.status)
							if(response.status==401)
								$location.path('/Login')
						})
			}
			//===============show Rejection reason=============================================
			$scope.showRejectionText=function(val)
			{
					$scope.isRejected=val	//true/false
			}
		})