/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */

var timeout = 3000;

(function ($) {
    "use strict";

    var mainApp = {

        initFunction: function () {
            /*MENU 
            ------------------------------------*/
            $('#main-menu').metisMenu();

            $(window).bind("load resize", function () {
                if ($(this).width() < 768) {
                    $('div.sidebar-collapse').addClass('collapse')
                } else {
                    $('div.sidebar-collapse').removeClass('collapse')
                }
            });

            /*2018-6-28 编辑trace——info 时修改，测试页面：employee， trace_info*/
            // /* MORRIS BAR CHART
            // -----------------------------------------*/
            // Morris.Bar({
            //     element: 'morris-bar-chart',
            //     data: [{
            //         y: '2006',
            //         a: 100,
            //         b: 90
            //     }, {
            //         y: '2007',
            //         a: 75,
            //         b: 65
            //     }, {
            //         y: '2008',
            //         a: 50,
            //         b: 40
            //     }, {
            //         y: '2009',
            //         a: 75,
            //         b: 65
            //     }, {
            //         y: '2010',
            //         a: 50,
            //         b: 40
            //     }, {
            //         y: '2011',
            //         a: 75,
            //         b: 65
            //     }, {
            //         y: '2012',
            //         a: 100,
            //         b: 90
            //     }],
            //     xkey: 'y',
            //     ykeys: ['a', 'b'],
            //     labels: ['Series A', 'Series B'],
            //     barColors: [
            //         '#A6A6A6', '#24C2CE',
            //         '#A8E9DC'
            //     ],
            //     hideHover: 'auto',
            //     resize: true
            // });
            //
            //
            // /* MORRIS DONUT CHART
            // ----------------------------------------*/
            // Morris.Donut({
            //     element: 'morris-donut-chart',
            //     data: [{
            //         label: "Download Sales",
            //         value: 12
            //     }, {
            //         label: "In-Store Sales",
            //         value: 30
            //     }, {
            //         label: "Mail-Order Sales",
            //         value: 20
            //     }],
            //     colors: [
            //         '#A6A6A6', '#24C2CE',
            //         '#A8E9DC'
            //     ],
            //     resize: true
            // });
            //
            // /* MORRIS AREA CHART
            // ----------------------------------------*/
            //
            // Morris.Area({
            //     element: 'morris-area-chart',
            //     data: [{
            //         period: '2010 Q1',
            //         iphone: 2666,
            //         ipad: null,
            //         itouch: 2647
            //     }, {
            //         period: '2010 Q2',
            //         iphone: 2778,
            //         ipad: 2294,
            //         itouch: 2441
            //     }, {
            //         period: '2010 Q3',
            //         iphone: 4912,
            //         ipad: 1969,
            //         itouch: 2501
            //     }, {
            //         period: '2010 Q4',
            //         iphone: 3767,
            //         ipad: 3597,
            //         itouch: 5689
            //     }, {
            //         period: '2011 Q1',
            //         iphone: 6810,
            //         ipad: 1914,
            //         itouch: 2293
            //     }, {
            //         period: '2011 Q2',
            //         iphone: 5670,
            //         ipad: 4293,
            //         itouch: 1881
            //     }, {
            //         period: '2011 Q3',
            //         iphone: 4820,
            //         ipad: 3795,
            //         itouch: 1588
            //     }, {
            //         period: '2011 Q4',
            //         iphone: 15073,
            //         ipad: 5967,
            //         itouch: 5175
            //     }, {
            //         period: '2012 Q1',
            //         iphone: 10687,
            //         ipad: 4460,
            //         itouch: 2028
            //     }, {
            //         period: '2012 Q2',
            //         iphone: 8432,
            //         ipad: 5713,
            //         itouch: 1791
            //     }],
            //     xkey: 'period',
            //     ykeys: ['iphone', 'ipad', 'itouch'],
            //     labels: ['iPhone', 'iPad', 'iPod Touch'],
            //     pointSize: 2,
            //     hideHover: 'auto',
            //     pointFillColors: ['#ffffff'],
            //     pointStrokeColors: ['black'],
            //     lineColors: ['#A6A6A6', '#24C2CE'],
            //     resize: true
            // });
            //
            // /* MORRIS LINE CHART
            // ----------------------------------------*/
            // Morris.Line({
            //     element: 'morris-line-chart',
            //     data: [
            //         {y: '2014', a: 50, b: 90},
            //         {y: '2015', a: 165, b: 185},
            //         {y: '2016', a: 150, b: 130},
            //         {y: '2017', a: 175, b: 160},
            //         {y: '2018', a: 80, b: 65},
            //         {y: '2019', a: 90, b: 70},
            //         {y: '2020', a: 100, b: 125},
            //         {y: '2021', a: 155, b: 175},
            //         {y: '2022', a: 80, b: 85},
            //         {y: '2023', a: 145, b: 155},
            //         {y: '2024', a: 160, b: 195}
            //     ],
            //
            //
            //     xkey: 'y',
            //     ykeys: ['a', 'b'],
            //     labels: ['Total Income', 'Total Outcome'],
            //     fillOpacity: 0.6,
            //     hideHover: 'auto',
            //     behaveLikeLine: true,
            //     resize: true,
            //     pointFillColors: ['#ffffff'],
            //     pointStrokeColors: ['black'],
            //     lineColors: ['gray', '#24C2CE']
            //
            // });


            // $('.bar-chart').cssCharts({type: "bar"});
            // $('.donut-chart').cssCharts({type: "donut"}).trigger('show-donut-chart');
            // $('.line-chart').cssCharts({type: "line"});
            //
            // $('.pie-thychart').cssCharts({type: "pie"});


        },

        initialization: function () {
            mainApp.initFunction();

        }

    }
    // Initializing ///

    $(document).ready(function () {
        mainApp.initFunction();
        $("#sideNav").click(function () {
            if ($(this).hasClass('closed')) {
                $('.navbar-side').animate({left: '0px'});
                $(this).removeClass('closed');
                $('#page-wrapper').animate({'margin-left': '260px'});

            }
            else {
                $(this).addClass('closed');
                $('.navbar-side').animate({left: '-260px'});
                $('#page-wrapper').animate({'margin-left': '0px'});
            }
        });

        $("a").mouseover(function(){
            $(this).css("background-color","#175191");
            //$(this).css("opacity","0.5");
        });

        $("a").mouseout(function(){
            $(this).css("background-color","transparent");
            //$(this).css("opacity","1");
        });

        getAdminInfo();
    });

}(jQuery));

