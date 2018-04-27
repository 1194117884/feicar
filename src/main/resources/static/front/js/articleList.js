var thisPage = 'zxtt';

$(function () {

    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    $('.am-list-thumb img').load(function () {
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


    $('.pet_bottom_nav_list li').on('click', function () {
        hoverNav(this);
        goNextPage($(this).attr("type"));
    })

    //出发本页clik
    $('.pet_bottom_nav_list li[type="' + thisPage + '"]').click();
});

function hoverNav(_nav) {
    var _li = $(_nav).find("a");
    var _oldClass = $(_li).attr('class');
    if (_oldClass.indexOf('pet_bottom_nav_item_active') > -1)
        return;
    $(_li).attr('class', _oldClass + "_active");
}

function goNextPage(type) {
    if (type == thisPage) return;

    if ("zxtt" == type) {
        goto('articleList.html');
        return;
    }
    if ("gcfw" == type) {
        goto('buyService.html');
        return;
    }
    if ("grzx" == type) {
        goto('userCenter.html');
        return;
    }

}


function goto(href) {
    var _aftStr = new Date().getTime();
    window.location.href = href + '?i=' + _aftStr;
}

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
    // alert($('.pet_list_one_nr').height());
}
