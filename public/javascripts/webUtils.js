// 傳入字串，會進行URL與Base64 encode
function urlBase64Encode(rawStr){
	return btoa(encodeURIComponent(rawStr));
}

// 傳入加密的Base64+URL encode字串，進行解密動作
function urlBase64Decode(encodeStr){
	return decodeURIComponent(atob(encodeStr));
}

// ajax檢查結果
function getCheckResultData(checkUrl){
    console.log("ajaxCheckUrl = " + checkUrl);
    var result = $.Deferred();
    $.getJSON(checkUrl).done(function(data){
  	  result.resolve(data);
    });
    return result;
}

// init 讀取會員明細資料
function initLoadMemberProfile(headerPicLink , headerPicPreview , datepicker , username , nickname , cellphone , systemMessage , url){
	var result = getCheckResultData(url);
	result.promise().then(initEven);
		
	function initEven(data) {
		$(systemMessage).html('');
		if (data.editable === false) {
			$(systemMessage).css("color", "red");
			$(systemMessage).append(data.desc);
			return ;
		} 
		$(headerPicLink).val(data.headerPicLink);
		if(data.headerPicLink != ''){
			$(headerPicPreview).val(data.headerPicLink);
		}
		$(datepicker).val(data.birthday)
		$(username).val(data.username);
		$(nickname).val(data.nickname);
		$(cellphone).val(data.cellphone);
	}
}

// 傳入欄位Id , 驗證資訊Id與檢查的url，進行資料檢查
function inputBlurHandler(inputName , verifyMessage, ajaxUrl){
	$(inputName).blur("change paste keyup", function() {
		var checkUrl = ajaxUrl + urlBase64Encode($(inputName).val());
		var result = getCheckResultData(checkUrl);
		result.promise().then(messageEven);
	});
	
	var selectorname = $(verifyMessage);
	
	function messageEven(data) {
		selectorname.html('');
		selectorname.append(data.statusDesc);
		if (data.pass === false) {
			selectorname.css("color", "red");
		} else {
			selectorname.css("color", "green");
		}
	}
}

// 傳入欄位Id，預覽圖片Id，驗證資訊Id與檢查的url，進行資料檢查
function imgInputBlurHandler(inputName , preImgName , verifyMessage, ajaxUrl){
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
		if (data.pass === false) {
			selectorname.css("color", "red");
			$(preImgName).hide();
		} else {
			// 圖片網址正確，會顯示預覽圖
			selectorname.css("color", "green");
			if($(inputName).val()!=''){
	      		$(preImgName).attr("src" , $(inputName).val());
		      	$(preImgName).show();
			} else {
				$(preImgName).hide();
			}

		}
	}
}

// 初始化日期選擇元件
function initDatePicker(datepicker , imgUrl ){

	$(datepicker).datepicker({
	      showOn: "button",
	      buttonImage: imgUrl,
	      buttonImageOnly: true,
	      buttonText: "Select date",
	      changeMonth: true,
	      changeYear: true ,  
	      onSelect: function () {
	    	  // 去觸發生日驗證
	    	  $(datepicker).trigger("blur");
	      }
	});
	// 設定中文顯示
	$.datepicker.setDefaults( $.datepicker.regional[ "zh-TW" ] );
}

