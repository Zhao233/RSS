//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '点击头像以登录',
    userInfo: null,
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  loginAsCustomer : function(e){

  },

  loginAsService : function(e){
    wx.navigateTo({
      url: 'servicePersonLogin/servicePersonLogin',
    })
  },

  onLoad: function () {
    var that = this;

    //获取头像
    wx.getUserInfo({
      success: res => {
        app.globalData.userInfo = res.userInfo
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    })

    //登录,获取用户信息
    wx.login({
      success(res) {
        if (res.code) {
          // 发起网络请求
          wx.request({
            url: "http://" + app.info.hostname + ':' + app.info.port +'/weapp/getLoginSession',
            data: {
              "code" : res.code
            },

            success: res => {
              app.globalData.userInfo = res.data.userInfo;

              that.checkLoginStatus(res.data.userInfo.openid);

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
  },


  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  checkLoginStatus(openid){
    wx.request({
      url: "http://" + app.info.hostname + ':' + app.info.port + '/weapp/loginCheck',
      data: {
        "openid": openid
      },

      success: res => {
        app.internetResponseHandler(res, function(e){
          console.log(res);
        })

        var identity = res.data.identity;

        console.log(res);
      },
      fail: res => {
        app.showToast("网络错误");
        console.log(res);
      }
    })

  }
})
