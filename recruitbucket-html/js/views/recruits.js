define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/recruits.html',
    'views/recruits/recruit-list'
], function($, _, Backbone, GlobalEvents, Template) {

    var RecruitListView = require('views/recruits/recruit-list');

    var SUBVIEWS = [
        new RecruitListView()
    ];

    var View = Backbone.View.extend({
        template : _.template(Template),

        render : function() {

            console.log('render recruits');
            this.$el.html(this.template({

            }));

            _.each(SUBVIEWS, _.bind(function(view) {
                view.render(this.model);
            }, this));

        }
    });

    return View;
});
