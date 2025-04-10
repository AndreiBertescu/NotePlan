/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 94.0, "KoPercent": 6.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.94, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Submit logout-1"], "isController": false}, {"data": [1.0, 500, 1500, "Submit logout-0"], "isController": false}, {"data": [1.0, 500, 1500, "Submit logout"], "isController": false}, {"data": [1.0, 500, 1500, "Get login page"], "isController": false}, {"data": [1.0, 500, 1500, "Create notes"], "isController": false}, {"data": [1.0, 500, 1500, "Create notes-0"], "isController": false}, {"data": [1.0, 500, 1500, "Create notes-1"], "isController": false}, {"data": [1.0, 500, 1500, "Submit login-0"], "isController": false}, {"data": [0.4, 500, 1500, "Submit login"], "isController": false}, {"data": [1.0, 500, 1500, "Submit login-1"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 50, 3, 6.0, 69.0, 2, 404, 33.5, 242.59999999999994, 360.84999999999997, 404.0, 47.52851711026616, 439.16071322480985, 19.503401259505704], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Submit logout-1", 5, 0, 0.0, 6.2, 5, 8, 6.0, 8.0, 8.0, 8.0, 9.900990099009901, 64.82054455445544, 1.7017326732673268], "isController": false}, {"data": ["Submit logout-0", 5, 0, 0.0, 2.6, 2, 3, 3.0, 3.0, 3.0, 3.0, 9.9601593625498, 5.943024775896414, 4.075494895418327], "isController": false}, {"data": ["Submit logout", 5, 0, 0.0, 9.0, 7, 12, 9.0, 12.0, 12.0, 12.0, 9.84251968503937, 70.31057763287401, 5.719042199803149], "isController": false}, {"data": ["Get login page", 5, 0, 0.0, 21.0, 6, 77, 8.0, 77.0, 77.0, 77.0, 6.944444444444444, 18.46652560763889, 0.8680555555555556], "isController": false}, {"data": ["Create notes", 5, 0, 0.0, 60.6, 54, 71, 56.0, 71.0, 71.0, 71.0, 9.057971014492754, 171.63439764492753, 7.306527400362318], "isController": false}, {"data": ["Create notes-0", 5, 0, 0.0, 17.4, 12, 19, 19.0, 19.0, 19.0, 19.0, 10.02004008016032, 2.915988226452906, 5.792835671342686], "isController": false}, {"data": ["Create notes-1", 5, 0, 0.0, 43.0, 35, 53, 37.0, 53.0, 53.0, 53.0, 9.380863039399626, 175.02271927767353, 2.1436737804878048], "isController": false}, {"data": ["Submit login-0", 5, 0, 0.0, 220.8, 109, 355, 203.0, 355.0, 355.0, 355.0, 5.945303210463734, 3.0249052467300834, 2.502368831747919], "isController": false}, {"data": ["Submit login", 5, 3, 60.0, 265.4, 141, 404, 247.0, 404.0, 404.0, 404.0, 5.649717514124294, 106.09110169491525, 3.3986581920903953], "isController": false}, {"data": ["Submit login-1", 5, 0, 0.0, 44.0, 32, 49, 48.0, 49.0, 49.0, 49.0, 9.433962264150942, 172.3522258254717, 1.7043779481132075], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 247 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 33.333333333333336, 2.0], "isController": false}, {"data": ["The operation lasted too long: It took 404 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 33.333333333333336, 2.0], "isController": false}, {"data": ["The operation lasted too long: It took 368 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 33.333333333333336, 2.0], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 50, 3, "The operation lasted too long: It took 247 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 404 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 368 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Submit login", 5, 3, "The operation lasted too long: It took 247 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 404 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 368 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "", "", "", ""], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
