<!-- Score -->
<p>
    <span class="key">Score: </span>
    <span>{{network.score}}</span>
</p>
<!-- Score History -->
<p>
    <span class="key">Score History (Last Week): </span>
</p>
<table class="table mid">
    <thead>
        <tr>
            <td>Date</td>
            <td>Score</td>
        </tr>
    </thead>
    <tbody>
        <tr ng-repeat="scoreHistory in network.scoreHistory">
            <td ng-bind="scoreHistory.date | date:'yyyy-MM-dd HH:mm:ss'"></td>
            <td>{{scoreHistory.score}}</td>
        </tr>
    </tbody>
</table>