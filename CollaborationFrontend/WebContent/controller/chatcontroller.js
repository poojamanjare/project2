/**
 * ChatController
 */
myApp.controller('ChatController',['$rootScope','$scope','socket' ,function($rootScope,$scope,socket)	//socket is service name
		{
			alert('Entering ChatController')
			$scope.chats=[];	//array of chat messages
			$scope.users=[];	//array of userIds in chatroom
			$scope.stompClient=socket.stompClient
			
			$scope.$on('sockConnected',function(event,frame)	//here sockConnected is id
			{
				alert('SockConnected')
				$scope.userName=$rootScope.currentUser.userId
				console.log($scope.userName)
				//=========Newly Joined User=================================
				$scope.stompClient.subscribe("/app/join/"+$scope.userName,function(message)
				{
					$scope.users=JSON.parse(message.body)	//List of Users
					console.log($scope.users)
					$scope.$apply();
				})
				//===============================
				// /topic-->to get newly join userId
				$scope.stompClient.subscribe("/topic/join",function(message)
				{
					user=JSON.parse(message.body)	//message.body====>userId
					//For newly joined user,"if" statement will not get satisfied 
					//and so statements inside "if" will not get executed
					console.log(user)
					if(user != $scope.userName && $.inArray(user,$scope.users)== -1)
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
				$scope.users.push(user);
				$scope.$apply();
			}
			//===========================================
			$scope.capitalize = function(str)
			{
				return str.charAt(0).toUpperCase() + str.slice(1);
			}
			
			//===========send Message=============================
			$scope.sendMessage=function(chat)
			{
				chat.from=$scope.userName
				$scope.stompClient.send("/app/chat",{},JSON.stringify(chat));	//{}-empty value for header
				$rootScope.$broadcast('sendingChat',chat);
				$scope.chat.message='';
			}
			//=========================================
			$scope.$on('sockConnected',function(event,frame)
			{
				$scope.userName=$rootScope.currentUser.userId;	//userName=to
				//private chat
				//	/queue-->to get chatmessages form destination
				$scope.stompClient.subscribe("/queue/chats/"+$scope.userName,function(message)
				{
					$scope.processIncomingMessage(message,false);	//broadcast=false
				})
				
				//group chat
				$scope.stompClient.subscribe("/queue/chats",function(message)
				{
					$scope.processIncomingMessage(message,true);		//broadcast=true
				})
			})
			//=======To add chat message to the chat array ===============
			$scope.processIncomingMessage = function(message, isBroadcast)
			{
				message = JSON.parse(message.body)
				message.direction='incoming';			//left side
				if(message.from != $scope.userName)
				{
					$scope.addChat(message)
					$scope.$apply();	//since inside subscribe closure
				}
			}			
			//====addChat========================================
			$scope.addChat = function(chat)
			{
				$scope.chats.push(chat);
			}
			//=======================================================
			$scope.$on('sendingChat',function(event,sentChat)
			{
				chat=angular.copy(sentChat);
				chat.from='Me';
				chat.direction='outgoing';				//right side
				$scope.addChat(chat);
			});
			
		}]);