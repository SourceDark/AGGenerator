<div id="sensor">
    <div class="config">
        <h4>配置说明</h4>
        <p>添加节点</p>
        <pre>+[id],[type],[ip1],[ip2],...</pre>
        <pre>type: center,sensor,switch,host</pre>
        <p>添加链接</p>
        <pre>-[source_id],[target_id]</pre>
        <p>样例</p>
        <pre>+Switch1,switch,192.0.0.0
+host1,Host,192.1.1.1
-Switch1,host1</pre>
        <h4>配置信息</h4>
        <textarea class="config_text" ng-model="config"></textarea>
        <button ng-click="apply()">预览</button>
        <button ng-click="save()">保存</button>
    </div>
    <div class="graph">
        <tree-graph nodes="nodes" links="links" options="options"></tree-graph>
    </div>
</div>