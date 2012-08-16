define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/reviews',
    'text!templates/recruit/reviews.html'
], function($, _, Backbone, ReviewsCollection, template) {

    var View = Backbone.View.extend({
        selector : "#reviews",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            ReviewsCollection.bind("reset", this.display);
        },
        render : function(recruit) {
            //if (Statistics.stages()) {
                //this.displayStatus();
            //} else {
            this.model = recruit;
            ReviewsCollection.fetch({data : {recruitId : this.model.id}});
            //}
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    reviews : ReviewsCollection.toJSON(),
                    recruit : this.model
                }
            })));
        }
    });

    return View;
});