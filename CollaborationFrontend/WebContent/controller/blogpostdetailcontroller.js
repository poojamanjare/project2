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
	
		$scope.showRejectionText=function(val)
		{
			$scope.isRejected=val	//true/false
		}
		})