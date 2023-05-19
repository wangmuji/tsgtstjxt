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
<script type="text/javascript" src="js/users.js" charset="utf-8"></script>
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
			<td bgcolor="#FFFFFF"><form action="users/updateUsers.action" name="myform" method="post"
					onsubmit="return check()">
					<table width="40%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#aec3de">
						<tr align="center" bgcolor="#F2FDFF">
							<td align="left" colspan="2" class="optiontitle">修改读者用户<input type="hidden" name="usersid"
								value="${users.usersid}" /><input type="hidden" name="password" id="password" value="${users.password}" /><input
								type="hidden" name="regdate" id="regdate" value="${users.regdate}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">用户名</td>
							<td align="left"><input type="text" name="username" style="width: 160px" id="username"
								value="${users.username}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">姓名</td>
							<td align="left"><input type="text" name="realname" style="width: 160px" id="realname"
								value="${users.realname}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">性别</td>
							<td align="left"><input type="radio" name="sex" id="sex" value="男" checked="checked" />男&nbsp;&nbsp;&nbsp;&nbsp;<input
								type="radio" name="sex" id="sex" value="女" />女</td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">出生日期</td>
							<td align="left"><input type="text" name="birthday" style="width: 160px" id="birthday"
								value="${users.birthday}" onclick="WdatePicker()" readonly="readonly" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">联系方式</td>
							<td align="left"><input type="text" name="contact" style="width: 160px" id="contact"
								value="${users.contact}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">联系地址</td>
							<td align="left"><input type="text" name="address" style="width: 160px" id="address"
								value="${users.address}" /></td>
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