define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/documents',
    'text!templates/recruit/documents.html'
], function($, _, Backbone, DocumentsCollection, template) {

    var View = Backbone.View.extend({
        selector : "#documents",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            DocumentsCollection.bind("reset", this.display);
        },
        render : function(recruit) {
            //if (Statistics.stages()) {
                //this.displayStatus();
            //} else {
            this.model = recruit;
            DocumentsCollection.fetch({data : {recruitId : this.model.id}});
            //}
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    documents : DocumentsCollection.toJSON(),
                    recruit : this.model
                }
            })));
        }
    });

    return View;
});