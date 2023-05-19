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
			<td bgcolor="#FFFFFF"><table width="96%" border="0" align="center" cellpadding="4" cellspacing="1"
					bgcolor="#aec3de">
					<tr align="left" bgcolor="#F2FDFF">
						<td colspan="20" class="optiontitle">图书信息列表</td>
					</tr>
					<tr align="center">
						<td align="center" bgcolor="#ebf0f7">图书名称</td>
						<td align="center" bgcolor="#ebf0f7">图书类型</td>
						<td align="center" bgcolor="#ebf0f7">出版社</td>
						<td align="center" bgcolor="#ebf0f7">作者</td>
						<td align="center" bgcolor="#ebf0f7">上架日期</td>
						<td align="center" bgcolor="#ebf0f7">库存数</td>
						<td align="center" bgcolor="#ebf0f7">可借数量</td>
						<td align="center" bgcolor="#ebf0f7">存放位置</td>
					</tr>
					<c:forEach items="${booksList}" var="books">
						<tr align="center" bgcolor="#FFFFFF">
							<td align="center">${books.booksname}</td>
							<td align="center">${books.catename}</td>
							<td align="center">${books.publisher}</td>
							<td align="center">${books.author}</td>
							<td align="center">${books.addtime}</td>
							<td align="center">${books.storage}</td>
							<td align="center">${books.lendnum}</td>
							<td align="center">${books.weizhi}</td>
						</tr>
					</c:forEach><!--毕设成品下载网址   codebag.cn-->
					<tr align="right" bgcolor="#ebf0f7">
						<td colspan="20"><form action="books/queryBooksByCond.action" name="myform" method="post">
								查询条件<select name="cond" style="width: 100px"><option value="booksname">按图书名称查询</option>
									<option value="cateid">按图书类型查询</option>
									<option value="publisher">按出版社查询</option>
									<option value="author">按作者查询</option>
									<option value="addtime">按上架日期查询</option>
									<option value="storage">按库存数查询</option>
									<option value="lendnum">按可借数量查询</option>
									<option value="weizhi">按存放位置查询</option></select>关键字<input type="text" name="name" style="width: 100px" /><input
									type="submit" value="查询" />
							</form></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>