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
        go('nr_login');
    })
    //登录
    $('.nr_no_login').on('click', function () {
        go('nr_no_login');
    })

    //个人信息
    $('.my_info').on('click', function () {
        go('my_info');
    });
    //卡券
    $('.my_card').on('click', function () {
        go('my_card');
    });
    //试驾
    $('.my_reserve').on('click', function () {
        go('my_reserve');
    });
    //询价
    $('.my_inquiry').on('click', function () {
        go('my_inquiry');
    });
    //咨询
    $('.my_request').on('click', function () {
        go('my_request');
    });
    //关于
    $('.ab_roewe').on('click', function () {
        go('ab_roewe');
    });
    $('.ab_dz_roewe').on('click', function () {
        go('ab_dz_roewe');
    });


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


function go(flag) {

    var _href = 'userCenter.html';
    switch (flag) {
        case 'nr_login':
            _href = 'userInfo.html';
            break;
        case 'nr_no_login':
            _href = 'login.html';
            break;
        case 'my_info':
            _href = 'userInfo.html';
            break;
        case 'my_card':
            _href = 'cardList.html';
            break;
        case 'my_reserve':
            _href = 'reserveList.html';
            break;
        case 'my_inquiry':
            _href = 'inquiryList.html';
            break;
        case 'my_request':
            _href = 'requestList.html';
            break;
        case 'ab_roewe':
            _href = 'aboutRoewe.html';
            break;
        case 'ab_dz_roewe':
            _href = 'aboutDzRoewe.html';
            break;
    }

    var user = getUserFromStorage();
    if (user != undefined && user != null && user != '') {
        window.location.href = _href;
    } else {
        if (_href == 'aboutRoewe.html' ||
            _href == 'aboutDzRoewe.html') {
            window.location.href = _href;
            return;
        }

        if (_href != 'login.html') {
            alert("请先登陆！");
        }
        window.location.href = 'login.html';
    }


}

function showUserInfo(user) {
    if (user == null || user == undefined || user == "") {//没登陆
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
        console.error(e);
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