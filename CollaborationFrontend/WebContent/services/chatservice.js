/**
 * ChatService
 */
myApp.factory('socket',function($rootScope)
		{
			alert('app factory')
			var socket=new SockJS("/CollaborationRestServices/portfolio");
			var stompClient=Stomp.over(socket);
			stompClient.connect('','',function(frame)
			{
				$rootScope.$broadcast("sockConnected",frame)
			})
		})