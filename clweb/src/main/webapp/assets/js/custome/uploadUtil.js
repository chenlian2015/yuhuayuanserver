/*w文件上传工具方法*/

function AjaxFileUpload() {
    this.fileId = "file";
    this.uploadUrl = "/gouda-merchant/upload/upload";
    this.imgId = false;
}


AjaxFileUpload.prototype = {
    upload: function () {
        var that = this;
        $.ajaxFileUpload
        (
            {
                url: this.uploadUrl,
                secureuri: false,
                fileElementId: this.fileId,
                dataType: 'json',
                data: {'imageType': '1', 'file_img': 'file_img'},
                success: function (data, status) {
                    console.log(data)
                    var fileElementId = that.fileId;
                    var imgId = data.imgId;
                    var imgUrl = data.url;
                    var fileId = data.fileId;
                    $("input[data-role=" + fileElementId + "]").val(imgId);
                    /*商家注册页面上传*/
                    $("input[data-role=" + fileElementId + "]").parent().find("img.imgs").attr("src", imgUrl);
                    $("img[data-role=" + fileElementId + "]").attr("layer-src", imgUrl);
                    $("[data-role=" + fileElementId + "]").parent().parent().find(".noDisplay").removeClass("noDisplay");
                    /*添加阿姨上传*/
                    if (!$("[data-role=" + fileElementId + "]").parent().find(".imgPlaceHoder img").length) {
                        $("[data-role=" + fileElementId + "]").parent().find(".imgPlaceHoder").addClass("uploadNewImage");
                        $("label[for=" + fileElementId + "]").find("label").addClass("hasImg");
                        $("[data-role=" + fileElementId + "]").parent().find(".imgPlaceHoder .defaultCenter").before("<img  src='" + imgUrl + "'>")
                    } else {
                        $("[data-role=" + fileElementId + "]").parent().find(".imgPlaceHoder img").attr("src", imgUrl);
                    }
                },
                error: function (data, status, e) {
                    var fileId = that.fileId;
                    alert(e);
                    if (!$("[data-role=" + fileId + "]").parent().find(".imgPlaceHoder img").length) {
                        $("[data-role=" + fileId + "]").parent().find(".imgPlaceHoder").addClass("uploadNewImage");
                        $("label[for=" + fileId + "]").find("label").addClass("hasImg");
                        $("[data-role=" + fileId + "]").parent().find(".imgPlaceHoder .defaultCenter").before("<img  src='http://img002.21cnimg.com/photos/album/20151216/m600/D74A69481195AA8260C6F9EAC2EBA689.jpeg'>")
                    } else {
                        $("[data-role=" + fileId + "]").parent().find(".imgPlaceHoder img").attr("src", "http://img002.21cnimg.com/photos/album/20151216/m600/D74A69481195AA8260C6F9EAC2EBA689.jpeg")
                    }
                }
            }
        )
        return false;
    }
}

function onloadFile(fileId) {
    var upload = new AjaxFileUpload();
    upload.fileId = fileId;
    upload.upload();
}


/*大图显示
 *
 * 该方法通用方法使用所有具有放大功能的页面使用
 * 该函数不需要具体模块调用 而是作为页面加载完成的一个回调函数 自动完成对应的放大镜效果
 *
 * */

function bigImg() {
    $("img[src*=btn_bigpic]").unbind("click").bind("click", function () {
        var bigImgSrc = $(this).attr("layer-src");//$(this).parent().siblings("img").attr("src");
        bindWatch("popuImgContainer", 800, 600, replaceBigImgSrc, bigImgSrc);
    })
}

/*替换大图地址*/
function replaceBigImgSrc(bgImgSrc) {
    $(".layui-layer-content img[data-role=bigImg]").attr("src", bgImgSrc);
}