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
			
				UserService.getUserById=function(userId)
				{
					console.log("userId:"+userId)
					return $http.get(BASE_URL+"/getUserById/"+userId)
				}
			
				UserService.usersApproved=function()
				{
					return $http.get(BASE_URL+"/getApproveUsers/"+1)		//select * from blogpost where status='1';
				}
			
				UserService.userWaitingForApproval=function()
				{
					return $http.get(BASE_URL+"/getApproveUsers/"+0)	//select * from blogpost where status='0';
				}
				
				UserService.approveUser=function(users)
				{
					console.log(users)
					return $http.put(BASE_URL+"/approveUser",users)
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