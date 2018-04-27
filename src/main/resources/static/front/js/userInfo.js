$(function () {

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }

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
        //头像点选
        $('#head_popup  input').click();
        $("#head_popup input").on("change", headPortraitListener);
    });
    //昵称点击
    $('.li_nickname').on('click', function () {
        editNickname();
    });
    //姓名点击
    $('.li_name').on('click', function () {
        editName();
    });
    //生日点击
    $('.li_birthday').on('click', function () {
        editBirthday();
    });
    //地址点击
    $('.li_address').on('click', function () {
        editAddress();
    });
    //退出
    $('.nr_logout').on('click', function () {
        $.myLoading.load();
        logOut(function (success) {
            $.myLoading.close();
            if (success) {
                refreshUserInfo();
                window.history.back();
            }
        });
    });

    //展示信息
    loadUserInfo();
});

function auto_resize() {
    //TODO:
}


function logOut(callBack) {
    $.ajax({
        type: "post",
        url: "/usc/logout",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: '{}',
        error: function (error, status) {
            callBack(false);
        },
        success: function (dd, status) {
            if (dd.code == 0) {
                callBack(true);
            } else {
                callBack(false);
            }
        }
    });
}


function editBirthday() {
    var $birthdayPop = $('#birthday_popup');

    var oldBirthday = $('.li_birthday div').text();

    //默认展示
    $($birthdayPop.find('.am-modal-hd')[0]).text("生日");
    $($birthdayPop.find('input')[0]).val(oldBirthday);

    editModalPop($birthdayPop, function (choose) {
        if (choose == 'confirm') {
            var newBirthday = $birthdayPop.find('input').val();
            if (newBirthday == "" || newBirthday == undefined) {

            } else {
                updUserBirthday(newBirthday);
            }
        }
    });
}


function editAddress() {
    var $addressPop = $('#nickname_popup');

    var oldAddress = $('.li_address div').text();

    //默认展示
    $($addressPop.find('.am-modal-hd')[0]).text("地址");

    $addressPop.find('input').attr("placeholder", oldAddress);
    $addressPop.find('input').attr("value", oldAddress);
    $addressPop.find('input').attr("maxlength", 100);


    editModalPop($addressPop, function (choose) {
        if (choose == 'confirm') {
            var newAddress = $addressPop.find('input').val();
            if (newAddress == "" || newAddress == undefined) {

            } else {
                updUserAddress(newAddress);
            }
        }
    });
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


function editName() {
    var $namePop = $('#nickname_popup');

    var oldName = $('.li_name div').text();

    //默认展示
    $($namePop.find('.am-modal-hd')[0]).text("姓名");

    $namePop.find('input').attr("placeholder", oldName);
    $namePop.find('input').attr("value", oldName);
    $namePop.find('input').attr("maxlength", 20);


    editModalPop($namePop, function (choose) {
        if (choose == 'confirm') {
            var newName = $namePop.find('input').val();
            if (newName == "" || newName == undefined) {

            } else {
                updUserName(newName);
            }
        }
    });
}


function editHeadPic() {
    editModalPop($('#head_popup'), function (choose) {
        if (choose == 'confirm') {
            var _file = $('#head_popup  input')[0].files[0];
            if (_file != undefined) {
                // var obServer = {
                //     next: function (res) {
                //         $.myLoading.close();
                //     },
                //     error: function (err) {
                //         $.myLoading.close();
                //     },
                //     complete: function (res) {
                //         $.myLoading.close();

                //         $.qiniu.gePhotoUrl(res.key, function (status, data) {
                //             if (status) {
                //                 console.log(data,111)
                //                 alert(1)
                //                 updUserHeadPic(data);
                //             } else {
                //                 $.myAlert.alert("获取文件地址错误！");
                //             }
                //         });
                //     }
                // };

                $.qiniu.getUpToken(function (success, token) {
                    if (success) {
                        $.myLoading.load();
                        // var subscription = qiniu.upload(
                        //     _file,//file
                        //     _file.name,//file name
                        //     token,//upload token
                        //     null,//
                        //     null//
                        // ).subscribe(obServer);
                        var Qiniu_UploadUrl = "http://up-z1.qiniup.com";  
                        var xhr = new XMLHttpRequest();  
                        xhr.open('POST', Qiniu_UploadUrl, true);  
                        var formData= new FormData();  
                        var key = _file.name
                        if (key !== null && key !== undefined){  
                            formData.append('key',key);  
                        }  
                        formData.append('token', token);  
                        formData.append('file', _file);  
                        xhr.onreadystatechange = function(response) {  
                            if (xhr.readyState == 4 && xhr.status == 200&& xhr.responseText != "") {  
                                var blkRet = JSON.parse(xhr.responseText);  
                                console.log(blkRet,22222222222222)  
                                updUserHeadPic('http://lf.datangleiyinsi.com/'+ blkRet.key);
                            } else if (xhr.status != 200 && xhr.responseText) {  
                                console.log("服务传输异常!!");  
                            }  
                        };  
                        xhr.send(formData);
                        
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

function updUserBirthday(birthday) {
    if (birthday == undefined || birthday == '') return;

    $('.li_birthday div').text(birthday);

    updUser({
        birthday: birthday
    });
}

function updUserAddress(address) {
    if (address == undefined || address == '') return;

    $('.li_address div').text(address);

    updUser({
        address: address
    });
}

function updUserNickname(nickname) {
    if (nickname == undefined || nickname == '') return;

    $('.li_nickname div').text(nickname);

    updUser({
        nickname: nickname
    });
}

function updUserName(name) {
    if (name == undefined || name == '') return;

    $('.li_nickname div').text(name);

    updUser({
        name: name
    });
}


function updUser(user) {
    $.myLoading.load();
    updUserInfo(user, function (scceess, data) {
        $.myLoading.close();
        if (scceess) {
            // window.location.reload();
            window.location.href = 'userCenter.html';
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
        //姓名
        var _name = user.name;
        if (_name != undefined && _name != '') {
            $('.li_name div').text(_name);
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
        var _birthday = user.birthday;
        if (_birthday != undefined && _birthday != '') {
            $('.li_birthday div').text(new Date(_birthday).format("yyyy-MM-dd"));
        }
        //地址
        var _address = user.address;
        if (_address != undefined && _address != '') {
            $('.li_address div').text(_address);
        }
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

function headPortraitListener(e) {
    var img = $('#head_popup img');
    if (window.FileReader) {
        var file = e.target.files[0];
        var reader = new FileReader();
        if (file && file.type.match('image.*')) {
            reader.readAsDataURL(file);
        } else {
            img.css('display', 'none');
            img.attr('src', '');
        }
        reader.onloadend = function (e) {
            img.attr('src', reader.result);
            base64head = reader.result;
        }
    }
}