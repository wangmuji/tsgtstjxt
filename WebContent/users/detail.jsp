<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<title>${title }</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="block box">
		<div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href=".">首页</a>
			<code> &gt; </code>
			<a href="index/all.action">全部图书</a>
			<code> &gt; </code>
			<a href="index/cate.action?id=${books.cateid }">${books.catename }</a>
			<code> &gt; </code>
			${books.booksname }
		</div>
	</div>
	<div class="blank"></div>
	<div class="block clearfix">
		<div class="AreaL">
			<div id="category_tree">
				<div class="tit">所有图书分类</div>
				<dl class="clearfix" style="overflow: hidden;">
					<c:forEach items="${cateList}" var="cate">
						<div class="box1 cate" id="cate">
							<h1 style="border-top: none">
								<a href="index/cate.action?id=${cate.cateid }" class="  f_l">${cate.catename }</a>
							</h1>
						</div>
						<div style="clear: both"></div>
					</c:forEach>
				</dl>
			</div>
			<div class="blank"></div>
			<div class="box" id='history_div'>
				<div class="box_1">
					<h3>
						<span>热门图书</span>
					</h3>
					<div class="boxCenterList clearfix" id='history_list'>
						<c:forEach items="${hotList}" var="books">
							<ul class="clearfix">
								<li class="goodsimg"><a href="index/detail.action?id=${books.booksid }" target="_blank"><img
										src="${books.image }" alt="${books.booksname }" class="B_blue" /> </a></li>
								<li><a href="index/detail.action?id=${books.booksid }" target="_blank" title="${books.booksname }">${books.booksname }</a>
								</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="blank5"></div>
		</div>
		<div class="AreaR">

			<div id="goodsInfo" class="clearfix">
				<div class="imgInfo">
					<img src="${books.image}" alt="${books.booksname }" width="360px;" height="360px" />
					<div class="blank5"></div>
					<div class="blank"></div>
				</div>
				<div class="textInfo">
					<h1 class="clearfix">${books.booksname }</h1>
					<ul class="ul2 clearfix">
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>图书类型：</strong><a href="index/cate.action?id=${books.cateid }">${books.catename }</a>
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>上架日期：</strong>${books.addtime}
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>出版社：</strong>${books.publisher}
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>作者：</strong>${books.author}
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>库存数：</strong>${books.storage}
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>可借数量：</strong>${books.lendnum}
							</dd>
						</li>
						<li class="clearfix" style="width: 100%">
							<dd>
								<strong>存放位置：</strong>${books.weizhi}
							</dd>
						</li>
					</ul>
					<c:if test="${books.lendnum !='0'}">
						<ul class="bnt_ul">
							<li class="padd"><a href="index/addorder.action?id=${books.booksid }"><img
									src="themes/dapu/images/yuyue.png" /></a></li>
						</ul>
					</c:if>
				</div>
			</div>
			<div class="blank"></div>

			<div class="box">
				<div style="padding: 0 0px;">
					<div id="com_b" class="history clearfix">
						<h2>图书描述</h2>
					</div>
				</div>
				<div class="box_1">
					<div id="com_v" class="  " style="padding: 6px;"></div>
					<div id="com_h">
						<blockquote>${books.contents}</blockquote>
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<div id="ECS_COMMENT">
				<div class="box">
					<div class="box_1">
						<h3>
							<span class="text">用户评论</span>(共<font class="f1">${tnum }</font>条评论)
						</h3>
						<div class="boxCenterList clearfix" style="height: 1%;">
							<ul class="comments">
								<c:forEach items="${topicList }" var="topic">
									<li class="word"><font class="f2">${topic.username }</font> <font class="f3">(${topic.addtime }) </font> <br />
										<img src="themes/dapu/images/stars${topic.num }.gif" />
										<p>${topic.contents }</p></li>
								</c:forEach>
							</ul>
							<div class="blank5"></div>
						</div>
					</div>
				</div>
				<div class="blank5"></div>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
