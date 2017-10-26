/**
 * friendController
 */
myApp.controller('FriendController',function($scope,$location,FriendService)
		{
			function listOfSuggestedUsers()
			{
				FriendService.listOfSuggestedUsers()
				.then
				(
					function(response)
					{
						$scope.suggestedUsers=response.data	//it return list of users object
					
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			//=================send friend request=============================================
			$scope.sendFriendRequest=function(toId)
			{
				FriendService.sendFriendRequest(toId)
				.then
				(
					function(response)
					{
						alert("Friend Request has been sent successfully")
						listOfSuggestedUsers()
						$location.path('/getSuggestedUsers')
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			//======================pending requests()=============================================
			function pendingRequests()
			{
				FriendService.pendingRequests()
				.then
				(
					function(response)
					{
						$scope.pendingRequests=response.data	//it returns list of friend object in json format
						
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			//================update pending requests=====================================================
			$scope.updatePendingRequests=function(request,statusValue)	//statusValue=A orD
			{
				//before assignment request.status is P
				console.log(request)	//it will show all values
				console.log(request.status)	//P
				request.status=statusValue	//status value is either A or D
				console.log(request.status)	//A or D
				console.log(request)	//it will show all values but status value get changed
				FriendService.updatePendingRequests(request)
				.then
				(
					function(response)
					{
						pendingRequests()
						$location.path('/pendingRequests')
						
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
				
			}
			//=========================list of friends=====================================
			function listOfFriends()
			{
				FriendService.listOfFriends()
				.then
				(
					function(response)
					{
						$scope.friends=response.data	//List<String>
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			
			//function call
			listOfSuggestedUsers()	//select
			pendingRequests()	//select
			listOfFriends()
		})