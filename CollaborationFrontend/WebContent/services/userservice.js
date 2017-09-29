/**
 * UserService is to make server side calls
 */
myApp.factory('UserService',function($http)
		{
			var UserService={}
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
				UserService.createUsers=function(users)
				{
					/*alert(users);*/
					return $http.post(BASE_URL+"/createUsers",users)
				}
			
				UserService.Login=function(userobj)
				{
					return $http.post(BASE_URL+"/Login",userobj)
				}
			return UserService;
		})