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


    //获取车型信息
    // getCarModel(getModelId());
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
    // alert($('.pet_list_one_nr').height());
}

function loadingAction(action) {
    $('#my-modal-loading').modal(action);
}

function getModelId() {
    return location.search.substring(1);//http://localhost/front/carList.html?14
}

function getCarModel(id) {
    //show loading
    loadingAction('open');

    $.ajax({
        type: "get",
        url: "/bsc/car_model/" + id,
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        error: function (error, status) {
            loadingAction('close');
        },
        success: function (data, status) {
            if (data.code == 0) {
                loadingAction('close');

            } else {
                loadingAction('close');
            }
        }
    })
}
