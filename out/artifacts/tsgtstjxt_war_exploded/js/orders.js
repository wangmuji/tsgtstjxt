$(function() {

$("#ordercode").blur(
		function() {
			$("#ordercode_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#ordercode").after("<span id='ordercode_msg' style='color: red'>预约单号不能为空</span>");
			}
	});

$("#usersid").blur(
		function() {
			$("#usersid_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#usersid").after("<span id='usersid_msg' style='color: red'>用户不能为空</span>");
			}
	});

$("#booksid").blur(
		function() {
			$("#booksid_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#booksid").after("<span id='booksid_msg' style='color: red'>图书不能为空</span>");
			}
	});

$("#status").blur(
		function() {
			$("#status_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#status").after("<span id='status_msg' style='color: red'>状态不能为空</span>");
			}
	});

$("#orderdate").blur(
		function() {
			$("#orderdate_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#orderdate").after("<span id='orderdate_msg' style='color: red'>预约日期不能为空</span>");
			}
	});

$("#thestart").blur(
		function() {
			$("#thestart_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#thestart").after("<span id='thestart_msg' style='color: red'>开始日期不能为空</span>");
			}
	});

$("#theend").blur(
		function() {
			$("#theend_msg").empty();
			var name = $(this).val();
			if (name == "" || name == null) {
				$("#theend").after("<span id='theend_msg' style='color: red'>结束日期不能为空</span>");
			}
	});







$('#sub').click(function(){
var ordercode = $("#ordercode").val();
var usersid = $("#usersid").val();
var booksid = $("#booksid").val();
var status = $("#status").val();
var orderdate = $("#orderdate").val();
var thestart = $("#thestart").val();
var theend = $("#theend").val();
$("#ordercode_msg").empty();
$("#usersid_msg").empty();
$("#booksid_msg").empty();
$("#status_msg").empty();
$("#orderdate_msg").empty();
$("#thestart_msg").empty();
$("#theend_msg").empty();
if (ordercode == "" || ordercode == null) {
	$("#ordercode").after("<span id='ordercode_msg' style='color: red'>预约单号不能为空</span>");
	return false;
}
if (usersid == "" || usersid == null) {
	$("#usersid").after("<span id='usersid_msg' style='color: red'>用户不能为空</span>");
	return false;
}
if (booksid == "" || booksid == null) {
	$("#booksid").after("<span id='booksid_msg' style='color: red'>图书不能为空</span>");
	return false;
}
if (status == "" || status == null) {
	$("#status").after("<span id='status_msg' style='color: red'>状态不能为空</span>");
	return false;
}
if (orderdate == "" || orderdate == null) {
	$("#orderdate").after("<span id='orderdate_msg' style='color: red'>预约日期不能为空</span>");
	return false;
}
if (thestart == "" || thestart == null) {
	$("#thestart").after("<span id='thestart_msg' style='color: red'>开始日期不能为空</span>");
	return false;
}
if (theend == "" || theend == null) {
	$("#theend").after("<span id='theend_msg' style='color: red'>结束日期不能为空</span>");
	return false;
}
});
$('#res').click(function() {
$("#ordercode_msg").empty();
$("#usersid_msg").empty();
$("#booksid_msg").empty();
$("#status_msg").empty();
$("#orderdate_msg").empty();
$("#thestart_msg").empty();
$("#theend_msg").empty();
});

});
