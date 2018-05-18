function setCookie(name, value, expireHours) {
    var exp = new Date();
    exp.setTime(exp.getTime() + expireHours * 60 * 60 * 1000); //exp过期时间 = 当前时间 +过期时间(秒)
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
}

function setStorage(name, admin) {
    window.localStorage.setItem(name, JSON.stringify(admin));
}

function storeAdmin(data) {
    //cookie token
    setCookie('algv', data.adminToken.token, 365 * 24);
    //local user
    setStorage('admin', data.admin);
}

function loginAdmin(username, password) {
    $.myLoading.load();
    $.ajax({
        type: 'get',
        url: '/admin/login',
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: 'username=' + username + '&password=' + $.md5(password),
        error: function (error, status) {
            $.myLoading.close();
            $.myAlert.alert("网络错误");
        },
        success: function (dd, status) {
            $.myLoading.close();
            if (dd.code == 0) {
                $.myAlert.alert("登录成功！");

                if ($('#remember-me').is(":checked")) {
                    window.localStorage.setItem('au', username);
                    window.localStorage.setItem('ap', password);
                } else {
                    window.localStorage.setItem('au', '');
                    window.localStorage.setItem('ap', '');
                }


                storeAdmin(dd.data);
                window.location = 'index.html';
            } else {
                $.myAlert.alert(dd.msg);
            }
        }
    });
}

function goLogin() {
    var username = $('#username').val();
    if (username == '' || username == undefined) {
        $.myAlert.alert("请填写用户名");
        $('#username').focus();
        return;
    }
    var password = $('#password').val();
    if (password == '' || password == undefined) {
        $.myAlert.alert("请填写密码");
        $('#password').focus();
        return;
    }

    username = username.trim();
    password = password.trim();

    loginAdmin(username, password);
}

function initUnameAndPwd() {
    var au = window.localStorage.getItem('au');
    var ap = window.localStorage.getItem('ap');
    if (au != undefined && au != '' && ap != undefined && ap != '') {
        $('#username').val(au);
        $('#password').val(ap);
        $('#remember-me').attr('checked', 'checked');
    }
}

$(document).ready(function () {

    // 风格切换
    $('.tpl-skiner-toggle').on('click', function () {
        $('.tpl-skiner').toggleClass('active');
    })
    $('.tpl-skiner-content-bar').find('span').on('click', function () {
        $('body').attr('class', $(this).attr('data-color'))
        saveSelectColor.Color = $(this).attr('data-color');
        // 保存选择项
        storageSave(saveSelectColor);
    })
    //登录点击
    $('.tpl-login-btn').on('click', goLogin);
    //初始化
    initUnameAndPwd();

})