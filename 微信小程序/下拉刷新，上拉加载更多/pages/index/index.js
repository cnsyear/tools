var url = "http://www.imooc.com/course/ajaxlist";
var page =0;
var page_size = 20;
var sort = "last";
var is_easy = 0;
var lange_id = 0;
var pos_id = 0;
var unlearn = 0;

var GetList = function(that){
    that.setData({
        hidden:false
    });
    wx.request({
        url:url,
        data:{
            page : page,
            page_size : page_size,
            sort : sort,
            is_easy : is_easy,
            lange_id : lange_id,
            pos_id : pos_id,
            unlearn : unlearn
        },
        success:function(res){
            //console.info(that.data.list);
            var list = that.data.list;
            for(var i = 0; i < res.data.list.length; i++){
                list.push(res.data.list[i]);
            }
            that.setData({
                list : list
            });
            page ++;
            that.setData({
                hidden:true
            });
        }
    });
}
Page({
  data:{
    hidden:true,
    list:[],
    scrollTop : 0,
    scrollHeight:0
  },
  onLoad:function(){
      var that = this;
      wx.getSystemInfo({
          success:function(res){
              console.info(res.windowHeight);
              that.setData({
                  scrollHeight:res.windowHeight
              });
          }
      });
  },
  onShow:function(){
    var that = this;
    GetList(that);
  },
  bindDownLoad:function(){
      var that = this;
      GetList(that);
  },
  scroll:function(event){
     this.setData({
         scrollTop : event.detail.scrollTop
     });
  },
  refresh:function(event){
      page = 0;
      this.setData({
          list : [],
          scrollTop : 0
      });
      GetList(this)
  }
})