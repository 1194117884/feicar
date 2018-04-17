var _pageSize = 6;
var _pageNow = 1;


$(function () {

    // 动态计算新闻列表文字样式
    auto_resize();
    $(window).resize(function () {
        auto_resize();
    });
    $('.cm_content_list_li_a_img').load(function () {
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
        getCarModels(_pageNow);
    });


    //获取车型列表
    getCarModels(1);
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
    // alert($('.pet_list_one_nr').height());
}

function loadingAction(action) {
    $('#my-modal-loading').modal(action);
}

function goCarDetail(id) {
    window.location.href = 'carDetail.html?' + id;
}

function getSeriesId() {
    return location.search.substring(1);//http://localhost/front/carList.html?14
}

function getPageBean(pageNum) {
    var pageBean = {
        status: 0,
        seriesId: getSeriesId(),
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

function getCarModels(pageNum) {
    //show loading
    loadingAction('open');

    $.ajax({
        type: "post",
        url: "/bsc/car_models",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: JSON.stringify(getPageBean(pageNum)),
        error: function (error, status) {
            loadingAction('close');
        },
        success: function (dd, status) {
            if (dd.code == 0) {
                loadingAction('close');
                var htmlText = '';
                if (dd.data == null || dd.data == undefined || dd.data.length < 1){
                    htmlText += '<li style="height: 40px;background-color: #fff;width: 100%;line-height: 40px;text-align: center;">更多车型正在补录中！！！</li>';
                } else {
                    $.each(dd.data, function (i, v) {
                        htmlText += '<li class="cm_content_list_li">' +
                            '<a class="iconfont cm_content_list_li_a" onclick="goCarDetail(' + v.id + ')">' +
                            '<div style="float: left; width: 90%">' +
                            '<div>' + v.name + '</div>' +
                            '<div class="am-g cm_am-g">' +
                            '<div class="am-u-sm-4 cm_am-u-sm-4">指导价:' + (v.guidePrice / 10000) + '万</div>' +
                            '<div class="am-u-sm-4 cm_am-u-sm-4">' + (v.policyPrice == null ? '' : '补贴价:' + (v.policyPrice / 10000) + '万') + '</div>' +
                            '<div class="am-u-sm-4 cm_am-u-sm-4">库存量:' + v.inventory + '</div>' +
                            '</div>' +
                            '</div>' +
                            '<span class="cm_content_list_li_a_ico">&#xe68b;</span>' +
                            '</a>' +
                            '</li>';
                    });
                }
                $(".am-list").append(htmlText);
                $('.cm_content').fadeIn();

                pageBeanShow(dd.data.length >= _pageSize);
            } else {
                loadingAction('close');
            }
        }
    })
}