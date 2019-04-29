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
    })  
  }
})
