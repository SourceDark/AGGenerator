<div>
    <div class="infobar">
        <h3>Selection Detail</h3>
        <div ng-if="selection" class="infobar-content">
            <p>ID: <span ng-bind="selection.id"></span></p>
            <p>Type: <span ng-bind="selection.type"></span></p>
            <div ng-if="selection.type == 'host'">
                <p ng-if="!selection.cves.length">该主机没有漏洞</p>
                <ul ng-repeat="cve in selection.cves">
                    <li>
                        <a ui-sref="cve({cve_id:cve.id})" ng-bind="cve.id"></a>
                    </li>
                </ul>
            </div>
        </div>
        <div ng-if="!selection" class="infobar-content">
            <p>Select an element in the visualization.</p>
        </div>
    </div>
    <svg id="topology-graph">
        <defs>
            <g id="host" transform="translate(-26.5 -38.5)scale(0.1,0.1)">
                <path id="path22" fill="#02709F" d="M0.002,770.005h401.689V128.589H0.002V770.005z"/>
                <path id="path24" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M401.692,128.589H0.002v641.416h401.689"/>
                <path id="path26" fill="#02709F" d="M401.692,770.005l128.311-128.594V0h-365.83L0.002,128.589h401.689V770.005"/>
                <path id="path28" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M401.692,770.005l128.311-128.594V0h-365.83 L0.002,128.589h401.689V770.005z"/>
                <path id="path30" fill="#02709F" d="M401.692,128.589L530.002,0"/>
                <path id="path32" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M401.692,128.589L530.002,0"/>
                <path id="path34" fill="#02709F" d="M6.506,180.591h391.572"/>
                <path id="path36" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M6.506,180.591h391.572"/>
                <path id="path38" fill="#02709F" d="M401.35,184.634L526.975,57.075"/>
                <path id="path40" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M401.35,184.634L526.975,57.075"/>
                <path id="path42" fill="#02709F" d="M35.774,337.583h138.682v-32.715H35.774V337.583z"/>
                <path id="path44" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M35.774,337.583h138.682v-32.715H35.774V337.583z"/>
                <path id="path46" fill="#02709F" d="M223.401,337.583h138.682v-32.715H223.401V337.583z"/>
                <path id="path48" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M223.401,337.583h138.682v-32.715H223.401V337.583z"/>
            </g>
            <g id="cloud" transform="translate(-40 -24)scale(0.1,0.1)">
                <path id="path22" fill="#FFFFFF" d="M283.298,68.218C148.123,41.206,76.78,110.576,88.054,170.532l0.835,3.242
					C-16.384,191.699,11.15,316.313,61.76,316.313l4.932-0.215c-12.764,58.457,110.435,118.008,179.058,88.379l4.419-4.941
					c24.463,52.578,111.479,61.465,197.598,63.711c74.414,1.816,118.057-4.063,149.893-36.875l4.98,1.504
					c119.316,9.707,173.73-72.969,147.471-132.153l6.553-1.875c40.693-10.635,45.273-104.751-24.531-118.662l1.348-3.599
					c30.303-61.23-41.152-118.882-140.273-108.013l-6.904-3.652C515.442-4.419,311.077,6.055,287.395,69.707L283.298,68.218"/>
                <path id="path24" fill="none" stroke="#02709F" stroke-width="3.9999" d="M283.298,68.218
					C148.123,41.206,76.78,110.576,88.054,170.532l0.835,3.242C-16.384,191.699,11.15,316.313,61.76,316.313l4.932-0.215
					c-12.764,58.457,110.435,118.008,179.058,88.379l4.419-4.941c24.463,52.578,111.479,61.465,197.598,63.711
					c74.414,1.816,118.057-4.063,149.893-36.875l4.98,1.504c119.316,9.707,173.73-72.969,147.471-132.153l6.553-1.875
					c40.693-10.635,45.273-104.751-24.531-118.662l1.348-3.599c30.303-61.23-41.152-118.882-140.273-108.013l-6.904-3.652
					C515.442-4.419,311.077,6.055,287.395,69.707L283.298,68.218L283.298,68.218z"/>
            </g>
            <g id="switch" transform="translate(-40 -27.5)scale(0.1,0.1)">
                <defs>
                    <polyline id="SVGID_1_" points="15,15.002 785.005,15.002 785.005,535.002 15,535.002 15,15.002 				"/>
                </defs>
                <clipPath id="SVGID_2_">
                    <use xlink:href="#SVGID_1_"  overflow="visible"/>
                </clipPath>
                <g clip-path="url(#SVGID_2_)">
                    <path id="path22" fill="#087BBB" d="M784.79,167.922c0,82.207-171.387,148.633-382.891,148.633
            c-211.353,0-382.749-66.426-382.749-148.633v217.773c0,82.344,171.396,148.77,382.749,148.77
            c211.504,0,382.891-66.426,382.891-148.77V167.922"/>
                    <path id="path24" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M784.79,167.922
            c0,82.207-171.387,148.633-382.891,148.633c-211.353,0-382.749-66.426-382.749-148.633v217.773
            c0,82.344,171.396,148.77,382.749,148.77c211.504,0,382.891-66.426,382.891-148.77V167.922z"/>
                    <path id="path26" fill="#087BBB" d="M401.899,316.555c211.504,0,382.891-66.426,382.891-148.633
            c0-82.222-171.387-148.779-382.891-148.779c-211.353,0-382.749,66.558-382.749,148.779
            C19.15,250.129,190.547,316.555,401.899,316.555"/>
                    <path id="path28" fill="none" stroke="#FFFFFF" stroke-width="3.9999" d="M401.899,316.555
            c211.504,0,382.891-66.426,382.891-148.633c0-82.222-171.387-148.779-382.891-148.779
            c-211.353,0-382.749,66.558-382.749,148.779C19.15,250.129,190.547,316.555,401.899,316.555z"/>
                    <path id="path30" fill="#FFFFFF" d="M314.575,109.714l31.582,47.373l-119.521,27.69l26.045-21.758L68.027,131.462l46.304-34.658
            l178.213,30.122L314.575,109.714"/>
                    <path id="path32" fill="#FFFFFF" d="M482.681,224.651l-21.66-48.848l107.988-21.489l-18.73,16.675l179.668,30.723
            l-43.281,34.502l-178.457-33.643L482.681,224.651"/>
                    <path id="path34" fill="#FFFFFF" d="M423.872,83.826l120.547-33.047l1.445,51.772l-30.117-5.703l-58.828,48.843l-56.279-8.218
            l60.85-47.764L423.872,83.826"/>
                    <path id="path36" fill="#FFFFFF" d="M371.919,269.27l-114.854,21.484l-4.385-53.222l33.047,7.305l63.242-54.053l56.016,9.453
            l-67.451,58.926L371.919,269.27"/>
                    <path id="path38" fill="#FFFFFF" d="M408.032,417.971l65.449,51.494l30.313-0.361l0.039-13.213l69.258,24.365l-69.258,24.668
            l-0.039-14.033l-39.316-0.02l-74.912-58.242l-74.619,58.242l-39.473,0.02l-0.02,14.033l-69.414-24.668l69.414-24.365
            l0.02,13.213l30.449,0.361l65.4-51.494l-65.4-51.172l-30.449,0.068l-0.02,13.477l-69.414-24.365l69.414-24.648l0.02,13.721
            l39.473,0.029l74.619,58.525l74.912-58.525l39.316-0.029l0.039-13.721l69.258,24.648l-69.258,24.365l-0.039-13.477
            l-30.313-0.068L408.032,417.971"/>
                </g>
            </g>
            <g id="sensor" transform="translate(-28.5 -39)scale(0.1,0.1)">
                <path id="path14" fill="#087BBB" d="M570.002,330.601l-97.49,92.324H0.002l122.402-92.324H570.002"/>
                <path id="path16" fill="none" stroke="#FFFFFF" stroke-width="4" d="M570.002,330.601l-97.49,92.324H0.002l122.402-92.324H570.002z
                    "/>
                <path id="path18" fill="#78B3DA" d="M0.002,779.995h472.51v-357.07H0.002V779.995z"/>
                <path id="path20" fill="none" stroke="#FFFFFF" stroke-width="4" d="M0.002,779.995h472.51v-357.07H0.002V779.995z"/>
                <path id="path22" fill="#328FC7" d="M560.256,692.358V335.425l-90.645,87.5v357.07L560.256,692.358"/>
                <path id="path24" fill="none" stroke="#FFFFFF" stroke-width="4" d="M560.256,692.358V335.425l-90.645,87.5v357.07L560.256,692.358
                    z"/>
                <path id="path26" fill="#087BBB" d="M570.002,0l-97.49,92.456H0.002L122.405,0H570.002"/>
                <path id="path28" fill="none" stroke="#FFFFFF" stroke-width="4" d="M570.002,0l-97.49,92.456H0.002L122.405,0H570.002z"/>
                <path id="path30" fill="#087BBB" d="M0.002,426.382h472.51V92.456H0.002V426.382z"/>
                <path id="path32" fill="none" stroke="#FFFFFF" stroke-width="4" d="M0.002,426.382h472.51V92.456H0.002V426.382z"/>
                <path id="path34" fill="#087BBB" d="M560.256,338.628V4.688l-90.645,87.769v333.926L560.256,338.628"/>
                <path id="path36" fill="none" stroke="#FFFFFF" stroke-width="4" d="M560.256,338.628V4.688l-90.645,87.769v333.926
                    L560.256,338.628z"/>
                <path id="path38" fill="#FFFFFF" d="M192.317,251.787h-70.684v-18.242l-26.67,26.934l26.67,26.958v-18.252h70.684V251.787"/>
                <path id="path40" fill="#FFFFFF" d="M232.317,308.999v70.723h-18.271l26.963,26.523l26.865-26.523h-18.281v-70.723H232.317"/>
                <path id="path42" fill="#FFFFFF" d="M232.317,213.955v-70.718h-18.271l26.963-26.655l26.865,26.655h-18.281v70.718H232.317"/>
                <path id="path44" fill="#FFFFFF" d="M289.612,269.185h70.762v18.252l26.729-26.958l-26.729-26.934v18.242h-70.762V269.185"/>
                <path id="path46" fill="none" stroke="#FFFFFF" stroke-width="3.4775" d="M354.885,373.921
                    c-13.438,13.906-74.443-24.57-136.309-85.977C156.555,226.509,117.2,165.283,130.491,151.25
                    c13.311-14.038,74.307,24.58,136.172,86.006C328.547,298.657,368.069,359.917,354.885,373.921z"/>
                <path id="path48" fill="none" stroke="#FFFFFF" stroke-width="3.4775" d="M351.262,150.181
                    c13.926,13.286-24.482,74.199-86.094,136.143c-61.494,61.797-122.803,101.279-136.836,87.988
                    c-13.936-13.281,24.59-74.219,86.084-136.162C275.901,176.343,337.327,137.002,351.262,150.181z"/>
                <path id="path50" fill="#BFBEBE" d="M270.022,301.567c23.33-16.357,28.887-48.564,12.646-71.86
                    c-16.377-23.311-48.506-28.994-71.982-12.651c-23.301,16.338-28.994,48.457-12.617,71.738
                    C214.417,312.222,246.555,317.915,270.022,301.567"/>
                <path id="path52" fill="none" stroke="#FFFFFF" stroke-width="14.25" d="M220.413,544.116H101.584"/>
                <path id="path54" fill="none" stroke="#FFFFFF" stroke-width="14.25" d="M265.051,505.678h131.338"/>
                <path id="path56" fill="#FFFFFF" d="M365.559,460.796v86.66l52.109-41.68L365.559,460.796"/>
                <path id="path58" fill="none" stroke="#FFFFFF" stroke-width="1.9" d="M365.559,460.796v86.66l52.109-41.68L365.559,460.796z"/>
                <path id="path60" fill="#FFFFFF" d="M113.743,588.257V501.85l-52.236,41.445L113.743,588.257"/>
                <path id="path62" fill="none" stroke="#FFFFFF" stroke-width="1.9" d="M113.743,588.257V501.85l-52.236,41.445L113.743,588.257z"/>
                <path id="path64" fill="none" stroke="#FFFFFF" stroke-width="14.25" d="M220.413,700.678H101.584"/>
                <path id="path66" fill="none" stroke="#FFFFFF" stroke-width="14.25" d="M265.051,661.987h131.338"/>
                <path id="path68" fill="#FFFFFF" d="M365.559,617.124v86.66l52.109-41.699L365.559,617.124"/>
                <path id="path70" fill="none" stroke="#FFFFFF" stroke-width="1.9" d="M365.559,617.124v86.66l52.109-41.699L365.559,617.124z"/>
                <path id="path72" fill="#FFFFFF" d="M113.743,744.839v-86.66l-52.236,41.68L113.743,744.839"/>
                <path id="path74" fill="none" stroke="#FFFFFF" stroke-width="1.9" d="M113.743,744.839v-86.66l-52.236,41.68L113.743,744.839z"/>
            </g>
        </defs>
    </svg>
</div>'