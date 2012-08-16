define([
    'require',
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/main.html',
    'views/main/status',
    'views/main/top-recruits',
    'views/main/activity',
    'views/main/recruit-statistics',
    'views/main/interviewer-statistics'
], function(require, $, _, Backbone, GlobalEvents, MainTemplate) {

    var StatusView = require('views/main/status');
    var TopRecruitsView = require('views/main/top-recruits');
    var ActivityView = require('views/main/activity');
    var RecruitStatisticsView = require('views/main/recruit-statistics');
    var InterviewerStatisticsView = require('views/main/interviewer-statistics');

    var SUBVIEWS = [
        new StatusView(),
        new TopRecruitsView(),
        new RecruitStatisticsView(),
        new ActivityView(),
        new InterviewerStatisticsView()
    ];

    var View = Backbone.View.extend({
        initialize : function() {
            _.bindAll(this, "render");
        },
        render : function(el) {
            el.html(MainTemplate);

            _.each(SUBVIEWS, function(view) {
                view.render();
            });
        }
    });

    return View;
});