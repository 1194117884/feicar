var _choseModel = 0;

$(function () {
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    var modelId = getModelId();
    if (modelId != undefined && modelId != null && !isNaN(modelId)) {
        //默认选项
        _choseModel = modelId;
    }

    //获取车型列表
    getCarLink();
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


function getCarLink() {
    //show loading
    loadingAction('open');

    $.ajax({
        type: "get",
        url: "/bsc/car_link",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: '',
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

                }
                $(".am-list").append(htmlText);
                $('.cr_content').fadeIn();
            } else {
                loadingAction('close');
            }
        }
    })
}