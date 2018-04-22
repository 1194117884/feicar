/**
 *
 *
 * 依赖jq，基于但不限于版本 jQuery v2.1.4
 *
 * */
$.extend({
    qiniu: {
        constant: {
            upTokenUrl: '/qiniu/token',
            photoUrl: '/qiniu/photo'
        },
        getUpToken: function (callBack) {
            $.ajax({
                type: "get",
                url: $.qiniu.constant.upTokenUrl,
                dataType: 'json',
                contentType: 'application/json',
                timeout: 10000,
                data: '',
                error: function (error, status) {
                    callBack(false, null);
                },
                success: function (value, status) {
                    if (value != undefined && value.data != '' && value.data != undefined) {
                        callBack(true, value.data);
                    }
                }
            })
        },
        gePhotoUrl: function (key, callBack) {
            $.ajax({
                type: "get",
                url: $.qiniu.constant.photoUrl + '/' + key,
                dataType: 'json',
                contentType: 'application/json',
                timeout: 10000,
                data: '',
                error: function (error, status) {
                    callBack(false, null);
                },
                success: function (value, status) {
                    if (value != undefined && value.data != '' && value.data != undefined) {
                        callBack(true, value.data);
                    }
                }
            })
        }
    }
})