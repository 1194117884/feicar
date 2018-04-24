$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    //头像点击
    $('.li_head').on('click', function () {
        editHeadPic();
    });
    $('#head_popup .am-modal-bd').on('click', function () {
        $('#head_popup  input').click();
    });
    //昵称点击
    $('.li_nickname').on('click', function () {
        editNickname();
    });

    //展示信息
    loadUserInfo();
});

function auto_resize() {
    //TODO:
}


function editNickname() {
    var $nicknamePop = $('#nickname_popup');

    var oldNickame = $('.li_nickname div').text();

    //默认展示
    $($nicknamePop.find('.am-modal-hd')[0]).text("昵称");

    $nicknamePop.find('input').attr("placeholder", oldNickame);
    $nicknamePop.find('input').attr("value", oldNickame);
    $nicknamePop.find('input').attr("maxlength", 20);


    editModalPop($nicknamePop, function (choose) {
        if (choose == 'confirm') {
            var newNickname = $nicknamePop.find('input').val();
            if (newNickname == "" || newNickname == undefined) {
                
            } else {
                updUserNickname(newNickname);
            }
        }
    });
}


function editHeadPic() {
    editModalPop($('#head_popup'), function (choose) {
        if (choose == 'confirm') {
            var _file = $('#head_popup  input')[0].files[0];
            if (_file != undefined) {

                $.qiniu.getUpToken(function (success, token) {
                    if (success) {
                        $.myLoading.load();
                        // var observable = qiniu.upload(_file, _file.name, token, null, null);//放弃保存文件名形式
                        var observable = qiniu.upload(
                            _file,//file
                            _file.name,//file name
                            token,//upload token
                            null,//
                            null//
                        ).subscribe({
                            next: function (res) {
                                console.log(JSON.stringify(res));
                                $.myLoading.close();
                            },
                            error: function (err) {
                                console.log(JSON.stringify(err));
                                $.myLoading.close();
                            },
                            complete: function (res) {
                                console.log(JSON.stringify(res));
                                $.myLoading.close();

                                $.qiniu.gePhotoUrl(res.key, function (status, data) {
                                    if (status) {
                                        updUserHeadPic(data);
                                    } else {
                                        $.myAlert.alert("获取文件地址错误！");
                                    }
                                });
                            }
                        });
                    } else {
                        $.myAlert.alert("上传授权失败！");
                    }
                });
            } else {
                $.myAlert.alert("请先选择文件！");
            }
        }
    });
}

function editModalPop(obj, callBack) {
    $(obj).modal({
        onConfirm: function (e) {
            callBack('confirm');
        },
        onCancel: function (e) {
            callBack('cancel');
        }
    });
}

function updUserHeadPic(img) {
    if (img == undefined || img == '') return;

    $('.li_head img').attr('src', img);

    updUser({
        headPic: img
    });
}

function updUserNickname(nickname) {
    if (nickname == undefined || nickname == '') return;

    $('.li_nickname div').text(nickname);

    updUser({
        nickname: nickname
    });
}


function updUser(user) {
    $.myLoading.load();
    updUserInfo(user, function (scceess, data) {
        $.myLoading.close();
        if (scceess) {
            window.location.reload();
        } else {
            $.myAlert.alert("修改失败！");
        }
    });
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
        $.myAlert.alert("登陆异常，请重新登陆");
        window.location.href = 'userCenter.html';
    } else {
        //头像
        var _headPic = user.headPic;
        if (_headPic != undefined && _headPic != '') {
            $('.li_head div img').attr('src', _headPic);
        }
        //昵称
        var _nickname = user.nickname;
        if (_nickname != undefined && _nickname != '') {
            $('.li_nickname div').text(_nickname);
        }
        //用户名
        var _username = user.username;
        if (_username != undefined && _username != '') {
            $('.li_username div').text(_username);
        }
        //手机号
        var _phone = user.phone;
        if (_phone != undefined && _phone != '') {
            $('.li_phone div').text(_phone);
        }
        //生日

        //地址

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
