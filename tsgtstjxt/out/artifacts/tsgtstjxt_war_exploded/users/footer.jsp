<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div id="VjiaFooter" class="block">
	<div class="copyRight">
		<div class="f_nav">&nbsp;</div>
		<div style="line-height: 22px; color: #000;">
			&copy; 2023 ${title } 版权所有，并保留所有权利。 <br /> <a href="index.jsp" style="font-family: Verdana; font-size: 11px;">Powered&nbsp;by&nbsp;<strong><span
					style="color: #3366FF">${title }</span>&nbsp;<span style="color: #FF9966">v2.7.3</span></strong></a>&nbsp;<br />
		</div>
		<div align="center">
			<a href="admin/index.jsp" target="_blank">管理员入口</a>
		</div>
		<div class="blank"></div>
	</div>
</div>
