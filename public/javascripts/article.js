$(document).ready(function() {
    var showChar = 100;  // 設定最長字數
    var ellipsestext = "...";
    var moretext = "更多內容 >";
    var lesstext = "少點內容 <";
    
    $('.more').each(function() {
        var content = $(this).html();
        // 如果沒超過字數限制，不需要修改
        if(content.length <= showChar) {
        	return;
        }
        var c = content.substr(0, showChar);
        var html = c + '<span class="moreellipses">' + ellipsestext+ '&nbsp;</span></p>' + 
        	   	   '<span class="morecontent"><span>' + content + '</span><a class="morelink">' + moretext + '</a></span>';
        // 重新設定文章內容
        $(this).html(html);
    });
	 
    $(".morelink").click(function(){
        if($(this).hasClass("less")) {
            $(this).removeClass("less");
            $(this).html(moretext);
        } else {
            $(this).addClass("less");
            $(this).html(lesstext);
        }
        $(this).parent().prev().toggle();
        $(this).prev().toggle();
        return false;
    });
    
    function restoreComments(){
   	   $("confirmationForm").remove();
   	   $('.icon.fa-comments').bind("click");
    }
    
    $(".icon.fa-comments").click(function(){
    	var inputText = 
    		'<form action="" name="confirmationForm" method="post">'
    		+'<textarea id="confirmationText" class="text" cols="30" rows ="5" name="confirmationText" form="confirmationForm"></textarea>'
    		+'<input type="submit" value="回覆" class="submitButton">'
    		+'<input type="submit" value="取消" onclick = "restoreComments()" class="submitButton">'
    		+'</form>';
    	$(this).append(inputText);
    	$(this).unbind("click");
    });
    
});