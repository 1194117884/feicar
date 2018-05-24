var thisPage = 'gcfw';

$(function () {
    // 动态计算新闻列表文字样式
    auto_resize();

    $(window).resize(function () {
        auto_resize();
    });

    $('.pet_bottom_nav_list li').on('click', function () {
        hoverNav(this);
        goNextPage($(this).attr("type"));
    })


    //出发本页clik
    $('.pet_bottom_nav_list li[type="' + thisPage + '"]').click();

    //初始化jsapi
    // initWechatSdk();

    //首页轮播s
    getHotImg();
});

function hoverNav(_nav) {
    var _li = $(_nav).find("a");
    var _oldClass = $(_li).attr('class');
    if (_oldClass.indexOf('pet_bottom_nav_item_active') > -1)
        return;
    $(_li).attr('class', _oldClass + "_active");
}

function goNextPage(type) {
    if (type == thisPage) return;

    if ("zxtt" == type) {
        goto('articleList.html');
        return;
    }
    if ("gcfw" == type) {
        goto('buyService.html');
        return;
    }
    if ("grzx" == type) {
        goto('userCenter.html');
        return;
    }

}


function goto(href) {
    var _aftStr = new Date().getTime();
    window.location.href = href + '?i=' + _aftStr;
}


function auto_resize() {
    $('.pet_list_one_nr').height($('.pet_list_one_img').height());
}

function getHotImg() {
    $.ajax({
        type: "get",
        url: "/bsc/hot_slider",
        data: "",
        success: function (data, status) {
            if (data.code == 0) {
                var htmlText = '<ul class="am-slides">';
                $.each(data.data, function (i, v) {
                    htmlText += '<li onclick="goto(\'' + v.href + '\')">';
                    htmlText += '<img src="' + v.img + '">';
                    htmlText += '<div class="pet_slider_font">';
                    htmlText += '<span>' + v.title + '</span>';
                    htmlText += '<span class="pet_slider_emoji">' + v.introduction + '</span>';
                    htmlText += '</div>';
                    htmlText += '<div class="pet_slider_shadow"></div>';
                    htmlText += '</li>';
                })
                htmlText += '</ul>';
                $(".bs_body_slider").html(htmlText);

                $('.am-slider').flexslider();
            } else {

            }
        },
        error: function (error, status) {

        }
    })
}


function initWechatSdk() {
    $.ajax({
        type: "get",
        url: "/api/wechat/jssdk_config",
        data: "url=" + location.href,
        success: function (data, status) {
            if (data.code == 0) {
                var config = data.data;

                if (window.wx){
                    wx.onMenuShareTimeline({
                        title: '哈哈', // 分享标题
                        link: location.href, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        imgUrl: 'http://a.datangleiyinsi.com/IMG_20180417_082558.jpg', // 分享图标
                        success: function () {
                            // 用户点击了分享后执行的回调函数
                        },
                        fail: function (err) {
                            console.log(err)
                        }
                    });

                    wx.onMenuShareAppMessage({
                        title: '哈哈', // 分享标题
                        link: location.href, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        imgUrl: 'http://lf.datangleiyinsi.com/IMG_20180417_082558.jpg', // 分享图标
                        type: '', // 分享类型,music、video或link，不填默认为link
                        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () {
                            // 用户点击了分享后执行的回调函数
                        },
                        fail: function (err) {
                            console.log(err)
                        }
                    });
                    /*
                * 注意：
                * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
                * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
                */
                    wx.config({
                        beta: true, //注入wx.invoke和wx.on方法。wx.invoke用于调用未开放的由页面主动调用的JS接口，wx.on用于调用未开放的监听类JS接口。
                        debug: true,
                        appId: config.appId,
                        timestamp: config.timestamp,
                        nonceStr: config.nonceStr,
                        signature: config.signature,
                        jsApiList: [
                            'onMenuShareTimeline',
                            'onMenuShareAppMessage'
                        ]
                    });
                }
            } else {

            }
        },
        error: function (error, status) {

        }
    })
}