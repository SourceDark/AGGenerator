/**
 * Created by Nettle on 2016/12/30.
 */

/**
 * 调用方式:
 *
 * <div ng-loading="status" ng-loading-empty="empty_info">
 *     正常显示的内容
 * </div>
 *
 * 参数:
 *
 * status: String 表示当前状态:
 *     'list' - 正常显示
 *     'fail' - 连接服务器失败
 *     'empty' - 返回为空
 *     'loading' - 载入中
 *
 * empty_info: String 当返回为空时显示的字符串(HTML代码片段)
 */

agbotApp.directive('ngLoading', ['$sce', function ($sce) {
    return {
        restrict: 'A',
        replace: true,
        transclude: true,
        scope: {
            status: '=ngLoading',
            empty: '=ngLoadingEmpty'
        },
        template: '<div>\
                <div class="display info" ng-if="status == \'loading\'"><i class="fa fa-spinner fa-spin" aria-hidden="true"></i> Loading.</div>\
                <div class="display error" ng-if="status == \'fail\'"><i class="fa fa-warning" aria-hidden="true"></i> Can\'t connect to server.</div>\
                <div class="display info" ng-if="status == \'empty\'" ng-bind-html="empty"></div>\
            <div ng-if="status==\'list\'"class="original_content" ng-transclude>\
            </div>\
        </div>',
        link: function (scope, element, attrs) {
            scope.empty = $sce.trustAsHtml(scope.empty);
        }
    }
}]);
