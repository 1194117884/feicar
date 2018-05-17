/**
 *
 * 依赖jq，基于但不限于版本 jQuery v2.1.4
 *
 * */
$.extend({
    lc: {
        constant: {
            server: '/api/lc/rtm',
            config: {}
        },
        init: function (_callBack) {
            console.log("init");
            $.ajax({
                type: "get",
                url: $.lc.constant.server,
                dataType: 'json',
                contentType: 'application/json',
                timeout: 10000,
                data: '',
                error: function (error, status) {
                    _callBack(false);
                },
                success: function (value, status) {
                    $.lc.constant.config = value.data;
                    _callBack(true);
                }
            })
        },
        rtc: {
            session: {
                body: {},
                create: function (_callBack) {
                    console.log("create");
                    var session = new AV.Realtime({
                        appId: $.lc.constant.config.appId,
                        appKey: $.lc.constant.config.appKey,
                        region: 'cn'
                    });
                    $.lc.rtc.session.body = session;
                    _callBack(session);
                },
                close: function () {
                    console.log("close");
                    $.lc.rtc.client.body.close();
                },
            },
            client: {
                body: {},
                create: function (_clientId, _callBack) {
                    $.lc.rtc.session.body.createIMClient(_clientId).then(
                        function (client) {
                            $.lc.rtc.client.body = client;
                            _callBack(client);
                        }
                    );
                },
                close: function () {
                    console.log("close");
                    $.lc.rtc.client.body.close();
                },
            },
            conversation: {
                body: {},
                create: function (_data, _callBack) {
                    var _conversationId = $.lc.rtc.conversation.body.id == undefined ? '' : $.lc.rtc.conversation.body.id;
                    if (localStorage.getItem('_conversationId') != null)
                        _conversationId = localStorage.getItem('_conversationId');

                    $.lc.rtc.client.body.getConversation(_conversationId).then(function (conversation) {
                        if (conversation == null) {
                            $.lc.rtc.client.body.createConversation(_data).then(function (newConversation) {
                                $.lc.rtc.conversation.body = newConversation;
                                localStorage.setItem('_conversationId', newConversation.id);
                                _callBack(newConversation);
                            });
                        } else {
                            $.lc.rtc.conversation.body = conversation;
                            localStorage.setItem('_conversationId', conversation.id);
                            _callBack(conversation);
                        }
                    });


                },
                find: function () {
                    console.log("msg find");

                },
                receive: function (_callBack) {
                    console.log("msg receive");
                    $.lc.rtc.client.body.on("message", function (msg, _conversation) {
                        $.lc.rtc.conversation.body = _conversation;
                        localStorage.setItem('_conversationId', _conversation.id);
                        _callBack(msg, _conversation);
                    });
                },
                send: function (_msg, _callBack) {
                    console.log("msg send");
                    $.lc.rtc.conversation.body.send(new AV.TextMessage(_msg)).then(
                        function (messeage) {
                            _callBack(messeage);
                        }
                    );
                }
            }
        }
    }
})