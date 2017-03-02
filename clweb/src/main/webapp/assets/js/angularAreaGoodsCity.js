/**
 * 区域选择组件 从此选择区域轻快多了
 * Created by xiankunfeng on 2016/11/18.
 *
 */
angular.module("MallApp.AreaSelectorGoodsCity", [])
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
                return "/ejiazi-merchant/jsp/template/goodsAreaSelectTemplate.html";
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
                operates:'='/*你所关心是查询条件*/,
                httpMethod:'='/*数据请求方法 post||get*/,
                disabledParam:'='/*不能操作的数据集合*/,
                otherQuery:'='/*不能操作的数据集合*/,
                maxRange:'='
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
                    stateCatch:{},
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
                            console.log(_.values($scope.data.stateCatch[3]))
                                var preCityList= _.pluck(_.values($scope.data.stateCatch[3]),"cityId");
                                var preCityListMap=_.groupBy(_.values(angular.copy($scope.data.stateCatch[3])),"cityId");

                                var preCityIdList= _.keys(preCityListMap);
                                var disabledCityList= _.keys($scope.disabledParam||{});
                                if(preCityIdList){
                                    console.log("preCityIdList",preCityIdList)
                                    _.each(data.data.citiesList, function (city) {
                                        console.log("处理前999",city)
                                        if(city.name.indexOf("-")<0){
                                            city.name=province.name+"-"+city.name;
                                            city.provinceId=province.id;
                                            city.cityId=city.id;
                                            city.provinceName=province.name;
                                            city["paramType"]="city";
                                            city["param_id"]=(city.provinceId||0)+"_"+(city.cityId||0)+"_0_0_0";
                                            city["catch_id"]=city["param_id"];
                                            var realPath=city.catch_id.replace(/(_0)+$/,'');
                                            city["realPath"]=realPath;

                                            console.log("处理后999",city)

                                        }
                                        _.each(preCityIdList, function (item) {
                                            if(city.id==item||city.cityId==item){
                                               // city.disabledd=true;
                                                if($scope.data.catch[city.param_id]){
                                                    city.checked=true;
                                                }
                                            }
                                        })
                                    })
                                }
                                if(disabledCityList){
                                    console.log("preCityIdList",preCityIdList)
                                    _.each(data.data.citiesList, function (city) {
                                        if(city.name.indexOf("-")<0){
                                            city.name=province.name+"-"+city.name;
                                            city.provinceId=province.id;
                                            /*city.provinceName=province.name;
                                            city["paramType"]="city";
                                            city["param_id"]=(city.provinceId||0)+"_"+(city.cityId||0)+"_0_0_0";
                                            city["catch_id"]=city["param_id"];
                                            var realPath=value.catch_id.replace(/(_0)+$/,'');
                                            city["realPath"]=realPath;*/
                                        }
                                        _.each(disabledCityList, function (disableItem) {
                                            if(city.id==$scope.disabledParam[disableItem].cityId||city.cityId==$scope.disabledParam[disableItem].cityId){
                                                if($scope.data.catch[city.param_id]){
                                                    city.disabledd=true;
                                                    city.checked=true;
                                                }
                                            }
                                        })
                                    })
                                }
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
                            city.cityId=city.id;
                            city.provinceId=city.pid;
                            if((city.id+"").indexOf("_")<0){
                                city.id=city.provinceId+"_"+city.cityId+"_0_0_0";
                                city.param_id=city.id;
                                city.catch_id=city.id;
                                var realPath=city.catch_id.replace(/(_0)+$/,'');
                                city["realPath"]=realPath;
                            }
                            console.log(city)
                        }
                        if(city.paramType=="community"){
                            city["catch_id"]=city.provinceId+"_"+city.cityId+"_"+city.districtId+"_"+city.ringId+"_"+city.id;
                            var realPath=city.catch_id.replace(/(_0)+$/,'');
                            city["realPath"]=realPath;
                            $scope.data.catch[city.catch_id]=city;
                        }else if(city.paramType=="city"){
                            console.log("处理前",city)

                            city["catch_id"]=city.provinceId+"_"+city.cityId+"_0_0_0";
                            var realPath=city.catch_id.replace(/(_0)+$/,'');
                            city["realPath"]=realPath;
                            $scope.data.catch[city.catch_id]=city;
                            console.log("处理后",city)
                        }else{
                            $scope.data.catch[city.catch_id]=city;
                        }
                        if(city.paramType=="community"){
                            var checkResult=$scope.clickUtils.isContainerNew(city);
                            if(checkResult&&checkResult.length>0){
                                console.log("大范围已存在不需要再次添加!!");
                                return;
                            }
                        }
                    },
                    removeItem: function (item) {
                        /*TUDO 后台接口校验删除*/
                        if(item.disable) return;/*已经入库保存的选择的小区或范围不允许红叉删除  只允许被大范围覆盖时候删除*/
                        item.checked=!item.checked;
                        delete $scope.data.catch[item.catch_id];
                        if(item.paramType=="community"){
                            _.each($scope.data.query.communityList, function (community) {
                                if(community.id==item.communityId||community.communityId==item.communityId){
                                    community.checked=false;
                                }
                            })
                        }
                        if(item.paramType=="city"){
                            _.each($scope.data.provinceList, function (province) {
                                var cityList=province.cityList;
                                _.each(cityList, function (city) {
                                    if(city.id==item.cityId||city.cityId==item.cityId){
                                        city.checked=false;
                                    }
                                })
                            })
                        }

                        /*检查是否存在绕行删除行为*/
                        /*if(_.values($scope.disabledParam).length){
                            $scope.checkHelp.checkAndRestoreErrorDelete();
                        }*/
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
                                   // value.cityName= (value.displayName||"").substring(0,_.lastIndexOf((value.displayName||""),"市"))+"市";
                                    value["param_id"]=(value.provinceId||0)+"_"+(value.cityId||0)+"_"+(value.districtId||0)+"_"+(value.ringId||0)+"_"+value.id;
                                    value["catch_id"]=value["param_id"];
                                    var realPath=value.catch_id.replace(/(_0)+$/,'');
                                    value["realPath"]=realPath;

                                    _.map($scope.disabledParam, function (disabledItem) {
                                        if((disabledItem.id==value.id||disabledItem.communityId==value.id)&&$scope.data.catch[value.realPath]){
                                            value.checked=true;
                                            value.disabledd=true;
                                        }
                                    });
                                    _.map(_.values($scope.data.catch), function (checkedItem) {
                                        if(checkedItem.id==value.id||checkedItem.communityId==value.id){
                                            value.checked=true;
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
                            provinceName:provinceParam.name||"",
                            cityId:cityParam.id||0,
                            cityName:cityParam.name||'',
                            districtId:districtParam.id||0,
                            districtName:districtParam.name||'',
                            ringId:circleParam.id||0,
                            ringName:circleParam.name||''
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
                        console.log("queryParam",queryParam);
                        if(!queryParam["included"]){
                            return;
                        }
                        if($scope.selectModel!='open'&&$scope.area.type==3&&(queryParam.paramType=='province'||queryParam.paramType=='city')){
                            /*无效参数不能添加 当指定区域或小区时候 不能添加市级别查询范围*/
                            return;
                        }
                        var realPath=queryParam.catch_id.replace(/(_0)+$/,'');
                        queryParam["realPath"]=realPath;
                        var checkResult=$scope.clickUtils.isContainerNew(queryParam);//$scope.selectModel=='open'?$scope.clickUtils.isContainerAll(queryParam):
                        if(checkResult&&checkResult.length>0){
                            console.log("大范围已存在不需要再次添加!!");
                            return;
                        }
                        $scope.data.catch[queryParam.catch_id]=queryParam;
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
                            $scope.data.stateCatch[$scope.area.type]=angular.copy($scope.data.catch);
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
                        $scope.data.queryArea.cityList=[];
                        $scope.data.queryArea.areaList=[];
                        $scope.data.queryArea.circleList=[];
                        return;
                    }
                    $scope.data.queryAreaParam.cityId="";
                    $scope.data.queryAreaParam.districtId="";
                    $scope.data.queryAreaParam.circleId="";
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
                    console.log("newValue, oldValue",newValue, oldValue)
                    $scope.data.catch={};
                    _.mapObject($scope.data.catch, function (item,seq,arr) {
                      //  item.checked=false;
                    });
                    console.log("缓存状态",$scope.data.stateCatch);
                    $scope.data.catch=angular.copy($scope.disabledParam||{});
                    //$scope.data.display={};
                    $scope.serviceRangeType=serviceRangeTypeMap[newValue];
                    if(newValue==2){
                        var preCityListMap=_.groupBy(_.values(angular.copy($scope.data.stateCatch[3])),"cityId");
                        _.map(_.keys(preCityListMap), function (itemCity,seq,arr) {
                            var item=preCityListMap[itemCity][0];
                                item.disabledd=true;
                                item.checked=true;
                                if(item.paramType=="community"||item.paramType=="circle"||item.paramType=="district"){
                                    delete $scope.data.catch[item.catch_id];
                                    if((item.name+"").indexOf("_")<0){
                                        item.name=item.provinceName+"-"+item.cityName;
                                    }
                                    item.paramType="city";
                                    item.catch_id=(item.provinceId||0)+"_"+(item.cityId||0)+"_0_0_0";
                                    item["param_id"]=item["catch_id"];
                                    var realPath=item.catch_id.replace(/(_0)+$/,'');
                                    item["realPath"]=realPath;
                                }
                                item.provinceId=item.pid?item.pid:item.provinceId;
                                if((item.catch_id+"").indexOf("_")<0){
                                    item["param_id"]=(item.provinceId||0)+"_"+(item.cityId||0)+"_"+(item.districtId||0)+"_"+(item.ringId||0)+"_0";
                                    item["catch_id"]=item["param_id"];
                                    var realPath=item.catch_id.replace(/(_0)+$/,'');
                                    item["realPath"]=realPath;
                                }
                                $scope.data.catch[item.catch_id]=item;
                        })

                        _.each($scope.data.provinceList, function (province) {
                            _.each(province.cityList, function (item) {
                                if(item.checked){
                                    console.log("itemitem之前选中的省份",item);
                                    item.paramType="city";
                                    item.cityId=item.id;
                                    item.provinceId=item.pid;
                                    if(item.catch_id.indexOf("undefined")>=0){
                                        item.catch_id=item.param_id;
                                    }
                                    if(item.realPath.indexOf("undefined")>=0){
                                        var realPath=item.catch_id.replace(/(_0)+$/,'');
                                        item["realPath"]=realPath;
                                    }
                                    if(item.realPath.indexOf("_0_0_0_0_0_0")>=0){
                                        var realPath=item.catch_id.replace(/(_0)+$/,'');
                                        item["realPath"]=realPath;
                                    }
                                    if((item.catch_id+"").indexOf("_")<0){
                                        item["param_id"]=(item.provinceId||0)+"_"+(item.cityId||0)+"_0_0_0";
                                        item["catch_id"]=item["param_id"];
                                        var realPath=item.catch_id.replace(/(_0)+$/,'');
                                        item["realPath"]=realPath;
                                    }
                                    $scope.data.catch[item.catch_id]=item;
                                }
                            })
                        })
                        _.each(_.values($scope.data.catch),function(item){if(item.paramType!="city"){delete $scope.data.catch[item.catch_id]}else if(item.paramType=="city"){
                            if(item.realPath.indexOf("_0_0_0_0_0_0")>=0){
                                delete $scope.data.catch[item.param_id];
                            }
                            _.each($scope.data.provinceList, function (province) {
                                _.each(province.cityList, function (city) {
                                    if(item.cityId==city.cityId){
                                        console.log("itemitem之前选中的省份",item);
                                        city.checked=true;
                                    }
                                })
                            })
                        }});
                    }

                    if(newValue==3){
                        if(_.keys($scope.data.stateCatch[newValue]).length){
                            $scope.data.catch=angular.copy($scope.data.stateCatch[newValue])||{};
                        }
                        _.each(_.values($scope.data.catch), function (item) {
                            if(item.paramType=="province"||item.paramType=="city"){
                                delete $scope.data.catch[item.catch_id];
                            }
                        })
                        console.log($scope.data.stateCatch[oldValue],$scope.data.catch)
                    }
                });
                $scope.$watch("data.queryAreaParam.cityId", function (newValue, oldValue) {
                    if(!newValue){
                         $scope.data.queryArea.areaList=[];
                         $scope.data.queryArea.districtList=[];
                         $scope.data.queryArea.circleList=[];
                        return;
                    }
                    $scope.data.queryAreaParam.districtId="";
                    $scope.data.queryAreaParam.circleId="";
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
                        console.log(error);
                    })
                });
                $scope.$watch("data.queryAreaParam.districtId", function (newValue, oldValue) {
                    $scope.data.queryAreaParam.circleId="";
                    if(!$scope.data.queryAreaParam.provinceId||!$scope.data.queryAreaParam.cityId){
                        return;
                    }
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


                $scope.checkHelp={
                    check: function (param) {
                        var catchValues=_.values($scope.data.catch);
                        if(catchValues&&param){
                            var leaveMap={
                                province:1,
                                city:2,
                                district:3,
                                circle:4,
                                community:5
                            };
                            /*检查小区是否被包含*/
                            var checkResult= _.filter(catchValues, function (item,seq,arr) {
                                /*param 小区 为参数*/
                                if(param.paramType=="community"){
                                    if(item.paramType=="province"||item.paramType=="city"||item.paramType=="district"||item.paramType=="community"){
                                        if(param.realPath.indexOf(item.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                            return param;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                /*小区被无区环包含不添加小区*/
                                                return param;
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if(param.realPath.indexOf(item.realPath)==0){
                                                /*小区被省市区包含不添加小区*/
                                                return param;
                                            }
                                        }
                                    }
                                }
                                /*param 有区环 为参数*/
                                if(param.paramType=="circle"&&param.districtId!=0){
                                    if(item.paramType=="province"||item.paramType=="city"||item.paramType=="district"){
                                        if(param.realPath.indexOf(item.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                /*小区被无区环包含不添加小区*/
                                                return param;
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==param.districtId&&item.districtId!=0){
                                            if(param.ringId<=item.ringId){
                                                /*小区被省市区包含不添加小区*/
                                                return param;
                                            }else{

                                            }
                                        }
                                    }
                                }

                                /*param 无区环 为参数*/
                                if(param.paramType=="circle"&&param.districtId==0){
                                    if(item.paramType=="province"||item.paramType=="city"){
                                        if(param.realPath.indexOf(item.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                            return param;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(param.ringId<=item.ringId){
                                                return param;
                                            }else{
                                                /*被无区环包含不添加小区*/
                                            }
                                        }else if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId!=0){
                                            if(param.ringId<item.ringId){
                                                /*小区被省市区包含不添加小区*/
                                                return param;
                                            }else if(param.ringId>=item.ringId){
                                                /*小区被省市区包含不添加小区*/
                                            }
                                        }
                                    }
                                }


                                /*param 区 为参数*/
                                if(param.paramType=="district"){
                                    if(item.paramType=="province"||item.paramType=="city"){
                                        if(param.realPath.indexOf(item.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                            return param;
                                        }
                                    }
                                    if(item.paramType=="circle"){
                                        if(item.provinceId==param.provinceId&&item.cityId==param.cityId&&item.districtId==0){
                                            if(item.ringId>=param.ringId){
                                                return param;
                                            }
                                        }
                                    }
                                }

                                /*param 城市 为参数*/
                                if(param.paramType=="city"){
                                    if(item.paramType=="province"){
                                        if(param.realPath.indexOf(item.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                            return item;
                                        }
                                    }
                                    if(item.paramType=="district"||item.paramType=="circle"||item.paramType=="community"){
                                        if(item.realPath.indexOf(param.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
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
                                            }
                                        })
                                    }
                                    if(item.paramType=="city"||item.paramType=="district"||item.paramType=="circle"||item.paramType=="community"){
                                        if(item.realPath.indexOf(param.realPath)==0){
                                            /*小区被省市区包含不添加小区*/
                                        }
                                    }
                                }

                            });
                            return checkResult;
                        }
                    },
                    checkAndRestoreErrorDelete: function () {
                        /*检查错误删除 自动恢复  范围由 小-->大-->删除 删除绕行  发现此种情景 直接允许删除大范围 静默恢复小范围  提示过多容易造成使用抓狂*/
                        var editMinRange=angular.copy($scope.disabledParam);
                        _.each(_.values(editMinRange), function (minRange) {
                            var checkResult=$scope.checkHelp.check(minRange);
                            if(!checkResult.length){
                                console.log("小范围被绕行删除 需要恢复 checkResultcheckResult",checkResult,"被恢复对象为:",minRange);
                                $scope.data.catch[minRange.catch_id]=minRange;

                                //  console.log("新建的对象:",$scope.data.catch[minRange.catch_id])
                            }
                        })
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
                    }
                }
            }
        }
    });

