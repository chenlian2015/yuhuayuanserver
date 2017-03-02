/**
 * Created by xkfeng on 2015/12/15.
 * 系统页面框架js
 */

$(function () {
    /*页面加载完成 1.加载公共页面头 左侧菜单 初始化菜单*/
    $("#headerContainer").load("/ejiazi-merchant/top", function (event) {
        initFirstLeavelMenu();
    })

    /*页面加载完成 1.加载公共页面头 左侧菜单 初始化菜单*/
    $("#leftMenu").load("/ejiazi-merchant/left", function (event) {
        loadSecondMenu();
    })
    loadSecondPage('/ejiazi-merchant/overview/index');
})

$.ajaxSetup({
    global: true,
    cache: false
});
var tipPopId = 0;
$(document).ajaxStart(function () {
    tipPopId = layer.load(2);
});
$(document).ajaxComplete(function (evt, req, settings) {
    layer.close(tipPopId);
})

function initFirstLeavelMenu() {
    $(".myMenu li").unbind().bind("click", function (event) {
        $(".myMenu .firstLeavelMenuCurrent").removeClass("firstLeavelMenuCurrent");
        $(this).addClass("firstLeavelMenuCurrent");
    })

}

/*加载二级菜单数据*/
function loadSecondMenu(parentId, callback) {
    if (null != parentId && undefined != parentId && "" != parentId) {
        $("#leftMenu").load("/ejiazi-merchant/left?parentId=" + parentId, function (event) {
            //loadSecondMenu();
            $(".mainPageFullScreen").removeClass("mainPageFullScreen");
            initSeconLeavelMenu();
            if (typeof callback == "function") {
                callback();
            }
        });
    }
}

/*初始化二级菜单*/
function initSeconLeavelMenu() {
    $("#leftMenu li").unbind().bind("click", function (event) {
        $("#leftMenu .myThis").removeClass("myThis");
        $(this).addClass("myThis");
    })
    $("#leftMenu li:eq(0)").click();
}


/*通用加载页面方法*/
function commonLoad(containerId, url, callBack) {
    var indexOf=containerId.indexOf(".");
    var prefix="#";
    if(indexOf!=-1){
        prefix="";
    }
    if (!$(prefix + containerId).length) {
        //alert("容器id=" + id + " 不存在！！");
        return;
    }
    if (!_.contains(url, "?")) {
        url += "?randomParam=" + Math.random() * 100000;
    } else {
        url += "&randomParam=" + Math.random() * 100000;
    }
    url = encodeURI(url);
    var popId=layer.open({type:3});

    if(typeof saveQueryDom == "function"){
        saveQueryDom();
    }


    $(prefix + containerId).load(url, function (event) {
        layer.close(popId);
        var fadeInTimeId = setTimeout(function () {
            $("div table").addClass("tableNoDisplay");
            clearTimeout(fadeInTimeId);
            var fadeInId = setTimeout(function () {
                $("div table").removeClass("tableNoDisplay");
                $("div table").addClass("showTableAnimate");
                clearTimeout(fadeInId);
            }, 0);
        }, 0);

        if (typeof callBack == "function") {
            callBack();
        }
        if (typeof bigImg == "function") {
            bigImg();
        }


    })
}

/*
 * 加载2级菜单页面
 * */
function loadSecondPage(url, callBack) {
    var containerId = "rightContainer";
    if ($("#rightContainer .wrapper div .table tr").length > 0) {
        var animationId = setTimeout(function () {
            commonLoad(containerId, url, callBack);
            clearTimeout(animationId);
        }, 10);
        $("#rightContainer .wrapper div table tr:even").addClass("moveLeft");
        $("#rightContainer .wrapper div table tr:odd").addClass("moveRight");
    } else {
        commonLoad(containerId, url, callBack);
    }
}

function getCancleReason(callBack) {
    var url = "/ejiazi-merchant/repair/getRepairOrderCancelReason.json";
    if ($("body").data("cancleReason")) {
        if (typeof callBack == "function") {
            callBack($("body").data("cancleReason"));
        }
        return $("body").data("cancleReason");
    }
    var options = {
        url: url,
        type: 'get',
        dataType: 'json',
        success: function (data) {
            if (data && data["code"] == 1 && data.data) {
                var reasonArr = data.data;
                var optionsHtml = "";
                for (var i = 0; i < reasonArr.length; i++) {
                    optionsHtml += '<option value="' + reasonArr[i].code + '">' + reasonArr[i].msg + '</option>'
                }
                ;
                $("body").data("cancleReason", optionsHtml);
                callBack($("body").data("cancleReason"));
                return optionsHtml;
            }
        }
    };
    $.ajax(options);
}

