angular.module("AjlApp.upload", []).service("uploadService", function () {
    var UploadService = {
        upload: function (fileId, item, $scope, url) {
            url = $("#uploadPath").val() + "ejiazi-upload/image/upload.do?belongId=" + Math.random();
            if( /ejiazi\.com/.test($("#uploadPath").val())){/*根据环境判断是否为开发平台 动态修改上传地址 避免来回修改*/
                url="http://localhost:8100/ejiazi-upload/image/upload.do?belongId="+Math.random();
            }
            this.fileId = fileId;
            var that = this;
            var xmlHttpRequest = new XMLHttpRequest();
            var genId = "UPLOAD_" + new Date().getTime();
            var file = document.getElementById(fileId);
            var format = file.files[0].name;
            var _index = format.lastIndexOf(".") + 1;
            var _length = format.length;
            var _string = format.substr(_index, _length).toLowerCase();
            if (_string != 'jpg' && _string != 'gif' && _string != 'jpeg' && _string != 'png') {
                alert("选择的文件应该为图片");
                return false;
            }
            var formData = new FormData();
            formData.append(genId, file.files[0]);
            xmlHttpRequest.open("POST", url, true);
            xmlHttpRequest.send(formData);
            var tipId=layer.open({type: 3});
            xmlHttpRequest.onreadystatechange = function () {
                layer.close(tipId);
                if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                    var ids = JSON.parse(xmlHttpRequest.responseText);
                    if (ids != null) {
                        for (var i = 0; i < ids.data.length; i++) {
                            var data = ids.data[i];
                            var fileElementId = that.fileId;
                            console.log(data)
                            var imgId = data.imageId;
                            var imgUrl = data.imageUrl;
                            item.picUrl = imgUrl;
                            item.picId = imgId;
                            if ("posters" == item.attr) {
                                item["id"] = imgId;
                                item["url"] = imgUrl;
                            } else if ("imageId" == item.attr) {
                                item["imageId"] = imgId;
                                item["imageUrl"] = imgUrl;
                            } else if (item.attr != undefined) {
                                item[item.attr + "PicUrl"] = imgUrl;
                                item[item.attr + "PicId"] = imgId;
                            }
                            if (!item.jumpParam) {
                                item.noPic = false;
                                item.noTarget = true;
                                if (item.type == 4 && item.jumpType == 5) {//首页推广广告位 限购专区
                                    item.noTarget = false;
                                }
                            } else {
                                item.noPic = false;
                                item.noTarget = false;
                            }
                            $scope.$apply();
                        }
                    }
                }
            }
        }
    }
    return UploadService;
}).directive("fileWatcher", function () {
    return {
        require: 'ngModel',
        scope: {item: '='},
        controller: function ($scope, uploadService) {
            $scope.upload = uploadService.upload;
            // $scope.uploadUrl=ConfigService.uploadUrl;
        },
        compile: function (tElement, tAttrs, transclude) {
            return {
                pre: function ($scope, iElement, iAttrs, controller) {

                },
                post: function ($scope, iElement, iAttrs, controller) {
                    $(function () {
                        $(iElement).bind("change", function () {
                            var fileElementId = iAttrs.id;
                            checkImgUpload(fileElementId, callBack);
                            function callBack(result) {
                                $scope.item.attr = iAttrs.attr;
                                $scope.upload(iAttrs.id, $scope.item, $scope, $scope.uploadUrl);
                            }
                        })
                    })
                }
            }
        }
    }
});