<html ng-app="agbotApp">
<head>
    <title>REBOT</title>
    @include('agbot.layouts.header')
</head>
<body id="agbot">
    @include('agbot.layouts.navbar')
    <ui-view>

    </ui-view>
    <div style="display: none">
        @include('agbot.layouts.footer')
    </div>
</body>
</html>