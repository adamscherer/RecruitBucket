define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/recruits',
    'text!templates/recruits/recruit-list.html',
    'views/modals/add-recruit',
], function($, _, Backbone, RecruitsCollection, template, AddRecruitModal) {

    var View = Backbone.View.extend({
        selector : "#recruit-list-widget",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            RecruitsCollection.bind("reset", this.display);
        },
        render : function() {
            RecruitsCollection.fetch();
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    recruits : RecruitsCollection.toJSON()
                }
            })));
            
            this.delegateEvents();
        },
        events : {
            "click .x-add-recruit" : "showAdd"
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        },
    });

    return View;

});