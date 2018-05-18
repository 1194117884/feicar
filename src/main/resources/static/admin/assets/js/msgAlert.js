/**
 *
 *
 * 依赖jq，基于但不限于版本 jQuery v2.1.4
 *
 * */
$.extend({
    myMsgAlert: {
        constant: {
            id: 'my_jq_msg_alert_id'
        },
        alert: function () {
            var alertObj = $('#' + $.myMsgAlert.constant.id).attr("id");
            if (alertObj == undefined || alertObj == "") {
                console.log("append alert body~");
                //append
                var _alertHtml = '<audio id="' + $.myMsgAlert.constant.id + '" src="audio/new_msg.mp3" controls hidden="true"/>';
                $('body').append(_alertHtml);
            }
            //alert
            $('#' + $.myMsgAlert.constant.id)[0].play();
        },
        close: function () {
            $('#' + $.myMsgAlert.constant.id)[0].pause();
            $('#' + $.myMsgAlert.constant.id).remove();
        }
    }
})