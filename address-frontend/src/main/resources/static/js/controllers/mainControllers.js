'use strict';
angular.module('app')
    .controller('mainController', function ($scope, addressService) {
    /* ********************************************************** */
        var mc = this;

        mc.uploadProgress = false;
        mc.close=true;
        mc.alert = {};
        mc.init = true;
        mc.keyword = "";
        mc.list = [];
        mc.addr1 ="";
        mc.addr2 ="";
        mc.addr3 ="";

        mc.pageOptions={
            currentPage : 1,
            pageSize : 15,
            total : 1
        };
    /* ********************************************************** */

        /**
         * 엔터 이벤트
         * @param $event
         * @param currentPage
         */
        mc.enterPress=function($event, currentPage){
            if($event.which === 13){
                mc.searchKeyword(currentPage);
            }
        };

        /**
         * 데이터 초기화
         */
        mc.reset = function(){
            mc.init=true;
            mc.keyword = "";
            mc.addr1 = "";
            mc.addr2 = "";
            mc.addr3 = "";
        };

        /**
         * 키워드 검색
         * @param currentPage
         */
        mc.searchKeyword =function(currentPage){
            $scope.$parent.loadingMain=true;
            mc.addr1 = "";
            mc.addr2 = "";
            mc.addr3 = "";

            if (angular.isDefined(currentPage) && currentPage != null) {
                mc.pageOptions.currentPage = currentPage;
            }
            var addressList = addressService.listByKeyword(currentPage, mc.pageOptions.pageSize, mc.keyword);
            addressList.success(function (data) {

                mc.init = false;
                mc.list = data;
                $scope.$parent.loadingMain=false;
                if( angular.isDefined(data.common) ){
                    mc.pageOptions.total = data.common.totalCount;
                    if(data.juso == null){
                        mc.close = false;
                        mc.alert = { msg: data.common.errorMessage};
                    }else{
                        mc.close =true;
                    }
                }
            }).error(function () {
                $scope.$parent.loadingMain=false;
                mc.init = false;
                mc.list = [];
            });
        };

        $scope.closeAlert = function() {
            mc.close =true;
        };

        /**
         * 선택된 값
         * @param data
         */
        mc.selected = function(data){
            mc.addr1 = data.zipNo;
            mc.addr2 = data.siNm + " "+ data.sggNm + " "+ data.rn + " "+ data.buldMnnm;
            mc.addr3 = "("+data.emdNm;
            if(data.bdNm !== ""){
                mc.addr3 += ", "+ data.bdNm + ")";
            }else{
                mc.addr3 += ")";
            }
        };

    });
