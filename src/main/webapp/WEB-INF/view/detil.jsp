<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Title</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/js/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-5">
            <img src="http://ozpkbpsga.bkt.clouddn.com/${phone.productImage}" style="width: 400px" alt="">
        </div>
        <div class="col-md-7">
            <h4 class="text-success">${phone.productName}</h4>
            <h3 class="text-warning">${phone.title}</h3>

            <h3 class="text-danger">秒杀价：${phone.price} <small style="text-decoration:line-through">市场价: ${phone.publicPrice}</small></h3>
            <br><br>
            <c:choose>
                <c:when test="${phone.other == 0}">
                    <button type="button" class="btn btn-default btn-lg" disabled>已抢完</button>
                </c:when>
                <c:when test="${phone.end}">
                    <button type="button" class="btn btn-default btn-lg" disabled>已结束</button>
                </c:when>
                <c:when test="${phone.start and not phone.end}">
                    <button type="button" class="btn btn-danger btn-lg wait" >立即抢购</button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-danger btn-lg wait" disabled><span id="clock">xx分xx秒</span></button>
                </c:otherwise>
            </c:choose>
                

        </div><br>
        ${phone.productDetils}

    </div>
</div>




<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/moment.js"></script>
<script src="/static/js/jquery.countdown.min.js"></script>
<script src="/static/layer/layer.js"></script>
<script>
    $(function () {

        $("#clock").countdown(${phone.startTime},function(event) {
            $(this).html(event.strftime('%D 天 %H小时%M分钟%S秒'));
        }).on("finish.countdown",function () {
            $(".wait").text("立即抢购").removeAttr("disabled");
        });

        $(".wait").click(function () {
            $.get("/phone/secKill?id="+${phone.id}).done(function (json) {

                if(json.state == "success") {
                    layer.alert("抢购成功");
                }else{
                    layer.alert("抢光了");
                }


            }).error(function () {
                layer.msg("服务器异常");
            });


        });



    });
</script>

</body>
</html>