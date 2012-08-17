define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/recruits.html',
    'collections/recruits',
    'views/modals/add-recruit'
], function($, _, Backbone, GlobalEvents, Template, RecruitsCollection, AddRecruitModal) {

    var View = Backbone.View.extend({
        template : _.template(Template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            RecruitsCollection.bind("reset", this.display);
        },

        render : function() {
            RecruitsCollection.fetch();
        },

        display : function() {
            console.log('render recruits');
            this.$el.html(this.template({
                data : {
                    recruits : RecruitsCollection.toJSON()
                }
            }));
        },

        events : {
            "click .x-add-recruit" : "showAdd"
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        }
    });

    return View;
});
