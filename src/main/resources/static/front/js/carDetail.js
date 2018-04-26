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
    getCarModel(getModelId());
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


function showTable(obj) {
    var open = $(obj).attr('class').indexOf('cd_features_type_active') > -1;
    if (open) {
        $(obj).find('.cd_features_type_open').hide();
        $(obj).find('.cd_features_type_close').show();

        $(obj).removeClass('cd_features_type_active');
        $(obj).next().fadeOut();
    } else {
        $(obj).find('.cd_features_type_open').show();
        $(obj).find('.cd_features_type_close').hide();

        $(obj).addClass('cd_features_type_active');
        $(obj).next().fadeIn();
    }

}

function goInquiry(id) {
    window.location.href = 'inquiry.html?' + id;
}

function goReserve(id) {
    window.location.href = 'reserve.html?' + id;
}

function getFeatureShowValue(modelInfo) {
    if (modelInfo == '-1') {//没有装配
        return '●';
    } else if (modelInfo == '0') {//选配
        return '〇';
    } else if (modelInfo == '1') {//配有
        return '-';
    } else {//自定义显示
        return modelInfo;
    }
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
        success: function (dd, status) {
            loadingAction('close');
            if (dd.code == 0) {
                var carModel = dd.data;
                var _features = carModel.features;

                //
                var carId = carModel.id;
                //name
                var carName = carModel.name;
                //img
                var modelImg = carModel.mainPic;
                if (modelImg == '' || modelImg == null) {
                    modelImg = carModel.seriesInfo.mainPic;
                }
                //price
                var guidePrice = carModel.guidePrice;
                var policyPrice = carModel.policyPrice;

                var cdHtml = '<div>' +
                    '<div class="cd_detail_info">' +
                    '<img src="' + modelImg + '"><div>' +
                    '<span class="cd_detail_info_name">' + carName + '</span>' +
                    '<span class="cd_detail_info_name">指导价:' + (guidePrice / 10000) + '万</span>' +
                    (policyPrice == null ? '' : '<span class="cd_detail_info_name">补贴价:' + (policyPrice / 10000) + '万</span>') +
                    '</div></div></div>' +
                    '<div class="am-g cd_detail_am_g">' +
                    '<a class="iconfont am-u-sm-6 cd_detail_am_g_zixun" onclick="goInquiry(\'' + carId + '\')">&#xe660; 优惠咨问</a>' +
                    '<a class="iconfont am-u-sm-6 cd_detail_am_g_shijia" onclick="goReserve(\'' + carId + '\')">&#xe74c; 免费试驾</a>' +
                    '</div>';
                $('.cd_detail').append(cdHtml);
                $('.cd_detail').fadeIn();

                var cdfHtml = '';
                if (_features == null || _features == undefined) {
                    cdfHtml += '<li class="cd_features_type" >详细配置正在补录入中！！！</li>';
                } else {
                    $.each(_features, function (key, value) {
                        cdfHtml += '<li class="cd_features_type" onclick="showTable(this)">' + key +
                            '<span class="iconfont cd_features_type_open" style="display: none"> &#xe619;</span>' +
                            '<span class="iconfont cd_features_type_close"> &#xe68b;</span>' +
                            '</li>';

                        cdfHtml += '<li class="cd_features_list" style="display: none"><table class="am-table am-table-hover am-table-bordered cd_features_type_table">';
                        $.each(value, function (i, v) {
                            cdfHtml += '<tr>' +
                                '<td class="cd_features_type_table_td_left">' + v.feature + '</td>' +
                                '<td>' + getFeatureShowValue(v.modelInfo) + '</td>' +
                                '</tr>';
                        });

                        cdfHtml += '</table></li>';
                    });
                }

                $('#cd_features').append(cdfHtml);
                $('#cd_features').fadeIn();
            } else {
            }
        }
    })
}
