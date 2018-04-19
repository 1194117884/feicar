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

    //详情
    $('.nr_login').on('click', function () {
        goInfo();
    })
    //登录
    $('.nr_no_login').on('click', function () {
        login();
    })
    //出发本页clik
    $('.pet_bottom_nav_list li[type="' + thisPage + '"]').click();
    //加载个人信息
    loadUserInfo();
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

function getLoginUser() {

}

function goLoginWithBackUrl(localUrl) {
    window.location.href = 'login.html?cb=' + localUrl;
}

function login() {
    var localUrl = window.location.href;

    goLoginWithBackUrl(localUrl);
}

function goto(href) {
    window.location.href = href;
}


function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}


function goInfo() {
    window.location.href = 'userInfo.html';
}

function showUserInfo(user) {
    if (user == null || user == undefined) {//没登陆
        $('.nr_login').hide();
        $('.nr_no_login').show();
    } else {
        $('.nr_login img').attr('src', (user.headPic != '' && user.headPic != null) ? user.headPic : './img/no_login.png');
        $('.nr_login .pet_grzx_name').text(user.nickname);
        $('.nr_login .pet_grzx_map').text(user.phone);

        $('.nr_no_login').hide();
        $('.nr_login').show();
    }
}

function loadUserInfo() {

    var user = getUserFromStorage();

    showUserInfo(user);

    try {
        refreshUserInfo();
    } catch (e) {

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