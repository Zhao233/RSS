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
    var that = this;

    wx.request({
      url: "http://" + app.info.hostname + ':' + app.info.port + '/weapp/login',
      data: {
        "openid": app.globalData.userInfo.openid
      },

      success: res => {
        app.showToast("登录成功");

        that.toCustomerOrder();

        console.log(res);
      },
      fail: res => {
        console.log(res);
      }
    })
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
      success:  res => {
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
    var that = this;

    wx.request({
      url: "http://" + app.info.hostname + ':' + app.info.port + '/weapp/loginCheck',
      data: {
        "openid": openid
      },

      success: res => {
        app.internetResponseHandler(res, function(){
          var identity = res.data.identity;

          switch(identity){
            case 1 : //服务员
              that.toWaiterWorkingBench();

              break;
            case 2 : //厨师
              that.toWaiterWorkingBench();
              
              break;
            case 3: //客户
              that.toCustomerOrder();

              break;
            default:
              app.showToast("身份错误,请重新打开微信小程序"); 
          }

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

  },

  toWaiterWorkingBench(){
    wx.redirectTo({
      url: '/pages/forWaiterService/waiter',
    })
  },

  toCustomerOrder(){
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  toCookerWoringBench(){}
})
