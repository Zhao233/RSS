//index.js
//获取应用实例
const app = getApp()

var url_pic = 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg';

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    result: [],

    food_cart : [
      {
        food_id : 0,
        name:'test1',
        desc:'desc desc',
        pic_url: url_pic,
        price:20,
        num:1
      },
      {
        food_id: 1,
        name: 'test2',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 2,
        name: 'test3',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 3,
        name: 'test4',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 4,
        name: 'test5',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 5,
        name: 'test6',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 6,
        name: 'test7',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
      {
        food_id: 7,
        name: 'test8',
        desc: 'desc desc',
        pic_url: url_pic,
        price: 20,
        num: 1
      },
    ]
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
  },

  onChange(event) {
    console.log(event.detail);

    this.setData({
      result: event.detail
    });
  }

})
