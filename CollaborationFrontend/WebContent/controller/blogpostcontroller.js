/**
 * BlogPostcontroller.js
 */
myApp.controller('BlogPostController',function($scope,$location,BlogPostService)
		{
			$scope.addBlogPost=function()
			{
				BlogPostService.addBlogPost($scope.blogPost)
				.then(
					function(response)
					{
						alert("BlogPost added Successfully & Waiting for approval...!!!")
						$location.path('/getBlogs')
					},function(response)	//response status[401/500]
					{
						$scope.error=response.data 	//Error(code,message)
						if(response.status==401)	//401
							$location.path('Login')
						else						//500
							$location.path('addBlogPost')
					}
					)
			}
			//list of blogs which are approved
			function blogsApproved()
			{
				BlogPostService.blogsApproved()
				.then(
					function(response)
					{
						$scope.listOfBlogsApproved=response.data	//List<BlogPost>approved(1)
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			//list of blogs waiting for approved
			function blogWaitingForApproval()
			{
				BlogPostService.blogWaitingForApproval()
				.then(
					function(response)	//success
					{
						$scope.listOfBlogsWaitingForApproval=response.data	//List<BlogPost> waiting for approval(0)
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					})
			}
			
			blogsApproved()				//select * from Blogpost where approved=1;
			blogWaitingForApproval()	//select * from Blogpost where approved=0;
		
		})