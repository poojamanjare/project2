/**
 * ChatService
 */
myApp.filter('reverse',function()
		{
			return function(items)
			{
				return items.slice().reverse();
			};
		});	
		
myApp.directive('ngFocus',function()
		{
			return function(scope,element,attrs)
			{
				element.bind('click', function()
				{
					$('.' + attrs.ngFocus)[0].focus();
				});
			};
		});
myApp.factory('socket',function($rootScope)
		{
			alert('myApp factory')
			var socket=new SockJS("/CollaborationRestServices/portfolio");
			var stompClient=Stomp.over(socket);		//bind socket with stomp
			stompClient.connect('','',function(frame)
			{
				$rootScope.$broadcast('sockConnected',frame)
			});
			
			return{
				stompClient:stompClient
			};
			
		});