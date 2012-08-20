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
            _.bindAll(this, "render", "display", "destroy");

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
            "click .x-add-recruit" : "showAdd",
            "click .delete" : "destroy"
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        },

        destroy : function(ev) {
            var id = $(ev.target).parents('td').data('id');
            var recruit = RecruitsCollection.get(id);
            if (recruit) {
                recruit.destroy({success : _.bind(function() {
                    this.render();
                }, this)});
            }

            return false;
        }
    });

    return View;
});
