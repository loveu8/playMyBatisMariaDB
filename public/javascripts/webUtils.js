
// 傳入Input欄位ID與要顯示imgID，檢查是否要顯示圖片
function imgBlurHandler(inputUrl , imgUrl){
	$(inputUrl).blur("change paste keyup", function() {
		if(!isURL($(inputUrl).val())){
	  		console.log("this url is not right , url = " + $(inputUrl).val());
    		$(imgUrl).hide();
	  		return;
		}
		var checkImage = function(url){
		    var s = document.createElement("IMG");
		    s.src = url;
		    s.onerror = function(){
		  		console.log("this image url is invalid , url = " + url);
	    		$(imgUrl).hide();
		    }
		    s.onload = function(){
		        console.log("this image url is valid , url = " + url);
		    	$(imgUrl).attr("src" , $(inputUrl).val());
		    	$(imgUrl).show();
		    }
		}
      	checkImage($(inputUrl).val()); 
	});
}	

// 確認是否是網址
function isURL(str) {
  var pattern = new RegExp(/^(http|https|ftp):\/\/[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/i); 
  return pattern.test(str);
}
