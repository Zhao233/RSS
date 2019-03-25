//index.js
//获取应用实例
const app = getApp()

var url1 = "https://baike.baidu.com/pic/%E6%B6%AE%E7%BE%8A%E8%82%89/476036/0/3801213fb80e7bec3d233a53252eb9389a506b82?fr=lemma&ct=single#aid=0&pic=3801213fb80e7bec3d233a53252eb9389a506b82";

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    /*判断点击的侧栏目录是否为推荐或者其他一级菜单*/
    choose_status : 0,

    classfiyList: [//一级菜单
      { id: 1, name: "推荐", idenity_name: "recommend" },
      { id: 2, name: "精品凉菜", idenity_name: "food_cool" },
      { id: 3, name: "下酒菜", idenity_name: "food_auxiliary" },
      { id: 4, name: "特色菜", idenity_name: "food_feature" },
      { id: 5, name: "汤", idenity_name: "food_soup" },
      { id: 6, name: "火锅", idenity_name: "food_hotPot" },
      { id: 7, name: "点心", idenity_name: "food_dessert" },
      { id: 8, name: "果汁", idenity_name: "food_juice" },
      { id: 9, name: "山珍海味", idenity_name: "food_treasure" },
    ],

    recommend_circly:[//轮播展示的推荐菜的列表
      { 
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_2",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_3",
        price: 20
      }
    ],

    recommend_list: [//列表视图的推荐菜列表
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_2",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_3",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_4",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_5",
        price: 20
      },
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_6",
        price: 20
      }
    ],

    food_list : [
      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },

      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },

      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },

      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },

      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
      },

      {
        pic_url: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg',
        name: "test_1",
        price: 20
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

  /* 点击左侧一级菜单，调整选中状态 */
  chooseMenuItem : function(e){
    this.setData({
      choose_status: e.currentTarget.dataset.num
    })
  }
  
})
