<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>jquery表格操作</title>
    <script src="<%=request.getContextPath()%>/js/jquery-1.12.0.min.js" type="text/javascript"></script>
    <style type="text/css">
        table
        {
            border: black solid 1px;
            border-collapse: collapse;
        }
        td
        {
            border: black solid 1px;
            padding: 3px;
        }
        .td_Num
        {
            width: 60px;
            text-align: center;
        }
        .td_Item
        {
            width: 160px;
            text-align: center;
        }
        .td_Oper
        {
            width: 120px;
            text-align: center;
        }
        .td_Oper span
        {
            cursor: pointer;
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <td class='td_Num'>
                序号
            </td>
            <td class='td_Item'>
                步骤名称
            </td>
            <td class='td_Item'>
                步骤描述
            </td>
            <td class='td_Oper'>
                相关操作 <a href="#" onclick="add_line();">添加</a>
            </td>
        </tr>
    </table>
    <table id="content">
    </table>
    <input type="button" value="提交数据" id="btnSubmit" onclick="SaveData()" />
</body>
</html>
<script type="text/javascript">
    var currentStep = 0;
    var max_line_num = 0;
    //添加新记录
    function add_line() {
        max_line_num = $("#content tr:last-child").children("td").html();
        if (max_line_num == null) {
            max_line_num = 1;
        }
        else {
            max_line_num = parseInt(max_line_num);
            max_line_num += 1;
        }
        $('#content').append(
        "<tr id='line" + max_line_num + "'>" +
            "<td class='td_Num'>" + max_line_num + "</td>" +
            "<td class='td_Item'><input type='text' class='stepName' value='步骤名称" + max_line_num + "'></input></td>" +
            "<td class='td_Item'><input type='text' class='stepDescription' value='步骤描述" + max_line_num + "'></td>" +
            "<td class='td_Oper'>" +
                "<span onclick='up_exchange_line(this);'>上移</span> " +
                "<span onclick='down_exchange_line(this);'>下移</span> " +
                "<span onclick='remove_line(this);'>删除</span> " +
            "</td>" +
        "</tr>");
    }
    //删除选择记录
    function remove_line(index) {
        if (index != null) {
            currentStep = $(index).parent().parent().find("td:first-child").html();
        }
        if (currentStep == 0) {
            alert('请选择一项!');
            return false;
        }
        if (confirm("确定要删除改记录吗？")) {
            $("#content tr").each(function () {
                var seq = parseInt($(this).children("td").html());
                if (seq == currentStep) { $(this).remove(); }
                if (seq > currentStep) { $(this).children("td").each(function (i) { if (i == 0) $(this).html(seq - 1); }); }
            });
        }
    }
    //上移
    function up_exchange_line(index) {
        if (index != null) {
            currentStep = $(index).parent().parent().find("td:first-child").html();
        }
        if (currentStep == 0) {
            alert('请选择一项!');
            return false;
        }
        if (currentStep <= 1) {
            alert('已经是最顶项了!');
            return false;
        }
        var upStep = currentStep - 1;
        //修改序号
        $('#line' + upStep + " td:first-child").html(currentStep);
        $('#line' + currentStep + " td:first-child").html(upStep);
        //取得两行的内容
        var upContent = $('#line' + upStep).html();
        var currentContent = $('#line' + currentStep).html();
        $('#line' + upStep).html(currentContent);
        //交换当前行与上一行内容
        $('#line' + currentStep).html(upContent);
        $('#content tr').each(function () { $(this).css("background-color", "#ffffff"); });
        $('#line' + upStep).css("background-color", "yellow");
        event.stopPropagation(); //阻止事件冒泡
    }
    //下移
    function down_exchange_line(index) {
        if (index != null) {
            currentStep = $(index).parent().parent().find("td:first-child").html();
        }
        if (currentStep == 0) {
            alert('请选择一项!');
            return false;
        }
        if (currentStep >= max_line_num) {
            alert('已经是最后一项了!');
            return false;
        }
        var nextStep = parseInt(currentStep) + 1;
        //修改序号
        $('#line' + nextStep + " td:first-child").html(currentStep);
        $('#line' + currentStep + " td:first-child").html(nextStep);
        //取得两行的内容
        var nextContent = $('#line' + nextStep).html();
        var currentContent = $('#line' + currentStep).html();
        //交换当前行与上一行内容
        $('#line' + nextStep).html(currentContent);
        $('#line' + currentStep).html(nextContent);
        $('#content tr').each(function () { $(this).css("background-color", "#ffffff"); });
        $('#line' + nextStep).css("background-color", "yellow");
        event.stopPropagation(); //阻止事件冒泡
    }
    //保存数据
    function SaveData() {
        
        var datajson = {"balance":"123","headpic":"test","id":1,"name":"chenlian","pwd":"123","yuhuayuanid":0};
        //
        //{"id":1,"name":"zhouli","pwd":"123","yuhuayuanid":2,"headpic":"terst"};
        $.ajax({
            type: "POST",   //访问WebService使用Post方式请求
            contentType: "application/json", 
            url: "<%=request.getContextPath()%>/registerBatch", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
            data: JSON.stringify(datajson),  //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到       
            dataType: 'json',   //WebService 会返回Json类型

            success: function(result) {     //回调函数，result，返回值
                alert(result);
            }
        });
    }
</script>