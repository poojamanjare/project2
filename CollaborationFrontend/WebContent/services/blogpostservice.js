/**
 *BlogPostService.js 
 */
myApp.factory('BlogPostService',function($http)
		{
			var blogPostService={}
			var BASE_URL="http://localhost:8085/CollaborationRestServices"
				blogPostService.addBlogPost=function(blogPost)
				{
					return $http.post(BASE_URL+"/addBlogPost",blogPost)
				}
				
				blogPostService.getBlogById=function(blogId)
				{
					return $http.get(BASE_URL+"/getBlogById",blogId)
				}
				
				blogPostService.blogsApproved=function()
				{
					return $http.get(BASE_URL+"/getBlogs/"+1)
				}
				
				blogPostService.blogWaitingForApproval=function()
				{
					return $http.get(BASE_URL+"/getBlogs/"+0)
				}
				//to update approved property and rejection reason(approve/reject)
				//blogId,blogTitle,blogContent,postedBy,postedOn,rejectionReason
				blogPostService.updateBlogPost=function()
				{
					return $http.put(BASE_URL+"/update")
				}
				
				
			
			return blogPostService;
		})