define([
    'jQuery', 'Underscore', 'Backbone', 'models/statistic', 'text!templates/main/status.html'
], function($, _, Backbone, Statistics, template) {

    var View = Backbone.View.extend({
        selector : "#status-section",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render", "displayStatus");

            // Once the collection is fetched re-render the view
            Statistics.bind("change", this.displayStatus);
        },
        render : function() {
            if (Statistics.stages()) {
                this.displayStatus();
            } else {
                Statistics.fetch();
            }
        },
        displayStatus : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : Statistics.stages()
            })));
            
            this.$el.tooltip({
                selector: "div[rel=tooltip]"
            });
        }
    });

    return View;
});