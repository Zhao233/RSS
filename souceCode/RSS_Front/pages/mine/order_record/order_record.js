//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},

    screenHeight : 0,
    screenWidth : 0,

    orderList : [
      { status: "已完成", foodList: [{ picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }], date: '2017-4-27 14-20-30', account_discount: "5", account_final:"125" },
      { status: "已完成", foodList: [{ picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }], date: '2017-4-27 14-20-30', account_discount: "5", account_final: "125" },
      { status: "已完成", foodList: [{ picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }], date: '2017-4-27 14-20-30', account_discount: "5", account_final: "125" },
      { status: "已完成", foodList: [{ picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }], date: '2017-4-27 14-20-30', account_discount: "5", account_final: "125" },
      { status: "已完成", foodList: [{ picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }, { picURL: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg" }], date: '2017-4-27 14-20-30', account_discount: "5", account_final: "125" }
    ],

    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  onLoad: function () {
    var that = this;

    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          screenHeight: res.windowHeight,
          screenWidth: res.windowWidth
        })

      },
    });

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/orderRecord/getOrderRecord",

      header: {
        'content-type': 'application/json' // 默认值
      },

      data : {
        openid : app.globalData.userInfo.openid
      },

      success(res) {
        app.internetResponseHandler(res);

        var temp_orderList = res.data.orderList;

        for(var x in temp_orderList){
          var temp_orderRecord = temp_orderList[x];

          switch(temp_orderRecord.status){
            case 0:
              //已完成
              temp_orderList[x].status = '已完成';

              break;
            case 1:
              //超时
              temp_orderList[x].status = '已超时';

              break;
            case 2:
              //未支付
              temp_orderList[x].status = '未支付';

              break;
          }
        }

        that.setData({
          orderList: temp_orderList
        })

        console.log(res);
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  }
})