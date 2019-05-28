//app.js
App({
  globalData: {
    userInfo: null,

    cartListRecord: new Map,//购物车中的菜品

    userOpenId: 0,

    tableNum : 0,

    quickService_foodIDList: [],
    quickService_styleIDList: [],
  },

  info:{
    hostname:"140.143.56.249",
    port:8881,
  },

  // info: {
  //   hostname: "localhost",
  //   port: 8881,
  // },

  onLaunch: function (options) {
    var that = this;

    var tableNum = options.query.tableNum;

    if(tableNum == undefined){
      this.showToast("请扫描桌上二维码");
    } else {
      this.globalData.tableNum = tableNum;
    }

    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    wx.reLaunch({
      url: '/pages/login/login',
    })

    // 登录
    // wx.login({
    //   success(res) {
    //     if (res.code) {
    //       // 发起网络请求
    //       wx.request({
    //         url: "http://" + that.info.hostname + ':' + that.info.port +'/customer/login',
    //         data: {
    //           "code" : res.code
    //         },

    //         success: res => {
    //           that.globalData.userInfo = res.data.userInfo;

    //           wx.reLaunch({
    //             url: '/pages/forWaiterService/waiter',
    //           })

    //           console.log(res);
    //         },
    //         fail : res => {
    //           console.log(res);
    //         }
    //       })
    //     } else {
    //       console.log('登录失败！' + res.errMsg)
    //     }
    //   }
    // })

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
  },

  internetResponseHandler : function (res, succeed, error, failed) {
    switch(res.data.status){
      case "SUCCEED" :
        succeed();

        break;
      case "ERROR" : 
        var message = "服务器错误";

        if(res.data.message != undefined){
          this.showToast(message + "  " + res.data.message)
        }

        break;

      case "FAILED":
        failed();

        if (res.data.message != undefined) {
          
        }
    }
  },

  socketResponseHandler : function(res, succeed, error, failed){
    switch (res.status) {
      case "SUCCEED":
        succeed();

        break;
      case "ERROR":
        var message = "服务器错误";

        if (res.message != undefined) {
          this.showToast(message + "  " + res.data.message)
        }

        break;

      case "FAILED":
        if (res.message != undefined) {
          this.showToast(res.data.message)
        }
    }
  }
})