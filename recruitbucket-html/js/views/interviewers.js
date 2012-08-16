define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/interviewers.html',
    'views/interviewers/interviewer-list'
], function($, _, Backbone, GlobalEvents, Template) {

    var InterviewerListView = require('views/interviewers/interviewer-list');

    var SUBVIEWS = [
        new InterviewerListView()
    ];

    var View = Backbone.View.extend({
        selector : "#body",

        template : _.template(Template),

        render : function() {
            $(this.selector).html(this.$el.html(Template));

            _.each(SUBVIEWS, function(view) {
                view.render();
            });

            GlobalEvents.trigger('render:sidebar', 'interviewers');
        }
    });

    return new View();
});