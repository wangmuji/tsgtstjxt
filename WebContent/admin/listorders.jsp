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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/four.css" rel="stylesheet" type="text/css" />
</head>
<%
	String message = (String) session.getAttribute("message");
	if (message == null) {
		message = "";
	}
	if (!message.trim().equals("")) {
		out.println("<script language='javascript'>");
		out.println("alert('" + message + "');");
		out.println("</script>");
	}
	session.removeAttribute("message");
%><body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td bgcolor="#FFFFFF"><table width="96%" border="0" align="center" cellpadding="4" cellspacing="1"
					bgcolor="#aec3de">
					<tr align="left" bgcolor="#F2FDFF">
						<td colspan="20" class="optiontitle">借阅预约信息列表</td>
					</tr>
					<tr align="center">
						<td align="center" bgcolor="#ebf0f7">预约单号</td>
						<td align="center" bgcolor="#ebf0f7">用户</td>
						<td align="center" bgcolor="#ebf0f7">图书</td>
						<td align="center" bgcolor="#ebf0f7">状态</td>
						<td align="center" bgcolor="#ebf0f7">预约日期</td>
						<td align="center" bgcolor="#ebf0f7">开始日期</td>
						<td align="center" bgcolor="#ebf0f7">结束日期</td>
						<td align="center" bgcolor="#ebf0f7">备注</td>
						<td align="center" bgcolor="#ebf0f7" width="10%">操作</td>
					</tr>
					<c:forEach items="${ordersList}" var="orders">
						<tr align="center" bgcolor="#FFFFFF">
							<td align="center">${orders.ordercode}</td>
							<td align="center">${orders.username}</td>
							<td align="center">${orders.booksname}</td>
							<td align="center">${orders.status}</td>
							<td align="center">${orders.orderdate}</td>
							<td align="center">${orders.thestart}</td>
							<td align="center">${orders.theend}</td>
							<td align="center">${orders.memo}</td>
							<td align="center"><c:if test="${orders.status eq'预约'}">
									<a href="orders/lend.action?id=${orders.ordersid}">借出</a>&nbsp;|&nbsp;
							</c:if> <c:if test="${orders.status eq'借阅' || orders.status eq '续借'}">
									<a href="orders/back.action?id=${orders.ordersid}">归还</a>&nbsp;|&nbsp;
							</c:if> <a href="orders/deleteOrders.action?id=${orders.ordersid}"
								onclick="{if(confirm('确定要删除吗?')){return true;}return false;}">删除</a></td>
						</tr>
					</c:forEach>
					<tr align="right" bgcolor="#ebf0f7">
						<td colspan="20"><span style="float: left; color: red">${map.msg }</span>&nbsp;<span style="float: right;">${html}</span></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>