<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><jsp:include page="check_logstate.jsp"></jsp:include>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<script type="text/javascript" src="js/jquery-1.3.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="js/complains.js" charset="utf-8"></script>
<script type="text/javascript" src="jsselimage.js" charset="utf-8"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<link href="css/four.css" rel="stylesheet" type="text/css" />
</head>
<%
	String message = (String) request.getAttribute("message");
	if (message == null) {
		message = "";
	}
	if (!message.trim().equals("")) {
		out.println("<script language='javascript'>");
		out.println("alert('" + message + "');");
		out.println("</script>");
	}
	request.removeAttribute("message");
%><body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td bgcolor="#FFFFFF"><form action="complains/updateComplains.action" name="myform" method="post"
					onsubmit="return check()">
					<table width="40%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#aec3de">
						<tr align="center" bgcolor="#F2FDFF">
							<td align="left" colspan="2" class="optiontitle">修改用户建议<input type="hidden" name="complainsid"
								value="${complains.complainsid}" /><input type="hidden" name="usersid" id="usersid"
								value="${complains.usersid}" /><input type="hidden" name="addtime" id="addtime" value="${complains.addtime}" /><input
								type="hidden" name="status" id="status" value="已回复" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">内容</td>
							<td align="left">${complains.contents}<input type="hidden" name="contents" style="width: 160px"
								id="contents" value="${complains.contents}" />
							</td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">回复内容</td>
							<td align="left"><textarea name="reps" style="width: 99%;height:120px" id="reps"></textarea></td>
						</tr>
						<tr align="center" bgcolor="#ebf0f7">
							<td colspan="2"><input type="submit" id='sub' value="提交" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset"
								id='res' value="重置" /></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>