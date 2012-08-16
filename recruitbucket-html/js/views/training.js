define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/training.html',
    'views/training/questions-list'
], function($, _, Backbone, GlobalEvents, Template) {

    var QuestionsListView = require('views/training/questions-list');

    var SUBVIEWS = [
        new QuestionsListView()
    ];

    var View = Backbone.View.extend({
        selector : "#body",

        template : _.template(Template),

        render : function() {
            $(this.selector).html(this.$el.html(Template));

            _.each(SUBVIEWS, function(view) {
                view.render();
            });

            GlobalEvents.trigger('render:sidebar', 'training');
        }
    });

    return new View();
});