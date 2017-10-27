/**
 * ChatController
 */
myApp.controller('ChatController',function($rootScope,$scope,socket)
		{
			alert('Entering ChatController')
			$scope.chats=[];	//array of chat messages
			$scope.users=[];	//array of userIds
			$scope.stompClient=socket.stompClient
			
			$scope.$on('sockConnected',function(event,frame)	//here sockConnected is id
			{
				alert('SockConnected')
				$scope.userName=$rootScope.currentuser.userId
				
				//=========Newly Joined User=================================
				$scope.stompClient.subscribe("/app/join"+$scope.userName,function(message)
				{
					$scope.users=JSON.parse(message.body)	//List of Users
					console.log($scope.users)
					$scope.$apply();
				})
				//===============================
				$scope.stompClient.subscribe("/topic/join",function(message)
				{
					user=JSON.parse(message.body)	//message.body====>userId
					//For newly joined user,"if" statement will not get satisfied 
					//and so statements inside "if" will not get executed
					if(user != $scope.userName && $inArray(user,$scope.users)== -1)
					{
						$scope.addUser(user);
						$scope.latestUser = user;
						$scope.$apply();
						$('#joinedChat').fadeIn(100).delay(10000).fadeOut(200);
					}
				})
			})
			//===========add user==================
			$scope.addUser=function(user)
			{
				$scope.users.push(user)
				$scope.$apply();
			}
		})	