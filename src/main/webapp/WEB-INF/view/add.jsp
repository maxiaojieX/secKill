<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>添加商品</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/js/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="/static/js/editer/styles/simditor.css" />
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>

<div class="container">

    <form action="/save" enctype="multipart/form-data" method="post">
        <legend>添加商品</legend>
        <div class="form-group">
            <lable>商品名称</lable>
            <input type="text" class="form-control" name="productName">
        </div>
        <div class="form-group">
            <lable>商品标题</lable>
            <input type="text" class="form-control" name="title">
        </div>
        <div class="form-group">
            <lable>商品数量</lable>
            <input type="text" class="form-control" name="other">
        </div>
        <div class="form-group">
            <lable>商品价格</lable>
            <input type="text" class="form-control" name="price">
        </div>
        <div class="form-group">
            <lable>商品市场价格</lable>
            <input type="text" class="form-control" name="publicPrice">
        </div>
        <div class="form-group">
            <lable>商品图片</lable>
            <input type="file" class="form-control" name="image">
        </div>
        <div class="form-group">
            <lable>开始时间</lable>
            <input type="text" class="form-control begin" name="sTime">
        </div>
        <div class="form-group">
            <lable>结束时间</lable>
            <input type="text" class="form-control begin" name="eTime">
        </div>
        <div class="form-group">
            <lable>商品详情</lable>
            <input type="text" class="form-control" id="detil" name="productDetils">
        </div>
        <div class="form-group">
            <button class="btn btn-success">保存商品</button>
        </div>


    </form>

</div>
<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/js/editer/scripts/module.js"></script>
<script src="/static/js/editer/scripts/hotkeys.js"></script>
<script src="/static/js/editer/scripts/uploader.js"></script>
<script src="/static/js/editer/scripts/simditor.js"></script>
<script>
    $(function () {

        var timepicker = $(".begin").datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });

        var editor = new Simditor({
            textarea: $('#detil')
            //optional options
        });


    });
</script>

</body>
</html>