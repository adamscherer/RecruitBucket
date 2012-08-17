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

        template : _.template(Template),

        render : function() {
            this.$el.html(this.template({

            }));

            _.each(SUBVIEWS, function(view) {
                view.render();
            });

        }
    });

    return View;
});