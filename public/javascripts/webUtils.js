// 傳入字串，會進行URL與Base64 encode
function urlBase64Encode(rawStr){
	return btoa(encodeURIComponent(rawStr));
}

// 傳入加密的Base64+URL encode字串，進行解密動作
function urlBase64Decode(encodeStr){
	return decodeURIComponent(atob(encodeStr));
}

// 傳入url 並使用 Deferred延遲物件，等候伺服器回傳JSON資料
function getCheckResultData(checkUrl){
    var result = $.Deferred();
    $.getJSON(checkUrl).done(function(data){
  	  result.resolve(data);
    });
    return result;
}

// 初始化頁面日期選擇元件
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