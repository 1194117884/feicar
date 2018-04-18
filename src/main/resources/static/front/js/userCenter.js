
$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });



});

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
