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
      // {
      //   food_id : 0,
      //   name:'test1',
      //   desc:'desc desc',
      //   pic_url: url_pic,
      //   price:20,
      //   num:1
      // },
      // {
      //   food_id: 1,
      //   name: 'test2',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 2,
      //   name: 'test3',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 3,
      //   name: 'test4',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 4,
      //   name: 'test5',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 5,
      //   name: 'test6',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 6,
      //   name: 'test7',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
      // {
      //   food_id: 7,
      //   name: 'test8',
      //   desc: 'desc desc',
      //   pic_url: url_pic,
      //   price: 20,
      //   num: 1
      // },
    ]
  },

  getCartList: function () {//获取购物车卡片列表
    var temp_food_cart = new Array;

    for (var x of app.globalData.cartListRecord) {
      var item = app.globalData.cartListRecord.get(x[0]);

      temp_food_cart.push(item);
    }

    this.setData({
      food_cart: temp_food_cart
    });
  },

  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    this.getCartList();
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