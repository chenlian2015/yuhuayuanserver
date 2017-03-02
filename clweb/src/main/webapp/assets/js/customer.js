/*
 * 1.使用layer进行模态窗弹窗
 * 2.根据模态窗口元素的id弹出
 * 3.回调函数无 可以自行绑定函数处理点击按钮 达到按钮随意定义个数不限制 风格自由化
 * 4.author:xiankun.feng
 * */
function bindWatch(id, width, height, callback, data) {
    if (width == undefined) {
        width = "80%";
    }
    if (height == undefined) {
        height = "80%";
    }
    if (!_.contains(width + "", "%")) {
        width += "px";
    }
    if (!_.contains(height + "", "%")) {
        height += "px";
    }
    layer.open({
        title: null,
        content: $('#' + id).html(),
        btn: null,
        closeBtn: null,
        area: [width, height],
        success: function (layer, index) {
            replaceAttrId();
            if (typeof  callback == 'function') {
                callback(data);
            }
        }
    })
}

function popNoCopy(dom, width, height, callBack, data) {
    if (width == undefined) {
        width = "80%";
    }
    if (height == undefined) {
        height = "80%";
    }
    if (!_.contains(width + "", "%")) {
        width += "px";
    }
    if (!_.contains(height + "", "%")) {
        height += "px";
    }
    layer.open({
        title: false,
        type: 1,
        content: dom,
        btn: 0,
        closeBtn: null,
        zIndex: layer.zIndex,
        area: [width, height],
        success: function (layer, index) {
            if (typeof  callBack == 'function') {
                callBack(replaceAttrId, data);
            } else {
                replaceAttrId();
            }
            $(".layui-layer-page .layui-layer-content").css({"overflow":"visible"});
            $(".layui-layer-content .labelHeader .closeBtn").css({padding:"1px"});
            $(".layui-layer-content .btnContainer").css({"padding":"5px"});
        }
    })

}

function replaceAttrId() {
    $(".layui-layer-content .my-modal-body .label_checkbox,.layui-layer-content .my-modal-body.cancleResonModal:eq(1) ").find("li").each(function (item) {
        $(this).find("input").attr("id", $(this).find("input").attr("id") + "_pop");
        $(this).find("label").attr("for", $(this).find("label").attr("for") + "_pop");
    })
}