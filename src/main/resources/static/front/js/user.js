var _flag = 'user';
var _token = 'ulgv';

function getUserFromStorage() {
    var token = getFromCookie(_token);
    if (token == null) return null;

    var item = window.localStorage.getItem(_flag);

    return JSON.parse(item);
}

function setUserToStorage(userInfo) {
    if (userInfo != null && userInfo != undefined) {
        window.localStorage.removeItem(_flag);
        window.localStorage.setItem(_flag, JSON.stringify(userInfo));
    }
}


function getUserFromNet(callBack) {
    $.ajax({
        type: "get",
        url: "/usc/info",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: '',
        error: function (error, status) {
            callBack(false, null);
        },
        success: function (value, status) {
            callBack(true, value);
        }
    })
}

function updUserInfo(user, callBack) {
    $.ajax({
        type: "put",
        url: "/usc/info",
        dataType: 'json',
        contentType: 'application/json',
        timeout: 10000,
        data: JSON.stringify(user),
        error: function (error, status) {
            callBack(false, null);
        },
        success: function (value, status) {
            callBack(true, value);
        }
    })
}


function getFromCookie(name) {
    if (name == undefined || name == null || name == '')
        return null;
    var cookies = document.cookie;
    if (cookies == null || cookies == undefined)
        return null;
    var split = cookies.split(";");
    for (var i = 0; i < split.length; i++) {
        var keyValue = split[i];
        if (keyValue == null || keyValue == undefined)
            continue;
        var kv = keyValue.trim().split("=");
        if (kv[0] == name)
            return kv[1];
    }
    return null;
}