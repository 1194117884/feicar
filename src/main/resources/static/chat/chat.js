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
                    var session = new AV.Realtime({
                        appId: $.lc.constant.config.appId,
                        appKey: $.lc.constant.config.appKey,
                        region: 'cn'
                    });
                    $.lc.rtc.session.body = session;
                    _callBack(session);
                },
                close: function () {
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
                    $.lc.rtc.client.body.close();
                },
            },
            conversation: {
                create: function (_data, _callBack) {
                    $.lc.rtc.client.body.createConversation(_data).then(function (newConversation) {
                        _callBack(newConversation);
                    });
                },
                get: function (_conversationId, _callBack) {
                    $.lc.rtc.client.body.getConversation(_conversationId).then(function (conversation) {
                        _callBack(conversation);
                    });
                },
                query: function (_callBack) {
                    $.lc.rtc.client.body.getQuery()
                        .containsMembers([$.lc.rtc.client.body.clientId]).find().then(function (conversations) {
                        _callBack(conversations);
                    });
                }
            },
            message: {
                find: function () {

                },
                receive: function (_callBack) {
                    $.lc.rtc.client.body.on("message", function (msg, _conversation) {
                        _callBack(msg, _conversation);
                    });
                },
                send: function (_conversionId, _msg, _callBack) {
                    $.lc.rtc.conversation.get(_conversionId, function (conversation) {
                        conversation.send(new AV.TextMessage(_msg)).then(function (message) {
                            _callBack(message);
                        });
                    });
                }
            }
        }
    }
})