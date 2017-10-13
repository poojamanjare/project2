/**
 * BlogPostDetailController
 * getBlogByid/1
 * getBlogByid/:id
 * $routeParams.id=>1
 */

myApp.controller('BlogPostDetailController',function($scope,$location,BlogPostService,$routeParams)
		{
	$scope.isRejected=false
			var blogId=$routeParams.blogId
			/*alert("blogId"+blogId)*/
			BlogPostService.getBlogById(blogId)
			.then(
					function(response)
					{
						console.log(response.data)
						$scope.blogPost=response.data	//BlogPost[select * from blogPost where blogId=?]
					},function(response)
					{
						if(response.status==401)
							$location.path('/Login')
					}
				)
				
			/*update approved/rejectionReason in blogPost table 
			 * update blogPost set approved=1 where blogid=?
			 * (or)
			 * update blogPost set rejectionReason=? where blogid=?
			 * */
				
			$scope.updateBlogPost=function()
			{
		/*console.log("Approved/Reject::"+$scope.blogPost.approved)*/
			BlogPostService.updateBlogPost($scope.blogPost)
					.then
					(
						function(response)
						{
							$location.path('/getBlogs')
						},function(response)
						{
							console.log(response.status)
							if(response.status==401)
								$location.path('/Login')
						})
			}
	
		//============================update likes===================================================================
		$scope.updateLikes=function()
		{
			$scope.isLiked=!$scope.isLiked;	//it is true(initially it is false...we convert it into true)
			if($scope.isLiked)
				{
					$scope.blogPost.likes=$scope.blogPost.likes + 1//1st click
				}
			else
				{
				$scope.blogPost.likes=$scope.blogPost.likes - 1//2nd click
				}
			
			//update blogpost set likes=? where blogid=?
			BlogPostService.updateBlogPost($scope.blogPost)
			.then
			(
				function(response)
				{
					/*alert("likes")*/
					console.log(response.data)
				},function(response)
				{
					console.log(response.status)
					if(response.status==401)
						$location.path('/Login')
				})
		}
	
		$scope.showRejectionText=function(val)
		{
			$scope.isRejected=val	//true/false
		}
		
		
		//==================insert into blogcomment==================================
		$scope.addComment=function()
		{
			console.log($scope.blogComment)//commentText property in blogcomment
			
			//value for FK blogId in blogComment
			$scope.blogComment.blogPost=$scope.blogPost	//blogpost property in blogcomment
			console.log($scope.blogComment)
			BlogPostService.addComment($scope.blogComment)
			.then
			(
					function(response)
					{
						console.log(response.data)
						$scope.blogComment.commentText=''	//after loading new comment then textasrea will get empty again
						getBlogComments()
					},function(response)
					{
						console.log(response.status)
						if(response.status==401)
							$location.path('/Login')
						else
							$location.path("/getBlogById/"+blogId)	//  else user go to same page i.e. blogDetails.html
					})
		}
		
		function getBlogComments()	//select blogcomments for particular blogpost
		{
			BlogPostService.getBlogComments(blogId)
			.then
			(
				function(response)
				{
					$scope.blogComments=response.data	//list of blogcomments for blogId
				},function(response)
				{
					console.log(response.status)
					if(response.status==401)
						$location.path('/Login')
				})
		}
		getBlogComments()
		
})