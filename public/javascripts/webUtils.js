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
			$(systemMessage).append(data.systemMessage);
			return ;
		} 
		if(data.headerPicLink != ''){
      		$(headerPicPreview).attr("src" , data.headerPicLink);
			$(headerPicPreview).fadeIn();
		}
		$(headerPicLink).val(data.headerPicLink);
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
		result.promise().then(ajaxMessageEven);
	});
	
	var selectorname = $(verifyMessage);
	
	function ajaxMessageEven(data) {
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
		result.promise().then(ajaxMessageEven);
	});
	
	var selectorname = $(verifyMessage);
	
	function ajaxMessageEven(data) {
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

// 修改表單驗證
function profileHandler(editProfile , systemMessage , headerPicPreview , VerifyMessage){
  var frm = $(editProfile);
  frm.submit(function (ev) {
      $.ajax({
          type: frm.attr('method'),
          url : frm.attr('action'),
          data: frm.serialize(),
          success: function (data) {
        	  
        	  // 系統訊息
              formSystemMessageEven( $(systemMessage) , data);
        	  
              // 欄位檢查結果
              var keys = Object.keys(data.verificResults);
              for(var i = 0 ; i < keys.length ; i++){
            	  var info = data.verificResults[keys[i]];
            	  var selectorname = $('#'+info.inputName + VerifyMessage);
            	  if('headerPicLink' === info.inputName){
            		  formImgMessageEven('#'+info.inputName , headerPicPreview , selectorname , info);
            	  } else {
            		  formMessageEven(selectorname, info);
            	  }
              }
          } , 
      	  error : function(){
      		$(systemMessage).html('系統忙碌中，請稍候再嘗試，謝謝。'); 
      		$(systemMessage).css("color", "red");
      	  }
      });

      ev.preventDefault();
  });
}

// 表單系統訊息提示
function formSystemMessageEven(selectorname , data){
	selectorname.html('');
	selectorname.append(data.statusDesc)
	if (data.update === false) {
		selectorname.css("color", "red");
	} else {
		selectorname.css("color", "green");
	}
}

// 表單欄位驗證提示
function formMessageEven( selectorname , data) {
	selectorname.html('');
	if (data.pass === false) {
		selectorname.css("color", "red");
		selectorname.append(data.statusDesc);
	} else {
		selectorname.css("color", "green");
	}
}

// 表單欄位預覽圖圖示
function formImgMessageEven(imgInputName , preImgName , selectorname , data) {
	selectorname.html('');
	// 若失敗，預覽圖不會顯示
	if (data.pass === false) {
		selectorname.css("color", "red");
		selectorname.append(data.statusDesc);
		$(preImgName).hide();
	} else {
		// 圖片網址正確，會顯示預覽圖
		selectorname.css("color", "green");
		if($(imgInputName).val()!=''){
	  		$(preImgName).attr("src" , $(imgInputName).val());
	      	$(preImgName).show();
		} else {
			$(preImgName).hide();
		}
	
	}
}