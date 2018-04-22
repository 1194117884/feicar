/**
 *
 *
 * 依赖jq，基于但不限于版本 jQuery v2.1.4
 * 依赖amazeui，基于但不限于版本 Amaze UI v2.4.2
 *
 * */
$.extend({
    myLoading: {
        constant: {
            id: 'my_jq_alert_id'
        },
        load: function (_content) {
            var alertObj = $('body #' + $.myAlert.constant.id).text();
            if (alertObj == undefined || alertObj == "") {
                console.log("append alert body~");
                //append
                var _alertHtml = ' <div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="' + $.myLoading.constant.id + '">\n' +
                    '        <div class="am-modal-dialog">\n' +
                    '            <div class="am-modal-hd">正在载入...</div>\n' +
                    '            <div class="am-modal-bd">\n' +
                    '                <span class="am-icon-spinner am-icon-spin"></span>\n' +
                    '            </div>\n' +
                    '        </div>\n' +
                    '    </div>';
                $('body').append(_alertHtml);
            }
            //loading
            $('#' + $.myAlert.constant.id).modal();
        },
        close: function (_obj) {
            $('#' + $.myAlert.constant.id).modal('close');
        }
    }
})