/*验证密码必须为8-16为数字大小写字母特殊符号*/
function checkPassword(value) {
    return /^[\d+a-zA-Z~!@#$%^&*()_+=]{8,16}$/.test(value) && /\d+/.test(value) && /[a-z]+/.test(value) && /[A-Z]+/.test(value) && /[~!@#$%^&*()_+=]+/.test(value);
}

var uploadConfig = JSON.parse('{"merchant-logo":{"key":"merchant-logo","detail":"商户logo","width":"200","height":"200","size":"<10000000","validate":false},"merchant-header":{"key":"merchant-header","detail":"店铺首图","width":"750","height":"263","size":"<100k","validate":false},"merchant-show":{"key":"merchant-show","detail":"店铺宣传图","width":"750","height":"<1500","size":"<200k","validate":false},"merchant-id-f#merchant-id-b":{"key":"merchant-id-f#merchant-id-b","detail":"身份证照片","validate":false},"merchant-yyzz-three2one":{"key":"merchant-yyzz-three2one","detail":"营业执照3合1","validate":false},"merchant-other":{"key":"merchant-other","detail":"其它证件1电子版","validate":false},"merchant-yyzz-one2one":{"key":"merchant-yyzz-one2one","detail":"营业执照单独","validate":false},"merchant-zzjg":{"key":"merchant-zzjg","detail":"组织机构代码证电子版","validate":false},"merchant-swdj":{"key":"merchant-swdj","detail":"税务登记证电子版","validate":false},"mall-catergory":{"key":"mall-catergory","detail":"电商商品分类","width":"200","height":"200","size":"<50k","extend":"png、jpeg、jpg","validate":false},"advance-assets":{"key":"advance-assets","detail":"上传素材","width":"750","height":"280","size":"50k","extend":"bmp、png、jpeg、jpg、gif","validate":false},"push-header-tgwxc-large":{"key":"push-header-tgwxc-large","detail":"首页-推广位宣传图","width":"374","height":"300","size":"<200k","extend":"png、jpeg、jpg","validate":false},"push-header-tgwxc":{"key":"push-header-tgwxc","detail":"首页-推广位宣传图","width":"374","height":"150","size":"<200k","extend":"png、jpeg、jpg","validate":false},"push-header-tonglan":{"key":"push-header-tonglan","detail":"首页-通栏推广1宣传","width":"730","height":"250","size":"<100k","extend":"png、jpeg、jpg","validate":false},"push-header-bqhh-loop":{"key":"push-header-bqhh-loop","detail":"首页-必抢好货轮播图","width":"288","height":"216","size":"<200k","extend":"png、jpeg、jpg","validate":false},"mall-fix-catergory":{"key":"mall-fix-catergory","detail":"商城-固定分类","width":"150","height":"150","size":"<200k","extend":"png、jpeg、jpg","validate":false},"mall-zxhw-goods":{"key":"mall-zxhw-goods","detail":"商城-甄选好物","width":"288","height":"216","size":"<200k","extend":"png、jpeg、jpg","validate":false},"mall-zxhw-merchant":{"key":"mall-zxhw-merchant","detail":"商城-优质店铺","width":"288","height":"216","size":"<200k","extend":"png、jpeg、jpg","validate":false},"mall-advace-catergory":{"key":"mall-advace-catergory","detail":"商城-分类广告图","width":"730","height":"250","size":"<100k","extend":"png、jpeg、jpg","validate":false},"visit-push":{"key":"visit-push","detail":"上门-推广位1宣传图","width":"374","height":"300","size":"<200k","extend":"png、jpeg、jpg","validate":false},"presell-activity-lager":{"key":"presell-activity-lager","detail":"活动大图区","width":"750","height":"<1000000","size":"<100k","extend":"png、jpeg、jpg","validate":false},"presell-goods":{"key":"presell-goods","detail":"预售商品1图","width":"246","height":"184","size":"<100k","extend":"png、jpeg、jpg","validate":false},"presell-bottom":{"key":"presell-bottom","detail":"底部规则图片","width":"750","height":"<1000000","size":"<100k","extend":"png、jpeg、jpg","validate":false},"merchant-goods-header":{"key":"merchant-goods-header","detail":"头图","width":"344","height":"258","size":"<200k","extend":"png、jpeg、jpg","validate":false},"merchant-goods-focus":{"key":"merchant-goods-focus","detail":"焦点图","width":"750","height":"562","size":"<200k","extend":"png、jpeg、jpg","validate":false},"merchant-goods-detail":{"key":"merchant-goods-detail","detail":"详情图","width":"50","height":"<1500px","size":"<200k","extend":"png、jpeg、jpg","validate":false},"property-notice-content-img":{"key":"property-notice-content-img","detail":"图文内容","width":"680","height":"406","size":"<60k","validate":false},"merchant-delivery-img":{"key":"merchant-delivery-img","detail":"图文内容","width":"100","height":"100","size":"<1000000k","validate":false}}');
function msg(msg) {
    //layer.msg("<div style='padding:10px;color:#fff;font-size:12px;'>" + msg + "</div>");
    alert(msg);
}

function getFileExtend(fileName) {
    fileName = fileName || "";
    return fileName.substring(fileName.lastIndexOf(".") + 1);
}

function checkImgUpload(fileElementId, callBack) {
    var key = $("#" + fileElementId).attr("key");
    var fileInput = document.getElementById(fileElementId);
    var fileObj = fileInput.files[0];
    var fileSize = fileObj.size;
    var fileType = fileObj.type;

    var imgConfig = uploadConfig[key];
    if (imgConfig == undefined || imgConfig == null) {
        imgConfig = {};
    }
    var fileExtend = imgConfig.extend;
    var currentExtend = getFileExtend(fileObj.name);
    if (fileExtend && !(fileExtend.indexOf(currentExtend) >= 0)) {
        var errMessage = "文件格式不正确，应为：" + fileExtend + " 所选文件为：" + currentExtend + " 请重新选择符合要求的文件！";
        $("#" + fileElementId).val("");
        msg(errMessage);
        return;
    }


    var localImgSrc = window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1 ? window.webkitURL.createObjectURL(fileInput.files[0]) : window.URL.createObjectURL(fileInput.files[0]);
    var testImg = new Image();
    testImg.src = localImgSrc;
    testImg.onload = function (event) {
        console.log(fileObj, "*******************", testImg.width, testImg.height, key, uploadConfig[key]);
        if (imgConfig && imgConfig.validate) {
            var filePermisionConfig = {
                width: imgConfig.width,
                maxWidth: imgConfig.width,
                height: /^</.test(imgConfig.height) ? null : imgConfig.height,
                maxHeight: /^</.test(imgConfig.height) ? imgConfig.height.replace("<", "") : null,
                maxFileSize: (imgConfig.size == null || imgConfig.size == undefined) ? null : imgConfig.size.replace("<", "").replace("k", "") * 1024
            };
            console.log(filePermisionConfig, testImg)
            if (/^\d+$/.test(filePermisionConfig.maxWidth) && filePermisionConfig.width * 1 != testImg.width) {
                var errMessage = "允许图片宽度为：" + filePermisionConfig.width + " 所选文件宽度为：" + testImg.width + " 请重新选择符合要求的文件！";
                $("#" + fileElementId).val("");
                msg(errMessage);
                return;
            }
            if (/^\d+$/.test(filePermisionConfig.height) && filePermisionConfig.height * 1 != testImg.height) {
                var errMessage = "允许图片高度为：" + filePermisionConfig.height + " 所选文件高度为：" + testImg.height + " 请重新选择符合要求的文件！";
                $("#" + fileElementId).val("");
                msg(errMessage);
                return;
            }

            if (/^\d+$/.test(filePermisionConfig.maxWidth) && filePermisionConfig.maxWidth * 1 < testImg.width) {
                var errMessage = "允许图片宽度为：" + filePermisionConfig.maxWidth + " 所选文件宽度为：" + testImg.width + " 请重新选择符合要求的文件！";
                $("#" + fileElementId).val("");
                msg(errMessage);
                return;
            }
            if (/^\d+$/.test(filePermisionConfig.maxHeight) && filePermisionConfig.maxHeight * 1 < testImg.height) {
                var errMessage = "允许图片最大高度为：" + filePermisionConfig.maxHeight + " 所选文件高度为：" + testImg.height + " 请重新选择符合要求的文件！";
                $("#" + fileElementId).val("");
                msg(errMessage);
                return;
            }
            if (/^\d+$/.test(filePermisionConfig.maxFileSize) && filePermisionConfig.maxFileSize * 1 < fileSize) {
                var errMessage = "允许图片最大为：" + filePermisionConfig.maxFileSize / 1024 + "k 所选文件大小为：" + fileSize / 1024 + "k 请重新选择符合要求的文件！";
                $("#" + fileElementId).val("");
                msg(errMessage);
                return;
            }
        }
        callBack();
    }
}
