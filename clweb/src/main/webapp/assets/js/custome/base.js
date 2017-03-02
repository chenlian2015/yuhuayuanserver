function uploadFileUtil(url, callBack) {
    //url="http://localhost:8100/ejiazi-upload/image/upload.do?belongId="+Math.random();
    if( /ejiazi\.com/.test(location.href)){/*根据环境判断是否为开发平台 动态修改上传地址 避免来回修改*/
        url="http://localhost:8100/ejiazi-upload/image/upload.do?belongId="+Math.random();
    }
    this.url = url;
    this.callBack = callBack;
    this.upload = function upload(fileId) {

        var file = document.getElementById(fileId);
        var format = file.files[0].name;
        var _index = format.lastIndexOf(".") + 1;
        var _length = format.length;
        var _string = format.substr(_index, _length).toLowerCase();
        if (_string != 'jpg' && _string != 'gif' && _string != 'jpeg' && _string != 'png') {
            alert("选择的文件应该为图片");
            return false;
        } else {
            var loadingId = layer.load(2);
            this.fileId = fileId;
            var that = this;
            var xmlHttpRequest = new XMLHttpRequest();
            var genId = "UPLOAD_" + new Date().getTime();
            var formData = new FormData();
            formData.append(genId, file.files[0]);
            xmlHttpRequest.open("POST", this.url, true);
            xmlHttpRequest.send(formData);
            xmlHttpRequest.onreadystatechange = function () {
                layer.close(loadingId);
                if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                    var ids = JSON.parse(xmlHttpRequest.responseText);
                    if (ids != null) {
                        for (var i = 0; i < ids.data.length; i++) {
                            var data = ids.data[i];
                            var fileElementId = that.fileId;
                            var imgId = data.imageId;
                            var imgUrl = data.imageUrl;
                            $("input[data-role=" + fileElementId + "]").val(imgId);
                            $("input[data-role=" + fileElementId + "]").parent().find(".imgPlaceHoder img").attr("src", imgUrl);
                            var callBack = that.callBack;
                            if (typeof callBack == "function") {
                                callBack(fileElementId, data.imageName, imgId);
                            }
                        }
                    }
                }
            }
        }
    }
}

function checkNumberLength(param, callBack, defaultValue) {
    if (param) {
        console.log(param.value)
        var paramVal = $(param).val();
        if (paramVal.split(".").length > 2) {
            $(param).val("");
            return;
        }
        var largerNum = paramVal.split(".")[0];
        var littleNum = paramVal.split(".")[1];
        if (littleNum) {
            littleNum = littleNum.replace(/[^\d]/g, "");
        } else {
            littleNum = "";
        }
        if (largerNum) {
            largerNum = largerNum.replace(/[^\d]/g, "");
        } else {
            largerNum = "0";
        }

        if (littleNum) {
            if (littleNum.length > 2) {
                littleNum = littleNum.substr(0, 2);
            }
        }
        var resultValue = parseFloat(largerNum + "." + littleNum);
        if (isNaN(resultValue)) {
            defaultValue = defaultValue == undefined ? 0.01 : defaultValue;
            resultValue = defaultValue;
        }
        if (resultValue > 1000000) {
            resultValue = 1000000;
        }
        if (resultValue == 0.00) {
            defaultValue = defaultValue == undefined ? 0.01 : defaultValue;
            resultValue = defaultValue;
        }
        $(param).val(resultValue);

        if (typeof  callBack == "function") {
            callBack(param);
        }
    }
}

/**
 * 被检查对象 本方法使用input的oninput事件 只允许输入整数或者是两位精度小数
 * @param param
 */
function checkPercision2Value(param, callBack, callBackParam) {
    param.value = param.value.replace(/[^\d\.]/g, "");
    var checkValue = param.value;
    var precision=$(param).attr("precision")||2;
    if (!/\d$|\.$/.test(checkValue)) {
        param.value = param.value.replace(/[^1-9\.]/g, "");
    }
    if(precision==3){
        if (/\.{3}$/.test(checkValue)) {
            param.value = checkValue.replace(/\.{3}/, ".");
        }
    }else{
        if (/\.{2}$/.test(checkValue)) {
            param.value = checkValue.replace(/\.{2}/, ".");
        }
    }
    if (/^\.$/.test(checkValue)) {
        param.value = "";
    }
    if (/^0\d+/.test(checkValue)) {
        param.value = param.value.replace(/^0+/g, "");
    }
    var execResult = /(\d+)(\.\d{1,2})/.exec(checkValue);
    if(precision==3){
        execResult = /(\d+)(\.\d{1,3})/.exec(checkValue);
    }
    if (execResult) {
        param.value = execResult[1] + execResult[2];
    }

    if (typeof callBack == "function") {
        callBack(callBackParam);
    }
}

function checkMaxValue(param) {
    var maxValue = $(param).attr("max") * 1;
    var thisValue = $(param).val() * 1;
    if (thisValue > maxValue) {
        $(param).val(maxValue);
    }
}

var chinesereg = /[\u4e00-\u9fa5]+/g;
var englisReg = /[a-zA-Z]/g;
var numberReg = /\d/g;
var other = /[-_]/g;
var note = /\./g;
var link = /\-/g;
