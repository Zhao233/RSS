//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  toCartPage : () => {
    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/quickService/getFrequentlyUsedFoodList",

      header: {
        'content-type': 'application/json' // 默认值
      },

      data : {
        "userID": app.globalData.userInfo.openid
      },

      success(res) {
        if (res.data.status == "SUCCEED") {
        } else {
          app.showToast("网络请求失败");
          return;
        }
        
        var frequentlyUsedModel = res.data.FrequentlyUsedFood;

        var foodList = frequentlyUsedModel.foodList;
        var styleList = frequentlyUsedModel.styleList;
        var numList = frequentlyUsedModel.numList;

        for( var x in foodList ){
        
          var item = {
            "id": foodList[x].id,
            "picUrl": foodList[x].picUrl,
            "name": foodList[x].name,
            "price": foodList[x].price,
            "num": numList[x]
          };

          app.globalData.cartListRecord.set(foodList[x].id, item);
        }

        wx.switchTab({
          url: '/pages/cart/cart'
        })
  
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
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
