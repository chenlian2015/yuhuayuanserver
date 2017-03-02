angular.module("Pager", []).directive("myPager", function () {
    return {
        restrict: "ECMA",
        template: '<ul><li ng-repeat="page in pages" ng-if="page.pageNum>=1&&page.pageNum<=pageData.totalPages&&page.isShow"><div ng-if="page.isCurrent" style="background: #39aa8f;cursor: not-allowed;color:#fff;border-color:#39aa8f;">{{page.label}}</div><div ng-if="!page.isCurrent&&page.clickAble" ng-click="pageClick()(this,page)">{{page.label}}</div><div ng-if="!page.isCurrent&&!page.clickAble" style="cursor: not-allowed">{{page.label}}</div></li><li ><div>每页{{pageData.rowsPerPage}}条</div></li><li ><div>共{{pageData.totalPages}}页  共{{pageData.totalRows}}条</div></li></ul>',
        replace: false,
        scope: {
            pageData: "=",
            showTotalNumber: '=',
            pageClick: '&'
        },
        compile: function (tElement, tAttrs, transclude) {
            return {
                pre: function ($scope, iElement, iAttrs) {

                },
                post: function ($scope, iElement, iAttrs) {
                    $scope.$watch("pageData", function (newValue, oldValue) {
                        var isPushPreShopFlag = false, isPushNextShopFlag = false;
                        $scope.pageData = $scope.pageData || {
                                totalRows: 0,
                                totalPages: 0,
                                rowsPerPage: 15,
                                currentPage: 1
                            };
                        var totalRows = $scope.pageData.totalRows, totalPages = $scope.pageData.totalPages, rowsPerPage = $scope.pageData.rowsPerPage, currentPage = $scope.pageData.currentPage;
                        /*总共显示个数*/
                        var showTotalNumber = $scope.showTotalNumber;
                        var pages = [];
                        if (totalPages > 1) {
                            var firstPage = {
                                pageNum: 1,
                                label: '首页',
                                clickAble: true,
                                isShow: currentPage > 1
                            };
                            var prePage = {
                                pageNum: ($scope.pageData.currentPage - 1),
                                clickAble: true,
                                label: '上一页',
                                isShow: currentPage > 1
                            };
                            pages.push(firstPage);
                            pages.push(prePage);
                        }
                        for (var i = 1; i <= totalPages; i++) {
                            if (totalPages > showTotalNumber) {
                                if (i == 1) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }

                                if (i < currentPage - (showTotalNumber - 1) / 2 && i > 1) {/*当前页前 总数一半隐藏*/
                                    if (!isPushPreShopFlag) {
                                        pages.push({
                                            pageNum: i,
                                            label: "...",
                                            isCurrent: false,
                                            clickAble: false,
                                            isShow: true
                                        });
                                        isPushPreShopFlag = !isPushPreShopFlag;
                                    }
                                } else if (i > (currentPage + (showTotalNumber - 1) / 2)) {
                                    if (!isPushNextShopFlag) {
                                        pages.push({
                                            pageNum: i,
                                            label: "...",
                                            isCurrent: false,
                                            clickAble: false,
                                            isShow: true
                                        });
                                        isPushNextShopFlag = !isPushNextShopFlag;
                                    }
                                } else if (i != totalPages && i != 1) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }
                                if (i == totalPages) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }
                            } else {
                                pages.push({
                                    pageNum: i,
                                    label: i,
                                    isCurrent: i == currentPage,
                                    clickAble: true,
                                    isShow: true
                                })
                            }
                        }
                        if (totalPages > 1) {
                            var lastPage = {
                                pageNum: totalPages,
                                label: '尾页',
                                clickAble: true,
                                isShow: currentPage < totalPages
                            };
                            var nextPage = {
                                pageNum: ($scope.pageData.currentPage + 1),
                                label: '下一页',
                                clickAble: true,
                                isShow: currentPage < totalPages
                            };
                            pages.push(nextPage);
                            pages.push(lastPage);
                        }
                        $scope.pages = pages;
                    })


                }
            }
        }
    }
}).directive("myPagerComponent", function () {
    return {
        restrict: "ECMA",
        template: '<ul><li ng-repeat="page in pages" ng-if="page.pageNum>=1&&page.pageNum<=pageData.totalPages&&page.isShow"><div ng-if="page.isCurrent" style="background: #39aa8f;cursor: not-allowed;color:#fff;border-color:#39aa8f;">{{page.label}}</div><div ng-if="!page.isCurrent&&page.clickAble" ng-click="pageClick()(this,page)">{{page.label}}</div><div ng-if="!page.isCurrent&&!page.clickAble" style="cursor: not-allowed">{{page.label}}</div></li><li ><div>每页{{pageData.rowsPerPage}}条</div></li><li ><div>共{{pageData.totalPages}}页  共{{pageData.totalRows}}条</div></li></ul>',
        replace: false,
        scope: {
            pageData: "=",
            showTotalNumber: '=',
            pageClick: '&'
        },
        compile: function (tElement, tAttrs, transclude) {
            return {
                pre: function ($scope, iElement, iAttrs) {

                },
                post: function ($scope, iElement, iAttrs) {
                    $scope.$watch("pageData", function (newValue, oldValue) {
                        var isPushPreShopFlag = false, isPushNextShopFlag = false;
                        $scope.pageData = $scope.pageData || {
                                totalRows: 0,
                                totalPages: 0,
                                rowsPerPage: 15,
                                currentPage: 1
                            };
                        var totalRows = $scope.pageData.totalRows, totalPages = $scope.pageData.totalPages, rowsPerPage = $scope.pageData.rowsPerPage, currentPage = $scope.pageData.currentPage;
                        /*总共显示个数*/
                        var showTotalNumber = $scope.showTotalNumber;
                        var pages = [];
                        if (totalPages > 1) {
                            var firstPage = {
                                pageNum: 1,
                                label: '首页',
                                clickAble: true,
                                isShow: currentPage > 1
                            };
                            var prePage = {
                                pageNum: ($scope.pageData.currentPage - 1),
                                clickAble: true,
                                label: '上一页',
                                isShow: currentPage > 1
                            };
                            pages.push(firstPage);
                            pages.push(prePage);
                        }
                        for (var i = 1; i <= totalPages; i++) {
                            if (totalPages > showTotalNumber) {
                                if (i == 1) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }

                                if (i < currentPage - (showTotalNumber - 1) / 2 && i > 1) {/*当前页前 总数一半隐藏*/
                                    if (!isPushPreShopFlag) {
                                        pages.push({
                                            pageNum: i,
                                            label: "...",
                                            isCurrent: false,
                                            clickAble: false,
                                            isShow: true
                                        });
                                        isPushPreShopFlag = !isPushPreShopFlag;
                                    }
                                } else if (i > (currentPage + (showTotalNumber - 1) / 2)) {
                                    if (!isPushNextShopFlag) {
                                        pages.push({
                                            pageNum: i,
                                            label: "...",
                                            isCurrent: false,
                                            clickAble: false,
                                            isShow: true
                                        });
                                        isPushNextShopFlag = !isPushNextShopFlag;
                                    }
                                } else if (i != totalPages && i != 1) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }
                                if (i == totalPages) {
                                    pages.push({
                                        pageNum: i,
                                        label: i,
                                        isCurrent: i == currentPage,
                                        clickAble: true,
                                        isShow: true
                                    })
                                }
                            } else {
                                pages.push({
                                    pageNum: i,
                                    label: i,
                                    isCurrent: i == currentPage,
                                    clickAble: true,
                                    isShow: true
                                })
                            }
                        }
                        if (totalPages > 1) {
                            var lastPage = {
                                pageNum: totalPages,
                                label: '尾页',
                                clickAble: true,
                                isShow: currentPage < totalPages
                            };
                            var nextPage = {
                                pageNum: ($scope.pageData.currentPage + 1),
                                label: '下一页',
                                clickAble: true,
                                isShow: currentPage < totalPages
                            };
                            pages.push(nextPage);
                            pages.push(lastPage);
                        }
                        $scope.pages = pages;
                    })
                }
            }
        }
    }
});