// 回覆表單
function replyFnc(){
	// 發現有回覆表單時，不再產生
    if($(this).next('form')!=null && $(this).next('form').length > 0){
    	console.log('same form not create new!!');
    	return;
    }
	var inputText = 
		'<form action="" id="confirmationForm" method="post">'
		+'<textarea id="confirmationText" class="text" cols="30" rows ="5" name="confirmationText" form="confirmationForm"></textarea>'
		+'<button type="submit" class = "submitButton">回覆</button>'
		+'<button type="button" onclick = "restoreComments()" class="submitButton">取消</button>'
		+'</form>';
	// 回覆按鈕隱藏
	$(this).hide();
	// 新增reply form
	$(this).after(inputText);
	console.log('reply form create ok!!');
}

// 移除表單
function restoreComments(){
	// 恢復回覆按鈕
	$('#confirmationForm').prev().show();
	// 移除回覆表單
	$('#confirmationForm').remove();
}

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
    
    // 設定回覆按鈕事件
    $(".icon.fa-comments").click(replyFnc);
        
});