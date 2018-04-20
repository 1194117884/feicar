$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    //编辑点击
    $('.ui_pet_grzx_list ul li ul li').on('click', function () {
        showUpdate(this);
    })

    //展示信息
    loadUserInfo();
});

function auto_resize() {
    //TODO:
}

function editModalPop(obj, callBack) {
    $('#edit-popup').modal({
        relatedTarget: obj,
        onConfirm: function (e) {
            callBack('confirm');
        },
        onCancel: function (e) {
            callBack('cancel');
        }
    });
}


function showUpdate(_li) {
    editModalPop(_li, function (choose) {
        if (choose == 'confirm') {
            $.myAlert.alert("哈哈哈哈哈哈哈哈");
        } else {

        }
    });
}

function updateUserInfo() {

}

function loadUserInfo() {

    var user = getUserFromStorage();

    showUserInfo(user);

    try {
        refreshUserInfo();
    } catch (e) {

    }
}

function showUserInfo(user) {
    if (user == null || user == undefined) {//没登陆

    } else {

    }
}

function refreshUserInfo() {
    getUserFromNet(function (success, value) {
        if (success) {
            if (value.code == '0') {
                var user = value.data;
                if (user != null && user != undefined) {
                    setUserToStorage(user);
                    showUserInfo(user);
                }
            } else {
                setUserToStorage('');
            }
        } else {
            alert("请求出错！");
        }
    });
}