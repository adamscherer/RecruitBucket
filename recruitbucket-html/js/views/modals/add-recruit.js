define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/modals/add-recruit-modal.html',
    'jQuery_chosen'
], function($, _, Backbone, GlobalEvents, template) {

    // Since this data is submitted to an IFRAME, we are adding an accessible
    // callback function.
    var callbackFunctionName = 'addRecruitCallback';
    var callback = function(data) {
        instance.hideModal();
        Backbone.history.navigate('recruit/' + data.id, {
            trigger : true
        });
    };

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'render');

            $('#modals').append(this.$el);
            window[callbackFunctionName] = callback;
        },

        render : function() {
            this.$el.html(this.template({}));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : true
            });

            $(".chzn-select").chosen();
            $(".chzn-select-deselect").chosen({
                allow_single_deselect : true
            });
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        }
    });

    // Return an instantiate view to ensure there is only one per page.
    var instance = new View();

    return instance;
});