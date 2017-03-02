/**
 * 区域选择组件 从此选择区域轻快多了
 * Created by xiankunfeng on 2016/11/18.
 *
 */
angular.module("MallApp.AreaSelector", [])
    .service("baseService", ["$http",function ($http) {
        return {
            getData: function (param) {
                var method=(param.method||"POST").toLocaleLowerCase();
                return $http({
                    method:method,
                    params:method=="post"?null:param,
                    url:param.url,
                    data:method!="post"?null:param
                }).then(function (data) {
                    return data;
                },function (error) {
                    return error;
                });
            }
        };
    }])
    .directive("ajlAreaSelector", function () {/*提供数据源url 剩下的交给它自己处理吧  这里会提供许多事件钩子 你可以挂在你所关心地事件  希望你在写列表的事件簿超过1分钟了...*/
        return {
            restrict: 'ECMA',
            templateUrl: function (tElement, tAttrs) {
                return "/ejiazi-merchant/jsp/template/areaSelectTemplate.html";
            },
            replace: false,
            scope: {
                selectedComplete: "&",/*操作事件钩子 会被去掉 */
                serviceRangeType: "=",/*服务范围*/
                chooseResult:'=',/*选择的结果存储对象*/
                selectModel:'=',/*选择模式  open 开放模式 只有两种选择类型 全国和指定区域   非open选择模式是指定区域不能选择省和城市信息*/
                reload: "=",/*是否重新加载数据*/
                url:'=',/*数据源地址*/
                query:'=',/*你所关心是查询条件*/
                pageSize:'=',/*你所关心是查询条件*/
                changeRangeType:'=',/*你所关心是查询条件*/
                selectCheckUrl:'='/*检查对象是否可以选择的url*/,
                operates:'='/*你所关心是查询条件*/,
                httpMethod:'='/*数据请求方法 post||get*/,
                disabledParam:'='/*不能操作的数据集合*/,
                otherQuery:'='/*不能操作的数据集合*/,
                maxRange:'=',
                selectCheckParams:'='/*校验接口要传的参数*/,
                pageStatus:"="
            },
            controller: function ($scope, baseService) {
                var postParam={
                    url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                    method:'get'
                };

                var serviceRangeTypeMapRev={
                    1:1,3:2,4:3
                };

                $scope.currentRange=serviceRangeTypeMapRev[$scope.maxRange];

                $scope.data={
                    choseList:[],
                    catch:{},
                    query:{},
                    display:{},
                    chooseResult:{},
                    queryAreaParam:{province:{},city:{},district:{},circle:{}},
                    queryArea:{provinceId:"",cityId:"",districtId:"",circleId:""}
                };
                $scope.data.catch=angular.copy($scope.disabledParam);
                console.log("**********************",$scope.data,$scope.disabledParam);
                $scope.area={
                    type:serviceRangeTypeMapRev[$scope.serviceRangeType]
                };

                $scope.data.catch=angular.copy($scope.disabledParam);
                console.log("*****++++++++++++++++++++++*********",$scope.data,$scope.disabledParam);


               /* PROVINCE(1, "省"),
                    CITY(2, "市"),
                    DISTRICT(3, "区"),
                    RING(4, "环"),
                    COMMUNITY(5, "小区");*/
                var leaveMap={
                    "province":"1",
                    "city":"2",
                    "district":"3",
                    "circle":"4",
                    "ring":"4",
                    community:"5"
                };
                $scope.checkUtils={
                    checkParam: function (param) {
                        param=angular.extend(param,$scope.selectCheckParams);
                        console.log(param);
                        param["areaLevel"]=leaveMap[param.paramType];
                        param["method"]="POST";
                        param["url"]=$scope.selectCheckUrl;
                        return baseService.getData(param);
                    }
                };

                $scope.clickUtils={
                    getCitys:function(province){
                        province.state=!province.state;
                        if(province.cityList) return;
                        var cityParam={
                            url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                            method:'get',
                            pid:province.id
                        };
                        baseService.getData(cityParam).then(function (data) {
                                province.cityList=data.data.citiesList;
                        },function (error) {
                            console.log(error)
                        })
                    },
                    chooseCity: function (city) {
                        if(!city.checked){
                            delete $scope.data.catch[city.catch_id];
                            return;
                        }
                        if($scope.area.type==2){
                            city.paramType="city";
                            city.catch_id=city.id;
                            city.cityId=city.id;
                            city.provinceId=city.pid;
                            console.log(city)
                        }
                        if(city.paramType=="community"){
                            city["catch_id"]=city.provinceId+"_"+city.cityId+"_"+city.districtId+"_"+city.ringId+"_"+city.id;
                            var realPath=city.catch_id.replace(/(_0)+$/,'');
                            city["realPath"]=realPath;
                            $scope.data.catch[city.catch_id]=city;
                        }else{
                            $scope.data.catch[city.catch_id]=city;
                        }

                        if(city.checked&&$scope.selectCheckUrl){
                            /*需要check*/
                            $scope.checkUtils.checkParam(city).then(function (data) {
                                console.log("check result ", data)
                                if(data.data.code==1){
                                    /*取消选择的情况*/
                                    if(!city.checked){
                                        delete $scope.data.catch[city.catch_id];
                                    }
                                }else{
                                    layer.msg(data.data.msg);
                                    /*如果已经设置过不允许再次添加*/
                                    if(city.checked){
                                        if(city.paramType=="community"){
                                            city["checked"]=false;
                                        }
                                        delete $scope.data.catch[city.catch_id];
                                    }
                                }
                            }, function (error) {
                                layer.msg(JSON.stringify(error));
                                if(city.checked){
                                    delete $scope.data.catch[city.catch_id];
                                }
                            })
                        }else{
                            if(city.paramType=="community"){
                                var checkResult=$scope.clickUtils.isContainerNew(city);
                                if(checkResult&&checkResult.length>0){
                                    console.log("大范围已存在不需要再次添加!!");
                                    return;
                                }
                            };
                            /*if(!city.checked){
                                delete $scope.data.catch[city.id];
                            }*/
                        }
                    },
                    removeItem: function (item) {
                        /*TUDO 后台接口校验删除*/
                        if(item.disable) return;/*已经入库保存的选择的小区或范围不允许红叉删除  只允许被大范围覆盖时候删除*/
                        item.checked=!item.checked;
                        delete $scope.data.catch[item.catch_id];
                    },
                    queryCommunity: function () {
                        $scope.data.query.groundCommunityCityList=[];
                        var communityListParam={
                            url:'/ejiazi-merchant/community/queryCommunitiesList.json',
                            method:'get',
                            communityName:($scope.data.query.communityName||'').replace(/^\s+/,'').replace(/\s+$/,''),
                            provinceId:($scope.data.query.provinceId||{}).id||0,
                            cityId:($scope.data.query.cityId||{}).id||0,
                            districtId:($scope.data.query.districtId||{}).id||0,
                            ringId:($scope.data.query.ringId||{}).id||0
                        };
                        if(communityListParam.cityId==0||communityListParam.communityName==''||communityListParam.communityName==null){
                            layer.msg("请选择城市和输入小区名称关键字");
                            return;
                        }
                        communityListParam=_.extend(communityListParam,$scope.otherQuery);
                        baseService.getData(communityListParam).then(function (data) {
                            var communityList=data.data.data;
                            if(communityList){
                                var cityNameCommunityList=_.map(communityList, function (value,seq,arr) {
                                    value["paramType"]="community";
                                    _.map($scope.disabledParam, function (disabledItem) {
                                        if(disabledItem.id==value.id){
                                            value.checked=true;
                                            value.disabledd=disabledItem.disabled;
                                        }
                                    });

                                    return value;
                                });
                                var groundCommunityList= _.groupBy(cityNameCommunityList,"provinceName");
                            }
                            $scope.data.query.communityList=data.data.data;
                            $scope.data.query.groundCommunityList=groundCommunityList;
                            $scope.data.query.groundCommunityCityList= _.map(_.keys(groundCommunityList), function (value) {
                                return {name:value};
                            });
                        },function (error) {
                            console.log(error)
                        })
                    },
                    addQueryParam: function () {
                        var provinceParam=$scope.data.queryAreaParam.provinceId||{};
                        var cityParam=$scope.data.queryAreaParam.cityId||{};
                        var districtParam=$scope.data.queryAreaParam.districtId||{};
                        var circleParam=$scope.data.queryAreaParam.circleId||{};
                        var queryParam={
                            name:(provinceParam.name||'')+" "+(cityParam.name||'')+" "+((districtParam||{}).name||'')+" "+((circleParam||{}).name||''),
                            id:(provinceParam.id||0)+"_"+(cityParam.id||0)+"_"+(districtParam.id||0)+"_"+(circleParam.id||0)+"_0",
                            provinceId:provinceParam.id||0,
                            cityId:cityParam.id||0,
                            districtId:districtParam.id||0,
                            ringId:circleParam.id||0,
                            communityId:0
                        };
                        queryParam["catch_id"]=queryParam["id"];
                        if(queryParam.provinceId==0&&queryParam.cityId==0&&queryParam.districtId==0&&queryParam.ringId==0){
                            /*无效参数不能添加*/
                            return;
                        }

                        if(queryParam.cityId==0&&queryParam.districtId==0&&queryParam.ringId==0){
                            /*添加省份查询参数*/
                            queryParam["paramType"]="province";
                            queryParam["included"]=provinceParam.included;
                        }else if(queryParam.cityId!=0&&queryParam.districtId==0&&queryParam.ringId==0){
                            /*添加城市查询参数*/
                            queryParam["paramType"]="city";
                            queryParam["included"]=cityParam.included;
                        }else if(queryParam.cityId!=0&&queryParam.districtId!=0&&queryParam.ringId==0){
                            /*添加省份查询参数*/
                            queryParam["paramType"]="district";
                            queryParam["included"]=districtParam.included;
                        }else{
                            /*添加环信息参数*/
                            queryParam["paramType"]="circle";
                            queryParam["included"]=circleParam.included;
                        }
                        if(!queryParam["included"]){
                            return;
                        }
                        console.log("queryParam",queryParam);
                        if($scope.selectModel!='open'&&$scope.area.type==3&&(queryParam.paramType=='province'||queryParam.paramType=='city')){
                            /*无效参数不能添加 当指定区域或小区时候 不能添加市级别查询范围*/
                            return;
                        }
                        var realPath=queryParam.catch_id.replace(/(_0)+$/,'');
                        queryParam["realPath"]=realPath;


                        if($scope.selectCheckUrl){
                            /*需要check*/
                            $scope.checkUtils.checkParam(queryParam).then(function (data) {
                                console.log("check result ", data)
                                if(data.data.code==1){
                                    $scope.data.catch[queryParam.catch_id]=queryParam;
                                }else{
                                    layer.msg(data.data.msg);
                                }
                            }, function (error) {
                                layer.msg(JSON.stringify(error));
                            })
                        }else{
                            var checkResult=$scope.clickUtils.isContainerNew(queryParam);
                            if(checkResult&&checkResult.length>0){
                                console.log("大范围已存在不需要再次添加!!");
                                return;
                            }
                            $scope.data.catch[queryParam.catch_id]=queryParam;
                        }
                    },
                    isContainerNew: function (param) {
                        var catchValues=_.values($scope.data.catch);
                        if(catchValues&&param){
                            console.log(param)
                            var leaveMap={
                                province:1,
                                city:2,
                                district:3,
                                circle:4,
                                community:5
                            };
                            /*检查小区是否被包含*/
                            var checkResult= _.filter(catchValues, function (item,seq,arr) {
                                if(item.realPath==param.realPath) return;
                                if(item.paramType=="community"&&param.paramType=="community") return;
                                if(item.paramType=="province"&&param.paramType=="province") return;
                                if(!item.realPath){
                                    if(!item.catch_id){
                                        item.catch_id=item.param_id;
                                    }
                                    var realPath=item.catch_id.replace(/(_0)+$/,'');
                                    item["realPath"]=realPath;
                                }
                                if(!param.realPath){
                                    if(!param.catch_id){
                                        param.catch_id=param.param_id;
                                    }
                                    var realPath=param.catch_id.replace(/(_0)+$/,'');
                                    param["realPath"]=realPath;
                                }

                                /*param 小区 为参数*/
                                if(param.paramType=="community"){
                                    if(item.paramType=="province"||item.paramType=="city"||item.paramType=="district"){
                                        if((param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)==""))){
                                            /*小区被省市区包含不添加小区*/
                                            param.checked=false;
                                            delete $scope.data.catch[param.catch_id];
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                /*小区被无区环包含不添加小区*/
                                                param.checked=false;
                                                delete $scope.data.catch[param.catch_id];
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if((param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)==""))||(item.districtId==param.districtId&&param.ringId<=item.ringId)){
                                                /*小区被省市区包含不添加小区*/
                                                param.checked=false;
                                                delete $scope.data.catch[param.catch_id];
                                            }
                                        }
                                    }
                                }
                                /*param 有区环 为参数*/
                                if(param.paramType=="circle"&&param.districtId!=0){
                                    if(item.paramType=="province"||item.paramType=="city"||item.paramType=="district"){
                                        if(param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            //param.checked=false;
                                            //delete $scope.data.catch[param.catch_id];
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                /*小区被无区环包含不添加小区*/
                                                /*param.checked=false;
                                                 delete $scope.data.catch[param.catch_id];*/
                                                return param;
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==param.districtId&&item.districtId!=0){
                                            if(item.ringId<=param.ringId){
                                                /*小区被省市区包含不添加小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }else{
                                                return param;
                                            }
                                        }
                                    }

                                    if(item.paramType=="community"){
                                        if(item.paramType=="community"){
                                            _.each($scope.data.query.communityList, function (community) {
                                                if(community.id==item.communityId){
                                                    community.checked=item.checked;
                                                }
                                            })
                                        }
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId>=item.ringId){
                                                /*小区被无区环包含不删除缓存小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if((item.realPath.indexOf(param.realPath)==0&&(item.realPath.substr(param.realPath.length,1)=="_"||item.realPath.substr(param.realPath.length,1)==""))||(item.districtId==param.districtId && item.ringId<=param.ringId)){
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                                /*小区被省市区包含不添加小区*/
                                                //return item;
                                            }
                                        }
                                    }
                                }

                                /*param 无区环 为参数*/
                                if(param.paramType=="circle"&&param.districtId==0){
                                    if(item.paramType=="province"||item.paramType=="city"){
                                        if(param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            //param.checked=false;
                                            //delete $scope.data.catch[param.catch_id];
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                return param;
                                            }else{
                                                /*被无区环包含不添加小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if(param.ringId<item.ringId){
                                                /*小区被省市区包含不添加小区*/
                                                /*param.checked=false;
                                                 delete $scope.data.catch[param.catch_id];*/
                                            }else if(param.ringId>=item.ringId){
                                                /*小区被省市区包含不添加小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }
                                        }
                                    }

                                    if(item.paramType=="community"){
                                        if(item.paramType=="community"){
                                            _.each($scope.data.query.communityList, function (community) {
                                                if(community.id==item.communityId){
                                                    community.checked=item.checked;
                                                }
                                            })
                                        }
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&param.districtId==0){
                                            if(param.ringId>=item.ringId){
                                                /*小区被无区环包含不删除缓存小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }
                                        }
                                    }
                                }


                                /*param 区 为参数*/
                                if(param.paramType=="district"){
                                    if(item.paramType=="province"||item.paramType=="city"){
                                        if(param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            //param.checked=false;
                                            //delete $scope.data.catch[param.catch_id];
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            /*if(param.realPath<=item.realPath){
                                             return param;
                                             }else{
                                             /!*被无区环包含不添加小区*!/
                                             item.checked=false;
                                             delete $scope.data.catch[item.catch_id];
                                             }*/
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if(item.realPath.indexOf(param.realPath)==0&&(item.realPath.substr(param.realPath.length,1)=="_"||item.realPath.substr(param.realPath.length,1)=="")){
                                                /*小区被省市区包含不添加小区*/
                                                item.checked=false;
                                                delete $scope.data.catch[item.catch_id];
                                            }
                                        }
                                    }

                                    if(item.paramType=="community"){
                                        if(item.paramType=="community"){
                                            _.each($scope.data.query.communityList, function (community) {
                                                if(community.id==item.communityId){
                                                    community.checked=item.checked;
                                                }
                                            })
                                        }
                                        if(item.realPath.indexOf(param.realPath)==0&&(item.realPath.substr(param.realPath.length,1)=="_"||item.realPath.substr(param.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            item.checked=false;
                                            delete $scope.data.catch[item.catch_id];
                                        }
                                    }
                                }

                                /*param 城市 为参数*/
                                if(param.paramType=="city"){
                                    if(item.paramType=="province"){
                                        if(param.realPath.indexOf(item.realPath)==0&&(param.realPath.substr(item.realPath.length,1)=="_"||param.realPath.substr(item.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            //param.checked=false;
                                            //delete $scope.data.catch[param.catch_id];
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="district"||item.paramType=="circle"||item.paramType=="community"){
                                        if(item.realPath.indexOf(param.realPath)==0&&(item.realPath.substr(param.realPath.length,1)=="_"||item.realPath.substr(param.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            item.checked=false;
                                            delete $scope.data.catch[item.catch_id];
                                        }
                                    }
                                }

                                /*param 省 为参数*/
                                if(param.paramType=="province"){
                                    if(item.paramType=="province"){

                                    }
                                    if(item.paramType=="community"){
                                        _.each($scope.data.query.communityList, function (community) {
                                            if(community.id==item.communityId){
                                                community.checked=item.checked;
                                            }
                                        })
                                    }
                                    if(item.paramType=="city"||item.paramType=="district"||item.paramType=="circle"||item.paramType=="community"){
                                        if(item.realPath.indexOf(param.realPath)==0&&(item.realPath.substr(param.realPath.length,1)=="_"||item.realPath.substr(param.realPath.length,1)=="")){
                                            /*小区被省市区包含不添加小区*/
                                            item.checked=false;
                                            delete $scope.data.catch[item.catch_id];
                                        }
                                    }
                                }

                            });
                            return checkResult;
                        }
                    },
                    chooseComplete: function () {
                        var catchValues=_.values($scope.data.catch);
                            $scope.data.chooseResult.provinceList= _.filter(catchValues, function (item) {
                                if(item.paramType=="province"){
                                    return item;
                                }
                            });
                            $scope.data.chooseResult.cityList= _.filter(catchValues, function (item) {
                                if(item.paramType=="city"){
                                    return item;
                                }
                            });
                            $scope.data.chooseResult.districtList= _.filter(catchValues, function (item) {
                                if(item.paramType=="district"){
                                    return item;
                                }
                            });
                            $scope.data.chooseResult.circleList= _.filter(catchValues, function (item) {
                                if(item.paramType=="circle"){
                                    return item;
                                }
                            });
                            $scope.data.chooseResult.communityList= _.filter(catchValues, function (item) {
                                if(item.paramType=="community"&&(item.checked||item.disabled)){
                                    return item;
                                }
                            });
                            $scope.chooseResult=$scope.data.chooseResult;
                    }
                };
                postParam=_.extend(postParam,$scope.otherQuery);
                baseService.getData(postParam).then(function (data) {
                    if(!$scope.data.query.province){
                        $scope.data.query.provinceList=data.data.citiesList;
                        $scope.data.provinceList=data.data.citiesList;
                        $scope.data.queryArea.provinceList=data.data.citiesList;
                    }
                },function (error) {
                    console.log(error)
                })

                $scope.$watch("data.catch", function (newValue,oldValue) {
                    var catchValues=_.values($scope.data.catch);
                    if(catchValues){
                        $scope.data.display.communityGroupedList= _.groupBy(catchValues,"provinceName");
                        $scope.data.display.communityGroupedTypeList= _.keys($scope.data.display.communityGroupedList);
                    }
                    /*同步给调用者*/
                    $scope.clickUtils.chooseComplete();
                },true);
                $scope.$watch("data.query.provinceId", function (newValue, oldValue) {
                    $scope.data.communityList = [];
                    if (!newValue) {
                        $scope.data.query.cityId = "";
                        $scope.data.query.cityList = [];
                        $scope.data.communityList = [];
                        return;
                    }
                    var cityParam={
                        url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                        method:'get',
                        pid:newValue.id
                    };
                    cityParam=_.extend(cityParam,$scope.otherQuery);
                    baseService.getData(cityParam).then(function (data) {
                        $scope.data.query.cityList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })

                })
                $scope.$watch("data.query.cityId", function (newValue, oldValue) {
                    if(!newValue){
                        $scope.data.query.areaList=[];
                        $scope.data.query.circleList=[];
                        return;
                    }
                    var areaParam={
                        url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                        method:'get',
                        pid:newValue.id
                    };
                    areaParam=_.extend(areaParam,$scope.otherQuery);
                    baseService.getData(areaParam).then(function (data) {
                        $scope.data.query.areaList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })
                    var circleParam={
                        url:'/ejiazi-merchant/ring/queryOpeningRingList.json',
                        method:'get',
                        provinceId:$scope.data.query.provinceId,
                        cityId:newValue.id
                    };
                    circleParam=_.extend(circleParam,$scope.otherQuery);
                    baseService.getData(circleParam).then(function (data) {
                        $scope.data.query.circleList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })
                })

                $scope.$watch("data.queryAreaParam.provinceId", function (newValue, oldValue) {
                    if (!newValue) {
                        $scope.data.queryAreaParam.cityId="";
                        $scope.data.queryAreaParam.areaId="";
                        $scope.data.queryAreaParam.circleId="";
                        $scope.data.queryArea.cityList=[];
                        $scope.data.queryArea.areaList=[];
                        $scope.data.queryArea.circleList=[];
                        return;
                    }
                    $scope.data.queryAreaParam.cityId="";
                    $scope.data.queryAreaParam.districtId="";
                    $scope.data.queryAreaParam.circleId="";
                    $scope.data.queryArea.cityList=[];
                    $scope.data.queryArea.areaList=[];
                    $scope.data.queryArea.circleList=[];
                    var cityParam={
                        url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                        method:'get',
                        pid:newValue.id
                    };
                    cityParam=_.extend(cityParam,$scope.otherQuery);
                    baseService.getData(cityParam).then(function (data) {
                        $scope.data.queryArea.cityList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })
                })
                var serviceRangeTypeMap={1:1,2:3,3:4};
                $scope.$watch("area.type", function (newValue, oldValue) {
                    _.mapObject($scope.data.catch, function (item,seq,arr) {
                        item.checked=false;
                    })
                    $scope.data.catch=$scope.disabledParam||{};
                    //$scope.data.display={};
                    $scope.serviceRangeType=serviceRangeTypeMap[newValue];
                })
                $scope.$watch("data.queryAreaParam.cityId", function (newValue, oldValue) {
                    if(!newValue){
                         $scope.data.queryArea.areaList=[];
                         $scope.data.queryArea.districtList=[];
                         $scope.data.queryArea.circleList=[];
                        return;
                    }
                    $scope.data.queryAreaParam.districtId="";
                    $scope.data.queryAreaParam.circleId="";
                    $scope.data.queryArea.areaList=[];
                    $scope.data.queryArea.circleList=[];
                    var areaParam={
                        url:'/ejiazi-merchant/citys/queryOpeningCitiesList.json',
                        method:'get',
                        pid:newValue.id
                    };
                    areaParam=_.extend(areaParam,$scope.otherQuery);
                    baseService.getData(areaParam).then(function (data) {
                        $scope.data.queryArea.areaList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })
                    var circleParam={
                        url:'/ejiazi-merchant/ring/queryOpeningRingList.json',
                        method:'get',
                        provinceId:$scope.data.queryAreaParam.provinceId.id,
                        cityId:newValue.id
                    };
                    circleParam=_.extend(circleParam,$scope.otherQuery);
                    baseService.getData(circleParam).then(function (data) {
                        $scope.data.queryArea.circleList=data.data.citiesList;
                    },function (error) {
                        console.log(error)
                    })
                });
                $scope.$watch("data.queryAreaParam.districtId", function (newValue, oldValue) {
                    if(!$scope.data.queryAreaParam.provinceId||!$scope.data.queryAreaParam.cityId){
                        return;
                    }
                    $scope.data.queryAreaParam.circleId="";
                    $scope.data.queryArea.circleList=[];
                    var circleParam={
                        url:'/ejiazi-merchant/ring/queryOpeningRingList.json',
                        method:'get',
                        provinceId:$scope.data.queryAreaParam.provinceId.id,
                        cityId:$scope.data.queryAreaParam.cityId.id,
                        districtId:newValue==null?0:newValue.id
                    };
                    circleParam=_.extend(circleParam,$scope.otherQuery);
                    baseService.getData(circleParam).then(function (data) {
                        $scope.data.queryArea.circleList=data.data.citiesList;
                    },function (error) {
                        console.log(error);
                    })
                })
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
    });

