/**
 * web 组件
 */
(function(){
    var abc = 5;

    // 一般首字母大写是构造函数，小写是普通函数
    function TabView(cfg) {
        this.a = cfg.a;
        this.b = cfg.b;
    }

    TabView.prototype = {
        c: function () {
            abc++;
        },

        d:function () {
            abc--;
        }
    }

    // 把需要用到的变量挂在到window作用域下
    window.TabView = TabView;

}
)();