function adjustTableHeight(Object, adjustment){
   /* var windowHeight = $(window).height();
    var topNavbarHeight = $(".top-navbar").height();
    var wrapperHeight = $("#wrapper").height();

    console.log("windowHeight: "+windowHeight);
    console.log("topNavbarHeight: "+topNavbarHeight);
    console.log("wapperHeight: "+wrapperHeight);

    if(wrapperHeight == windowHeight){//等长
        return ;
    }
    console.log("before: " + Object.height());

    Object.height( Object.height() - (wrapperHeight - windowHeight) - topNavbarHeight - adjustment);

    console.log("after: " + Object.height());*/
};

function adjustHeight(Object, adjustment){
    var windowHeight = $(window).height();
    var topNavbarHeight = $(".top-navbar").height();

    console.log("windowHeight: "+windowHeight);
    console.log("topNavbarHeight: "+topNavbarHeight);

    var distanceOfObjectFromTheButtom = windowHeight - ( Object.height()+Object.offset().top );

    Object.height( Object.height() + distanceOfObjectFromTheButtom - topNavbarHeight);

}

function getAdminInfo(){
    //get the admin info
        // $.ajax({
        //     type: "get",
        //     url: "/console/user_management/getAdminInfo",
        //     dataType: "json",
        //     timeout : timeout,
        //     success : function(json) {
        //         switch (json.status){
        //             case "SUCCEED" :
        //                 $("#adminName").append(json.adminName);
        //                 $("#adminEmail").append(json.adminEmail);
        //                 console.log("管理员姓名 ： "+json.adminName);
        //                 console.log("管理员邮箱 ： "+json.adminEmail);

        //                 break;
        //             case "FAILED" :
        //                 $("#dialog-return").find(".modal-body").html("获取管理员信息失败");
        //                 $("#dialog-return").modal("show");

        //                 break;
        //         }


        //     },
        //     error : function () {
        //         $("#dialog-return").find(".modal-body").html("获取管理员信息失败---网络错误");
    //         $("#dialog-return").modal("show");
    //     }
    // });
};

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function getToDayDateTime() {
    var oDate = new Date();
    var date = oDate.getFullYear() + '-'+ (parseInt(oDate.getMonth())+1) +'-'+oDate.getDate();

    var todayDateTime = date+" 00:00:00";

    return todayDateTime;
}

function getToDayDateTime_00_00() {
    var date = new Date();
    var dateTime = date.getFullYear()+"-"+parseInt( date.getMonth() )+1+"-"+date.getDate();


    dateTime += " "+"00";
    dateTime += ":"+"00";
    dateTime += ":"+"00";

    return dateTime;
}

//2018-10-23T13:51:45.000+0000
function getFormattedTime(time){
    if(time == undefined){
        return "-";
    }
    
    time = time.split("T");

    return time[0]+" "+ ( time[1].split(".")[0] )
}



function getPrimaryMenuNumber(secondaryMenuName){
    switch (secondaryMenuName){
        case "index" :
            return {primaryMenuNumber : 0,
                secondaryMenu : 0};

        case "activityManagement" :
            return {primaryMenuNumber : 0,
                    secondaryMenu : 1};


        case "userManagement" :
            return {primaryMenuNumber : 1,
                    secondaryMenu : 0};

        case "waiterManagement" :
            return {primaryMenuNumber : 1,
                    secondaryMenu : 1};

        case "cookerManagement" :
            return {primaryMenuNumber : 1,
                    secondaryMenu : 2};






        case "menuManagement" :
            return {primaryMenuNumber : 2,
                    secondaryMenu : 0};
        case "foodManagement" :
            return {primaryMenuNumber : 2,
                    secondaryMenu : 1};




    }
}

function loadMenu(){
    $.ajax({
        url : '/admin/getMenu',
        type : 'GET',
        dataType : 'html',
        async:false,
        success : function(res){
            $("#nave_side").append( $(res).find('nav') );
        },

        fail : function (res) {

        },

        timeout : function (res) {

        }
    });

    //获取确认当前界面
    var url = window.location.pathname;

    var paths = url.split("/");
    var presentPath = paths[paths.length-1]

    //添加隐藏效果
    var primaryMenus = $("#nave_side").find('.primaryMenu');
    var menuNum = getPrimaryMenuNumber(presentPath);

    var primaryMenu = primaryMenus.get(menuNum.primaryMenuNumber);

    //展开一级菜单
    $(primaryMenu).addClass("active");
    $(primaryMenu).find("ul").removeAttr("style");


    var secondaryMenus = $(primaryMenu).find('li');
    var secondaryMenu = secondaryMenus.get(menuNum.secondaryMenu);

    $(secondaryMenu).addClass("active-menu-item");
}