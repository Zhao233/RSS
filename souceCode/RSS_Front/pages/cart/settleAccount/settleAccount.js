//index.js
//获取应用实例
const app = getApp()

var url_pic = 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg';

Page({
  data: {
    motto: 'Hello World',
    account: 0,
    account_discount : 0,
    screenHeight: 0,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    usedDiscountID : 90000, 
    result: [],

    discount: [

    ],

    food_cart : []
  },

  useDiscount : function(e) {
    var that = this;

    var discount_id = e.target.dataset.discountid;
    var discount = e.target.dataset.discount;

    var discount_account = this.data.account * discount;
    discount_account = discount_account.toFixed(2);

    that.setData({
      usedDiscountID : discount_id,
      account_discount : discount_account
    })


  },

  onLoad : function(e) {
    var account = e.account;
    var that = this;

    this.setData({
      account : account,
      account_discount : account
    })

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/cart/getDiscountList",

      header: {
        'content-type': 'application/json' // 默认值
      },
      data : {
        "account" : account
      },

      success(res) {
        if (res.data.status == "SUCCEED") {
        } else {
          app.showToast("网络请求失败");
          return;
        }

        var data_discount = res.data.discountList;

        that.setData({  
          discount : data_discount
        })
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })

    console.log(account);
  },
  
  onShow: function () {
    var that = this;

    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          screenHeight : res.windowHeight-110
        })
      }
    });

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

  onSubmit(){
      var foodIDList = new Array;
      var foodNumList = new Array;
      var styleIDList = new Array;
      var openid = app.globalData.userInfo.openid;
      var account = this.data.account;
      var discountID = this.data.usedDiscountID;

      var expirationTime = new Date;
      expirationTime.setMinutes(expirationTime.getMinutes() + 5);

      for( var x of app.globalData.cartListRecord){
        foodIDList.push( x[0] );
        foodNumList.push( x[1].num )
      }

      wx.request({
        url: "http://" + app.info.hostname + ":" + app.info.port +"/customer/cart/onSubmit",
  
        header: {
          'content-type': 'application/json' // 默认值
        },

        data: {
          "foodIDList" : foodIDList,
          "foodNumList": foodNumList,
          "styleIDList" : styleIDList,
          "discountID": discountID,
          "openID" : openid,
          "account" : account,
          "expirationTime" : expirationTime.getTime()
        },
  
        success(res) {
          if(res.data.status == "SUCCEED"){
          } else {
            app.showToast("网络请求失败");
            return ;
          }
  
          console.log(res);
  
          var temp_classfiyList = new Array;
  
          temp_classfiyList.push({ id: "", name: "推荐" });
  
          for (var x in res.data.menuList) {
            var rawData = res.data.menuList[x];
  
            var item = { id: rawData.id, name: rawData.name }
  
            temp_classfiyList.push(item);
          }
  
          that.setData({
            classfiyList: temp_classfiyList
          })
  
          console.log(res.data)
        },
        fail(res) {
          app.showToast("网络请求失败")
        }
      })
  },

  onChange(event) {
    console.log(event.detail);

    this.setData({
      result: event.detail
    });
  }

})