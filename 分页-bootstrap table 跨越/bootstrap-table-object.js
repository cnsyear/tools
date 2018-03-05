/**
 * 初始化 BootStrap Table 的封装
 *
 * 约定：toolbar的id为 (bstableId + "Toolbar")
 *
 * @author Jiabin.Zhao
 */
(function() {
	var BSTable = function(bstableId, url, columns) {
		this.btInstance = null; // jquery和BootStrapTable绑定的对象
		this.bstableId = bstableId;
		this.url =  url;
		this.method = "post";
		this.paginationType = "server"; // 默认分页方式是服务器分页,可选项"client"
		this.toolbarId = bstableId + "Toolbar";
		this.columns = columns;
		this.height = 665; // 默认表格高度665
		this.data = {};
		this.paginationFirstText='首页';
		this.paginationLastText='尾页';
		this.paginationtransBtnText='确定';
        this.showPaginationFirst=false;//是否显示 首页
        this.showPaginationLast=false;//是否显示 尾页
		this.showPaginationtrans=false;//是否显示 手动跳转
		this.queryParams = {}; // 向后台传递的自定义参数
		this.pageSize = 2;// 每页的记录行数（*）
		this.pageList = [ 10, 15, 20, 25, 50, 100 ,500,1000]; // 可供选择的每页的行数（*）
		this.pagination = true;// 是否显示分页条
		this.showColumns = false; // 是否显示所有的列
		this.showRefresh = false; // 是否显示刷新按钮
		this.search = false; // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		this.searchOnEnterKey = false;// 设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
		this.strictSearch = false;// 设置为 true启用 全匹配搜索，否则为模糊搜索
		this.striped = true; // 是否显示行间隔色
		this.cache = false; // 是否使用缓存,默认为true
		this.sortable = true; // 是否启用排序
		this.isMergeCells = false;// 是否开启合并单元格
		this.isMergeCellsData = null;// 需要合并单元格的 列 index所在行数,field 列名称
		this.showHeader = false;// 是否显示列头
		this.showFooter = false;// 是否显示列脚
		this.sortOrder = "desc"; // 排序方式
		this.pageNumber = 1; // 初始化加载第一页，默认第一页
		this.queryParamsType = 'limit'; // 默认值为 'limit' ,在默认情况下  传给服务端的参数为：offset,limit,sort
		this.showToggle = false;// 是否显示 切换试图（table/card）按钮
		this.minimumCountColumns = 2; // 最少允许的列数
		this.clickToSelect = false; // 是否启用点击选中行
		this.idField = 'undefined';// 默认:undefined 指定主键列
		this.uniqueId = 'undefined';// 默认:undefined 为每一行指定唯一的标识符
		this.searchAlign = 'right';// 指定 搜索框 水平方向的位置。’left’ or ‘right’
		this.buttonsAlign = 'right';// 指定 按钮 水平方向的位置。’left’ or ‘right’
		this.toolbarAlign = 'left';// 指定 toolbar 水平方向的位置。’left’ or ‘right’
		this.paginationVAlign = 'bottom';// 指定 分页条 在垂直方向的位置。’top’ or ‘bottom’  or ‘bonth’
		this.paginationHAlign = 'right';// 指定 分页条 在水平方向的位置。’left’ or ‘right’
		this.paginationDetailHAlign = 'left';// 指定 分页详细信息 在水平方向的位置。’left’ or ‘right’
		this.paginationPreText = '&lsaquo;';// 指定分页条中上一页按钮的图标或文字
		this.paginationNextText = '&rsaquo;';// 指定分页条中下一页按钮的图标或文字
		this.singleSelect = false;// 设置True 将禁止多选
		this.checkboxHeader = true;// 设置false 将在列头隐藏check-all checkbox .
		this.maintainSelected = false;// 设置为 true 在点击分页按钮或搜索按钮时，将记住checkbox的选择项
		this.silentSort = true;// 设置为 false 将在点击分页按钮时，自动记住排序项。仅在  sidePagination设置为 server时生效.
		this.showPaginationSwitch = false;// 是否显示 数据条数选择框
		this.isInitSwitchery=false;//是否开启 ios7风格
		this.titleMethod=null;
		this.expandColumn=1;
		this.collapseAll = false;// 合并所有列  默认为false
		this.isTreegrid = false;
	};

	BSTable.prototype = {
		/**
		 * 初始化bootstrap table
		 */
		init : function() {
			var tableId = this.bstableId;
			var me = this;
			this.btInstance = $('#' + tableId).bootstrapTable(
					{
						contentType : "application/x-www-form-urlencoded",
						url : this.url, // 请求地址
						method : this.method, // ajax方式,post还是get
						ajaxOptions : { // ajax请求的附带参数
							data : this.data
						},
						toolbar : "#" + this.toolbarId,// 顶部工具条
						striped : this.striped, // 是否显示行间隔色
						cache : this.cache, // 是否使用缓存,默认为true
						sortable : this.sortable, // 是否启用排序
						sortOrder : this.sortOrder, // 排序方式
						pageNumber : this.pageNumber, // 初始化加载第一页，默认第一页
						pageSize : this.pageSize, // 每页的记录行数（*）
						pageList : this.pageList, // 可供选择的每页的行数（*）
						queryParamsType : this.queryParamsType, // 默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
						queryParams : function(param) {
							return $.extend(me.queryParams, param);
							// return $.extend(this.queryParams, param);
						}, // 向后台传递的自定义参数
						sidePagination : this.paginationType, // 分页方式：client客户端分页，server服务端分页（*）
						search : this.search, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
						searchOnEnterKey : this.searchOnEnterKey,// 设置为  true时，按回车触发搜索方法，否则自动触发搜索方法
						strictSearch : this.strictSearch,// 设置为 true启用 全匹配搜索，否则为模糊搜索
						showColumns : this.showColumns, // 是否显示所有的列
						showRefresh : this.showRefresh, // 是否显示刷新按钮
						showToggle : this.showToggle,// 是否显示 切换试图（table/card）按钮
						minimumCountColumns : this.minimumCountColumns, // 最少允许的列数
						clickToSelect : this.clickToSelect, // 是否启用点击选中行
						columns : this.columns, // 列数组
						pagination : this.pagination,// 是否显示分页条
						height : this.height,// 表格高度
						showHeader : this.showHeader,// 是否显示列头
						showFooter : this.showFooter,// 是否显示列脚
						idField : this.idField,// 默认:undefined 指定主键列
						uniqueId : this.uniqueId,// 默认:undefined 为每一行指定唯一的标识符
						searchAlign : this.searchAlign,// 指定 搜索框 水平方向的位置。’left’  or ‘right’
						buttonsAlign : this.buttonsAlign,// 指定 按钮 水平方向的位置。’left’ or ‘right’
						toolbarAlign : this.toolbarAlign,// 指定 toolbar 水平方向的位置。’left’ or ‘right’
						paginationVAlign : this.paginationVAlign,// 指定 分页条 在垂直方向的位置。’top’ or  ‘bottom’  or  ‘bonth’
						paginationHAlign : this.paginationHAlign,// 指定 分页条  在水平方向的位置。’left’  or ‘right’
						paginationDetailHAlign : this.paginationDetailHAlign,// 指定 分页详细信息 在水平方向的位置。’left’ or ‘right’
						paginationPreText : this.paginationPreText,// 指定分页条中上一页按钮的图标或文字
						paginationNextText : this.paginationNextText,// 指定分页条中下一页按钮的图标或文字
						singleSelect : this.singleSelect,// 设置True 将禁止多选
						checkboxHeader : this.checkboxHeader,// 设置false 将在列头隐藏check-all checkbox .
						maintainSelected : this.maintainSelected,// 设置为 true  在点击分页按钮或搜索按钮时，将记住checkbox的选择项
						silentSort : this.silentSort,// 设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为  server时生效.
						showPaginationSwitch : this.showPaginationSwitch,// 是否显示  数据条数选择框
                        showPaginationtrans:this.showPaginationtrans,//是否显示 手动跳转
                        showPaginationFirst:this.showPaginationFirst,//是否显示 首页
                        showPaginationLast:this.showPaginationLast,//是否显示 尾页
						paginationFirstText:this.paginationFirstText,
						paginationLastText:this.paginationLastText,
						paginationtransBtnText:this.paginationtransBtnText,
						 titleMethod:this.titleMethod,
						 expandColumn :this.expandColumn,
						 collapseAll:this.collapseAll,
						 isTreegrid:this.isTreegrid,
						
						icons : {
							refresh : 'glyphicon-repeat',
							toggle : 'glyphicon-list-alt',
							columns : 'glyphicon-list'
						},

						iconSize : 'outline'
					});
			return this;
		},
        setShowPaginationFirst:function(data){
            this.showPaginationFirst = data;
        },
        setShowPaginationLast:function(data){
            this.showPaginationLast = data;
        },

		setShowPaginationtrans:function(data){
			this.showPaginationtrans = data;
		},
		setIsTreegrid:function(data){
			this.isTreegrid=data;
		},
		setCollapseAll:function(data){
			this.collapseAll=data;
		},
		setExpandColumn :function(data){
			this.expandColumn =data;
		},
		 setTitleMethod:function(data){
	        	this.titleMethod =data;
	     },
		/**
		 * 设置是否开启 ios7风格
		 */
		setIsInitSwitchery:function(data){
			this.isInitSwitchery = data;
		},
		/**
		 * 初始化Switchery
		 */
		initSwitchery:function(){
			var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
			elems.forEach(function(html) {
				IMap[html.id]= new Switchery(html,{ color: '#038de5', jackColor: '#038de5' });
			});
		},
		/**
		 * 是否显示 数据条数选择框
		 */
		setShowPaginationSwitch : function(data) {
			this.showPaginationSwitch = data;
		},
		/**
		 * 设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
		 */
		setSilentSort : function(data) {
			this.silentSort = data;
		},
		/**
		 * 设置为 true 在点击分页按钮或搜索按钮时，将记住checkbox的选择项
		 */
		setMaintainSelected : function(data) {
			this.maintainSelected = data;
		},
		/**
		 * 设置false 将在列头隐藏check-all checkbox .
		 */
		setCheckboxHeader : function(data) {
			this.checkboxHeader = data;
		},
		/**
		 * 设置True 将禁止多选
		 */
		setSingleSelect : function(data) {
			this.singleSelect = data;
		},
		/**
		 * 指定 分页详细信息 在水平方向的位置。’left’ or ‘right’
		 */
		setPaginationDetailHAlign : function(data) {
			this.paginationDetailHAlign = data;
		},
		/**
		 * 指定分页条中上一页按钮的图标或文字
		 */
		setPaginationPreText : function(data) {
			this.paginationPreText = data;
		},
		/**
		 * 指定分页条中下一页按钮的图标或文字
		 */
		setPaginationNextText : function(data) {
			this.paginationNextText = data;
		},
		/**
		 * 指定 分页条 在水平方向的位置。’left’ or ‘right’
		 */
		setPaginationHAlign : function(data) {
			this.paginationHAlign = data;
		},
		/**
		 * 指定 分页条 在垂直方向的位置。’top’ or ‘bottom’ or ‘bonth’
		 */
		setPaginationVAlign : function(data) {
			this.paginationVAlign = data;
		},
		/**
		 * 指定 toolbar 水平方向的位置。’left’ or ‘right’
		 */
		setToolbarAlign : function(data) {
			this.toolbarAlign = data;
		},
		/**
		 * 指定 按钮 水平方向的位置。’left’ or ‘right’
		 */
		setButtonsAlign : function(data) {
			this.buttonsAlign = data;
		},
		/**
		 * 指定 搜索框 水平方向的位置。’left’ or ‘right’
		 */
		setSearchAlign : function(data) {
			this.searchAlign = data;
		},

		/**
		 * 默认:undefined 为每一行指定唯一的标识符
		 */
		setUniqueId : function(data) {
			this.uniqueId = data;
		},
		/**
		 * 默认:undefined 指定主键列
		 */
		setIdField : function(data) {
			this.idField = data;
		},

		/**
		 * 默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
		 */
		setQueryParamsType : function(data) {
			this.queryParamsType = data;
		},
		/**
		 * 初始化加载第一页，默认第一页
		 */
		setPageNumber : function(data) {
			this.pageNumber = data;
		},
		/**
		 * 排序方式
		 */
		setSortOrder : function(data) {
			this.sortOrder = data;
		},
		/**
		 * 是否显示列脚
		 */
		setShowFooter : function(data) {
			this.showFooter = data;
		},
		/**
		 * 是否显示列头
		 */
		setShowHeader : function(data) {
			this.showHeader = data;
		},
		/**
		 * 设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
		 */
		setSearchOnEnterKey : function(data) {
			this.searchOnEnterKey = data;
		},
		/**
		 * 是否启用点击选中行
		 */
		setClickToSelect : function(data) {
			this.clickToSelect = data;
		},
		/**
		 * 是否显示 切换试图（table/card）按钮
		 */
		setShowToggle : function(data) {
			this.showToggle = data;
		},
		/**
		 * 最少允许的列数
		 */
		setMinimumCountColumns : function(data) {
			this.minimumCountColumns = data;
		},
		/**
		 * 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		 */
		setSearch : function(data) {
			this.search = data;
		},
		/**
		 * 设置为 true启用 全匹配搜索，否则为模糊搜索
		 */
		setStrictSearch : function(data) {
			this.strictSearch = data;
		},
		/**
		 * 设置合并单元格的列
		 */
		setIsMergeCellsData : function(data) {
			this.isMergeCellsData = data;
		},
		/**
		 * 设置是否开启合并单元格
		 */
		setIsMergeCells : function(isMergeCells) {
			this.isMergeCells = isMergeCells;
		},
		/**
		 * 设置是否显示行间隔色
		 */
		setStriped : function(striped) {
			this.striped = striped;
		},
		/**
		 * 设置是否使用缓存,默认为true
		 */
		setCache : function(cache) {
			this.cache = cache;
		},
		/**
		 * 设置是否启用排序
		 */
		setSortable : function(sortable) {
			this.sortable = sortable;
		},
		/**
		 * 设置是否显示所有的列
		 */
		setShowColumns : function(showColumns) {
			this.showColumns = showColumns;
		},
		/**
		 * 设置显示刷新按钮
		 */
		setShowRefresh : function(showRefresh) {
			this.showRefresh = showRefresh;
		},
		/**
		 * 设置表格高度
		 */
		setHeight : function(height) {
			this.height = height;
		},
		/**
		 * 向后台传递的自定义参数
		 *
		 * @param param
		 */
		setQueryParams : function(param) {
			this.queryParams = param;
		},
		/**
		 * 设置分页方式：server 或者 client
		 */
		setPaginationType : function(type) {
			this.paginationType = type;
		},
		/**
		 * 设置是否显示分页条： true 或者false
		 */
		setPagination : function(pagination) {
			this.pagination = pagination;
		},
		/**
		 * 设置每页的记录行数（*）
		 */
		setPageSize : function(pageSize) {
			this.pageSize = pageSize;
		},
		/**
		 * 设置可供选择的每页的行数（*）
		 */
		setPageList : function(pageList) {
			this.pageList = pageList;
		},
		/**
		 * 设置ajax post请求时候附带的参数
		 */
		set : function(key, value) {
			if (typeof key == "object") {
				for ( var i in key) {
					if (typeof i == "function")
						continue;
					this.data[i] = key[i];
				}
			} else {
				this.data[key] = (typeof value == "undefined") ? $("#" + key)
						.val() : value;
			}
			return this;
		},

		/**
		 * 设置ajax post请求时候附带的参数
		 */
		setData : function(data) {
			this.data = data;
			return this;
		},

		/**
		 * 清空ajax post请求参数
		 */
		clear : function() {
			this.data = {};
			return this;
		},

		/**
		 * 刷新 bootstrap 表格 Refresh the remote server data, you can set {silent:
		 * true} to refresh the data silently, and set {url: newUrl} to change
		 * the url. To supply query params specific to this request, set {query:
		 * {foo: 'bar'}}
		 */
		refresh : function(parms) {
			//console.log('执行刷新');
			if (typeof parms != "undefined") {
				this.btInstance.bootstrapTable('refresh', parms);
			} else {
				this.btInstance.bootstrapTable('refresh');
			}
		}
	};

	window.BSTable = BSTable;

}());