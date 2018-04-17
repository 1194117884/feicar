var _pageSize = 6;
var _pageNow = 1;


$(function () {

    // 动态计算新闻列表文字样式
    auto_resize();
    $(window).resize(function () {
        auto_resize();
    });
    $('.cs_content_list_li_a_img').load(function () {
        auto_resize();
    });

    $('.pet_article_like li:last-child').css('border', 'none');


    $('.pet_article_user').on('click', function () {
        if ($('.pet_article_user_info_tab').hasClass('pet_article_user_info_tab_show')) {
            $('.pet_article_user_info_tab').removeClass('pet_article_user_info_tab_show').addClass('pet_article_user_info_tab_cloes');
        } else {
            $('.pet_article_user_info_tab').removeClass('pet_article_user_info_tab_cloes').addClass('pet_article_user_info_tab_show');
        }
    });

    $('.pet_head_gd_ico').on('click', function () {
        $('.pet_more_list').addClass('pet_more_list_show');
    });
    $('.pet_more_close').on('click', function () {
        $('.pet_more_list').removeClass('pet_more_list_show');
    });

    $('.am-list-page-tool').on('click', function () {
        $('.am-list-page-tool').addClass('am-list-page-tool-click');

        _pageNow = _pageNow + 1;
        getCarSeries(_pageNow);
    });


    //获取车系列表
    getCarSeries(1);
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
    // alert($('.pet_list_one_nr').height());
}

function loadingAction(action) {
    $('#my-modal-loading').modal(action);
}

function goCarList(id) {
    window.location.href = 'carList.html?' + id;
}

function getPageBean(pageNum) {
    var pageBean = {
        status: 0,
        pageSize: _pageSize,
        pageNum: pageNum
    }
    return pageBean;
}

function pageBeanShow(show) {

    $('.am-list-page-tool').removeClass('am-list-page-tool-click');
    $('.am-list-page-tool').removeClass('am-list-page-tool-show');
    $('.am-list-page-tool').removeClass('am-list-page-tool-disable');
    if (show) {
        $('.am-list-page-tool').addClass('am-list-page-tool-show');
    } else {
        $('.am-list-page-tool').addClass('am-list-page-tool-disable');
    }
}

function getCarSeries(pageNum) {
    //show loading
    loadingAction('open');

    $.ajax({
        type: "post",
        url: "/bsc/car_series",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: JSON.stringify(getPageBean(pageNum)),
        error: function (error, status) {
            loadingAction('close');
        },
        success: function (data, status) {
            if (data.code == 0) {
                loadingAction('close');
                var htmlText = '';
                $.each(data.data, function (i, v) {
                    htmlText += '<li class="cs_content_list_li">';
                    htmlText += '<a class="iconfont cs_content_list_li_a" style="padding: 10px;" onclick="goCarList(\'' + v.id + '\')">';
                    htmlText += '<img class="cs_content_list_li_a_img" src="' + v.mainPic + '"/>';
                    htmlText += '<span class="cs_content_list_li_a_text">' + v.name + '</span>';
                    htmlText += '<span class="cs_content_list_li_a_ico">&#xe68b;</span>';
                    htmlText += '</a></li>';
                });
                $(".am-list").append(htmlText);
                $('.cs_content').fadeIn();

                pageBeanShow(data.data.length >= _pageSize);
            } else {
                loadingAction('close');
            }
        }
    })
}