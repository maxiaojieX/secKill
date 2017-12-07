<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>秒杀专区</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <style>
        .ih1{
            font-size: 50px;
        }
        .addProduct{
            position: absolute;
            right: 50px;
            top: 50px;
        }
    </style>
</head>
<body>
<center><h1 class="ih1">秒杀专柜</h1></center>
<hr>

<p class="addProduct">
    <a href="/add" type="button" class="btn btn-primary btn-xs">添加商品</a>
</p>

<div class="container">

    <c:forEach items="${phoneList}" var="phone">
    <div class="row">
        <div class="col-md-3">
            <img src="http://ozpkbpsga.bkt.clouddn.com/${phone.productImage}"style="width: 200px" alt="">
        </div>
        <div class="col-md-9">
            <h4><a href="/phone/detil/${phone.id}">${phone.productName}</a></h4>
            <br>
            <h3><p class="text-danger">抢购价:${phone.price}</p></h3>
            <h4><p class="text-success">开始时间: <fmt:formatDate value="${phone.starttime}" pattern="yyyy年MM月dd日 HH时mm分"/></p></h4>
        </div>
    </div>
    </c:forEach>
    <c:if test="${empty phoneList}">
        暂无活动！
    </c:if>
</div>

</body>
</html>