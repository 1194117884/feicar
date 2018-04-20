/**
 *
 *
 * 依赖jq，基于但不限于版本 jQuery v2.1.4
 * 依赖amazeui，基于但不限于版本 Amaze UI v2.4.2
 *
 * */
$.extend({
    myAlert: {
        constant: {
            id: 'my_jq_alert_id'
        },
        alert: function (_content) {
            var alertObj = $('body #' + $.myAlert.constant.id).text();
            if (alertObj == undefined || alertObj == "") {
                console.log("append alert body~");
                //append
                var _alertHtml = '<div class="am-modal am-modal-alert" tabindex="-1" id="' + $.myAlert.constant.id + '">\n' +
                    '            <div class="am-modal-dialog" style="border-radius: 100px;">\n' +
                    '                <div class="am-modal-bd" style="padding: 0px; border: 0px;">' + _content + '</div>\n' +
                    '            </div>\n' +
                    '        </div>';
                $('body').append(_alertHtml);
            } else {
                console.log("change alert info~");
                var _innerHtml = '<div class="am-modal-dialog" style="border-radius: 100px;">\n' +
                    '                <div class="am-modal-bd" style="padding: 0px; border: 0px;">' + _content + '</div>\n' +
                    '            </div>\n';
                $('#' + $.myAlert.constant.id).html(_innerHtml);
            }
            //alert
            $('#' + $.myAlert.constant.id).modal();
        },
        close: function (_obj) {
            $('#' + $.myAlert.constant.id).modal('close');
        }
    }
})