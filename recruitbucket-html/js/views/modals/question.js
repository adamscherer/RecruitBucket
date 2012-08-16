define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/question',
    'text!templates/modals/question-modal.html'
], function($, _, Backbone, GlobalEvents, QuestionModel, template) {

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'render');

            $('#modals').append(this.$el);
        },

        render : function() {
            this.$el.html(this.template({}));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : true
            });
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        },
        
        events : {
            "submit form" : "save"
        },

        save : function(ev) {
            this.errorMessage.addClass('hide');
            var creds = $(ev.currentTarget).serializeObject();
            var model = new QuestionModel();
            model.set(creds);
            model.save();

            return false;
        }
    });

    // Return an instantiate view to ensure there is only one per page.
    return new View();
});