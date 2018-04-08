//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    snsCodeMsg: '获取验证码',//默认字
    snsMsgWait:60,//时间秒
    snsDisabled:false,//是否可点击
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
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
  /**
   * 验证码发送
   */
  sendSms:function(){
    // 60秒后重新获取验证码
    var inter = setInterval(function () {
      this.setData({
        snsCodeMsg: "重新发送(" + this.data.snsMsgWait + ")",
        snsMsgWait: this.data.snsMsgWait - 1,
        snsDisabled:true
      });
      if (this.data.snsMsgWait < 0) {
        clearInterval(inter)
        this.setData({
          snsCodeMsg: "获取验证码",
          snsMsgWait: 60,
          snsDisabled: false
        });
      }
    }.bind(this), 1000);
    //注意后面的bind绑定，最关键。不然又是未定义，无法使用外围的变量

  }
})
