// ajax檢查結果
function getCheckResultData(checkUrl){
    console.log("checkUrl = " + checkUrl);
    var result = $.Deferred();
    $.getJSON(checkUrl).done(function(data){
  	  result.resolve(data);
    });
    return result;
}

// 傳入URL進行URL與Base64 encode
function urlBase64Encode(rawStr){
	return btoa(encodeURIComponent(rawStr));
}

// 傳入加密的Base64+URL encode字串，進行解密動作
function urlBase64Decode(encodeStr){
	return decodeURIComponent(atob(encodeStr));
}

// 傳入欄位Id , 驗證資訊Id與檢查的url，進行資料檢查
function inputBlurHandler(inputName , verifyMessage, ajaxUrl){
	$(inputName).blur("change paste keyup", function() {
		var checkUrl = ajaxUrl + $(inputName).val();
		var result = getCheckResultData(checkUrl);
		result.promise().then(messageEven);
	});
	
	var selectorname = $(verifyMessage);
	
	function messageEven(data) {
		selectorname.html('');
		selectorname.append(data.statusDesc);
		if (data.status != 200) {
			selectorname.css("color", "red");
		} else {
			selectorname.css("color", "green");
		}
	}
}

// 傳入欄位Id，預覽圖片Id，驗證資訊Id與檢查的url，進行資料檢查
function inputBlurHandler(inputName , preImgName , verifyMessage, ajaxUrl){
	$(inputName).blur("change paste keyup", function() {
		var checkUrl = ajaxUrl + urlBase64Encode($(inputName).val());
		var result = getCheckResultData(checkUrl);
		result.promise().then(messageEven);
	});
	
	var selectorname = $(verifyMessage);
	
	function messageEven(data) {
		selectorname.html('');
		selectorname.append(data.statusDesc);
		// 若失敗，預覽圖不會顯示
		if (data.status != 200) {
			selectorname.css("color", "red");
			$(preImgName).hide();
		} else {
			// 圖片網址正確，會顯示預覽圖
			selectorname.css("color", "green");
	      	$(preImgName).attr("src" , $(inputName).val());
	      	$(preImgName).show();
		}
	}
}

