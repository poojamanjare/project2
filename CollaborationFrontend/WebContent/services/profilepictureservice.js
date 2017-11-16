/**
 * ProfilePictureService
 */
myApp.factory('ProfilePictureService',function($http)
		{
			var ProfilePictureService={}
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
				
			ProfilePictureService.uploadProfilePicture=function(profilePicture)
			{
				return $http.post(BASE_URL + "/uploadProfilePicture",profilePicture)
			}
		})