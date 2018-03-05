/**
 * Created by haley on 2018/3/3.
 */
$(function () {
    var el = $('#box')
        ,pageBox=$('#page-link-box')
        ,dataArr=data.content
        ,num=5 //每页显示条数
        ,currentPage=1 //当前页码
        ,len=dataArr.length //数据总条数
        ,pages=Math.ceil(len/num)
        ,middle=4
        ,current=0
        ;
    console.log("数据长度：",dataArr.length);
    update();
    // 重新渲染页面
    function update(){
        el.html(updateContent(dataArr,(currentPage-1)*num,currentPage*num));
        pageBox.html(updatePagination(currentPage,middle));
        currentPageShow();
        addHandle();
        prevPage();
        nextPage();
    }
    // 分页更新
    function updatePagination(currentPage,n){
        var config={
            a1:'<span class="page-link page">',
            a3:'</span>',
            span1:'<span class="page-link active">',
            span2:'</span>'
        };
        var htmlArr=[];

        if(currentPage>1){
            htmlArr.push('<span class="page-link" title="Previous Page" id="prevPage">«</span>');
        }
        htmlArr.push('<div id="page-link-container1">');
        if(currentPage<=middle){
            for(var i= 1;i<=n+currentPage-1;i++){
                if(currentPage==i){
                    addSpan(i);
                }else{
                    addA(i);
                }
            }
        }else{
            var len=0;
            if(pages-currentPage<middle){
                len=pages;
            }else{
                len=n+currentPage-1;
            }
            for(var j= currentPage-middle+1;j<=len;j++){
                if(currentPage==j){
                    addSpan(j);
                }else{
                    addA(j);
                }
            }
        }
        htmlArr.push('</div>');
        if(currentPage!=pages){
            htmlArr.push('<span class="page-link" title="Next Page" id="nextPage">»</span>');
        }
        function addA(i){
            htmlArr.push(
                config.a1+i+config.a3
            );
        }
        function addSpan(i){
            htmlArr.push(
                config.span1+i+config.span2
            );
        }
        return htmlArr.join('');
    }
    // 顶部提示
    function currentPageShow(){
        $('#currentPage').html('当前页：'+currentPage+'， 总页数：'+pages);
    }

    // 点击事件添加
    function addHandle(){
        $('#page-link-box span.page').on('click',function(){
            current=parseInt($(this).text());
            console.log(current);
            currentPage=current;
            update();
        });
    }
    // 上一页按钮
    function prevPage(){
        $('#prevPage').click(function(){
            console.log('prevPage');
            currentPage=--current;
            update();
        });
    }
    // 下一页按钮
    function nextPage(){
        $('#nextPage').click(function(){
            currentPage=++current;
            update();
        });
    }
    // 内容更新
    function updateContent(dataArr,currentPage,num) {
        var htmlArr = [];
        for (var i = currentPage; i < num; i++) {
            htmlArr.push('<ul>');
            htmlArr.push(
                    '<li>' +
                        dataArr[i] +
                    '</li>'
            );
            htmlArr.push('</ul>');
            //htmlArr.push('<p>"'+i+'&nbsp;&nbsp;'+dataArr[i]+'",</p>');
        }
        return htmlArr;
    }
});