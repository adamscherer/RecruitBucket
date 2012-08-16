define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'views/modals/add-recruit',
    'collections/recruits',
    'text!templates/main/top-recruits.html'
], function($, _, Backbone, GlobalEvents, AddRecruitModal, RecruitsCollection, template) {

    var View = Backbone.View.extend({
        selector : "#top-recruits-widget",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render", "displayRecruits");

            // Once the collection is fetched re-render the view
            RecruitsCollection.bind("reset", this.displayRecruits);
        },

        events : {
            "click .x-add-recruit" : "showAdd"
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        },

        render : function() {
            RecruitsCollection.fetch();
        },

        displayRecruits : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    recruits : RecruitsCollection.toJSON()
                }
            })));
            
            this.delegateEvents();
        }
    });

    return View;
});