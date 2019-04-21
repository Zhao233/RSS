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
    ],
    food_accounts:0
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

  getAccounts : function(){
    var account = 0;

    for (var x of app.globalData.cartListRecord) {
      var temp_item = x[1];

      account+=(temp_item.price*temp_item.num);
    }

    account*=100;

    this.setData({
      food_accounts : account
    })
  }, 

  updateCartData : function(id, index, data){
    app.globalData.cartListRecord.set(id, data);//将全局的数据也一并更新

    this.setData({
      ["food_cart[" + index + "]"]: data
    });
  },

  getOneFromFoodCartList(id){
    for (var x in this.data.food_cart) {
      var temp = this.data.food_cart[x];

      if (temp.id == id){
        return {
          "index" : x,
          "data" : temp
        };
      }
    }
  },

  addOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var temp = this.getOneFromFoodCartList(id);

    var index = temp.index;
    var item_cart = temp.data;

    item_cart.num++;

    this.updateCartData(id, index, item_cart);
    this.getAccounts();
  }, 

  removeOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var item_cart = null;
    var index;

    for(var x in this.data.food_cart){
      var temp = this.data.food_cart[x];

      if (temp.id == id){
        

      }
    }

    console.log(id);
  }, 

  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    
  },

  onShow : function () {
    this.getCartList();
    this.getAccounts();
  },

  onChange(event) {
    console.log(event.detail);

    this.setData({
      result: event.detail
    });
  }

  

})