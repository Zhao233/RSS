//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    loginCode : '',

    user: null,
    userType:"",
    countDown : 3,
    popupShow : false,

    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  toServiceWorkBench : function(userType){
    switch(userType){
      case "waiter" : 
        wx.redirectTo({
          url: '/pages/forWaiterService/waiter',
        })
        break;
      case "cooker":
        wx.redirectTo({
          url: '/pages/forCookerService/cooker',
        })
        break;
    }
  },

  onOpenPopup : function(e){
    this.setData({
      popupShow: true
    })
  },

  onClosePopup : function(e){
    this.setData({
      popupShow: false
    })
  },

  inputLoginCode: function (e) {
    this.setData({
      loginCode: e.detail.value
    })
  },

  countDown: function () {
    let that = this;
    let countDownNum = that.data.countDown;

    that.setData({
      timer: setInterval(function () {
        countDownNum--;
        that.setData({
          countDown: countDownNum
        })
        if (countDownNum == 0) {
          that.onClosePopup();
          
          console.log("跳转至工作页面");

          that.toServiceWorkBench(that.data.userType);
        }
      }, 1000)
    })
  },



  loginWithLoginCode : function(res){
    var that = this;

    wx.request({
      url: 'http://'+app.info.hostname+':'+app.info.port+'/weapp/login',
      data: {
        "openid" : app.globalData.userInfo.openid,
        "loginID": this.data.loginCode
      },
      success : function(res){
        app.internetResponseHandler(res,function(){
          var user = res.data.userInfo;

          that.setData({
            user : user,
            userType : res.data.userType
          })

          that.onOpenPopup();
          that.countDown();
        })
      },
      fail : function(e){
        app.showToast("网络错误");
      }
    })
  },

  onLoad: function () {

  }
})
