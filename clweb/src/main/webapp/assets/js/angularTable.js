/**
 * 列表组件 从此列表轻快多了
 * Created by xiankunfeng on 2016/11/18.
 *
 */

    /*
    *
    * 页面配置说明 可以配置查询参数 和 列表列 以及 分页相关信息 以及每行的操作事件
    *
    * */
/*var pageTableConfigSample={query:{
    name:1231,
    queryElements:[
        {
            label:'店铺名称是',
            type:'input',
            placeholder:'请输入店铺名称',
            prop:'shopName'
        },
        {
            label:'请输入时间',
            type:'date',
            format:'YYYY-MM-DD hh:mm:ss',
            placeholder:'请输入时间',
            prop:'time'
        },
        {
            label:'活动时间',
            type:'composite',/!*复合组件 如日期类型*!/
            link:'至',/!*组合条件之间的连字符-／到*!/
            components:[
                {
                    type:'date',
                    placeholder:'请输入开始时间',
                    prop:'start'
                },
                {
                    type:'date',
                    placeholder:'请输入开始时间',
                    prop:'end'
                }
            ]
        },
        {
            label:'搜索',
            type:'search'
        },
        {
            id:1,
            label:'小区选择',
            type:'select',
            placeholder:'全部',
            options:[
                {
                    label:'全部',
                    value:''
                },
                {
                    label:'城市花园',
                    value:'1'
                }
            ],
            url: function () {
                return data.url
            },
            prop:'community_id',
            casecadeParen:"",/!*级联父对象*!/
            casecadeChild:""/!*级联子对象*!/
        },
        {
            label:'所有tab面板',
            type:'tab',/!*tab页 如 ''全部 1未开始 2已开始 3进行中 4已结束*!/
            prop:'status',
            listener: function (param) {
                console.log("table change",param)
            },
            components:[
                {
                    label:'全部',
                    value:''
                },
                {
                    label:'未开始',
                    value:'1'
                },
                {
                    label:'已开始',
                    value:'2'
                },
                {
                    label:'进行中',
                    value:'3'
                },
                {
                    label:'已结束',
                    value:'4'
                }
            ]
        },
    ]
}};*/
angular.module("MallApp.Table", [])
    .service("BaseDataService", ["$http", function ($http) {
        return {
            getDataList: function (url,param) {
                var tipId=layer.load(1);
                return $http({
                    method:param.method||'POST',
                    params:param,
                    url:url,
                    data:param
                }).success(function (data) {
                    layer.close(tipId);
                    return data.data;
                }).error(function (error) {
                    layer.close(tipId);
                    console.log(error)
                });
            }
        };
    }])
    .directive("ajlPagerTableList", function () {/*提供数据源url 剩下的交给它自己处理吧  这里会提供许多事件钩子 你可以挂在你所关心地事件  希望你在写列表的事件簿超过1分钟了...*/
        return {
            restrict: 'ECMA',
            templateUrl: function (tElement, tAttrs) {
                return "/ejiazi-merchant/jsp/template/pagerTableListTemplate.html";
            },
            replace: false,
            scope: {
                selectedComplete: "&",/*操作事件钩子 会被去掉 */
                reload: "=",/*是否重新加载数据*/
                colums:'=',/*显示列对象*/
                url:'=',/*数据源地址*/
                listExcept:'=',
                query:'=',/*你所关心是查询条件*/
                pageSize:'=',/*你所关心是查询条件*/
                operates:'='/*你所关心是查询条件*/,
                httpMethod:'='/*数据请求方法 post||get*/
            },
            controller: function ($scope, BaseDataService) {
                $scope.queryParam={};
                $scope.data={};
                $scope.queryUtil = {
                    getListByParam: function (param) {
                        param["method"]=$scope.httpMethod;
                        BaseDataService.getDataList($scope.url,param).then(function (data) {
                            $scope.utils.pagerDataHelper("data", data);
                        })
                    }
                };
                $scope.utils = {
                    pagerDataHelper: function (dataType, data) {
                        if (!data.data.data) {
                            $scope.data[dataType + "List"] = [];
                            $scope.data[dataType + "Pages"] = {
                                totalRows: 0,
                                totalPages: 0,
                                rowsPerPage: 0,
                                currentPage: 0
                            };
                            return;
                        }
                        $scope.data[dataType + "List"] = data.data.data.dataPerPage;
                        $scope.data[dataType + "Pages"] = {
                            totalRows: data.data.data.totalRows,
                            totalPages: data.data.data.totalPages,
                            rowsPerPage: data.data.data.rowsPerPage,
                            currentPage: data.data.data.currentPage
                        }
                    },
                    queryData: function (item) {
                        console.log(item);
                        if(typeof item == "function"){
                            var checkResult=item($scope.queryParam);
                            if(checkResult){
                                $scope.queryDataByPage();
                            }
                        }else{
                            $scope.queryDataByPage();
                        }

                    }
                };
                /*列表处分页理函数*/
                $scope.queryDataByPage = function (param) {
                    var queryParam=$scope.queryParam;
                    queryParam["pageSize"]=$scope.pageSize;
                    queryParam["pageNum"]= param&&param.page&&param.page.pageNum||1;
                    $scope.queryUtil.getListByParam(queryParam);
                };

                for(var i=0;i<$scope.query.queryElements.length;i++){
                    if($scope.query.queryElements[i].type=='tab'){
                        $scope.$watch("queryParam."+$scope.query.queryElements[i].prop, function (newVal,oldVal) {
                            $scope.utils.queryData();
                        })
                    }
                    if($scope.query.queryElements[i].type=='hidden'){
                        $scope.queryParam[$scope.query.queryElements[i].prop]=$scope.query.queryElements[i].value;
                    }
                }
                for(var o in $scope.operates){
                    for (var p in $scope.operates[o].map){
                        if(!angular.isArray($scope.operates[o].map[p])){
                            $scope.operates[o].map[p] = [$scope.operates[o].map[p]]
                        }
                        console.log($scope.operates[o].map[p],'fwafwa')
                    }
                }
            },
            controllerAs: 'pagerTestController',
            compile: function (tElement, tAttrs, transclude) {
                return {
                    pre: function ($scope, iElement, iAttrs, controller) {
                    },
                    post: function ($scope, iElement, iAttrs, controller) {
                        $scope.templateName = iAttrs.templateName;
                        $scope.$watch("reload", function (newValue, oldValue) {
                            var param=$scope.queryParam;
                            param["pageSize"]=$scope.pageSize;
                            if(/currentPage/.test(newValue)){
                                param["pageNum"]=$scope.data.dataPages.currentPage;/*刷新当前页 用于修改或删除*/
                            }else{
                                param["pageNum"]="1";/*第一页刷新 用于新建*/
                            }
                            $scope.queryUtil.getListByParam(param);
                        })
                    }
                }
            }
        }
    })
    .directive("ajlPagerTableQueryParam", function () {/*提供数据源自动生成查询条件 并不列表组件配合 达到显示列表页只需处理事件 无需关心列表累参数  提高开发速度...*/
        return {
            restrict: 'ECMA',
            templateUrl: function (tElement, tAttrs) {
                return "/ejiazi-merchant/jsp/template/pagerTableQueryListTemplate.html";
            },
            replace: false,
            controller: function ($scope, BaseDataService) {
                $scope.dataQuery={
                    query:{}
                };
                /*n 级 级联查询开始*/
                var requestUrlParam= _.filter($scope.query.queryElements, function (item) {
                    if(item.dataUrl){
                        return item;
                    }
                });
                _.each(requestUrlParam, function (item) {
                    if(item.casecadeParen==null||item.casecadeParen==""){
                        /*顶级元素优先查询完成一级数据初始化*/
                        var postParam={
                            url:item.dataUrl,
                            method:'get'
                        };
                        BaseDataService.getDataList(postParam.url,postParam).then(function (data) {
                            var selfList=item.propList;
                            console.log("selfListselfListselfListselfList",selfList)
                            $scope.dataQuery.query[selfList]=data.data[item.selfListProp];
                        },function (error) {
                            console.log(error)
                        });
                    }
                    if(item.casecadeChild!=""){
                        /*有级联元素需要绑定监控*/
                        $scope.$watch("queryParam."+item.prop, function (newValue,oldValue) {
                            console.log("queryParam."+item.prop+" 发生变化",newValue,oldValue);
                            //禁止初始化监听
                            if(!newValue){
                                return;
                            }
                            var casecadeChild=item.casecadeChild;
                            var listName=casecadeChild.replace("Id","List");
                            var areaParam={
                                url:item.casecadeChildDataUrl,
                                method:'get',
                                pid:newValue
                            };

                            if(item.queryParam){
                                areaParam[item.queryParam]= newValue;
                            }

                            /*清空下级元素集合*/
                            $scope.dataQuery.query[listName]=[];
                            /*清空所有孙级别元素集合*/
                            _.each(item.casecadeGrandsonList, function (grandsonElement) {
                                console.log("$scope.queryParam",$scope.queryParam,"grandsonElement="+grandsonElement)
                                $scope.dataQuery.query[grandsonElement.replace("Id"),"List"]=[];
                                $scope.queryParam[grandsonElement]="";
                            });
                            BaseDataService.getDataList(areaParam.url,areaParam).then(function (data) {
                                $scope.dataQuery.query[listName]=data.data[item.casecadeChildListProp||'citiesList'];
                                if(!item.casecadeChildListProp){
                                    console.error("item.casecadeChildListProp 配置错误"+item.casecadeChildListProp);
                                }
                            },function (error) {
                                console.log(error)
                            })
                        })
                    }
                });
                /*n 级 级联查询结束*/

                /*n级 switch级联开始  业务模式为 当某个查询条件值为特定值时 动态添加新查询参数 动态添加的参数又可以是n级级联查询*/
                var requestSwitchElementsParam= _.filter($scope.query.queryElements, function (item) {
                    if(item.switchElements){
                        return item;
                    }
                });
                _.each(requestSwitchElementsParam, function (itemParent) {

                        $scope.$watch("queryParam."+itemParent.prop, function (newValue,oldValue) {
                            console.log("selected, itemParent",itemParent,newValue,oldValue);
                            if(!newValue) return;
                            var temp=_.filter(itemParent.switchElements, function (item) {
                                if(item.whenSwitchValue==newValue){
                                    return item;
                                }
                            })[0];
                            console.log("temptemptemptemptemptemp",temp)
                            if(temp&&(temp.casecadeParen==null||temp.casecadeParen=="")){
                                /*顶级元素优先查询完成一级数据初始化*/
                                var postParam={
                                    url:temp.dataUrl,
                                    method:'get'
                                };
                                BaseDataService.getDataList(postParam.url,postParam).then(function (data) {
                                    var selfList=temp.propList;
                                    $scope.dataQuery.query[selfList]=data.data[temp.selfListProp];
                                },function (error) {
                                    console.log(error)
                                });
                            }
                        })
                    _.each(itemParent.switchElements, function (item) {
                        if(item.casecadeParen==null||item.casecadeParen==""){
                            /*顶级元素优先查询完成一级数据初始化*/
                            var postParam={
                                url:item.dataUrl,
                                method:'get'
                            };
                            BaseDataService.getDataList(postParam.url,postParam).then(function (data) {
                                var selfList=item.propList;
                                $scope.dataQuery.query[selfList]=data.data[item.selfListProp];
                            },function (error) {
                                console.log(error)
                            });
                        }
                        if(item.casecadeChild!=""&&item.casecadeChild!="false"){
                            /*有级联元素需要绑定监控*/
                            $scope.$watch("queryParam."+item.prop, function (newValue,oldValue) {
                                var casecadeChild=item.casecadeChild;
                                var listName=casecadeChild.replace("Id","List");
                                var areaParam={
                                    url:item.casecadeChildDataUrl,
                                    method:'get',
                                    pid:newValue
                                };
                                    console.log("itemitemitemitemitem",item)
                                /*清空下级元素集合*/
                                if(((item.casecadeParen==""&&item.casecadeParen==null)&&(item.casecadeChild!=""||item.casecadeChild!=null))){
                                    $scope.dataQuery.query[listName]=[];
                                    /*清空所有孙级别元素集合*/
                                    _.each(item.casecadeGrandsonList, function (grandsonElement) {
                                        $scope.dataQuery.query[grandsonElement.replace("Id"),"List"]=[];
                                        $scope.queryParam[grandsonElement]="";
                                    });
                                }else{
                                    areaParam.pid=$scope.queryParam[item.prop];
                                }
                                BaseDataService.getDataList(areaParam.url,areaParam).then(function (data) {
                                    if(((item.casecadeParen==""&&item.casecadeParen==null)&&(item.casecadeChild!=""||item.casecadeChild!=null))){
                                        $scope.dataQuery.query[listName]=data.data[item.casecadeChildListProp||'citiesList'];
                                        console.log("listName",listName,$scope.dataQuery.query[listName])
                                    }else{
                                        listName=listName.replace("List","SubList");
                                        $scope.dataQuery.query[listName]=data.data[item.casecadeChildListProp||'citiesList'];
                                    }
                                    if(!item.casecadeChildListProp){
                                        console.error("item.casecadeChildListProp 配置错误"+item.casecadeChildListProp);
                                    }
                                },function (error) {
                                    console.log(error)
                                })
                            })
                        }
                    })
                });
            },
            controllerAs: 'pagerTestController',
            compile: function (tElement, tAttrs, transclude) {
                return {
                    pre: function ($scope, iElement, iAttrs, controller) {
                    },
                    post: function ($scope, iElement, iAttrs, controller) {
                        $scope.templateName = iAttrs.templateName;

                    }
                }
            }
        }
    })
    .directive("layerDate", function () {/*使用laydate 选择时间 包括 时间关联选择...*/
        return {
            restrict: 'ECMA',
            replace: false,
            controller: function ($scope, BaseDataService) {

            },
            require:"?ngModel",
            compile: function (tElement, tAttrs, transclude) {
                return {
                    pre: function ($scope, iElement, iAttrs, controller) {
                    },
                    post: function ($scope, iElement, iAttrs, controller) {
                        iElement.on("click", function () {
                            /*
                                elem: '#id', //需显示日期的元素选择器
                                event: 'click', //触发事件
                                format: 'YYYY-MM-DD hh:mm:ss', //日期格式
                                istime: false, //是否开启时间选择
                                isclear: true, //是否显示清空
                                istoday: true, //是否显示今天
                                issure: true, 是否显示确认
                                festival: true //是否显示节日
                                min: '1900-01-01 00:00:00', //最小日期
                                max: '2099-12-31 23:59:59', //最大日期
                                start: '2014-6-15 23:00:00',  //开始日期
                                fixed: false, //是否固定在可视区域
                                zIndex: 99999999, //css z-index*/
                            laydate({
                                elem:"#"+iAttrs.id,
                                format: iAttrs.format||'YYYY-MM-DD',
                                istime: true,
                                istoday: false,
                                festival: true,
                                min:$('#'+iAttrs.minelementid).val(),
                                max:$('#'+iAttrs.maxelementid).val(),
                                choose: function(datas){
                                    $('#'+iAttrs.id).trigger('change');
                                }
                            })
                        })
                    }
                }
            }
        }
    });

