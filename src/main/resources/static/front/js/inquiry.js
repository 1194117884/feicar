var _seriesList = [];
var _selectModelId = 0;

$(function () {
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });
    //默认展示车型车系
    _selectModelId = getModelId();

    //车系选择
    $('#doc-select-series').on('change', function () {
        var seriesId = $(this).val();
        changeSeries(seriesId);
    })
    //车型选择
    $('#doc-select-models').on('change', function () {
        var modeId = $(this).val();
    })
    //提交询价
    $('#sub-btn').on('click', function () {
        inquiry();
    })


    //加载车型列表
    loadCarLink(_selectModelId);
    //记载个人信息
    loadUserInfo();
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
    // alert($('.pet_list_one_nr').height());
}

function getModelId() {
    return location.search.substring(1);//http://localhost/front/carList.html?14
}


function loadCarLink(selectedModelId) {
    //show loading
    $.myLoading.load();

    $.ajax({
        type: "get",
        url: "/bsc/car_link",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: '',
        error: function (error, status) {
            $.myLoading.close();
        },
        success: function (dd, status) {
            $.myLoading.close();
            if (dd.code == 0) {
                if (dd.data == null || dd.data == undefined || dd.data.length < 1) {
                } else {
                    _seriesList = dd.data;
                    showSeries(_seriesList);

                    if (selectedModelId != undefined && selectedModelId >= 0) {
                        selectModel(selectedModelId);
                    }
                }
                $('.cr_content').fadeIn();
            }
        }
    })
}

function showSeries(seriesList) {
    var _html = '<option value="0">选择车系</option>\n';
    if (seriesList != undefined && seriesList != null) {
        $.each(seriesList, function (i, v) {
            _html += '<option value="' + v.id + '">' + v.name + '</option>\n';
        })
    }
    $('#doc-select-series').html(_html);
}

function showModels(modelList) {
    var _html = '<option value="0">选择车型</option>\n';
    if (modelList != undefined && modelList != null) {
        $.each(modelList, function (i, v) {
            _html += '<option value="' + v.id + '">' + v.name + '</option>\n';
        })
    }
    $('#doc-select-models').html(_html);
}


function changeSeries(seriesId) {
    if (seriesId == undefined || seriesId == "" || seriesId <= 0) {
        showModels(null);
    } else {
        $.each(_seriesList, function (i, v) {
            if (v.id == seriesId) {
                showModels(v.modelList);
                return;
            }
        });
    }
}

function selectModel(modelId) {
    var _sid = 0;
    var _mid = 0;

    $.each(_seriesList, function (i, s) {
        if (s.modelList != null && s.modelList != undefined) {
            $.each(s.modelList, function (j, m) {
                if (m.id == modelId) {
                    _sid = s.id;
                    _mid = m.id;
                }
            })
        }
    })

    $('#doc-select-series').val(_sid);
    changeSeries(_sid);
    $('#doc-select-models').val(_mid);
}

function loadUserInfo() {
    var user = getUserFromStorage();
    if (user) {
        if (user.name) {
            $('#doc-ipt-name').val(user.name);
        }
        if (user.phone) {
            $('#doc-ipt-phone').val(user.phone);
        }
    }
}

function inquiry() {
    //车系
    var seriesId = $('#doc-select-series').val();
    if (seriesId <= 0) {
        $.myAlert.alert("请选择询问车系！");
        return;
    }
    //车型
    var modelId = $('#doc-select-models').val();
    if (modelId <= 0) {
        $.myAlert.alert("请选择询问车型！");
        return;
    }
    //姓名
    var name = $('#doc-ipt-name').val();
    if (name == undefined || name == '') {
        $.myAlert.alert("请填写您的姓名！");
        return;
    }
    //手机号
    var phone = $('#doc-ipt-phone').val();
    if (phone == undefined || phone == '') {
        $.myAlert.alert("请填写您的手机号！");
        return;
    }
    //备注
    var desc = $('#doc-ta-desc').val();
    //提交
    $.myLoading.load();
    $.ajax({
        type: "post",
        url: "/bsc/inquiry",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: JSON.stringify({
            seriesId: seriesId,
            modelId: modelId,
            userName: name,
            userPhone: phone,
            message: desc
        }),
        error: function (error, status) {
            $.myLoading.close();
        },
        success: function (dd, status) {
            $.myLoading.close();
            if (dd.code == 0) {
                $.myAlert.alert("恭喜，请等待询价结果！");

                $('#doc-select-series').val(0);
                $('#doc-select-models').val(0);

                $('#doc-ipt-name').val('');
                $('#doc-ipt-phone').val('');
                $('#doc-ta-desc').val('');
            } else {
                $.myAlert.alert(dd.msg);
            }
        }
    })
}