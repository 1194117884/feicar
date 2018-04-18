$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    window.addEventListener("popstate", function(e) {
        var state = e.state;
        alert(JSON.toString(e));
    });
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}
