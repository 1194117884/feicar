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
    window.location.href = href;
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