function validateNumberLength(param) {
    if (param) {
        var paramVal = param;//$(param).val();
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
            resultValue = 0.01;
        }
        if (resultValue > 1000000) {
            resultValue = 1000000;
        }
        if (resultValue == 0.00) {
            resultValue = 0.01;
        }
        /* $(param).val(resultValue);*/
        return resultValue;
    }
}

function checkNumberLength(param) {
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
            resultValue = 0.01;
        }
        if (resultValue > 1000000) {
            resultValue = 1000000;
        }
        if (resultValue == 0.00) {
            resultValue = 0.01;
        }
        if ($(param).attr("id") == "updiscount") {
            if ($(".layui-layer-content")) {
                if (parseFloat($(".layui-layer-content #upprice").val()) < parseFloat($(".layui-layer-content #updiscount").val())) {
                    resultValue = $(".layui-layer-content #upprice").val();
                }
            }
        }


        $(param).val(resultValue);
    }
}

function checkNumber(param) {
    if (param) {
        console.log(param.value)
        var paramVal = $(".layui-layer-content #" + param).val();
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
            resultValue = 0.01;
        }
        /*if(resultValue>1000000){
         resultValue=1000000;
         }*/
        if (resultValue == 0.00) {
            resultValue = 0.01;
        }
        $(param).val(resultValue);
        var fullMoney = $(".layui-layer-content #fullMoney").val();
        var minusMoney = $(".layui-layer-content #minusMoney").val();
        if (fullMoney > minusMoney) {
            $("#minusMoney").val("");
        }
    }
}