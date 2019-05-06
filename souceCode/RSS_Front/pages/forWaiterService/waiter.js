// pages/forWaiterService/waiter.js
var app = getApp();

var webSocket = undefined;
var socketOpen = false;
var socketMsgQueue = [];

/*
  发送信息的内容标识符 
*/
const type_getMession =  101;
const type_changeWorkingStatus = 102;

/*
  工作状态
*/
const status_working = 10;
const status_stop = 11;

Page({
  /**
   * 页面的初始数据
   */
  data: {
    screenHeight : 500,

    /*
     *true: 正常工作
     *false: 暂停工作
     */
    workingStatus : true,

    isConnect : false,

    messageQueue : [
      { id: 41, type: "派送", picUrl: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg", tableNum: 10, createTime: "2018-10-9:10-11"},
      { id: 42,  type: "派送", picUrl: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg", tableNum: 11, createTime: "2018-10-9:10-11" },
      { id: 43,  type: "服务", picUrl: "", tableNum: 11, createTime: "2018-10-9:10-11" },
      { id: 44,  type: "服务", picUrl: "", tableNum: 13, createTime: "2018-10-9:10-11" },
      { id: 45,  type: "派送", picUrl: "https://rss-1252828635.cos.ap-beijing.myqcloud.com/image/1555678910117.jpg", tableNum: 11, createTime: "2018-10-9:10-11" },
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad : function (options) {
    var that = this;

    //获取设备高度
    wx.getSystemInfo({
      success: function(res) {
        var screenHeight = res.windowHeight - 90;

        that.setData({
          screenHeight: screenHeight
        })
        screenHeight
      },
    });

    if(!socketOpen) {
      wx.connectSocket({
        url: 'ws://localhost:8881/websocket/'+app.globalData.userInfo.openid
      });

      //端口打开的监听注册
      wx.onSocketOpen( this.onSocketOpen );

      //注册socket收到消息的监听方法
      wx.onSocketMessage( this.onSocketMessage );

      //注册监听 WebSocket 错误事件
      wx.onSocketError( this.onSocketError );

      //注册关闭 WebSocket 连接
      wx.onSocketClose( this.onSocketClose );
    }
  },

  onSocketOpen : function(res){
    socketOpen = true

    this.setData({
      isConnect : socketOpen
    })

    console.log(res);
  },

  onSocketMessage : function(res){
    var that = this;
    var res = JSON.parse(res.data);
    
    switch(res.type){
      case type_getMession :
        app.socketResponseHandler(res, function () {
          var message = that.data.messageQueue;

          for (var i in message) {
            var tempMessage = message[i];

            if (tempMessage.id == res.id) {
              message.splice(i, 1);

              that.setData({
                messageQueue: message
              })
            }
          }
        })

        break;
      case type_changeWorkingStatus:
        
        
        break;
    }

    if (socketOpen) {
      return res;
    } else {
      app.showToast("连接服务器失败");
    }

    console.log(res);
  },

  onSocketError : function(res){
    console.log(res.data);
  },

  onSocketClose : function(res){
    socketOpen = false;

    this.setData({
      isConnect: socketOpen
    })

    app.showToast("与服务器已断开连接，请重试");

    console.log('WebSocket 已关闭！')
  },

  changeWorkingStatus : function(e){
    var temp_workingStatus = this.data.workingStatus;

    var workingStatus = undefined;

    if(temp_workingStatus){//工作状态
      workingStatus = status_stop;
    } else {
      workingStatus = status_working;
    }

    /**
     * 0: 切换工作状态,
     * 1: 接收工作信息
    */
    var data = {
      "type" : type_changeWorkingStatus,
      "workingStatus": workingStatus
    }

    this.sendSocketMessage(JSON.stringify(data));

    this.setData({
      workingStatus: !temp_workingStatus
    })
  },

  completeMission : function(e){
    if ( ! this.data.workingStatus){//不在工作状态
      app.showToast("暂停工作中");

      return;
    }

    var id = parseInt(e.target.dataset.recordid);

    var msg = {
      "type": type_getMession,
      "id": id
    }

    var msg = JSON.stringify(msg);
    
    this.sendSocketMessage(msg);
  },

  sendSocketMessage : function(msg){
    if (socketOpen) {
      wx.sendSocketMessage({
        data: msg
      })
    } else {
      
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    wx.closeSocket()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})