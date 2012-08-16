define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/activities',
    'text!templates/recruit/bucket-log.html'
], function($, _, Backbone, ActivitiesCollection, template) {

    var View = Backbone.View.extend({
        selector : "#bucket-log",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            ActivitiesCollection.bind("reset", this.display);
        },
        render : function(recruit) {
            //if (Statistics.stages()) {
                //this.displayStatus();
            //} else {
            this.model = recruit;
            ActivitiesCollection.fetch({data : {recruitId : this.model.id}});
            //}
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    activities : ActivitiesCollection.toJSON(),
                    recruit : this.model
                }
            })));
        }
    });

    return View;
});