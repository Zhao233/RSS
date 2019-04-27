//app.js
App({
  info:{
    hostname:"localhost",
    port:8881,

  },


  onLaunch: function () {
    var that = this;

    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success(res) {
        if (res.code) {
          // 发起网络请求
          wx.request({
            url: "http://" + that.info.hostname + ':' + that.info.port +'/customer/login',
            data: {
              "code" : res.code
            },

            success: res => {
              that.globalData.userInfo = res.data.userInfo;

              console.log(res);
            },
            fail : res => {
              console.log(res);
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
    
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo

    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })
  },
  globalData: {
    userInfo: {},

    cartListRecord: new Map,//购物车中的菜品

    userOpenId: 0,

    quickService_foodIDList: [],
    quickService_styleIDList: [],
  },

  //全局方法
  showToast : function (message) {
    wx.showToast({
      title: message,
      icon: 'none',   
      duration: 1000
    })
  },

  clearCartList : function(e){
    this.globalData.cartListRecord = new Map;
  }
})