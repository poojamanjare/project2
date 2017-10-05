/**
 * UserService is to make server side calls
 */
myApp.factory('UserService',function($http)
		{
			var UserService={}
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
				UserService.createUsers=function(users)
				{
					/*alert(users.userId);*/
					return $http.post(BASE_URL+"/createUsers",users)
				}
			
				UserService.Login=function(userobj)
				{
					return $http.post(BASE_URL+"/Login",userobj)
				}
				
				UserService.Logout=function()
				{
					return $http.put(BASE_URL+"/Logout")
				}
				
				UserService.getUser=function()
				{
					return $http.get(BASE_URL+"/getUser")
				}
				
				UserService.updateUser=function(users)
				{
					return $http.put(BASE_URL+"/updateUser",users)
				}
				
				
				
				
			return UserService;
		})