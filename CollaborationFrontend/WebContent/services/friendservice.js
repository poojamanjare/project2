/**
 * FriendService
 */
myApp.factory('FriendService',function($http)
		{
			var friendService={}
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
			
			friendService.listOfSuggestedUsers=function()
			{
				return $http.get(BASE_URL+"/getSuggestedUsers")
			}
			friendService.sendFriendRequest=function(toId)
			{
				return $http.get(BASE_URL+"/friendRequest/"+toId)
			}
			friendService.pendingRequests=function()
			{
				return $http.get(BASE_URL+"/pendingRequests")
			}
			friendService.updatePendingRequests=function(request)	//request is friend object{friendId,fromId,toId,status}this request object have updated value of status
			{
				return $http.put(BASE_URL+"/updatePendingRequests",request)
			}
			friendService.listOfFriends=function()
			{
				return $http.get(BASE_URL+"/listOfFriends")
			}
			
			return friendService;
		})