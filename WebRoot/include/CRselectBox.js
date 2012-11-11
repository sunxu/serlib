/* 
Dev by CssRain.cn 
Test in IE6,IE7,Firefox3,Chrome
http://leeiio.me/jquery-select-option/
*/
jQuery.fn.CRselectBox = jQuery.fn.sBox = function(options){
	options = $.extend({
		animated : false
	},options);
	var _self = this;
	/*构建结构*/
	var _parent = _self.parent();
	var wrapHtml = '<div class="CRselectBox"></div>';
	var $wrapHtml = $(wrapHtml).appendTo(_parent);
	var selectedOptionValue = _self.find("option:selected").attr("value");
	var selectedOptionTxt = _self.find("option:selected").text();
	var name = _self.attr("name");
	var id = _self.attr("id");
	var inputHtml = '<input type="hidden" value="'+selectedOptionValue+'" name="'+name+'" id="'+id+'"/>';
	$(inputHtml).appendTo($wrapHtml);
	var inputTxtHtml = '<input type="hidden" value="'+selectedOptionTxt+'" name="'+name+'_CRtext" id="'+id+'_CRtext"/>';
	$(inputTxtHtml).appendTo($wrapHtml);
	var aHtml = '<a class="CRselectValue" href="#">'+selectedOptionTxt+'</a>';
	$(aHtml).appendTo($wrapHtml);
	var ulHtml = '<ul class="CRselectBoxOptions"></ul>';
	var $ulHtml = $(ulHtml).appendTo($wrapHtml);
	var liHtml = "";
	_self.find("option").each(function(){
		if($(this).attr("selected")){
			liHtml += '<li class="CRselectBoxItem"><a href="#" class="selected" rel="'+$(this).attr("value")+'">'+$(this).text()+'</a></li>';
		}else{
			liHtml += '<li class="CRselectBoxItem"><a href="#" rel="'+$(this).attr("value")+'">'+$(this).text()+'</a></li>';
		}
	});
	$(liHtml).appendTo($ulHtml);
	/*添加效果*/
	$( $wrapHtml, _parent).hover(function(){
		$(this).addClass("CRselectBoxHover");
	},function(){
		$(this).removeClass("CRselectBoxHover");
	});
	$(".CRselectValue",$wrapHtml).click(function(){
		$(this).blur();	
		if( $(".CRselectBoxOptions",$wrapHtml).is(":hidden") ){
			if(options.animated){
				$(".CRselectBoxOptions").slideUp("fast");
				$(".CRselectBoxOptions",$wrapHtml).slideDown("fast");
			}else{
				$(".CRselectBoxOptions").hide();
				$(".CRselectBoxOptions",$wrapHtml).show();
			}
		}
		return false;
	});
	$(".CRselectBoxItem a",$wrapHtml).click(function(){
		$(this).blur();
		var value = $(this).attr("rel");
		var txt = $(this).text();
		$("#"+id).val(value);
		$("#"+id+"_CRtext").val(txt);
		$(".CRselectValue",$wrapHtml).text(txt);
		$(".CRselectBoxItem a",$wrapHtml).removeClass("selected");
		$(this).addClass("selected");
		if(options.animated){
			$(".CRselectBoxOptions",$wrapHtml).slideUp("fast");
		}else{
			$(".CRselectBoxOptions",$wrapHtml).hide();
		}
		return false;
	});
	$(document).click(function(event){
		if( $(event.target).attr("class") != "CRselectBox" ){
			if(options.animated){
			$(".CRselectBoxOptions",$wrapHtml).slideUp("fast");
			}else{
				$(".CRselectBoxOptions",$wrapHtml).hide();
			}
		}
	});
	_self.remove();
	return _self;
}
//
//$(function () {
//	$("#search_type").CRselectBox();	
//	$("#search_text").focus(function () {
//		$(this).css({
//			"border-color":"#ff7700 #ffa200 #ffa200 #ff7700","border-style":"solid","border-width":"1px","border-right":"none"
//		});		
//		
//		$(".CRselectBox").css({
//			"border-color":"#ff7700 #ff7700 #ffa200 #ff7700","border-style":"solid","border-width":"1px","border-left":"none","background":"#fff0d4 url(skin/images/select_box_on.gif) no-repeat right center"
//		});
//		$(".CRselectValue").css({"color":"#b96821"});
//	}).blur(function () {
//		$(this).css({
//			"border-color":"#7c7c7c #fff #ddd #7c7c7c","border-style":"solid","border-width":"1px","border-right":"none"
//		});
//		$(".CRselectBox").css({
//			"border-color":"#7c7c7c #ddd #ddd #7c7c7c","border-style":"solid","border-width":"1px","border-left":"none","background":"#f4f4f4 url(skin/images/select_box_off.gif) no-repeat right center"
//		});
//		$(".CRselectValue").css({"color":"#666666"});
//	});
//})
