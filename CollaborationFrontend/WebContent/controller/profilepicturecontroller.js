/**
 * ProfilePictureController
 */
myApp.controller('ProfilePictureController',function($scope,$location,ProfilePictureService)
		{
			$scope.uploadProfilePicture=function()
			{
				var file = $scope.myFile;
	               
	               console.log('file is ' );
	               console.dir(file);
	               
	               var uploadUrl = "/fileUpload";
	               fileUpload.uploadFileToUrl(file, uploadUrl);
			}
		})