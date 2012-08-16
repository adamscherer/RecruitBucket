define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/questions',
    'text!templates/training/questions-list.html',
    'views/modals/question',
], function($, _, Backbone, QuestionsCollection, template) {

    var QuestionModal = require('views/modals/question');
    
    var View = Backbone.View.extend({
        selector : "#questions-list-widget",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            QuestionsCollection.bind("reset", this.display);
        },
        render : function() {
            QuestionsCollection.fetch();
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    questions : QuestionsCollection.toJSON()
                }
            })));
            
            this.delegateEvents();
        },

        events : {
            "click .x-add-question" : "showAdd"
        },

        showAdd : function(ev) {
            QuestionModal.render();

            return false;
        }
    });

    return View;

});