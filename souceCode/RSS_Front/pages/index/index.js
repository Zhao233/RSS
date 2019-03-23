//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    classfiyList: [
      { id: 1, name: "热煮", idenity_name: "food_hot" },
      { id: 2, name: "精品凉菜", idenity_name: "food_cool" },
      { id: 3, name: "下酒菜", idenity_name: "food_auxiliary" },
      { id: 4, name: "特色菜", idenity_name: "food_feature" },
      { id: 5, name: "汤", idenity_name: "food_soup" },
      { id: 6, name: "火锅", idenity_name: "food_hotPot" },
      { id: 7, name: "点心", idenity_name: "food_dessert" },
      { id: 8, name: "果汁", idenity_name: "food_juice" },
      { id: 9, name: "山珍海味", idenity_name: "food_treasure" },
    ],
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },

  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
  
})
