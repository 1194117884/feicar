var thisPage = 'grzx';


$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
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
    window.location.href = href;
}


function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}

function getLoginUser(){

}

function goLoginWithBackUrl(localUrl) {
    window.location.href= 'login.html?cb=' + localUrl;
}

function login(){
    var localUrl = window.location.href;

    goLoginWithBackUrl(localUrl);
}

function goto(href) {
    window.location.href = href;
}


function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}
