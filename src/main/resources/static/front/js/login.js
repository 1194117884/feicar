$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    //验证码
    $('.login_vt_img').on('click', getNewImage);
    //发送验证码
    $('.send-code').on('click', sendCode);
    //登陆
    $('.send-login').on('click', requestLogin);
    //防止推出重新重发验证码
    checkCodeSendInfo();
});

function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}

function getNewImage() {
    $('.login_vt_img').unbind('click');

    $('.login_vt_img').attr('src', '/api/vst?time=' + Date.parse(new Date()));

    $('.login_vt_img').bind('click', getNewImage);
}


function getNextSendTime() {
    return localStorage.getItem('vclt');
}

function getCanSendCode() {
    var nextSendDate = getNextSendTime();
    if (nextSendDate != undefined && new Date().getTime() - nextSendDate <= 0) {
        return false;
    } else {
        return true;
    }
}

function checkCodeSendInfo() {
    //TODO
}

function setNextSendTime() {
    var nextTime = new Date().getTime() + 1000 * 120;

    localStorage.setItem('vclt', nextTime);
}

function sendSmsCode(token, phone, callBack) {
    $.ajax({
        type: "post",
        url: "/api/sms/code?type=user_phone_login_and_register&phone=" + phone + "&token=" + token,
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: '',
        error: function (error, status) {
            callBack(false, null);
        },
        success: function (dd, status) {
            if (dd.code == 0) {
                callBack(true, null);
            } else {
                callBack(false, dd.msg);
            }
        }
    });

}


function storeUserInfo(data) {
    console.log(data);

    //cookie token
    setCookie('ulgv', data.userToken.token, 365 * 24);
    //local user
    setStorage('user', data.user);
}

function requestLogin() {
    //phone
    var _phone = $('#user-name').val();
    if (_phone == undefined || _phone == '') {
        alert('请填写手机号！');
        $('#user-name').focus();
        return;
    }
    if (_phone.length != 11) {
        alert('请填写正确手机号！');
        $('#user-name').focus();
        $('#user-name').select();
        return;
    }
    //code
    var _code = $('#user-vt').val();
    if (_code == undefined || _code == '') {
        alert('请填写验证码！');
        $('#user-vt').focus();
        return;
    }

    $('.send-login').unbind('click');
    sendLogin(_phone, _code, function (status, data) {
        $('.send-login').on('click', requestLogin);
        if (status) {
            storeUserInfo(data.data);

            window.history.back();
        } else {
            if (data == undefined || data == null || data.msg != undefined) {
                alert(data.msg);
            } else {
                alert('服务异常！');
            }
        }
    });

}

function getRequestBean(phone, code) {
    var request = {
        phone: phone,
        code: code
    };

    return request;
}

function sendLogin(phone, code, callBack) {
    $.ajax({
        type: "post",
        url: "/usc/ral",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: JSON.stringify(getRequestBean(phone, code)),
        error: function (error, status) {
            callBack(false, null);
        },
        success: function (dd, status) {
            if (dd.code == 0) {
                callBack(true, dd);
            } else {
                callBack(false, dd);
            }
        }
    });

}


function sendCode() {
    //phone
    var _phone = $('#user-name').val();
    if (_phone == undefined || _phone == '') {
        alert('请填写手机号！');
        $('#user-name').focus();
        return;
    }
    if (_phone.length != 11) {
        alert('请填写正确手机号！');
        $('#user-name').focus();
        $('#user-name').select();
        return;
    }
    //token
    var _token = $('#user-token').val();
    if (_token == undefined || _token == '' || _token.length != 4) {
        alert('请填写验证码！');
        $('#user-token').focus();
        return;
    }


    if (!getCanSendCode()) {
        alert('2分钟后才能重发');
        return;
    }


    $('.send-code').unbind('click');
    sendSmsCode(_token, _phone, function (status, msg) {
        $('.send-code').on('click', sendCode);
        if (status) {
            $('.send-code').fadeOut();
            $('.token-div').fadeOut();

            $('.send-login').fadeIn();
            $('.code-div').fadeIn();

            setNextSendTime();
        } else {
            alert(msg);
        }
    });

}

function setCookie(name, value, expireHours) {
    var exp = new Date();
    exp.setTime(exp.getTime() + expireHours * 60 * 60 * 1000); //exp过期时间 = 当前时间 +过期时间(秒)
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
}


function setStorage(name, user) {
    window.localStorage.setItem(name, JSON.stringify(user));
}